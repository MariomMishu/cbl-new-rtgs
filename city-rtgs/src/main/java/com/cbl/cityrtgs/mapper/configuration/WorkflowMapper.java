package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.workflow.WorkflowRequest;
import com.cbl.cityrtgs.models.dto.configuration.workflow.WorkflowResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.WorkflowEntity;
import org.springframework.stereotype.Component;

@Component
public class WorkflowMapper {
    public WorkflowResponse entityToDomain(WorkflowEntity entity) {
        WorkflowResponse response = new WorkflowResponse();
        response
                .setName(entity.getName())
                .setId(entity.getId());
        return response;
    }

    public WorkflowEntity domainToEntity(WorkflowRequest domain) {
        WorkflowEntity entity = new WorkflowEntity();
        entity
                .setName(domain.getName());
        return entity;
    }

//    public ObjectMapper<WorkflowRequest, WorkflowEntity> domainToEntity() {
//        return domain -> {
//            WorkflowEntity entity = new WorkflowEntity();
//            BeanUtils.copyProperties(domain, entity, "id");
//            return entity;
//        };
//    }

//    public ObjectMapper<WorkflowEntity, WorkflowResponse> entityToDomain() {
//        return entity -> {
//            WorkflowResponse response = new WorkflowResponse();
//            BeanUtils.copyProperties(entity, response);
//            response
//                    .setName(entity.getName())
//                    .setId(entity.getId());
//            return response;
//        };
//    }
}
