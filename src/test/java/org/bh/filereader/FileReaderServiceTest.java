package org.bh.filereader;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.bh.domain.Client;
import org.bh.domain.FuturesTransaction;
import org.bh.domain.Product;
import org.bh.filereader.model.Line;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.opentest4j.AssertionFailedError;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ExtendWith(MockitoExtension.class)
public class FileReaderServiceTest {

    FileReaderService fileReaderService;

    @Mock
    TransactionFileReader transactionFileReader;

    @Mock
    FileReaderDomainMapper fileReaderDomainMapper;

    @BeforeEach
    void before() {
        fileReaderService = new FileReaderService(transactionFileReader, fileReaderDomainMapper);
    }

    @Test
    void testOneClientOneProductOneTransaction() {
        // Given (data)
        List<Line> testLines = List.of(buildLine("10"));

        String testProductGroupCode = "Test Product";
        Product testProduct = buildProduct(testProductGroupCode);

        String testClientAccountNumber = "Test Client";
        Client testClient = buildClient(testClientAccountNumber);

        int numberLong = 10;
        FuturesTransaction futuresTransaction = buildFuturesTransaction(numberLong, 0);

        // And (interactions)
        when(transactionFileReader.readFile()).thenReturn(testLines);

        when(fileReaderDomainMapper.mapToClient(any(Line.class))).thenReturn(testClient);
        when(fileReaderDomainMapper.mapToProduct(any(Line.class))).thenReturn(testProduct);
        when(fileReaderDomainMapper.mapToFutureTransaction(any(Line.class))).thenReturn(futuresTransaction);

        // When (action)
        var testResult = fileReaderService.buildModel();

        // Then (results)
        assertNotNull(testResult);
        assertNotNull(testResult.getClients());
        assertEquals(1, testResult.getClients().size());
        assertEquals(Set.of(testClient), testResult.getClients());

        var clientResult = testResult.getClients().stream()
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(testClientAccountNumber, clientResult.getAccountNumber());

        var productResult = clientResult.getProducts().stream()
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(testProductGroupCode, productResult.getProductGroupCode());

        var transactionResult = productResult.getTransactions().stream()
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(numberLong, transactionResult.getNumberLong());

        // And (verifications)
        verify(transactionFileReader).readFile();
        verify(fileReaderDomainMapper).mapToClient(eq(testLines.get(0)));
        verify(fileReaderDomainMapper).mapToProduct(eq(testLines.get(0)));
        verify(fileReaderDomainMapper).mapToFutureTransaction(eq(testLines.get(0)));
    }

    @Test
    void testOneClientOneProductMulTransactions() {
        // Given (data)
        List<Line> testLines = List.of(
                buildLine("10"),
                buildLine("20"),
                buildLine("30")
        );

        String testProductGroupCode = "Test Product";
        Product testProduct = buildProduct(testProductGroupCode);

        String testClientAccountNumber = "Test Client";
        Client testClient = buildClient(testClientAccountNumber);

        int firstTransactionLong = 10;
        int secondTransactionShort = 20;
        int thirdTransactionShort = 30;
        FuturesTransaction futuresTransaction1 = buildFuturesTransaction(firstTransactionLong, 0);
        FuturesTransaction futuresTransaction2 = buildFuturesTransaction(0, secondTransactionShort);
        FuturesTransaction futuresTransaction3 = buildFuturesTransaction(0, thirdTransactionShort);

        // And (interactions)
        when(transactionFileReader.readFile()).thenReturn(testLines);

        when(fileReaderDomainMapper.mapToClient(any(Line.class))).thenReturn(testClient);
        when(fileReaderDomainMapper.mapToProduct(any(Line.class))).thenReturn(testProduct);
        when(fileReaderDomainMapper.mapToFutureTransaction(any(Line.class)))
                .thenReturn(futuresTransaction1)
                .thenReturn(futuresTransaction2)
                .thenReturn(futuresTransaction3)
        ;

        // When (action)
        var testResult = fileReaderService.buildModel();

        // Then (results)
        assertNotNull(testResult);
        assertNotNull(testResult.getClients());
        assertEquals(1, testResult.getClients().size());
        assertEquals(Set.of(testClient), testResult.getClients());

        var clientResult = testResult.getClients().stream()
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(testClientAccountNumber, clientResult.getAccountNumber());

        var productResult = clientResult.getProducts().stream()
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(testProductGroupCode, productResult.getProductGroupCode());

        assertEquals(List.of(futuresTransaction1, futuresTransaction2, futuresTransaction3), productResult.getTransactions());

        // And (verifications)
        verify(transactionFileReader).readFile();
        verify(fileReaderDomainMapper, times(3)).mapToClient(any(Line.class));
        verify(fileReaderDomainMapper, times(3)).mapToProduct(any(Line.class));
        verify(fileReaderDomainMapper, times(3)).mapToFutureTransaction(any(Line.class));
    }


