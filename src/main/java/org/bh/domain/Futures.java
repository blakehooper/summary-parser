package org.bh.domain;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class Futures {
    private Set<Client> clients;
}
