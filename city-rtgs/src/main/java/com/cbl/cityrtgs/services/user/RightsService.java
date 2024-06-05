package com.cbl.cityrtgs.services.user;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.rights.RightsRequest;
import com.cbl.cityrtgs.models.dto.configuration.rights.RightsResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.RightsMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.RightsEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.RightsRepository;
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
public class RightsService {

    private final RightsMapper mapper;
    private final RightsRepository repository;

    Date dateTimeNow = new Date();

    public Page<RightsResponse> getAll(Pageable pageable) {
        Page<RightsEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createRights(RightsRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        RightsEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Rights {} is saved", entity.getName());
    }

    public RightsResponse getById(Long id) {
        RightsEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        RightsResponse response = mapper.entityToDomain(entity);

        return response;
    }

    public void updateOne(Long id, RightsRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<RightsEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            RightsEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Rights {} Updated", _entity.get().getName());
    }

    public void deleteOne(Long id) {
        RightsEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Rights {} Deleted", id);
    }

    public RightsEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Rights not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String name) {
        return repository.existsByName(name);
    }

    private void isExistValidation(RightsRequest request, boolean isUpdate, Long id) {
        List<RightsEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getName().equals(request.getName().toUpperCase())) {
                                throw new ResourceAlreadyExistsException("Already exist with Name : " + request.getName());
                            }
                        }
                        return;

                    } else {
                        if (entity.getName().equals(request.getName().toUpperCase())) {
                            throw new ResourceAlreadyExistsException("Already exist with Name : " + request.getName());
                        }
                    }
                });
    }

    public List<RightsEntity> getAllRightsList() {
        List<RightsEntity> allRightsList = repository.findAll().stream()
                .filter(rights -> rights.isActive()).collect(Collectors.toList());
        return allRightsList;
    }
}