    @Test
    void testMultipleClientsMultipleProductsMultipleTransactions() {
        // Given (data)
        List<Line> testLines = List.of(
                buildLine("10"),
                buildLine("20"),
                buildLine("30"),
                buildLine("40"),
                buildLine("50")
        );

        String testProductGroupCode1 = "Test Product1";
        Product testClient1Product1 = buildProduct(testProductGroupCode1);
        Product testClient2Product1 = buildProduct(testProductGroupCode1);

        String testProductGroupCode2 = "Test Product2";
        Product testClient2Product2 = buildProduct(testProductGroupCode2);

        String testClientAccountNumber1 = "Test Client1";
        Client testClient1 = buildClient(testClientAccountNumber1);
        String testClientAccountNumber2 = "Test Client2";
        Client testClient2 = buildClient(testClientAccountNumber2);

        int firstTransactionLong = 10;
        int secondTransactionShort = 20;
        int thirdTransactionShort = 30;
        int forthTransactionLong = 20;
        int fifthTransactionLong = 30;
        FuturesTransaction futuresTransaction1 = buildFuturesTransaction(firstTransactionLong, 0);
        FuturesTransaction futuresTransaction2 = buildFuturesTransaction(0, secondTransactionShort);
        FuturesTransaction futuresTransaction3 = buildFuturesTransaction(0, thirdTransactionShort);
        FuturesTransaction futuresTransaction4 = buildFuturesTransaction(forthTransactionLong, 0);
        FuturesTransaction futuresTransaction5 = buildFuturesTransaction(fifthTransactionLong, 0);

        // And (interactions)
        when(transactionFileReader.readFile()).thenReturn(testLines);

        var lineNumber = 0;
        when(fileReaderDomainMapper.mapToClient(eq(testLines.get(lineNumber)))).thenReturn(testClient1);
        when(fileReaderDomainMapper.mapToProduct(eq(testLines.get(lineNumber)))).thenReturn(testClient1Product1);
        when(fileReaderDomainMapper.mapToFutureTransaction(eq(testLines.get(lineNumber)))).thenReturn(futuresTransaction1);

        // line++;
        lineNumber = 1;
        when(fileReaderDomainMapper.mapToClient(eq(testLines.get(lineNumber)))).thenReturn(testClient1);
        when(fileReaderDomainMapper.mapToProduct(eq(testLines.get(lineNumber)))).thenReturn(testClient1Product1);
        when(fileReaderDomainMapper.mapToFutureTransaction(eq(testLines.get(lineNumber)))).thenReturn(futuresTransaction2);

        lineNumber = 2;
        when(fileReaderDomainMapper.mapToClient(eq(testLines.get(lineNumber)))).thenReturn(testClient2);
        when(fileReaderDomainMapper.mapToProduct(eq(testLines.get(lineNumber)))).thenReturn(testClient2Product1);
        when(fileReaderDomainMapper.mapToFutureTransaction(eq(testLines.get(lineNumber)))).thenReturn(futuresTransaction3);

        lineNumber = 3;
        when(fileReaderDomainMapper.mapToClient(eq(testLines.get(lineNumber)))).thenReturn(testClient2);
        when(fileReaderDomainMapper.mapToProduct(eq(testLines.get(lineNumber)))).thenReturn(testClient2Product2);
        when(fileReaderDomainMapper.mapToFutureTransaction(eq(testLines.get(lineNumber)))).thenReturn(futuresTransaction4);

        lineNumber = 4;
        when(fileReaderDomainMapper.mapToClient(eq(testLines.get(lineNumber)))).thenReturn(testClient2);
        when(fileReaderDomainMapper.mapToProduct(eq(testLines.get(lineNumber)))).thenReturn(testClient2Product2);
        when(fileReaderDomainMapper.mapToFutureTransaction(eq(testLines.get(lineNumber)))).thenReturn(futuresTransaction5);

        // When (action)
        var testResult = fileReaderService.buildModel();

        // Then (results)
        assertNotNull(testResult);
        assertNotNull(testResult.getClients());
        assertEquals(2, testResult.getClients().size());
        assertEquals(Set.of(testClient1, testClient2), testResult.getClients());

        var client1Result = testResult.getClients().stream()
                .filter(testClient1::isMatchingClient)
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(testClientAccountNumber1, client1Result.getAccountNumber());

        assertNotNull(client1Result.getProducts());
        assertEquals(1, client1Result.getProducts().size());
        assertEquals(Set.of(testClient1Product1), client1Result.getProducts());

        var client1product1Result = client1Result.getProducts().stream()
                .filter(testClient1Product1::isMatchingProduct)
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));

        assertNotNull(client1product1Result.getTransactions());
        assertEquals(2, client1product1Result.getTransactions().size());
        assertEquals(List.of(futuresTransaction1, futuresTransaction2), client1product1Result.getTransactions());

        var client2Result = testResult.getClients().stream()
                .filter(testClient2::isMatchingClient)
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));
        assertEquals(testClientAccountNumber2, client2Result.getAccountNumber());

        assertNotNull(client2Result.getProducts());
        assertEquals(2, client2Result.getProducts().size());
        assertEquals(Set.of(testClient2Product1, testClient2Product2), client2Result.getProducts());

        var client2product1Result = client2Result.getProducts().stream()
                .filter(testClient2Product1::isMatchingProduct)
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));

        assertNotNull(client2product1Result.getTransactions());
        assertEquals(1, client2product1Result.getTransactions().size());
        assertEquals(List.of(futuresTransaction3), client2product1Result.getTransactions());

        var client2product2Result = client2Result.getProducts().stream()
                .filter(testClient2Product2::isMatchingProduct)
                .findAny()
                .orElseThrow(() -> new AssertionFailedError("empty"));

        assertNotNull(client2product2Result.getTransactions());
        assertEquals(2, client2product2Result.getTransactions().size());
        assertEquals(List.of(futuresTransaction4, futuresTransaction5), client2product2Result.getTransactions());

        // And (verifications)
        verify(transactionFileReader).readFile();
        verify(fileReaderDomainMapper, times(5)).mapToClient(any(Line.class));
        verify(fileReaderDomainMapper, times(5)).mapToProduct(any(Line.class));
        verify(fileReaderDomainMapper, times(5)).mapToFutureTransaction(any(Line.class));
    }


    // Test Data
    static Line buildLine(String accountNumber) {
        return Line.builder()
                .accountNumber(accountNumber)
                .build();
    }

    static Client buildClient(String accountNumber) {
        return Client.builder()
                .accountNumber(accountNumber)
                .products(new HashSet<>())
                .build();
    }

    static Product buildProduct(String productGroupCode) {
        return Product.builder()
                .productGroupCode(productGroupCode)
                .transactions(new ArrayList<>())
                .build();
    }

    static FuturesTransaction buildFuturesTransaction(Integer numberLong, Integer numberShort) {
        return FuturesTransaction.builder()
                .numberLong(numberLong)
                .numberShort(numberShort)
                .build();
    }
}
