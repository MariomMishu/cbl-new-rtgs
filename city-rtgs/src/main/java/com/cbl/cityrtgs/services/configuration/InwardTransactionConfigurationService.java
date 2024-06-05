package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.inwardtransactionConfiguration.InwardTransactionConfigurationRequest;
import com.cbl.cityrtgs.models.dto.configuration.inwardtransactionConfiguration.InwardTransactionConfigurationResponse;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.InwardTransactionConfigurationMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.InwardTransactionConfigurationEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.InwardTransactionConfigurationRepository;
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
public class InwardTransactionConfigurationService {
    private final InwardTransactionConfigurationMapper mapper;
    private final InwardTransactionConfigurationRepository repository;

    //Date dateTimeNow = new Date();

    public Page<InwardTransactionConfigurationResponse> getAll(Pageable pageable) {
        Page<InwardTransactionConfigurationEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createInwardTransactionConfiguration(InwardTransactionConfigurationRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        InwardTransactionConfigurationEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("Inward Transaction Configuration {} is saved");
    }

    public InwardTransactionConfigurationResponse getById(Long id) {
        InwardTransactionConfigurationEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        InwardTransactionConfigurationResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, InwardTransactionConfigurationRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<InwardTransactionConfigurationEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            InwardTransactionConfigurationEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Inward Transaction Configuration {} Updated");
    }

    public void deleteOne(Long id) {
        InwardTransactionConfigurationEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Inward Transaction Configuration {} Deleted", id);
    }

    public InwardTransactionConfigurationEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Inward Transaction Configuration not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }
    public List<InwardTransactionConfigurationResponse> getAllList() {
        List<InwardTransactionConfigurationEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }
}
