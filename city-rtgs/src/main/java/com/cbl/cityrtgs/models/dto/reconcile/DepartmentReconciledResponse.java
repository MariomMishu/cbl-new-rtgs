package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.Date;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DepartmentReconciledResponse {
    private String departmentName;
    private String deptGlAcc;
    private String deptChargeGlAcc;
    private String deptVatGlAcc;
    private String currency;
    private String routingType;
    private String voucherNumber;
    private String vatVoucherNumber;
    private String chargeVoucherNumber;
    private Date reconcileSettlementDate;
    private Date chargeReconcileSetDate;
    private Date vatReconcileSetDate;
}
