package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class UserInfoFilter {

    private Long deptId;

    private String deptName;

    private String fullName;

    private String username;

    private UserStatus recStatus;

    private Long branchId;

    private String branchName;

    private String createdBy;

    private String updatedBy;
}
