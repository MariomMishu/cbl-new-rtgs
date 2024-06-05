package com.cbl.cityrtgs.services.configuration;

import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountFilter;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.reconcile.ReconcileTxnType;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.common.exception.ResourceAlreadyExistsException;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.configuration.DepartmentAccountMapper;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountAudEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.DepartmentAccountAudRepository;
import com.cbl.cityrtgs.repositories.configuration.DepartmentAccountRepository;
import com.cbl.cityrtgs.repositories.specification.DepartmentAccountSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentAccountService {
    private final DepartmentAccountMapper mapper;
    private final DepartmentAccountRepository repository;
    private final DepartmentAccountAudRepository audRepository;

    public Page<DepartmentAccountResponse> getAll(Pageable pageable, DepartmentAccountFilter filter) {
        Page<DepartmentAccountEntity> entities = repository.findAll(DepartmentAccountSpecification.all(filter), pageable);
        return entities.map(mapper::entityToResponseDomain);
    }

    public void createDepartmentAcc(DepartmentAccountRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, false, null);
        DepartmentAccountEntity entity = mapper.requestDomainToEntity(request);
        entity.setCreatedAt(new Date());
        entity.setCreatedBy(currentUser.getId());
        entity = repository.save(entity);
        log.info("New Department Account {} is saved", entity.getAccountNumber());
    }

    public DepartmentAccountResponse getById(Long deptId) {
        return repository.findByIdAndIsDeletedFalse(deptId)
                .map(mapper::entityToResponseDomain)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department Account setup not found"));
    }

    public void updateOne(Long id, DepartmentAccountRequest request) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        this.isExistValidation(request, true, id);
        Optional<DepartmentAccountEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            DepartmentAccountEntity entity = _entity.get();
            entity = mapper.requestDomainToEntity(request);
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Department Account {} Updated", request.getAccountNumber());
    }

    public void deleteOne(Long id) {
        DepartmentAccountEntity entity = this.getEntityById(id);
        entity.isDeleted = true;
        repository.save(entity);
        log.info("Department Account{} Deleted", id);
    }

    public DepartmentAccountEntity getEntityById(Long id) {
        return repository
                .findByIdAndIsDeletedFalse(id)
                .orElseThrow(
                        () ->
                                new ResourceNotFoundException("Department Account not found"));
    }

    public boolean existOne(Long deptId) {
        return repository.existsById(deptId);
    }

    public DepartmentAccountResponse getDepartmentAcc(Long deptId, Long currencyId, RoutingType routingType) {
        return repository.findByDeptIdAndCurrencyIdAndRoutingTypeAndIsDeletedFalse(deptId, currencyId, routingType)
                .map(mapper::entityToResponseDomain)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Department Account setup not found"));
    }

    public DepartmentAccountEntity getDepartmentAccEntity(Long deptId, Long currencyId, RoutingType routingType) {

        Optional<DepartmentAccountEntity> optional = repository.findByDeptIdAndCurrencyIdAndRoutingTypeAndIsDeletedFalse(deptId, currencyId, routingType);
        if (optional.isEmpty()) {
            throw new ResourceNotFoundException("Department Account not found");
        }else{
            return optional.get();
        }
       // return optional.orElse(null);

    }

    public DepartmentAccountEntity departmentAccountTransaction(
            BigDecimal amount,
            RoutingType routingType,
            Long deptId,
            FundTransferType fundTransferType,
            boolean isReturnTxn,
            BigDecimal charge,
            BigDecimal vat,
            String referenceNumber,
            Long currencyId,
            String voucherNumber) {
        //  UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        DepartmentAccountEntity departmentAccount = this.getDepartmentAccEntity(deptId, currencyId, routingType);
        BigDecimal previousBalance = departmentAccount.getBalance();
        BigDecimal previousCharge = departmentAccount.getCharge();
        BigDecimal previousVat = departmentAccount.getVat();
        BigDecimal previousTotalAmount = departmentAccount.getTotalAmount();
        Long previousConfirmTxnNumber = departmentAccount.getConfirmTxnNo();
        if (isReturnTxn) {
            previousBalance = previousBalance.subtract(amount);
            previousCharge = previousCharge.subtract(charge);
            previousVat = previousVat.subtract(vat);
            previousTotalAmount = previousTotalAmount.subtract(amount.add(charge).add(vat));
            previousConfirmTxnNumber = previousConfirmTxnNumber - 1L;
        } else {
            if (fundTransferType.equals(FundTransferType.BankToBank)) {
                previousBalance = previousBalance.add(amount);
                previousCharge = previousCharge.add(BigDecimal.ZERO);
                previousVat = previousVat.add(BigDecimal.ZERO);
                previousTotalAmount = previousTotalAmount.add(amount);
                if (amount.compareTo(BigDecimal.ZERO) > 0) {
                    previousConfirmTxnNumber = previousConfirmTxnNumber + 1L;
                }
            }
            if (fundTransferType.equals(FundTransferType.CustomerToCustomer)) {
                // if (StringUtils.isNotBlank(departmentAccount.getChargeAccNumber()) && StringUtils.isNotBlank(departmentAccount.getVatAccNumber())) {
                previousBalance = previousBalance.add(amount);
                previousCharge = previousCharge.add(charge != null ? charge : BigDecimal.ZERO);
                previousVat = previousVat.add(vat != null ? vat : BigDecimal.ZERO);
                previousTotalAmount = previousTotalAmount.add(amount.add(charge != null ? charge : BigDecimal.ZERO).add(vat != null ? vat : BigDecimal.ZERO));
                if (amount.compareTo(BigDecimal.ZERO) == 1) {
                    previousConfirmTxnNumber = previousConfirmTxnNumber + 1L;
                }
                //}
            }
        }
        departmentAccount.setBalance(previousBalance);
        departmentAccount.setCharge(previousCharge);
        departmentAccount.setVat(previousVat);
        departmentAccount.setConfirmTxnNo(previousConfirmTxnNumber);
        departmentAccount.setReferenceNumber(referenceNumber);
        departmentAccount.setReconcile(false);
        departmentAccount.setVoucherNumber(voucherNumber);
        departmentAccount.setChargeReconcile(false);
        departmentAccount.setVatReconcile(false);
        departmentAccount.setTotalAmount(previousTotalAmount);
        try {

            departmentAccount.setReconcileDate(new Date());
            departmentAccount.setUpdatedAt(new Date());
            departmentAccount.setId(departmentAccount.getId());
            repository.save(departmentAccount);
            DepartmentAccountAudEntity audEntity = mapper.entityToAudEntity(departmentAccount);
            audRepository.save(audEntity);
            log.info("Rtgs Department Account Balance {} Updated", departmentAccount.getAccountNumber());
        } catch (Exception e) {
            log.error("Rtgs Department account Balance Update Failed");
            e.printStackTrace();
        }
        return departmentAccount;
    }

    public void updateDepAcc(Long id, String cbsReference, ReconcileTxnType txnType) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        Optional<DepartmentAccountEntity> _entity = repository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            DepartmentAccountEntity entity = _entity.get();
            if (txnType.equals(ReconcileTxnType.SettlementTxn)) {
                entity.setBalance(BigDecimal.ZERO);
                entity.setConfirmTxnNo(0L);
                entity.setReconcile(true);
                entity.setVoucherNumber(cbsReference);
                entity.setReconcileUser(currentUser.getUsername());
                entity.setReconcileSettlementDate(new Date());
            } else if (txnType.equals(ReconcileTxnType.ChargeReconcile)) {
                entity.setCharge(BigDecimal.ZERO);
                entity.setChargeReconcile(true);
                entity.setChargeVoucherNumber(cbsReference);
                entity.setChargeReconcileUser(currentUser.getUsername());
                entity.setChargeReconcileSetDate(new Date());
            } else if (txnType.equals(ReconcileTxnType.VatReconcile)) {
                entity.setVat(BigDecimal.ZERO);
                entity.setVatReconcile(true);
                entity.setVatVoucherNumber(cbsReference);
                entity.setVatReconcileUser(currentUser.getUsername());
                entity.setVatReconcileSetDate(new Date());
            }
            entity.setId(_entity.get().getId());
            entity.setCreatedAt(_entity.get().getCreatedAt());
            entity.setCreatedBy(_entity.get().getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            repository.save(entity);
        }
        log.info("Department Account {} Updated");
    }
    public DepartmentAccountEntity departmentAccountChargeVatTransaction(
            RoutingType routingType,
            Long deptId,
            FundTransferType fundTransferType,
            BigDecimal charge,
            BigDecimal vat,
            String referenceNumber,
            Long currencyId,
            String voucherNumber) {
        DepartmentAccountEntity departmentAccount = getDepartmentAccEntity(deptId, currencyId, routingType);
        BigDecimal previousBalance = departmentAccount.getBalance();
        BigDecimal previousCharge = departmentAccount.getCharge();
        BigDecimal previousVat = departmentAccount.getVat();
        BigDecimal previousTotalAmount = departmentAccount.getTotalAmount();
        Long previousConfirmTxnNumber = departmentAccount.getConfirmTxnNo();

        if (fundTransferType.equals(FundTransferType.CustomerToCustomer)) {
             if (StringUtils.isNotBlank(departmentAccount.getChargeAccNumber()) && StringUtils.isNotBlank(departmentAccount.getVatAccNumber())) {
            previousCharge = previousCharge.add(charge != null ? charge : BigDecimal.ZERO);
            previousVat = previousVat.add(vat != null ? vat : BigDecimal.ZERO);
                 assert charge != null;
                 previousTotalAmount = previousTotalAmount.add(charge.add(vat != null ? vat : BigDecimal.ZERO));
            }
        }

        departmentAccount.setBalance(previousBalance);
        departmentAccount.setCharge(previousCharge);
        departmentAccount.setVat(previousVat);
        departmentAccount.setConfirmTxnNo(previousConfirmTxnNumber);
        departmentAccount.setReferenceNumber(referenceNumber);
        departmentAccount.setReconcile(false);
        departmentAccount.setVoucherNumber(voucherNumber);
        departmentAccount.setChargeReconcile(false);
        departmentAccount.setVatReconcile(false);
        departmentAccount.setTotalAmount(previousTotalAmount);
        try {

            departmentAccount.setReconcileDate(new Date());
            departmentAccount.setUpdatedAt(new Date());
            departmentAccount.setId(departmentAccount.getId());
            repository.save(departmentAccount);
            DepartmentAccountAudEntity audEntity = mapper.entityToAudEntity(departmentAccount);
            audRepository.save(audEntity);
            log.info("Rtgs Department Account Charge Vat {} Updated", departmentAccount.getAccountNumber());
        } catch (Exception e) {
            log.error("Rtgs Department account  Charge Vat Update Failed");
            e.printStackTrace();
        }
        return departmentAccount;
    }

    private void isExistValidation(DepartmentAccountRequest request, boolean isUpdate, Long id) {
        List<DepartmentAccountEntity> entityList = repository.findAllByIsDeletedFalse();
        entityList.forEach(
                entity -> {
                    if (isUpdate) {
                        if (!entity.getId().equals(id)) {
                            if (entity.getRoutingType().equals(request.getRoutingType()) && entity.getCurrency().getId().equals(request.getCurrencyId()) && entity.getDept().getId().equals(request.getDeptId())) {
                                throw new ResourceAlreadyExistsException("Duplicate Data Found with Department, Currency And Routing");
                            }
                        }

                    } else {
                        if (entity.getRoutingType().equals(request.getRoutingType()) && entity.getCurrency().getId().equals(request.getCurrencyId()) && entity.getDept().getId().equals(request.getDeptId())) {
                            throw new ResourceAlreadyExistsException("Duplicate Data Found with Department, Currency And Routing");
                        }
                    }
                });
    }

}
