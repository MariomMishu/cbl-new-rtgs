package com.cbl.cityrtgs.models.dto.configuration.departmentaccount;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DepartmentAccountResponse {

    private Long id;

    private Long deptId;

    private String deptName;

    private String accountNumber;

    private String chargeAccNumber;

    private String vatAccNumber;

    private Long currencyId;

    private String currencyName;

    private RoutingType routingType;

    private boolean reconcile;
}
