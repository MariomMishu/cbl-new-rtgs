package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.workflow.WorkflowRequest;
import com.cbl.cityrtgs.models.dto.configuration.workflow.WorkflowResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.WorkflowMapper;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.WorkflowEntity;
import com.cbl.cityrtgs.repositories.configuration.WorkflowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class WorkflowService {
    private final WorkflowMapper mapper;
    private final WorkflowRepository repository;
    private final UserTaskService userTaskService;

    Date dateTimeNow = new Date();

    public Page<WorkflowResponse> getAll(Pageable pageable) {
        Page<WorkflowEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    @Transactional
    public void createWorkflow(WorkflowRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        WorkflowEntity entity = mapper.domainToEntity(request);

        if (this.isExist(
                request.getName())) {
            log.error("Workflow Already Exists");
            throw new RuntimeException("Workflow Already Exists");
        }
        try {
            entity.setCreatedAt(dateTimeNow);
            entity.setCreatedBy(currentUser.getId());
            entity = repository.save(entity);
        } catch (Exception e) {
            log.error("Failed to create workflow");
            throw new RuntimeException("Failed to create workflow");
        }
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        if (Objects.nonNull(request.getUserTasks())) {
            if (request.getUserTasks().size() > 0) {
                userTaskService.createWorkFlowUserTask(request, entity.getId());
            }
        }

        log.info("New Workflow {} is saved", request.getName());
    }

    public WorkflowResponse getById(Long id) {
        WorkflowEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        WorkflowResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, WorkflowRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<WorkflowEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            WorkflowEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Workflow {} Updated", request.getName());
    }

    public void deleteOne(Long id) {
        WorkflowEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.delete(entity);
        log.info("Workflow {} Deleted", id);
    }

    public WorkflowEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("workflow not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String name) {
        return repository.existsByName(name);
    }
}
