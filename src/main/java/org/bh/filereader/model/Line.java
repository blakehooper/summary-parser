package org.bh.filereader.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Line {
    private String recordCode;
    private String clientType;
    private String clientNumber;
    private String accountNumber;
    private String subAccountNumber;
    private String oppositeParityCode;
    private String productGroupCode;
    private String exchangeCode;
    private String symbol;
    private String expirationDate;
    private String currencyCode;
    private String movementCode;
    private String buySellCode;
    private String quantityLongSign;
    private String quantityLong;
    private String quantityShortSign;
    private String quantityShort;
    private String exchBrokerFeeDec;
    private String exchBrokerFeeDc;
    private String exchBrokerFeeCurrencyCode;
    private String clearingFeeDec;
    private String clearingFeeDc;
    private String clearingFeeCurrencyCode;
    private String commission;
    private String commissionDc;
    private String commissionCurrencyCode;
    private String transactionDate;
    private String futureReference;
    private String ticketNumber;
    private String externalNumber;
    private String transactionPriceDec;
    private String traderInitials;
    private String oppositeTraderId;
    private String openCloseCode;
}
