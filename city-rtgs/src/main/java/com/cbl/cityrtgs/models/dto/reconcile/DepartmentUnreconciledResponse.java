package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DepartmentUnreconciledResponse {
    private Long departmentAccId;
    private String departmentName;
    private String deptGlAcc;
    private String deptChargeGlAcc;
    private String deptVatGlAcc;
    private String currency;
    private String routingType;
    private Double amount;
    private Double charge;
    private Double vat;
    private Double totalAmount;
    private Double mismatchAmount;
    private Boolean reconcileEnable;
    private Boolean vatReconcile;
    private Boolean chargeReconcile;
}
