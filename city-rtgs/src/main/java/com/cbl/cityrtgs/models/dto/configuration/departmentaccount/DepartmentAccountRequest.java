package com.cbl.cityrtgs.models.dto.configuration.departmentaccount;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class DepartmentAccountRequest {

    @NotNull(message = "Department is mandatory")
    private Long deptId;

    private String accountNumber;

    private String chargeAccNumber;

    private String vatAccNumber;

    @NotNull(message = "Currency is mandatory")
    private Long currencyId;

    private RoutingType routingType;


}
