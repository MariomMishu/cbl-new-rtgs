package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface SettlementStatementReport {
    String getTxnDate();

    String getSettlementAcc();

    String getSettlementName();

    String getCreditorBank();

    String getCreditorBranch();

    BigDecimal getDebitAmount();

    BigDecimal getCreditAmount();

    String getDebitorBank();

    String getDebitorBranch();

    String getReference();
    String getVoucher();

    BigDecimal getPostBalance();

    String getNarration();

}
