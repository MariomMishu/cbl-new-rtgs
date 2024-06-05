package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.fcaccounttype.FcAccountTypeRequest;
import com.cbl.cityrtgs.models.dto.configuration.fcaccounttype.FcAccountTypeResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.FcAccountTypeMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.FcAccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.FcAccountTypeRepository;
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
public class FcAccountTypeService {
    private final FcAccountTypeMapper mapper;
    private final FcAccountTypeRepository repository;

    Date dateTimeNow = new Date();

    public Page<FcAccountTypeResponse> getAll(Pageable pageable) {
        Page<FcAccountTypeEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createFcAccountType(FcAccountTypeRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        if (this.isExist(request.getName()))
            throw new RuntimeException("Already exist with Name : " + request.getName());

        FcAccountTypeEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Fc Account Type {} is saved", entity.getName());
    }

    public FcAccountTypeResponse getById(Long id) {
        FcAccountTypeEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        FcAccountTypeResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, FcAccountTypeRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<FcAccountTypeEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            FcAccountTypeEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Fc Account Type {} Updated", _entity.get().getName());
    }

    public void deleteOne(Long id) {
        FcAccountTypeEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Fc Account Type {} Deleted", id);
    }

    public FcAccountTypeEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Fc Account Type not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String name) {
        return repository.existsByName(name);
    }

    public List<FcAccountTypeResponse> getAllList() {
        List<FcAccountTypeEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }
}
