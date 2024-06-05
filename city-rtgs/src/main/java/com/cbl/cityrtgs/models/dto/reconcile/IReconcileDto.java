package com.cbl.cityrtgs.models.dto.reconcile;

import java.math.BigDecimal;

public interface IReconcileDto {
    String getAccountNumber();

    String getCurrencyCode();

    BigDecimal getCentralBankClosingBalance();

    BigDecimal getSettlementAccountBalance();

    String getCreateDate();
    String getCreateTime();

}
