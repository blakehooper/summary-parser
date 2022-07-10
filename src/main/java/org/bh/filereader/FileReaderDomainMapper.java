package org.bh.filereader;

import static org.bh.utility.MapperHelper.nullSafeTrim;

import lombok.RequiredArgsConstructor;
import org.bh.domain.Client;
import org.bh.domain.FuturesTransaction;
import org.bh.domain.Product;
import org.bh.filereader.model.Line;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class FileReaderDomainMapper {
    public Client mapToClient(Line line) {
        return Client.builder()
                .clientType(nullSafeTrim(line.getClientType()))
                .clientNumber(nullSafeTrim(line.getClientNumber()))
                .accountNumber(nullSafeTrim(line.getAccountNumber()))
                .subAccountNumber(nullSafeTrim(line.getSubAccountNumber()))
                .products(new HashSet<>())
                .build();
    }
    public Product mapToProduct(Line line) {
        return Product.builder()
                .productGroupCode(nullSafeTrim(line.getProductGroupCode()))
                .exchangeCode(nullSafeTrim(line.getExchangeCode()))
                .symbol(nullSafeTrim(line.getSymbol()))
                .transactions(new ArrayList<>())
                .build();
    }
    public FuturesTransaction mapToFutureTransaction(Line line) {
        return FuturesTransaction.builder()
                .numberLong(Integer.valueOf(line.getQuantityLong()))
                .numberShort(Integer.valueOf(line.getQuantityShort()))
                .build();
    }
}
