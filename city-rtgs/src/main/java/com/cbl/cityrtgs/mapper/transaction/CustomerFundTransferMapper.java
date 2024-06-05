package com.cbl.cityrtgs.mapper.transaction;

import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.department.DepartmentResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.c2c.*;
import com.cbl.cityrtgs.models.entitymodels.transaction.IbTransactionEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.configuration.BankService;
import com.cbl.cityrtgs.services.configuration.BranchService;
import com.cbl.cityrtgs.services.configuration.CurrencyService;
import com.cbl.cityrtgs.services.configuration.DepartmentService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@RequiredArgsConstructor
public class CustomerFundTransferMapper {
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final BranchService branchService;
    private final DepartmentService departmentService;
    private final InterCustomerFundTransferRepository repository;

    public CustomerFndTransferResponse entityToDomainResponse(InterCustomerFundTransferEntity interC2C, List<CustomerFndTransferEntity> c2cTxns) {
        CustomerFndTransferResponse response = new CustomerFndTransferResponse();
        List<CustomerTxnResponse> c2cTxnResponsList = new ArrayList<>();
        if (c2cTxns.size() > 0) {
            CurrencyResponse currency = currencyService.getById(c2cTxns.get(0).getCurrencyId());
            response
                    .setId(interC2C.getId())
                    .setCurrencyId(c2cTxns.get(0).getCurrencyId())
                    .setCurrency(currency.getShortCode())
                    .setTxnTypeCode(interC2C.getTxnTypeCode())
                    .setPriorityCode(c2cTxns.get(0).getPriorityCode())
                    .setParentBatchNumber(interC2C.getBatchNumber())
                    .setEventId(interC2C.getEventId())
                    .setInwardActionStatus(interC2C.getInwardActionStatus())
                    .setReturnReason(interC2C.getReturnReason())
                    .setReturnCode(interC2C.getReturnCode())
                    .setReturnDateTime(interC2C.getReturnDateTime());
            c2cTxns.forEach(c2cTxn -> {
                CustomerTxnResponse customerTxnResponse = this.entityToTxnResponse(c2cTxn, currency.getShortCode());
                c2cTxnResponsList.add(customerTxnResponse);
            });
            response.setCustomerTxnResponseList(c2cTxnResponsList);
        }
        return response;
    }

