package org.bh.endpoints;

import lombok.RequiredArgsConstructor;
import org.bh.domain.Futures;
import org.bh.domain.FuturesTransaction;
import org.bh.domain.Product;
import org.bh.endpoints.model.SummaryRow;
import org.bh.filereader.FileReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/summary")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class SummaryEndpoint {

    private final FileReaderService fileReaderService;

    @GetMapping()
    public Mono<List<SummaryRow>> getJsonSummary() {
        return Mono.just(
                buildRows(fileReaderService.buildModel())
        );
    }

    public List<SummaryRow> buildRows(Futures futures) {
        List<SummaryRow> summaryRows = new ArrayList<>();
        futures.getClients().forEach((client) -> buildRows(summaryRows, client.clientSummary(), client.getProducts()));
        Collections.sort(summaryRows);
        return summaryRows;
    }

    public void buildRows(List<SummaryRow> rows, String name, Set<Product> products) {
        products.forEach(
                product -> rows.add(SummaryRow.builder()
                                .clientInformation(name)
                                .productInformation(product.productSummary())
                                .totalTransactionAmount(sumTransactions(product.getTransactions()))
                                .build())
        );
    }

    public Integer sumTransactions(List<FuturesTransaction> transactions) {
        return transactions.stream()
                .mapToInt(tx -> tx.getNumberLong() - tx.getNumberShort())
                .sum();
    }
}
