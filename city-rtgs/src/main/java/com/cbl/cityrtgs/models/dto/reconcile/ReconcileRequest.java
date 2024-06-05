package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ReconcileRequest {

    private Long deptId;

    private long currencyId;

    private long departmentAccId;

    private ReconcileTxnType txnType;

    private List<String> accountNumber;

}
