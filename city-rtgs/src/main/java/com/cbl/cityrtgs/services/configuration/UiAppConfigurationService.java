package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.uiappconfiguration.UiAppConfigurationRequest;
import com.cbl.cityrtgs.models.dto.configuration.uiappconfiguration.UiAppConfigurationResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.UiAppConfigurationMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.UiAppConfigurationEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.UiAppConfigurationRepository;
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
public class UiAppConfigurationService {
    private final UiAppConfigurationMapper mapper;
    private final UiAppConfigurationRepository repository;

    public Page<UiAppConfigurationResponse> getAll(Pageable pageable) {
        Page<UiAppConfigurationEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createUiAppConfiguration(UiAppConfigurationRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        UiAppConfigurationEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New UI App Configuration {} is saved");
    }

    public UiAppConfigurationResponse getById(Long id) {
        UiAppConfigurationEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        UiAppConfigurationResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, UiAppConfigurationRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<UiAppConfigurationEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            UiAppConfigurationEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("UI App Configuration {} Updated");
    }

    public void deleteOne(Long id) {
        UiAppConfigurationEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("UI App Configuration {} Deleted", id);
    }

    public UiAppConfigurationEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("UI App Configuration not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public List<UiAppConfigurationResponse> getUiConfiguration() {
        List<UiAppConfigurationEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }
}
