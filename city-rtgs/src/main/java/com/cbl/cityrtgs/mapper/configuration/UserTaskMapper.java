package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.workflow.UserTaskRequest;
import com.cbl.cityrtgs.models.dto.configuration.workflow.UserTaskResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.UserTaskEntity;
import org.springframework.stereotype.Component;

@Component
public class UserTaskMapper {
    public UserTaskResponse entityToDomain(UserTaskEntity entity) {
        UserTaskResponse response = new UserTaskResponse();
        response
                .setRightsForTask(entity.getRightsForTask())
                .setAllowMakerCheckerSame(entity.getAllowMakerCheckerSame())
                .setApproverLevelRequirement(entity.getApproverLevelRequirement())
                .setLastTask(entity.getLastTask())
                .setMakerCheckerInSameBranch(entity.getMakerCheckerInSameBranch())
                .setMakerCheckerInSameDept(entity.getMakerCheckerInSameDept())
                .setTaskName(entity.getTaskName())
                .setTaskStatus(entity.getTaskStatus())
                .setApproverBranch(entity.getApproverBranch())
                .setApproverDept(entity.getApproverDept())
                .setId(entity.getId());
        return response;
    }

    public UserTaskEntity domainToEntity(UserTaskRequest domain) {
        UserTaskEntity entity = new UserTaskEntity();
        entity
                .setRightsForTask(domain.getRightsForTask())
                .setAllowMakerCheckerSame(domain.getAllowMakerCheckerSame())
                .setApproverLevelRequirement(domain.getApproverLevelRequirement())
                .setLastTask(domain.getLastTask())
                .setMakerCheckerInSameBranch(domain.getMakerCheckerInSameBranch())
                .setMakerCheckerInSameDept(domain.getMakerCheckerInSameDept())
                .setTaskName(domain.getTaskName())
                .setTaskStatus(domain.getTaskStatus())
                .setApproverBranch(domain.getApproverBranch())
                .setApproverDept(domain.getApproverDept());
        return entity;
    }
}
