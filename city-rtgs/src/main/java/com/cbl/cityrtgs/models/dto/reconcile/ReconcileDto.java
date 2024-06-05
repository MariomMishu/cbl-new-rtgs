package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
public class ReconcileDto {
    private String accountNumber;
    private String currencyCode;
    private BigDecimal centralBankClosingBalance;
    private BigDecimal settlementAccountBalance=BigDecimal.ZERO;
    private LocalDateTime createDate;
    private LocalDateTime createTime;

    public ReconcileDto(String accountNumber, String currencyCode, BigDecimal centralBankClosingBalance, BigDecimal settlementAccountBalance, LocalDateTime createDate, LocalDateTime createTime) {
        this.accountNumber = accountNumber;
        this.currencyCode = currencyCode;
        this.centralBankClosingBalance = centralBankClosingBalance;
        this.settlementAccountBalance = settlementAccountBalance;
        this.createDate = createDate;
        this.createTime = createTime;
    }
}
