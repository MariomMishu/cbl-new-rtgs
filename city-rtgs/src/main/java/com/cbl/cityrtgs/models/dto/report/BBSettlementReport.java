package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;

public interface BBSettlementReport {
    String getCurrencyCode();

    BigDecimal getDebitAmount();

    BigDecimal getCreditAmount();

    String getTxnCode();

    String getTxnId();

    String getReference();

    String getTxnStatus();
}
