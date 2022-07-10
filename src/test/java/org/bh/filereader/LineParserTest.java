package org.bh.filereader;

import static org.junit.jupiter.api.Assertions.*;

import org.bh.filereader.model.LineConfig;
import org.junit.jupiter.api.Test;

public class LineParserTest {
    LineParser lineParser = new LineParser(new LineConfig());

    @Test
    public void lineParserCanReadAllFields() {
        // Given
        var testLine = "315CL  123400030001FCC   FUCME NK.   20100910USD01B 0000000003 0000000000000000000000DUSD000000000015DUSD000000000000DUSD20100819059724      000557000092150000000             O";

        // When
        var paresdLine = lineParser.parseLine(testLine);

        // Then
        assertEquals("0003", paresdLine.getAccountNumber());
        assertEquals("20100910", paresdLine.getExpirationDate());
        assertEquals("315", paresdLine.getRecordCode());
        assertEquals("CL  ", paresdLine.getClientType());
        assertEquals("1234", paresdLine.getClientNumber());
        assertEquals("0003", paresdLine.getAccountNumber());
        assertEquals("0001", paresdLine.getSubAccountNumber());
        assertEquals("FCC   ", paresdLine.getOppositeParityCode());
        assertEquals("FU", paresdLine.getProductGroupCode());
        assertEquals("CME ", paresdLine.getExchangeCode());
        assertEquals("NK.   ", paresdLine.getSymbol());
        assertEquals("20100910", paresdLine.getExpirationDate());
        assertEquals("USD", paresdLine.getCurrencyCode());
        assertEquals("01", paresdLine.getMovementCode());
        assertEquals("B", paresdLine.getBuySellCode());
        assertEquals(" ", paresdLine.getQuantityLongSign());
        assertEquals("0000000003", paresdLine.getQuantityLong());
        assertEquals(" ", paresdLine.getQuantityShortSign());
        assertEquals("0000000000", paresdLine.getQuantityShort());
        assertEquals("000000000000", paresdLine.getExchBrokerFeeDec());
        assertEquals("D", paresdLine.getExchBrokerFeeDc());
        assertEquals("USD", paresdLine.getExchBrokerFeeCurrencyCode());
        assertEquals("000000000015", paresdLine.getClearingFeeDec());
        assertEquals("D", paresdLine.getClearingFeeDc());
        assertEquals("USD", paresdLine.getClearingFeeCurrencyCode());
        assertEquals("000000000000", paresdLine.getCommission());
        assertEquals("D", paresdLine.getCommissionDc());
        assertEquals("USD", paresdLine.getCommissionCurrencyCode());
        assertEquals("20100819", paresdLine.getTransactionDate());
        assertEquals("059724", paresdLine.getFutureReference());
        assertEquals("      ", paresdLine.getTicketNumber());
        assertEquals("000557", paresdLine.getExternalNumber());
        assertEquals("000092150000000", paresdLine.getTransactionPriceDec());
        assertEquals("      ", paresdLine.getTraderInitials());
        assertEquals("       ", paresdLine.getOppositeTraderId());
        assertEquals("O", paresdLine.getOpenCloseCode());
    }
}
