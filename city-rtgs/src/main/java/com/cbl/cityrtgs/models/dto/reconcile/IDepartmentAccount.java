package com.cbl.cityrtgs.models.dto.reconcile;

import java.math.BigDecimal;

public interface IDepartmentAccount {
    Long getDepartmentId();
    String getRoutingType();
    BigDecimal getAmount();
    BigDecimal getCharge();
    BigDecimal getVat();


}
