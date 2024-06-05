package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class SettlementAccountStatementSearchFilter {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private String currencyCode;
    private String accountNumber;
    private Boolean reconciled;
    private LocalDateTime createDate;
    private BigDecimal settlementAmount;
}
