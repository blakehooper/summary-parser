package org.bh.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FuturesTransaction {
    private final Integer numberLong;
    private final Integer numberShort;
}
