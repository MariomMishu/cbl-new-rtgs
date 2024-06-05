package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.schemecode.ConfigurationKey;
import com.cbl.cityrtgs.models.dto.configuration.schemecode.SchemeCodeRequest;
import com.cbl.cityrtgs.models.dto.configuration.schemecode.SchemeCodeResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.SchemeCodeMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.SchemeCodeEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.SchemeCodeRepository;
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
public class SchemeCodeService {
    private final SchemeCodeMapper mapper;
    private final SchemeCodeRepository repository;

    Date dateTimeNow = new Date();

    public Page<SchemeCodeResponse> getAll(Pageable pageable) {
        Page<SchemeCodeEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }


    public void createSchemeCode(SchemeCodeRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        SchemeCodeEntity entity = mapper.domainToEntity(request);
        entity.setConfigurationKey(ConfigurationKey.CHARGE_WAIVER_SCHEMES);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Scheme Code {} is saved", entity.getConfigurationValue());
    }

    public SchemeCodeResponse getById(Long id) {
        SchemeCodeEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        SchemeCodeResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, SchemeCodeRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<SchemeCodeEntity> _entity = repository.findByConfigurationKeyAndIsDeletedFalse(ConfigurationKey.CHARGE_WAIVER_SCHEMES);
        if (_entity.isPresent()) {
            SchemeCodeEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setConfigurationKey(ConfigurationKey.CHARGE_WAIVER_SCHEMES);
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Scheme Code {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        SchemeCodeEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Scheme Code {} Deleted", id);
    }

    public SchemeCodeEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Scheme Code not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    private void isExistValidation(SchemeCodeRequest request, boolean isUpdate, Long id) {
        List<SchemeCodeEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (entity.getConfigurationValue().equals(request.getSchemeCodeValue())) {
                            if (!entity.getId().equals(id)) {
                                if (entity.getConfigurationValue().equals(request.getSchemeCodeValue())) {
                                    throw new ResourceAlreadyExistsException("Already exist with Scheme Code : " + request.getSchemeCodeValue());
                                }
                            }
                            return;
                        }
                    } else {
                        if (entity.getConfigurationValue().equals(request.getSchemeCodeValue())) {
                            throw new ResourceAlreadyExistsException("Already exist with Scheme Code : " + request.getSchemeCodeValue());
                        }
                    }
                });
    }

    public SchemeCodeResponse getByConfigKey(ConfigurationKey configurationKey) {
        SchemeCodeEntity entity = repository.findByConfigurationKeyAndIsDeletedFalse(configurationKey)
                .orElse(null);
        SchemeCodeResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public List<SchemeCodeResponse> getAllConfigForCustomsOperation() {
        List<SchemeCodeEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }
}
