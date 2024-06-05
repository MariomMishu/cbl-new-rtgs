package com.cbl.cityrtgs.models.dto.transaction.b2b;

import java.math.BigDecimal;

public interface B2BProjection {

    Long getBankId();
    BigDecimal getAmount();
    String getName();
}