    public CustomerTxnResponse entityToTxnResponse(CustomerFndTransferEntity c2cTxn, String currencyCode) {
        CustomerTxnResponse txnResponse = new CustomerTxnResponse();
        var interC2C = repository.findAllByBatchNumberAndIsDeletedFalse(c2cTxn.getParentBatchNumber()).get(0);
            if (StringUtils.isBlank(currencyCode)) {
                currencyCode = currencyService.getById(c2cTxn.getCurrencyId()).getShortCode();
            }
            BankResponse benBank = bankService.getBankById(c2cTxn.getBenBankId());
            BranchResponse benBranch = branchService.getBranchById(c2cTxn.getBenBranchId());
            BankResponse payerBank = bankService.getBankById(c2cTxn.getPayerBankId());
            BranchResponse payerBranch = branchService.getBranchById(c2cTxn.getPayerBranchId());
            DepartmentResponse dept = departmentService.getById(c2cTxn.getDepartmentId());
            if (!currencyCode.equals("BDT")) {
                txnResponse
                        .setFcRecBranchId(c2cTxn.getFcRecBranchId())
                        .setFcOrgAccountType(c2cTxn.getFcOrgAccountType())
                        .setFcRecAccountType(c2cTxn.getFcRecAccountType())
                        .setLcNumber(c2cTxn.getLcNumber())
                        .setBillNumber(c2cTxn.getBillNumber());
            }
            txnResponse
                    .setEntryUser(interC2C.getEntryUser())
                    .setApproveBy(StringUtils.isNotBlank(interC2C.getApprover())? interC2C.getApprover(): "")
                    .setId(c2cTxn.getId())
                    .setAmount(c2cTxn.getAmount())
                    .setBatchTxn(c2cTxn.isBatchTxn())
                    .setBenAccNo(c2cTxn.getBenAccNo())
                    .setBenBankId(c2cTxn.getBenBankId())
                    .setBenBankName(Objects.nonNull(benBank) ? benBank.getName() : "Not Found")
                    .setBenBranchId(c2cTxn.getBenBranchId())
                    .setBenBranchName(Objects.nonNull(benBranch) ? benBranch.getName() : "Not Found")
                    .setBenName(c2cTxn.getBenName())
                    .setPayerBankId(c2cTxn.getPayerBankId())
                    .setPayerBankName(Objects.nonNull(payerBank) ? payerBank.getName() : "Not Found")
                    .setPayerBranchId(c2cTxn.getPayerBranchId())
                    .setPayerBranchName(Objects.nonNull(payerBranch) ? payerBranch.getName() : "Not Found")
                    .setReferenceNumber(c2cTxn.getReferenceNumber())
                    .setRoutingType(c2cTxn.getRoutingType())
                    .setTransactionStatus(c2cTxn.getTransactionStatus())
                    .setTxnGlAccount(c2cTxn.getTxnGlAccount())
                    .setDepartmentId(c2cTxn.getDepartmentId())
                    .setDepartmentName(Objects.nonNull(dept) ? dept.getName() : "Not Found")
                    .setDepartmentAccountId(c2cTxn.getDepartmentAccountId())
                    .setBatchTxnChargeWaived(c2cTxn.isBatchTxnChargeWaived())
                    .setVatGl(c2cTxn.getVatGl())
                    .setChargeGl(c2cTxn.getChargeGl())
                    .setVat(c2cTxn.getVat())
                    .setCharge(c2cTxn.getCharge())
                    .setCbsName(c2cTxn.getCbsName())
                    .setNarration(c2cTxn.getNarration())
                    .setFundTransferType(FundTransferType.CustomerToCustomer.toString())
                    .setFailedReason(c2cTxn.getFailedReason())
                    .setRmtCusCellNo(c2cTxn.getRmtCusCellNo())
                    .setRmtDeclareCode(c2cTxn.getRmtDeclareCode())
                    .setRmtCustOfficeCode(c2cTxn.getRmtCustOfficeCode())
                    .setRmtRegYear(c2cTxn.getRmtRegYear())
                    .setRmtRegNum(c2cTxn.getRmtRegNum())
                    .setTransactionDate(c2cTxn.getTransactionDate())
                    .setPayerAccNo(c2cTxn.getPayerAccNo())
                    .setPayerName(c2cTxn.getPayerName())
                    .setBenBankBic(Objects.nonNull(benBank) ? benBank.getBic() : "Not Found")
                    .setBenBranchRouting(Objects.nonNull(benBranch) ? benBranch.getRoutingNumber() : "Not Found")
                    .setCurrency(currencyCode)
                    .setParentBatchNumber(c2cTxn.getParentBatchNumber())
                    .setReturnReason(c2cTxn.getReturnReason())
                    .setStatus(c2cTxn.getVerificationStatus())
                    .setBenAccType(c2cTxn.getFcRecAccountType())
                    .setPayerAccType(c2cTxn.getFcOrgAccountType());
        return txnResponse;
    }

    public InterCustomerFundTransferEntity domainToEntity(String txnTypeCode) {
        InterCustomerFundTransferEntity entity = new InterCustomerFundTransferEntity();
        entity
                .setCreateTime(new Date())
                .setEventId("OPACS008")// comes from workflow setup
                .setRoutingType(RoutingType.Outgoing)
                .setType(0L)
                .setTxnTypeCode(txnTypeCode);
        return entity;
    }

