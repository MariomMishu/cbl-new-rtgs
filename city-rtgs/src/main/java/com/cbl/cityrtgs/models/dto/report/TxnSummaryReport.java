package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface TxnSummaryReport {

    Date getTransactionDate();

    String getBranch();

    BigDecimal getTotalInwardCount();

    BigDecimal getTotalInwardAmount();

    BigDecimal getTotalOutwardCount();

    BigDecimal getTotalOutwardAmount();

    BigDecimal getTotalInwardReversedCount();

    BigDecimal getTotalInwardReversedAmount();

    BigDecimal getTotalOutwardReversedCount();

    BigDecimal getTotalOutwardReversedAmount();

    BigDecimal getTotalInwardPendingCount();

    BigDecimal getTotalInwardPendingAmount();

    BigDecimal getTotalOutwardPendingCount();

    BigDecimal getTotalOutwardPendingAmount();
}
