package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.rights.RightsResponse;
import com.cbl.cityrtgs.models.dto.configuration.role.RoleResponse;
import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserInfoRequest;
import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserInfoResponse;
import com.cbl.cityrtgs.models.dto.menu.response.GroupMenuResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.models.entitymodels.menu.GroupMenu;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.menu.GroupMenuRepository;
import com.cbl.cityrtgs.services.user.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserInfoMapper {

    private final RoleService roleService;
    private final GroupMenuRepository groupMenuRepository;

    public UserInfoResponse entityToDomain(UserInfoEntity entity) {

        GroupMenuResponse menuAccess = null;

        Optional<GroupMenu> optional = groupMenuRepository.findGroupMenuByUserId(entity.getId());

        if(optional.isPresent()){
            menuAccess = GroupMenuResponse.toDTO(optional.get());
        }

        UserInfoResponse response = new UserInfoResponse();
        Set<RoleResponse> roles = new HashSet<>();
        Set<RightsResponse> rights = new HashSet<>();

        entity.getRoles().forEach(role -> {

            if(role != null){

                RoleEntity userRole = roleService.getRoleById(role.getId());

                if(userRole != null){

                    userRole.getRights().forEach(right -> {

                        if(right != null){
                            rights.add(RightsResponse.toDTO(right));
                        }
                    });

                    roles.add(RoleResponse.toDTO(role, rights));
                }
            }
        });

        response
                .setUsername(entity.getUsername())
                .setFullName(entity.getFullName())
                .setPhoneNo(entity.getPhoneNo())
                .setCellNo(entity.getCellNo())
                .setEmailAddr(entity.getEmailAddr())
                .setBranchId(entity.getBranch() != null ? entity.getBranch().getId() : null)
                .setBranchName(entity.getBranch() != null ? entity.getBranch().getName() : null)
                .setDeptId(entity.getDept() != null ? entity.getDept().getId() : null)
                .setDeptName(entity.getDept() != null ? entity.getDept().getName() : null)
                .setEmployeeId(entity.getEmployeeId())
                .setRecStatus(entity.getRecStatus().name())
                .setUsername(entity.getUsername())
                .setUserLevel(entity.getUserLevel())
                .setProfileId(entity.getProfile() != null ? entity.getProfile().getId() : null)
                .setProfileName(entity.getProfile() != null ? entity.getProfile().getName() : null)
                .setRolesResponses(roles)
                .setId(entity.getId())
                .setCreatedBy(entity.getCreatedBy())
                .setUpdatedBy(entity.getUpdatedBy())
                .setMenuAccess(menuAccess);

        return response;
    }

    public UserInfoEntity domainToEntity(UserInfoRequest domain) {

        UserInfoEntity entity= null ;
        entity
                .setFullName(domain.getFullName())
                .setPhoneNo(domain.getPhoneNo())
                .setCellNo(domain.getCellNo())
                .setEmailAddr(domain.getEmailAddress())
                .setEmployeeId(domain.getEmployeeId())
                .setRecStatus(domain.getRecStatus())
                .setUsername(domain.getUsername())
                .setUserLevel(domain.getUserLevel());
        return entity;
    }
}
