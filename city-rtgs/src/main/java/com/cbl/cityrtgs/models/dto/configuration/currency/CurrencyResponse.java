package com.cbl.cityrtgs.models.dto.configuration.currency;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class CurrencyResponse {
    private Long id;

    private String shortCode;

    private String description;

    private BigDecimal b2bMinAmount;

    private BigDecimal c2cMinAmount;

    private boolean b2bManualTxn;

    private boolean c2cManualTxn;
}
