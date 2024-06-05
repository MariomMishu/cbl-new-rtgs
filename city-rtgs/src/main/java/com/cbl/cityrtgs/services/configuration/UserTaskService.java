package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.workflow.UserTaskRequest;
import com.cbl.cityrtgs.models.dto.configuration.workflow.UserTaskResponse;
import com.cbl.cityrtgs.models.dto.configuration.workflow.WorkflowRequest;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.UserTaskMapper;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.UserTaskEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.WorkflowEntity;
import com.cbl.cityrtgs.repositories.configuration.UserTaskRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserTaskService {
    private final UserTaskMapper mapper;
    private final UserTaskRepository repository;

    Date dateTimeNow = new Date();

    public Page<UserTaskResponse> getAll(Pageable pageable) {
        Page<UserTaskEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createUserTask(UserTaskRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserTaskEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("");
    }

    public UserTaskResponse getById(Long id) {
        UserTaskEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        UserTaskResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, UserTaskRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<UserTaskEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            UserTaskEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("");
    }

    public void deleteOne(Long id) {
        UserTaskEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.delete(entity);
        log.info("", id);
    }

    public UserTaskEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("User Task not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public void createWorkFlowUserTask(WorkflowRequest request, Long workflowId) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        WorkflowEntity workflowEntity = new WorkflowEntity().setId(workflowId);
        Set<UserTaskEntity> items = new HashSet<>();
        request.getUserTasks()
                .forEach(
                        item -> {
                            UserTaskRequest userTaskRequest = new UserTaskRequest();
                            userTaskRequest
                                    .setRightsForTask(item.getRightsForTask())
                                    .setAllowMakerCheckerSame(item.getAllowMakerCheckerSame())
                                    .setApproverLevelRequirement(item.getApproverLevelRequirement())
                                    .setLastTask(item.getLastTask())
                                    .setMakerCheckerInSameBranch(item.getMakerCheckerInSameBranch())
                                    .setMakerCheckerInSameDept(item.getMakerCheckerInSameDept())
                                    .setTaskName(item.getTaskName())
                                    .setTaskStatus(item.getTaskStatus())
                                    .setApproverBranch(item.getApproverBranch())
                                    .setApproverDept(item.getApproverDept());
                            UserTaskEntity userTaskEntity = mapper.domainToEntity(userTaskRequest);
                            userTaskEntity.setWorkflow(workflowEntity);
                            userTaskEntity.setCreatedAt(dateTimeNow);
                            userTaskEntity.setCreatedBy(currentUser.getId());
                            items.add(userTaskEntity);
                        });
        repository.saveAll(items);
    }
}
