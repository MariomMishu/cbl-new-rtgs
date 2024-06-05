package com.cbl.cityrtgs.models.dto.configuration.branch;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class BranchRequest {

    @NotBlank(message = "Branch name is mandatory")
    private String name;

    private String address1;

    private String address2;

    private String address3;

    @Size(min = 9, max = 9, message = "Routing Number must be 9 characters long")
    private String routingNumber;

    private String cbsBranchId;

    private Boolean rtgsBranch;

    private Boolean treasuryBranch;

    @NotNull(message = "Bank is mandatory")
    private Long bankId;
}
