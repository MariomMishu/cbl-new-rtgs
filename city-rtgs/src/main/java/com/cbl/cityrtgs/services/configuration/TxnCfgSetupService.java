package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration.TxnCfgSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration.TxnCfgSetupResponse;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionTypeCodeResponse;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.TxnCfgSetupMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.TxnCfgSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.TxnCfgSetupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class TxnCfgSetupService {
    private final TxnCfgSetupMapper mapper;
    private final TxnCfgSetupRepository repository;
    private final TransactionPriorityService transactionPriorityService;

    public Page<TxnCfgSetupResponse> getAll(Pageable pageable) {
        Page<TxnCfgSetupEntity> entities = repository.findAllByIsDeletedFalse(pageable);
        return entities.map(mapper::entityToDomain);
    }

    public void createTxnCfgSetup(TxnCfgSetupRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);

        TxnCfgSetupEntity entity = mapper.domainToEntity(request);

        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        repository.save(entity);
        log.info("Outward Transaction Configuration Setup {} is saved");
    }

    public TxnCfgSetupResponse getById(Long id) {
        Optional<TxnCfgSetupEntity> optionalEntity = repository.findByIdAndIsDeletedFalse(id);
        if (optionalEntity.isPresent()) {
            return mapper.entityToDomain(optionalEntity.get());
        } else {
            return null;
        }
    }

    public void updateOne(Long id, TxnCfgSetupRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<TxnCfgSetupEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            this.isExistValidation(request, true, id);
            TxnCfgSetupEntity entity = _entity.get();
            entity = mapper.domainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setExtraEndTime(_entity.get().getExtraEndTime());
            entity.setCloseTime(_entity.get().getCloseTime());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Outward Transaction Configuration Setup {} Updated");
    }

    public void deleteOne(Long id) {
        TxnCfgSetupEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Outward Transaction Configuration Setup {} Deleted", id);
    }

    public TxnCfgSetupEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Outward Transaction Configuration Setup not found"));
    }

    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public TxnCfgSetupEntity getTransactionCfgSetup(Long currencyId) {
        List<TxnCfgSetupEntity> transactionCfgSetups = repository.findByCurrencyIdAndTxnActiveTrueAndIsDeletedFalse(currencyId);
        return transactionCfgSetups.size() > 0 ? transactionCfgSetups.get(0) : null;
    }

    public boolean txnPossibleOld(Long currencyId) {
        TxnCfgSetupEntity txnCfgSetup = this.getTransactionCfgSetup(currencyId);

        if (txnCfgSetup != null) {

            if (!txnCfgSetup.getTxnActive()) {
                return false;
            } else if (txnCfgSetup.getTimeRestricted()) {
                LocalTime now = LocalTime.now();
                LocalTime start = new LocalTime(txnCfgSetup.getStartTime());
                LocalTime end = new LocalTime(txnCfgSetup.getEndTime());
                return !now.isAfter(end) && !now.isBefore(start);
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

//    public boolean txnPossibleOld(Long currencyId) {
//        TxnCfgSetupEntity txnCfgSetup = this.getTransactionCfgSetup(currencyId);
//
//        if (txnCfgSetup != null) {
//
//            if (!txnCfgSetup.getTxnActive()) {
//                return false;
//            } else if (txnCfgSetup.getTimeRestricted()) {
//                LocalTime now = LocalTime.now();
//                LocalTime start = new LocalTime(txnCfgSetup.getStartTime());
//                LocalTime end = new LocalTime(txnCfgSetup.getEndTime());
//                return !now.isAfter(end) && !now.isBefore(start);
//            } else {
//                return true;
//            }
//        } else {
//            return false;
//        }
//    }

    public boolean txnPossible(String txnTypeCode, Long currencyId) {
        TxnCfgSetupEntity txnCfgSetup = this.getTransactionCfgSetup(currencyId);
        Boolean validTime;
        TransactionTypeCodeResponse txnTypeCodeResponse = transactionPriorityService.getDetailsByTransactionTypeCode(txnTypeCode);
        if (txnCfgSetup != null) {
            if (!txnCfgSetup.getTxnActive()) {
                return false;
            } else if (txnCfgSetup.getTimeRestricted()) {
                LocalTime now = LocalTime.now();
                LocalTime start = new LocalTime(txnCfgSetup.getStartTime());
                LocalTime end = new LocalTime(txnCfgSetup.getEndTime());
                LocalTime extraEndTime = new LocalTime(txnCfgSetup.getExtraEndTime());
                Boolean validStartTime = now.isBefore(start);
                Boolean validEndTime = now.isAfter(end);
                Boolean validExtraEndTime = now.isAfter(extraEndTime);
                if (!txnTypeCodeResponse.getExtraEndTime()) {
                    validTime = !validEndTime && !validStartTime;
                } else {
                    validTime = !validExtraEndTime && !validStartTime;
                }
                return validTime;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    public boolean b2bOutwardTxnPossible(Long currencyId) {
        TxnCfgSetupEntity txnCfgSetup = this.getTransactionCfgSetup(currencyId);
        if (txnCfgSetup != null) {
            if (!txnCfgSetup.getTxnActive()) {
                return false;
            } else if (txnCfgSetup.getTimeRestricted()) {
                LocalTime now = LocalTime.now();
                LocalTime start = new LocalTime(txnCfgSetup.getStartTime());
                LocalTime end = new LocalTime(txnCfgSetup.getCloseTime());
                Boolean validStartTime = now.isBefore(start);
                Boolean validEndTime = now.isAfter(end);
                return !validEndTime && !validStartTime;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }

    private void isExistValidation(TxnCfgSetupRequest request, boolean isUpdate, Long id) {
        List<TxnCfgSetupEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getCurrencyId().equals(request.getCurrencyId())) {
                                throw new ResourceAlreadyExistsException("Already exist with Currency!");
                            }
                        }
                        return;
                    } else {
                        if (entity.getCurrencyId().equals(request.getCurrencyId())) {
                            throw new ResourceAlreadyExistsException("Already exist with Currency!");
                        }
                    }
                });
    }

}
