package com.cbl.cityrtgs.models.dto.reconcile;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;

import java.math.BigDecimal;

public interface IFundTransferInfo {
    RoutingType getRoutingType();

    Long getDepartmentId();

    BigDecimal getAmount();

    BigDecimal getVat();

    BigDecimal getCharge();
}
