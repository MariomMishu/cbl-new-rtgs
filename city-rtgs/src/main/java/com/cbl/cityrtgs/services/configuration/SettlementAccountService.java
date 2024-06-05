package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountFilter;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.SettlementAccountMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.SettlementAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.SettlementAccountRepository;
import com.cbl.cityrtgs.repositories.specification.SettlementAccountSpecification;
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
public class SettlementAccountService {
    private final SettlementAccountMapper mapper;
    private final SettlementAccountRepository repository;

    public Page<SettlementAccountResponse> getAll(Pageable pageable, SettlementAccountFilter filter) {
        Page<SettlementAccountEntity> entities = repository.findAll(SettlementAccountSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }


    public void createSettlementAccount(SettlementAccountRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        SettlementAccountEntity entity = mapper.domainToEntity(request);
        entity.setOpenDate(new Date());
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Settlement Account {} is saved", entity.getCode());
    }

    public SettlementAccountResponse getById(Long id) {
        SettlementAccountEntity entity = repository.findById(id).orElse(null);
        SettlementAccountResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, SettlementAccountRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<SettlementAccountEntity> _entity = repository.findById(id);
        this.isExistValidation(request, true, id);
        if (_entity.isPresent()) {
            SettlementAccountEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setOpenDate(_entity.get().getOpenDate());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Settlement Account {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        SettlementAccountEntity entity = this.getEntityById(id);
        repository.delete(entity);
        log.info("Settlement Account {} Deleted", id);
    }

    public SettlementAccountEntity getEntityById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Settlement Account not found"));
    }

    public SettlementAccountResponse getEntityByCurrencyId(Long currencyId) {
        SettlementAccountEntity entity = repository.findByCurrencyIdAndIsDeletedFalse(currencyId).orElseThrow(() -> new ResourceNotFoundException("Settlement Account not found"));
        SettlementAccountResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String shortCode) {
        return repository.existsByCode(shortCode);
    }

    public List<SettlementAccountResponse> getSettlementAccList() {
        List<SettlementAccountEntity> entities = repository.findAllByIsDeletedFalse();
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    public SettlementAccountResponse getSettlementAccByCode(String code) {
        Optional<SettlementAccountEntity> entityOptional = repository.findByCodeAndIsDeletedFalse(code);
        if (entityOptional.isPresent()) {
            return  mapper.entityToDomain(entityOptional.get());
        }
        return null;
    }

    private void isExistValidation(SettlementAccountRequest request, boolean isUpdate, Long id) {
        List<SettlementAccountEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(entity -> {
            if (isUpdate) {
                if (!entity.getId().equals(id)) {
                    if (entity.getCode().equals(request.getCode())) {
                        throw new ResourceAlreadyExistsException("Already exist with Code : " + request.getCode());
                    }
                    if (entity.getCurrency().getId().equals(request.getCurrencyId())) {
                        throw new ResourceAlreadyExistsException("Already exist with Currency : " + entity.getCurrency().getShortCode());
                    }
                }
                return;

            } else {
                if (entity.getCode().equals(request.getCode())) {
                    throw new ResourceAlreadyExistsException("Already exist with Code : " + request.getCode());
                }
                if (entity.getCurrency().getId().equals(request.getCurrencyId())) {
                    throw new ResourceAlreadyExistsException("Already exist with Currency : " + entity.getCurrency().getShortCode());
                }
            }
        });
    }

}
