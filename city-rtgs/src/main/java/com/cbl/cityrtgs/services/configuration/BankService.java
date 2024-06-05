package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankFilter;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankRequest;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.BankMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.BankEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.BankRepository;
import com.cbl.cityrtgs.repositories.specification.BankSpecification;
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
public class BankService {

    private final BankMapper mapper;
    private final BankRepository repository;

    public Page<BankResponse> getAll(Pageable pageable, BankFilter filter) {
        Page<BankEntity> entities = repository.findAll(BankSpecification.all(filter), pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createBank(BankRequest bankRequest) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(bankRequest, false, null);
        BankEntity entity = mapper.domainToEntity(bankRequest);

        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Bank {} is saved", entity.getName());
    }

    public BankResponse getBankById(Long bankId) {
        Optional<BankEntity> entityOptional = repository.findByIdAndIsDeletedFalse(bankId);
        if (entityOptional.isPresent()) {
            return mapper.entityToDomain(entityOptional.get());
        } else {
            return null;
        }
    }

    public BankResponse updateBank(Long id, BankRequest request) {
        BankResponse response = null;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<BankEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            BankEntity entity = this.getEntityById(id);
            entity = mapper.updateDomainToEntity(request);
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            entity.setId(id);
            entity = repository.save(entity);
            response = mapper.entityToDomain(entity);
        }
        return response;
    }

    public void deleteOne(Long id) {
        BankEntity entity = this.getEntityById(id);
        entity.setId(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Bank {} Deleted", id);
    }

    public BankEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Bank not found"));
    }

    public boolean existOne(Long bankId) {
        return repository.existsById(bankId);
    }

    public Boolean isExist(String name, String bic, String bankCode) {
        return repository.existsByNameOrBicOrBankCode(name, bic, bankCode);
    }

    private void isExistValidation(BankRequest request, boolean isUpdate, Long id) {
        List<BankEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getName().equals(request.getName())) {
                                throw new ResourceAlreadyExistsException("Already exist with Bank Name : " + request.getName());
                            }
                            if (entity.getBankCode().equals(request.getBankCode())) {
                                throw new ResourceAlreadyExistsException("Already exist with Bank Code : " + request.getBankCode());
                            }
                            if (entity.getBic().equals(request.getBic())) {
                                throw new ResourceAlreadyExistsException("Already exist with Bic : " + request.getBic());
                            }
                        }
                        return;

                    } else {
                        if (entity.getName().equals(request.getName())) {
                            throw new ResourceAlreadyExistsException("Already exist with Bank Name : " + request.getName());
                        }
                        if (entity.getBankCode().equals(request.getBankCode())) {
                            throw new ResourceAlreadyExistsException("Already exist with Bank Code : " + request.getBankCode());
                        }
                        if (entity.getBic().equals(request.getBic())) {
                            throw new ResourceAlreadyExistsException("Already exist with Bic : " + request.getBic());
                        }
                    }
                });
    }

    public BankResponse getOwnerBank() {
        return repository
                .findByIsOwnerBankTrueAndIsDeletedFalse()
                .map(e -> mapper.entityToDomain(e)).orElseThrow(
                        () ->
                                new ResourceNotFoundException("Bank not found"));
    }

    public BankResponse getSettlementBank() {
        return repository
                .findBySettlementBankTrueAndIsDeletedFalse()
                .map(e -> mapper.entityToDomain(e)).orElseThrow(
                        () ->
                                new ResourceNotFoundException("Bank not found"));
    }

    public List<BankResponse> getAllBenBankList() {
        List<BankEntity> entities = repository.findAllByIsOwnerBankFalseAndIsDeletedFalse();
        return entities.stream().map(mapper::entityToDomain).collect(Collectors.toList());
    }

    public BankResponse getBankByBic(String bic) {
        BankEntity entity = repository.findByBicAndIsDeletedFalse(bic)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Bank not found"));
        BankResponse response = mapper.entityToDomain(entity);
        return response;
    }

    public BankResponse getBankByBicCode(String bic) {
        Optional<BankEntity> optionalBankEntity = repository.findByBicAndIsDeletedFalse(bic);
        if (optionalBankEntity.isPresent()) {
            return mapper.entityToDomain(optionalBankEntity.get());
        }
        return null;
    }
    public BankEntity getByBicAndSettlementBankIsTrue(String bic) {
        Optional<BankEntity> optionalBankEntity = repository.findByBicAndIsDeletedFalse(bic);
        if (optionalBankEntity.isPresent()) {
          return optionalBankEntity.get();
        }
        return null;
    }

}
