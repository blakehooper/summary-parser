package org.bh.filereader;

import lombok.RequiredArgsConstructor;
import org.bh.domain.Futures;
import org.bh.filereader.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileReaderService {
    private final TransactionFileReader transactionFileReader;
    private final FileReaderDomainMapper fileReaderDomainMapper;

    public Futures buildModel() {
        List<Line> lines = transactionFileReader.readFile();

        var futures = Futures.builder()
                .clients(new HashSet<>())
                .build();

        lines.forEach((line) -> {
            var builtClient = fileReaderDomainMapper.mapToClient(line);

            var client = futures.getClients().stream()
                    .filter(builtClient::isMatchingClient)
                    .findAny()
                    .orElse(null);

            if (client == null) {
                futures.getClients().add(builtClient);
                client = builtClient;
            }

            var builtProduct = fileReaderDomainMapper.mapToProduct(line);

            var product = client.getProducts().stream()
                    .filter(builtProduct::isMatchingProduct)
                    .findAny()
                    .orElse(null);

            if (product == null) {
                client.getProducts().add(builtProduct);
                product = builtProduct;
            }

            var transaction = fileReaderDomainMapper.mapToFutureTransaction(line);

            product.getTransactions().add(transaction);

        });
        return futures;
    }
}
