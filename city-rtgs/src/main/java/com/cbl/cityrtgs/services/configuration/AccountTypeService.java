package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountTypeFilter;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountTypeRequest;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.AccountTypeResponse;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.AccountTypeMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.AccountTypesRepository;
import com.cbl.cityrtgs.repositories.specification.AccountTypeSpecification;
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
public class AccountTypeService {
    private final AccountTypeMapper mapper;
    private final AccountTypesRepository repository;

    public Page<AccountTypeResponse> getAll(Pageable pageable, AccountTypeFilter filter) {
        Page<AccountTypeEntity> entities = repository.findAll(AccountTypeSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createAccountType(AccountTypeRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        isExistValidation(request, false, null);
        AccountTypeEntity entity = mapper.domainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Account Type {} is saved", entity.getCode());
    }

    public AccountTypeResponse getById(Long id) {
        AccountTypeEntity entity = repository.findByIdAndIsDeletedFalse(id)
                .orElse(null);
        AccountTypeResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public void updateOne(Long id, AccountTypeRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<AccountTypeEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        isExistValidation(request, true, id);
        if (_entity.isPresent()) {
            AccountTypeEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Account Type {} Updated", _entity.isPresent());
    }

    public void deleteOne(Long id) {
        AccountTypeEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.delete(entity);
        log.info("Account Type {} Deleted", id);
    }

    public AccountTypeEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Account Type not found"));
    }

    public AccountTypeEntity getAccountByRtgsAccountIdAndCbsName(Long rtgsAccountId, CbsName cbs) {
        return repository
                .findByRtgsAccountIdAndCbsNameAndIsDeletedFalse(rtgsAccountId, cbs)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Account Type not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public Boolean isExist(String shortCode) {
        return repository.existsByCode(shortCode);
    }
    private void isExistValidation(AccountTypeRequest request, boolean isUpdate, Long id) {
        List<AccountTypeEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getCode().trim().toUpperCase().equals(request.getCode().trim().toUpperCase())) {
                                throw new ResourceAlreadyExistsException("Already exist with Code : " + request.getCode());
                            }
                            if (entity.getRtgsAccount().getId().equals(request.getSettlementAccountId()) && entity.getCbsName().equals(request.getCbsName())) {
                                throw new ResourceAlreadyExistsException("Already exist with RTGS Settlement Account And CBS Name" );
                            }
                        }
                        return;

                    } else {
                        if (entity.getCode().trim().toUpperCase().equals(request.getCode().trim().toUpperCase())) {
                            throw new ResourceAlreadyExistsException("Already exist with Code : " + request.getCode());
                        }
                        if (entity.getRtgsAccount().getId().equals(request.getSettlementAccountId()) && entity.getCbsName().equals(request.getCbsName())) {
                            throw new ResourceAlreadyExistsException("Already exist with RTGS Settlement Account And CBS Name" );
                        }
                    }
                });
    }


}
