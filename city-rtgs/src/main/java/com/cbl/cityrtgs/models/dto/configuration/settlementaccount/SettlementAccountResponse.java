package com.cbl.cityrtgs.models.dto.configuration.settlementaccount;

import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SettlementAccountResponse {
    private Long id;

    private String code;

    private String name;

    private String accountStatus;

    private BigDecimal balanceCCY;

    private BigDecimal balanceLCY;

    private LocalDate closingDate;

    private LocalDate openDate;

    private Long accountType;

    private Long bankId;

    private String bankName;

    private Long branchId;

    private Long currencyId;

    private String currencyDescription;

    private String currencyCode;
}
