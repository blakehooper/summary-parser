package org.bh.filereader.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class LineConfig {
    private final FieldConfig recordCode = FieldConfig.of("recordCode", Line.LineBuilder::recordCode, 3, 0);
    private final FieldConfig clientType = FieldConfig.of("clientType", Line.LineBuilder::clientType, 4, 3);
    private final FieldConfig clientNumber = FieldConfig.of("clientNumber", Line.LineBuilder::clientNumber, 4, 7);
    private final FieldConfig accountNumber = FieldConfig.of("accountNumber", Line.LineBuilder::accountNumber, 4, 11);
    private final FieldConfig subAccountNumber = FieldConfig.of("subAccountNumber", Line.LineBuilder::subAccountNumber, 4, 15);
    private final FieldConfig oppositeParityCode = FieldConfig.of("oppositeParityCode", Line.LineBuilder::oppositeParityCode, 6, 19);
    private final FieldConfig productGroupCode = FieldConfig.of("productGroupCode", Line.LineBuilder::productGroupCode, 2, 25);
    private final FieldConfig exchangeCode = FieldConfig.of("exchangeCode", Line.LineBuilder::exchangeCode, 4, 27);
    private final FieldConfig symbol = FieldConfig.of("symbol", Line.LineBuilder::symbol, 6, 31);
    private final FieldConfig expirationDate = FieldConfig.of("expirationDate", Line.LineBuilder::expirationDate, 8, 37);
    private final FieldConfig currencyCode = FieldConfig.of("currencyCode", Line.LineBuilder::currencyCode, 3, 45);
    private final FieldConfig movementCode = FieldConfig.of("movementCode", Line.LineBuilder::movementCode, 2, 48);
    private final FieldConfig buySellCode = FieldConfig.of("buySellCode", Line.LineBuilder::buySellCode, 1, 50);
    private final FieldConfig quantityLongSign = FieldConfig.of("quantityLongSign", Line.LineBuilder::quantityLongSign, 1, 51);
    private final FieldConfig quantityLong = FieldConfig.of("quantityLong", Line.LineBuilder::quantityLong, 10, 52);
    private final FieldConfig quantityShortSign = FieldConfig.of("quantityShortSign", Line.LineBuilder::quantityShortSign, 1, 62);
    private final FieldConfig quantityShort = FieldConfig.of("quantityShort", Line.LineBuilder::quantityShort, 10, 63);
    private final FieldConfig exchBrokerFeeDec = FieldConfig.of("exchBrokerFeeDec", Line.LineBuilder::exchBrokerFeeDec, 12, 73);
    private final FieldConfig exchBrokerFeeDc = FieldConfig.of("exchBrokerFeeDc", Line.LineBuilder::exchBrokerFeeDc, 1, 85);
    private final FieldConfig exchBrokerFeeCurrencyCode = FieldConfig.of("exchBrokerFeeCurrencyCode", Line.LineBuilder::exchBrokerFeeCurrencyCode, 3, 86);
    private final FieldConfig clearingFeeDec = FieldConfig.of("clearingFeeDec", Line.LineBuilder::clearingFeeDec, 12, 89);
    private final FieldConfig clearingFeeDc = FieldConfig.of("clearingFeeDc", Line.LineBuilder::clearingFeeDc, 1, 101);
    private final FieldConfig clearingFeeCurrencyCode = FieldConfig.of("clearingFeeCurrencyCode", Line.LineBuilder::clearingFeeCurrencyCode, 3, 102);
    private final FieldConfig commission = FieldConfig.of("commission", Line.LineBuilder::commission, 12, 105);
    private final FieldConfig commissionDc = FieldConfig.of("commissionDc", Line.LineBuilder::commissionDc, 1, 117);
    private final FieldConfig commissionCurrencyCode = FieldConfig.of("commissionCurrencyCode", Line.LineBuilder::commissionCurrencyCode, 3, 118);
    private final FieldConfig transactionDate = FieldConfig.of("transactionDate", Line.LineBuilder::transactionDate, 8, 121);
    private final FieldConfig futureReference = FieldConfig.of("futureReference", Line.LineBuilder::futureReference, 6, 129);
    private final FieldConfig ticketNumber = FieldConfig.of("ticketNumber", Line.LineBuilder::ticketNumber, 6, 135);
    private final FieldConfig externalNumber = FieldConfig.of("externalNumber", Line.LineBuilder::externalNumber, 6, 141);
    private final FieldConfig transactionPriceDec = FieldConfig.of("transactionPriceDec", Line.LineBuilder::transactionPriceDec, 15, 147);
    private final FieldConfig traderInitials = FieldConfig.of("traderInitials", Line.LineBuilder::traderInitials, 6, 162);
    private final FieldConfig oppositeTraderId = FieldConfig.of("oppositeTraderId", Line.LineBuilder::oppositeTraderId, 7, 168);
    private final FieldConfig openCloseCode = FieldConfig.of("openCloseCode", Line.LineBuilder::openCloseCode, 1, 175);

    public Set<FieldConfig> allFields() {
        return Set.of(recordCode, clientType, clientNumber, accountNumber, subAccountNumber, oppositeParityCode, productGroupCode,
                exchangeCode, symbol, expirationDate, currencyCode, movementCode, buySellCode, quantityLongSign, quantityLong,
                quantityShortSign, quantityShort, exchBrokerFeeDec, exchBrokerFeeDc, exchBrokerFeeCurrencyCode, clearingFeeDec,
                clearingFeeDc, clearingFeeCurrencyCode, commission, commissionDc, commissionCurrencyCode, transactionDate,
                futureReference, ticketNumber, externalNumber, transactionPriceDec, traderInitials, oppositeTraderId, openCloseCode
        );
    }
}
