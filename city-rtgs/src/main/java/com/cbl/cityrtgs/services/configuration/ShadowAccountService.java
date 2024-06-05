package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountFilter;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.ShadowAccountMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.ShadowAccountRepository;
import com.cbl.cityrtgs.repositories.specification.ShadowAccountSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ShadowAccountService {
    private final ShadowAccountMapper mapper;
    private final ShadowAccountRepository repository;

    public Page<ShadowAccountResponse> getAll(Pageable pageable, ShadowAccountFilter filter) {
        Page<ShadowAccountEntity> entities = repository.findAll(ShadowAccountSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createShadowAccount(ShadowAccountRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        isExistValidation(request, false, null);
        ShadowAccountEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Shadow Account {} is saved", entity.getRtgsSettlementAccount());
    }

    public ShadowAccountResponse getById(Long id) {
        ShadowAccountEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Shadow Account not found"));
        ShadowAccountResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, ShadowAccountRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<ShadowAccountEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        isExistValidation(request, true, id);
        if (_entity.isPresent()) {
            ShadowAccountEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Shadow Account {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        ShadowAccountEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.delete(entity);
        log.info("Shadow Account {} Deleted", id);
    }

    public ShadowAccountEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Shadow Account not found"));
    }

    public ShadowAccountResponse getShadowAcc(Long bankId, Long currencyId) {
        ShadowAccountResponse response;
        Optional<ShadowAccountEntity> entity = repository.findBybankIdAndCurrencyIdAndIsDeletedFalse(bankId,currencyId);
                if(entity.isPresent()){
                     response = mapper.entityToDomain(entity.get());
                }else{
                     response = null;
                }

        return response;
    }
    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String rtgsSettlementNumber) {
        return repository.existsByRtgsSettlementAccount(rtgsSettlementNumber);
    }
    private void isExistValidation(ShadowAccountRequest request, boolean isUpdate, Long id) {
        List<ShadowAccountEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(entity -> {
            if (isUpdate) {
                if (!entity.getId().equals(id)) {
                    if (entity.getRtgsSettlementAccount().equals(request.getRtgsSettlementAccount())) {
                        throw new ResourceAlreadyExistsException("Already exist with Rtgs settlement Account : " + request.getRtgsSettlementAccount());
                    }
                    if (entity.getCurrency().getId().equals(request.getCurrencyId()) && entity.getBank().getId().equals(request.getBankId())) {
                        throw new ResourceAlreadyExistsException("Already exist with Bank With Currency");
                    }
                }
                return;

            } else {
                if (entity.getRtgsSettlementAccount().equals(request.getRtgsSettlementAccount())) {
                    throw new ResourceAlreadyExistsException("Already exist with Rtgs settlement Account : " + request.getRtgsSettlementAccount());
                }
                if (entity.getCurrency().getId().equals(request.getCurrencyId()) && entity.getBank().getId().equals(request.getBankId())) {
                    throw new ResourceAlreadyExistsException("Already exist with Bank With Currency");
                }
            }
        });
    }


}
