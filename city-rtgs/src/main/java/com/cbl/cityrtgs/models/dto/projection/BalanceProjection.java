package com.cbl.cityrtgs.models.dto.projection;

import java.math.BigDecimal;

public interface BalanceProjection {
    BigDecimal getBalance();
    String getShortcode();
}