    public InterCustomerFundTransferEntity domainToEntity(IbTransactionRequest domain) {
        InterCustomerFundTransferEntity entity = new InterCustomerFundTransferEntity();
        entity
                .setCreateTime(new Date())
                .setEventId("OPACS008")// comes from workflow setup
                .setRoutingType(RoutingType.Outgoing)
                .setType(0L)
                .setTxnTypeCode(domain.getTransactionTypeCode());
        return entity;
    }

    public CustomerFndTransferEntity domainToCustomerFndEntity(IbTransactionRequest domain, Long currencyId) {
        CustomerFndTransferEntity entity = new CustomerFndTransferEntity();

        entity.setAmount(domain.getAmount())
                .setNarration(ValidationUtility.narrationValidation(domain.getNarration()))
                .setRoutingType(RoutingType.Outgoing)
                .setCurrencyId(currencyId)
                .setPayerAccNo(domain.getPayerAccount())
                .setPayerName(domain.getPayerName())
                .setBenAccNo(domain.getBenAccount())
                .setBenName(domain.getBenName())
                .setRmtCusCellNo(domain.getCustomerMobileNumber())
                .setRmtCustOfficeCode(domain.getCustomsOfficeCode())
                .setRmtDeclareCode(domain.getDeclarantCode())
                .setRmtRegNum(domain.getRegistrationNumber())
                .setRmtRegYear(domain.getRegistrationYear());
        return entity;
    }


    public IbTransactionResponse entityToResponse(IbTransactionEntity entity) {
        IbTransactionResponse response = new IbTransactionResponse();
        BeanUtils.copyProperties(entity, response);
        return response;
    }

    public CustomerFndTransferEntity domainToCustomerFndEntityBatchTxn(CustomerFndTransferTxn domain) {
        CustomerFndTransferEntity entity = new CustomerFndTransferEntity();

        entity.setAmount(domain.getAmount())
                .setNarration(ValidationUtility.narrationValidation(domain.getNarration()))
                .setRoutingType(RoutingType.Outgoing)
                .setPayerAccNo(domain.getPayerAccNo())
                .setPayerName(domain.getPayerName())
                .setPayerInsNo(domain.getPayerInsNo())
                .setBenBankId(domain.getBenBankId())
                .setBenBranchId(domain.getBenBranchId())
                .setBenAccNo(domain.getBenAccNo())
                .setBenName(domain.getBenName())
                .setFcOrgAccountType(domain.getPayerAccType())
                .setFcRecAccountType(domain.getBenAccType())
                .setLcNumber(domain.getLcNumber())
                .setRmtCusCellNo(domain.getRmtCusCellNo())
                .setRmtCustOfficeCode(domain.getRmtCustOfficeCode())
                .setRmtDeclareCode(domain.getRmtDeclareCode())
                .setRmtRegNum(domain.getRmtRegNum())
                .setRmtRegYear(domain.getRmtRegYear());
        return entity;
    }
    public CustomerFndTransferEntity ibEntityToCustomerFndEntity(IbTransactionEntity domain, Long currencyId) {
        CustomerFndTransferEntity entity = new CustomerFndTransferEntity();

        entity.setAmount(domain.getAmount())
                .setNarration(ValidationUtility.narrationValidation(domain.getNarration()))
                .setRoutingType(RoutingType.Outgoing)
                .setCurrencyId(currencyId)
                .setPayerAccNo(domain.getPayerAccount())
                .setPayerName(domain.getPayerName())
                .setBenAccNo(domain.getBenAccount())
                .setBenName(domain.getBenName())
                .setRmtCusCellNo(domain.getCustomerMobileNumber())
                .setRmtCustOfficeCode(domain.getCustomsOfficeCode())
                .setRmtDeclareCode(domain.getDeclarantCode())
                .setRmtRegNum(domain.getRegistrationNumber())
                .setRmtRegYear(domain.getRegistrationYear());
        return entity;
    }
}
