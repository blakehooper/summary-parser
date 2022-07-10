package org.bh.endpoints.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SummaryRow implements Comparable<SummaryRow> {
    private final String clientInformation;
    private final String productInformation;
    private final Integer totalTransactionAmount;


    @Override
    public int compareTo(SummaryRow o) {
        if (this.equals(o)) {
            return 0;
        }
        if (!this.clientInformation.equals(o.clientInformation)) {
            return this.clientInformation.compareTo(o.clientInformation);
        }
        return this.productInformation.compareTo(o.productInformation);
    }
}
