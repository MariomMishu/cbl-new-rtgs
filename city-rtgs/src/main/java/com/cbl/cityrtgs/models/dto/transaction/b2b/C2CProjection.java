package com.cbl.cityrtgs.models.dto.transaction.b2b;

import java.math.BigDecimal;

public interface C2CProjection {

    Long getBankId();
    BigDecimal getAmount();
    String getName();
}
