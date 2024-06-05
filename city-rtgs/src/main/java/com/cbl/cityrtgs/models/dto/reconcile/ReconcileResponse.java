package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.*;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
@Accessors(chain = true)
public class ReconcileResponse {
    private String accountNumber;
    private String currencyCode;
    private BigDecimal centralBankClosingBalance;
    private BigDecimal settlementAccountBalance;
    private BigDecimal mismatchAmount;
    private String createDate;
    private String createTime;
    private boolean reconciled;

    public ReconcileResponse(String accountNumber, String currencyCode, BigDecimal centralBankClosingBalance, BigDecimal settlementAccountBalance, Boolean reconciled, String createDate, String createTime) {
        this.accountNumber = accountNumber;
        this.currencyCode = currencyCode;
        this.centralBankClosingBalance = centralBankClosingBalance;
        this.settlementAccountBalance = settlementAccountBalance;
        this.createDate = createDate;
        this.createTime = createTime;
        if (reconciled == null) {
            this.reconciled = false;
        } else {
            this.reconciled = reconciled;
        }

        if (this.reconciled) {
            this.mismatchAmount = BigDecimal.ZERO;
        } else {
            this.mismatchAmount = settlementAccountBalance.subtract(centralBankClosingBalance);
        }

    }

}
