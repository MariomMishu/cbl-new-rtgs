package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentFilter;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentRequest;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.DepartmentMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.DepartmentRepository;
import com.cbl.cityrtgs.repositories.specification.DepartmentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentMapper mapper;
    private final DepartmentRepository repository;

    public Page<DepartmentResponse> getAll(Pageable pageable, DepartmentFilter filter) {
        Page<DepartmentEntity> entities = repository.findAll(DepartmentSpecification.all(filter), pageable);
        return entities.map(mapper::entityToResponseDomain);
    }

    public void createDepartment(DepartmentRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        DepartmentEntity entity = mapper.requestDomainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Department {} is saved", entity.getName());
    }

    public DepartmentResponse getById(Long deptId) {
        Optional<DepartmentEntity> entityOptional = repository.findByIdAndIsDeletedFalse(deptId);
        if (entityOptional.isPresent()) {
            DepartmentEntity entity = entityOptional.get();
            return mapper.entityToResponseDomain(entity);
        } else {
            return null;
        }
    }

    public void updateOne(Long id, DepartmentRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<DepartmentEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            DepartmentEntity entity = _entity.get();
            entity = mapper.requestDomainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Department {} Updated", request.getName());
    }

    public void deleteOne(Long id) {
        DepartmentEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Department {} Deleted", id);
    }

    public DepartmentEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Department not found"));
    }

    public boolean existOne(Long deptId) {
        return repository.existsById(deptId);
    }

    public Boolean isExist(String name) {
        return repository.existsByName(name);
    }

    private void isExistValidation(DepartmentRequest request, boolean isUpdate, Long id) {
        List<DepartmentEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getName().equals(request.getName())) {
                                throw new ResourceAlreadyExistsException("Already exist with Department Name : " + request.getName());
                            }
                        }

                    } else {
                        if (entity.getName().equals(request.getName())) {
                            throw new ResourceAlreadyExistsException("Already exist with Department Name : " + request.getName());
                        }
                    }
                });
    }


    public DepartmentEntity getDeptByName(String name) {

        Optional<DepartmentEntity> optEntity = repository.findByNameAndIsDeletedFalse(name);
        if (optEntity.isEmpty()) {
            throw new ResourceNotFoundException("Department not found");
        }else{
            return optEntity.get();
        }

    }

    public List<DepartmentResponse> getDeptList() {
        List<DepartmentEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToResponseDomain).collect(Collectors.toList());
    }

}
