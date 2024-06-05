package com.cbl.cityrtgs.models.dto.configuration.branch;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class BranchResponse {
    private Long id;

    private String name;

    private String address1;

    private String address2;

    private String address3;

    private String routingNumber;

    private String cbsBranchId;

    private Boolean rtgsBranch;

    private Boolean treasuryBranch;

    private Long bankId;
}
