package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupFilter;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.ExemptionChargeSetupMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.ExemptionChargeSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.ExemptionChargeRepository;
import com.cbl.cityrtgs.repositories.specification.ExemptionSetupSpecification;
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
public class ExemptionChargeSetupService {
    private final ExemptionChargeSetupMapper mapper;
    private final ExemptionChargeRepository repository;

    Date dateTimeNow = new Date();

    public Page<ExemptionSetupResponse> getAll(Pageable pageable, ExemptionSetupFilter filter) {
        Page<ExemptionChargeSetupEntity> entities = repository.findAll(ExemptionSetupSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createExemptionSetup(ExemptionSetupRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        ExemptionChargeSetupEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(dateTimeNow);
        entity.setCreatedBy(currentUser.getId());
        repository.save(entity);
        log.info("New Exemption Charge Setup {} is saved");
    }

    public ExemptionSetupResponse getById(Long id) {
        ExemptionChargeSetupEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        ExemptionSetupResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, ExemptionSetupRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<ExemptionChargeSetupEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            ExemptionChargeSetupEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(dateTimeNow);
            repository.save(entity);
        }
        log.info("Exemption Charge Setup {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        ExemptionChargeSetupEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Exemption Charge Setup {} Deleted", id);
    }

    public ExemptionChargeSetupEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Exemption Charge Setup not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    private void isExistValidation(ExemptionSetupRequest request, boolean isUpdate, Long id) {
        List<ExemptionChargeSetupEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (entity.getAccountCode().equals(request.getAccountNumber())) {
                            if (!entity.getId().equals(id)) {
                                if (entity.getAccountCode().equals(request.getAccountNumber())) {
                                    throw new ResourceAlreadyExistsException("Already exist with Account Number : " + request.getAccountNumber());
                                }
                            }
                            return;
                        }
                    } else {
                        if (entity.getAccountCode().equals(request.getAccountNumber())) {
                            throw new ResourceAlreadyExistsException("Already exist with Account Number : " + request.getAccountNumber());
                        }
                    }
                });
    }

    public boolean existByAccountNo(String accountNo) {
        return repository.existsByAccountCodeAndIsDeletedFalse(accountNo);
    }

    public List<ExemptionSetupResponse> getAllExemptionList(ExemptionSetupFilter filter) {
        List<ExemptionChargeSetupEntity> entities = repository.findAll(ExemptionSetupSpecification.all(filter));
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

}
