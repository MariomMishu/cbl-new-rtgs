package com.cbl.cityrtgs.models.dto.configuration.departmentaccount;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class DepartmentAccountFilter {

    private Long deptId;

    private String dept;

    private String accountNumber;

    private String chargeAccNumber;

    private String vatAccNumber;

    private Long currencyId;

    private String currency;

    private RoutingType routingType;

}
