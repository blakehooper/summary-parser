package org.bh.domain;

import static org.bh.utility.Comparison.compareStrings;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.Set;

@Data
@Builder
@EqualsAndHashCode
public class Client {
    private String clientType;
    private String clientNumber;
    private String accountNumber;
    private String subAccountNumber;

    private Set<Product> products;

    public String clientSummary() {
        return "%s-%s-%s-%s".formatted(clientType, clientNumber, accountNumber, subAccountNumber);
    }

    public boolean isMatchingClient(Client toCompare) {
        return compareStrings(clientType, toCompare.clientType) &&
                compareStrings(clientNumber, toCompare.clientNumber) &&
                compareStrings(accountNumber, toCompare.accountNumber) &&
                compareStrings(subAccountNumber, toCompare.subAccountNumber);
    }
}
