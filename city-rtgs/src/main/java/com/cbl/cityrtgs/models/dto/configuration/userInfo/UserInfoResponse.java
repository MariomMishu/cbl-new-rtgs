package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import com.cbl.cityrtgs.models.dto.configuration.role.RoleResponse;
import com.cbl.cityrtgs.models.dto.menu.response.GroupMenuResponse;
import lombok.*;
import lombok.experimental.Accessors;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserInfoResponse {

    private Long id;

    private String fullName;

    private String phoneNo;

    private String cellNo;

    private String emailAddr;

    private Long branchId;

    private String branchName;

    private Long deptId;

    private String deptName;

    private String employeeId;

    private String recStatus;

    private String username;

    private int userLevel;

    private Set<RoleResponse> rolesResponses;

    private Long profileId;

    private String profileName;

    private String createdBy;

    private String updatedBy;

    private GroupMenuResponse menuAccess;

}
