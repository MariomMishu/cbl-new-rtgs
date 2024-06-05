package com.cbl.cityrtgs.models.dto.reconcile;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DeptAccReconcileResponse {

    private List<DepartmentUnreconciledResponse> unreconciledList = new ArrayList<>();

    private List<DepartmentReconciledResponse> reconciledList = new ArrayList<>();

}
