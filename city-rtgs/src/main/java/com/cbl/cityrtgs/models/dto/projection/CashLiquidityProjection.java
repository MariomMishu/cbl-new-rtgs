package com.cbl.cityrtgs.models.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface CashLiquidityProjection {

    Long getId();

    Long getRegId();

    String getAccNo();

    BigDecimal getCrAmountCcy();

    BigDecimal getDbAmountCcy();
    LocalDateTime getSettlementDate();
    LocalDateTime getTransactionDate();

    String getRefNo();

    Long getIsValid();

    String getAccId();

    String getVoucherNo();

    Long getDebitorId();

    Long getCreditorId();

    Long getDebitorBranchId();

    Long getCreditorBranchId();

    String getCounterAccNo();

    String getNarration();

    String getPostBalance();

    String getCurrency();
}
