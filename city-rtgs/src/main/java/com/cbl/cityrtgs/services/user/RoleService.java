package com.cbl.cityrtgs.services.user;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.rights.RightsResponse;
import com.cbl.cityrtgs.models.dto.configuration.role.RoleFilter;
import com.cbl.cityrtgs.models.dto.configuration.role.RoleRequest;
import com.cbl.cityrtgs.models.dto.configuration.role.RoleResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.RoleMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.RightsEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.RightsRepository;
import com.cbl.cityrtgs.repositories.configuration.RolesRepository;
import com.cbl.cityrtgs.repositories.specification.RoleSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RoleService {
    private final RoleMapper mapper;
    private final RolesRepository repository;
    private final RightsRepository rightsRepository;

    Date dateTimeNow = new Date();
    public RoleEntity getRoleById(Long id){

        if(repository.findByIdAndIsDeletedFalse(id).isPresent()){

            return repository.findByIdAndIsDeletedFalse(id).get();
        }

        return null;
    }

    public Page<RoleResponse> getAll(Pageable pageable, RoleFilter filter) {
        Page<RoleEntity> entities = repository.findAll(RoleSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createRole(RoleRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        this.isExistValidation(request, false, null);
        Set<RightsEntity> rightsReference = new HashSet<>();
        request.getRightId().forEach(right -> {
            RightsEntity rightsEntity = rightsRepository.getReferenceById(right);
            rightsReference.add(rightsEntity);
        });
        RoleEntity entity = mapper.domainToEntity(request);
        entity.setRights(rightsReference);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Role {} is saved", entity.getName());
    }

    public RoleResponse getById(Long id) {
        RoleEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        RoleResponse response = mapper.entityToDomain(entity);

        return response;
    }

    public void updateOne(Long id, RoleRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<RoleEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            RoleEntity entity = _entity.get();
            Set<RightsEntity> rightsReference = new HashSet<>();
            request.getRightId().forEach(right -> {
                RightsEntity rightsEntity = rightsRepository.getReferenceById(right);
                rightsReference.add(rightsEntity);
            });
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setRights(rightsReference);
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Role {} Updated", _entity.get().getName());
    }

    public void deleteOne(Long id) {
        RoleEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Role {} Deleted", id);
    }

    public RoleEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Role not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String name) {
        return repository.existsByName(name);
    }

    public Page<RightsResponse> getAllRights(Pageable pageable) {
        Page<RightsEntity> entities = rightsRepository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::rightsEntityToRightsDomain);
    }

    public RightsEntity getRightsEntityById(Long id) {
        return rightsRepository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Right not found"));
    }

    public List<RoleEntity> getRolesWithinIds(Set<Long> ids) {
        List<RoleEntity> entities = repository.findAllByIdInAndIsDeletedFalse(ids);

        return entities;
    }

    private void isExistValidation(RoleRequest request, boolean isUpdate, Long id) {
        List<RoleEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getName().equals(request.getName())) {
                                throw new ResourceAlreadyExistsException("Already exist with Name : " + request.getName());
                            }
                        }
                        return;

                    } else {
                        if (entity.getName().equals(request.getName())) {
                            throw new ResourceAlreadyExistsException("Already exist with Name : " + request.getName());
                        }
                    }
                });
    }
}
