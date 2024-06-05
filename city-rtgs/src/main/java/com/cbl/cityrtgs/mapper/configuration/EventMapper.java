package com.cbl.cityrtgs.mapper.configuration;

import com.cbl.cityrtgs.models.dto.configuration.event.EventRequest;
import com.cbl.cityrtgs.models.dto.configuration.event.EventResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.EventEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.WorkflowEntity;
import org.springframework.stereotype.Component;

@Component
public class EventMapper {
    public EventResponse entityToDomain(EventEntity entity) {
        EventResponse response = new EventResponse();
        response
                .setEventId(entity.getEventId())
                .setEventName(entity.getEventName())
                .setActionUrl(entity.getActionUrl())
                .setApprovalProcessRequired(entity.getApprovalProcessRequired())
                .setDirectPostingLimit(entity.getDirectPostingLimit())
                .setWorkflowId(entity.getWorkflow().getId())
                .setWorkflowName(entity.getWorkflow().getName())
                .setId(entity.getId());
        return response;
    }

    public EventEntity domainToEntity(EventRequest domain) {
        EventEntity entity = new EventEntity();
        WorkflowEntity workflowEntity = new WorkflowEntity().setId(domain.getWorkflowId());
        entity
                .setEventId(domain.getEventId())
                .setEventName(domain.getEventName())
                .setActionUrl(domain.getActionUrl())
                .setApprovalProcessRequired(domain.getApprovalProcessRequired())
                .setDirectPostingLimit(domain.getDirectPostingLimit())
                .setWorkflow(workflowEntity);
        return entity;
    }
}
