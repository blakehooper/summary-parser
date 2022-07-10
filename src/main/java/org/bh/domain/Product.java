package org.bh.domain;

import lombok.Builder;
import lombok.Data;

import java.util.List;

import static org.bh.utility.Comparison.compareStrings;

@Data
@Builder
public class Product {
    private final String exchangeCode;
    private final String productGroupCode;
    private final String symbol;
    private final List<FuturesTransaction> transactions;

    public String productSummary() {
        return "%s-%s-%s".formatted(exchangeCode, productGroupCode, symbol);
    }
    public boolean isMatchingProduct(Product toCompare) {
        return compareStrings(exchangeCode, toCompare.exchangeCode) &&
                compareStrings(productGroupCode, toCompare.getProductGroupCode()) &&
                compareStrings(symbol, toCompare.symbol);
    }
}
