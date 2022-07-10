package org.bh.endpoints;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.bh.domain.Client;
import org.bh.domain.Futures;
import org.bh.domain.FuturesTransaction;
import org.bh.domain.Product;
import org.bh.endpoints.model.SummaryRow;
import org.bh.filereader.FileReaderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import reactor.test.StepVerifier;

import java.util.List;
import java.util.Set;

@SpringBootTest
public class SummaryEndpointTest {

    @MockBean
    FileReaderService fileReaderService;

    @Autowired
    SummaryEndpoint summaryEndpoint;

    @Test
    void testSingleClientSingleProductsSingleTxs() {
        // Given
        FuturesTransaction ft1 = buildFuturesTxLong(10);
        Product product1 = buildProduct("p1", List.of(ft1));

        Client client = buildClient("c1", Set.of(product1));

        var result = Futures.builder()
                .clients(Set.of(client))
                .build();

        // And
        when(fileReaderService.buildModel()).thenReturn(result);

        // When
        var summaryMono = summaryEndpoint.getJsonSummary();

        // Then
        StepVerifier.create(summaryMono)
                .assertNext(summaryRows -> {
                    assertNotNull(summaryRows);
                    assertEquals(1, summaryRows.size());
                    SummaryRow row1 = summaryRows.get(0);
                    assertEquals("c1ct-c1cn-c1an-c1san", row1.getClientInformation());
                    assertEquals("p1ec-p1gc-p1s", row1.getProductInformation());
                    assertEquals(10, row1.getTotalTransactionAmount());
                })
                .verifyComplete();

        // And
        verify(fileReaderService).buildModel();
    }

    @Test
    void testSingleClientMultipleProductsMultipleTxs() {
        // Given
        FuturesTransaction ft1 = buildFuturesTxLong(10);
        FuturesTransaction ft2 = buildFuturesTxLong(10);
        FuturesTransaction ft3 = buildFuturesTxShort(30);
        Product product1 = buildProduct("p1", List.of(ft1, ft2));
        Product product2 = buildProduct("p2", List.of(ft3));

        Client client = buildClient("c1", Set.of(product1, product2));

        var result = Futures.builder()
                .clients(Set.of(client))
                .build();

        // And
        when(fileReaderService.buildModel()).thenReturn(result);

        // When
        var summaryMono = summaryEndpoint.getJsonSummary();

        // Then
        StepVerifier.create(summaryMono)
                .assertNext(summaryRows -> {
                    assertNotNull(summaryRows);
                    assertEquals(2, summaryRows.size());
                    SummaryRow row1 = summaryRows.get(0);
                    assertEquals("c1ct-c1cn-c1an-c1san", row1.getClientInformation());
                    assertEquals("p1ec-p1gc-p1s", row1.getProductInformation());
                    assertEquals(20, row1.getTotalTransactionAmount());
                    SummaryRow row2 = summaryRows.get(1);
                    assertEquals("c1ct-c1cn-c1an-c1san", row2.getClientInformation());
                    assertEquals("p2ec-p2gc-p2s", row2.getProductInformation());
                    assertEquals(-30, row2.getTotalTransactionAmount());
                })
                .verifyComplete();

        // And
        verify(fileReaderService).buildModel();
    }

    @Test
    void testMultipleClientMultipleProductsMultipleTxs() {
        // Given
        FuturesTransaction ft1 = buildFuturesTxLong(10);
        FuturesTransaction ft2 = buildFuturesTxLong(10);
        FuturesTransaction ft3 = buildFuturesTxShort(30);
        FuturesTransaction ft4 = buildFuturesTxLong(10);
        FuturesTransaction ft5 = buildFuturesTxShort(30);

        Product client1Product1 = buildProduct("p1", List.of(ft1, ft2));
        Product client2Product1 = buildProduct("p1", List.of(ft3));
        Product client2Product2 = buildProduct("p2", List.of(ft4, ft5));

        Client client1 = buildClient("c1", Set.of(client1Product1));
        Client client2 = buildClient("c2", Set.of(client2Product1, client2Product2));

        var result = Futures.builder()
                .clients(Set.of(client1, client2))
                .build();

        // And
        when(fileReaderService.buildModel()).thenReturn(result);

        // When
        var summaryMono = summaryEndpoint.getJsonSummary();

        // Then
        StepVerifier.create(summaryMono)
                .assertNext(summaryRows -> {
                    assertNotNull(summaryRows);
                    assertEquals(3, summaryRows.size());
                    SummaryRow row1 = summaryRows.get(0);
                    assertEquals("c1ct-c1cn-c1an-c1san", row1.getClientInformation());
                    assertEquals("p1ec-p1gc-p1s", row1.getProductInformation());
                    assertEquals(20, row1.getTotalTransactionAmount());
                    SummaryRow row2 = summaryRows.get(1);
                    assertEquals("c2ct-c2cn-c2an-c2san", row2.getClientInformation());
                    assertEquals("p1ec-p1gc-p1s", row2.getProductInformation());
                    assertEquals(-30, row2.getTotalTransactionAmount());
                    SummaryRow row3 = summaryRows.get(2);
                    assertEquals("c2ct-c2cn-c2an-c2san", row3.getClientInformation());
                    assertEquals("p2ec-p2gc-p2s", row3.getProductInformation());
                    assertEquals(-20, row3.getTotalTransactionAmount());
                })
                .verifyComplete();

        // And
        verify(fileReaderService).buildModel();
    }

    public static FuturesTransaction buildFuturesTxLong(Integer numberOfTx) {
        return FuturesTransaction.builder()
                .numberLong(numberOfTx)
                .numberShort(0)
                .build();
    }
    public static FuturesTransaction buildFuturesTxShort(Integer numberOfTx) {
        return FuturesTransaction.builder()
                .numberLong(0)
                .numberShort(numberOfTx)
                .build();
    }

    public static Product buildProduct(String productPrefix, List<FuturesTransaction> txs) {
        return Product.builder()
                .productGroupCode("%sgc".formatted(productPrefix))
                .exchangeCode("%sec".formatted(productPrefix))
                .symbol("%ss".formatted(productPrefix))
                .transactions(txs)
                .build();
    }

    public static Client buildClient(String clientPrefix, Set<Product> products) {
        return Client.builder()
                .clientType("%sct".formatted(clientPrefix))
                .clientNumber("%scn".formatted(clientPrefix))
                .accountNumber("%san".formatted(clientPrefix))
                .subAccountNumber("%ssan".formatted(clientPrefix))
                .products(products)
                .build();
    }
}
