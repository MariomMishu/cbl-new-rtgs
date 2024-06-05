package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import lombok.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoRequest {

    private String fullName;
    private String phoneNo;
    private String cellNo;
    private String emailAddress;
    private Long branchId;
    private Long deptId;
    private String employeeId;
    private UserStatus recStatus;
    private String username;
    private String password;
    private int userLevel;
    private Set<Long> roleIds;
    private Long profileId;
    private Long groupId;

    public static UserInfoEntity toMODEL(UserInfoEntity entity,
                                         Set<RoleEntity> roles,
                                         ProfileEntity profile,
                                         GroupAccess groupAccess,
                                         DepartmentEntity department,
                                         BranchEntity branch,
                                         UserInfoRequest request){

        entity.setRoles(roles);
        entity.setFullName(request.getFullName());
        entity.setPhoneNo(request.getPhoneNo());
        entity.setCellNo(request.getCellNo());
        entity.setEmailAddr(request.getEmailAddress());
        entity.setBranch(branch);
        entity.setEmployeeId(request.getEmployeeId());
        entity.setRecStatus(request.getRecStatus());
        entity.setUserLevel(request.getUserLevel());
        entity.setUpdatedBy(LoggedInUserDetails.getCreatorName());
        entity.setUpdatedAt(new Date());
        entity.setGroupAccess(groupAccess);
        entity.setDept(department);
        entity.setProfile(profile);
        entity.setRoles(roles);;

        return entity;
    }

    public static UserInfoEntity toMODEL(UserInfoRequest request,
                                         Set<RoleEntity> roles,
                                         ProfileEntity profile,
                                         GroupAccess groupAccess,
                                         DepartmentEntity department,
                                         BranchEntity branch){

        return UserInfoEntity.builder()
                .activated(false)
                .fullName(request.getFullName().trim())
                .phoneNo(request.getPhoneNo())
                .cellNo(request.getCellNo())
                .emailAddr(request.getEmailAddress())
                .branch(branch)
                .dept(department)
                .employeeId(request.getEmployeeId())
                .recStatus(UserStatus.InActive)
                .username(request.getUsername().trim())
                .password(request.getPassword())
                .userLevel(request.getUserLevel())
                .profile(profile)
                .roles(roles)
                .createdBy(LoggedInUserDetails.getCreatorName())
                .createdAt(new Date())
                .groupAccess(groupAccess)
                .build();
    }
}
