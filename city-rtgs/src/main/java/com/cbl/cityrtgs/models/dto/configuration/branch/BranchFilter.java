package com.cbl.cityrtgs.models.dto.configuration.branch;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class BranchFilter {

    private Long bankId;

    private String name;

    private String address;

    private String routingNumber;

    private String bankName;

}
