package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.userAud.UserAud;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ProfileEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserAudEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserAudMapper {

    public UserAud entityToDomain(UserAudEntity entity) {
        UserAud response = new UserAud();
        response
                .setRev(entity.getRev())
                .setRevType(entity.getRevType())
                .setUsername(entity.getUsername())
                .setFullName(entity.getFullName())
                .setPhone_No(entity.getPhone_No())
                .setCell_No(entity.getCell_No())
                .setEmailAddr(entity.getEmailAddr())
                .setBranchId(entity.getBranch().getId())
                .setDeptId(entity.getDept().getId())
                .setEmployeeId(entity.getEmployeeId())
                .setRecStatus(entity.getRecStatus())
                .setUsername(entity.getUsername())
              //  .setMenuGroupId(entity.getMenuGroup().getId())
                .setProfileId(entity.getProfile().getId())
                .setId(entity.getId());
        return response;
    }

    public UserAudEntity domainToEntity(UserAud domain) {
        UserAudEntity entity = new UserAudEntity();
        BranchEntity branchEntity = new BranchEntity().setId(domain.getBranchId());
        DepartmentEntity deptEntity = new DepartmentEntity().setId(domain.getDeptId());
        ProfileEntity profileEntity = new ProfileEntity().setId(domain.getProfileId());
        entity
                .setRev(domain.getRev())
                .setRevType(domain.getRevType())
                .setFullName(domain.getFullName())
                .setPhone_No(domain.getPhone_No())
                .setCell_No(domain.getCell_No())
                .setEmailAddr(domain.getEmailAddr())
                .setBranch(branchEntity)
                .setDept(deptEntity)
                .setEmployeeId(domain.getEmployeeId())
                .setRecStatus(domain.getRecStatus())
                .setUsername(domain.getUsername())
             //   .setMenuGroup(menuGroupEntity)
                .setProfile(profileEntity);
        return entity;
    }

    public UserAudEntity entityToAudEntity(UserInfoEntity entity, int revType) {
        UserAudEntity audEntity = new UserAudEntity();
        BeanUtils.copyProperties(entity, audEntity);
        audEntity
                .setUserId(entity.getId())
                .setRev(entity.getId())
                .setRevType(revType);
        return audEntity;
    }
}
