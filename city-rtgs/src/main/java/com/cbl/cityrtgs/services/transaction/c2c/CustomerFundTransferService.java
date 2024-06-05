package com.cbl.cityrtgs.services.transaction.c2c;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.configuration.inwardtransactionConfiguration.InwardTransactionConfigurationResponse;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.report.TxnAuditReport;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.si.CustomerFundTransfer;
import com.cbl.cityrtgs.models.dto.si.CustomerFundTransferTransaction;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.transaction.CustomerFundTransferMapper;
import com.cbl.cityrtgs.models.dto.transaction.*;
import com.cbl.cityrtgs.models.dto.transaction.c2c.*;
import com.cbl.cityrtgs.models.entitymodels.configuration.*;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.ChargeSetupRepository;
import com.cbl.cityrtgs.repositories.configuration.CurrencyRepository;
import com.cbl.cityrtgs.repositories.configuration.SettlementAccountRepository;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.ShadowAccountRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferAudRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.*;
import com.cbl.cityrtgs.services.transaction.returnTxn.ReturnFundTransferService;
import com.cbl.cityrtgs.services.user.UserInfoService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;


@Slf4j
@RequiredArgsConstructor
@Service
public class CustomerFundTransferService {
    private final CustomerFundTransferMapper mapper;
    private final InterCustomerFundTransferRepository interCustomerFundTransferRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final DepartmentAccountService departmentAccountService;
    private final ChargeAccountSetupService chargeAccountSetupService;
    private final CustomerAccountDetailsService customerAccountDetailsService;
    private final UserInfoService userInfoService;
    private final SettlementAccountService settlementAccountService;
    private final MessageGenerateService messageGenerateService;
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final CbsTransactionService cbsTransactionService;
    private final C2COutwardValidationService validationService;
    private final AccountTransactionRegisterService registerService;
    private final CustomerFndTransferAudRepository custTxnAudRepo;
    private final UiAppConfigurationService uiConfigurationService;
    private final BranchService branchService;
    private final TransactionPriorityService transactionPriorityService;
    private final ShadowAccountRepository shadowAccountRepository;
    private final ReturnFundTransferService returnFundTransferService;
    private final SettlementAccountRepository settlementAccountRepository;
    private final NarrationMappingService narrationMappingService;
    private final ChargeSetupRepository chargeSetupRepository;
    private final CbsTransactionLogService cbsTransactionLogService;
    private final TxnCfgSetupService txnCfgSetupService;
    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final InwardTransactionConfigurationService inwardTransactionConfigurationService;
    private final RoutingNumberConfigService routingNumberConfigService;
    private final CurrencyRepository currencyRepository;

    public TransactionResponse createBatchTxn(CustomerFndTransferBatch request) {
        if (!request.getCustomerFndTransferTxns().isEmpty()) {
            //    validationService.validateTransactionTime(request.getTxnTypeCode());
            return createC2CBatchTxn(request);
        }

        return TransactionResponse.builder().error(true).message("Empty list!").build();
    }

    public TransactionResponse createC2CBatchTxn(CustomerFndTransferBatch request) {
        AtomicReference<String> csvFileError = new AtomicReference<>("");
        ChargeSetupResponse batchChargeVat = new ChargeSetupResponse();
        PayerDetailsResponse payerDetailsResponse;
        BranchResponse payerBranch;
        Long payerBranchId;
        String payerName;
        BigDecimal batchChargeAmount = BigDecimal.ZERO;
        BigDecimal batchVatAmount = BigDecimal.ZERO;
        boolean batchCharge = false;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());

        if (userInfoDetails == null) {

            return TransactionResponse.builder().error(true).message("User not found!").build();
        }

        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());

        if (currency == null) {

            return TransactionResponse.builder().error(true).message("Currency not found!").build();
        }
        DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);

        long accMaxLength = uiConfigurationService.getUiConfiguration().get(0).getAccNumberMaxLength();
        long accMinLength = uiConfigurationService.getUiConfiguration().get(0).getAccNumberMinLength();
        if (request.getPayerAccNo() == null || request.getPayerAccNo().equalsIgnoreCase("undefined") || request.getPayerAccNo().trim().isEmpty()) {
            csvFileError.set(csvFileError + "Please Insert Payer Account Number. ");
            return TransactionResponse.builder().error(true).message("Please Insert Payer Account Number. ").build();
        }

        var transactionTypeCode = transactionPriorityService.getDetailsByTransactionTypeCode(request.getTxnTypeCode());
        if (transactionTypeCode == null) {
            csvFileError.set(csvFileError + "Invalid Transaction Type Code!. ");
            return TransactionResponse.builder().error(true).message("Invalid Transaction Type Code!. ").build();

        }
        if (request.getCustomerFndTransferTxns().size() > uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem() && !csvFileError.get().isEmpty()) {
            csvFileError.set("CSV File parsing error. " + csvFileError + "MAX Number Of Transaction in One File: " + this.uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem() + ". ");
            return TransactionResponse.builder().error(true).message(String.valueOf(csvFileError)).build();
        } else if (request.getCustomerFndTransferTxns().size() > uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem() && csvFileError.get().isEmpty()) {
            csvFileError.set("CSV File MAX Number Of Transaction in One File: " + uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem() + ". ");
            return TransactionResponse.builder().error(true).message(String.valueOf(csvFileError)).build();
        } else if (request.getCustomerFndTransferTxns().size() <= uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem() && !csvFileError.get().isEmpty()) {
            csvFileError.set("CSV File parsing error. " + csvFileError);
            return TransactionResponse.builder().error(true).message(String.valueOf(csvFileError)).build();
        } else {
            payerDetailsResponse = customerAccountDetailsService.getAccountDetails(request.getPayerAccNo());
            if (payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                if (!currency.getShortCode().equals(payerDetailsResponse.getCurrencyCode())) {
                    csvFileError.set(csvFileError + "Payer Account And Transaction Currency Not Same!. ");
                    return TransactionResponse.builder().error(true).message("Payer Account And Transaction Currency Not Same!. ").build();

                }
                payerName = payerDetailsResponse.getPayerName().replace("&", "and");
                if (payerDetailsResponse.getPayerBranchName() != null) {
                    String[] payerBranchNameRouting = payerDetailsResponse.getPayerBranchName().split("-");
                    payerBranch = branchService.getBranchByRouting(payerBranchNameRouting[0]);
                    payerBranchId = payerBranch.getId();
                } else {
                    payerBranchId = userInfoDetails.getBranch().getId();
                }
                if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode()) && request.getCustomerFndTransferTxns().get(0).isBatchTxn()) {
                    boolean batchChargeEnabled = customerAccountDetailsService.getChargeEnabled(request.getPayerAccNo(), payerDetailsResponse.getSchemeCode());
                    if (batchChargeEnabled) {
                        batchCharge = true;
                        batchChargeVat = validationService.chargeVatCalculation(request.getPayerAccNo(), request.getCurrencyId(), request.getCustomerFndTransferTxns().get(0).getAmount(), request.getTxnTypeCode(), payerDetailsResponse.getSchemeCode());
                    } else {
                        batchChargeVat.setChargeAmount(BigDecimal.ZERO.doubleValue());
                        batchChargeVat.setVatAmount(BigDecimal.ZERO.doubleValue());
                    }
                    batchChargeAmount = BigDecimal.valueOf(batchChargeVat.getChargeAmount());
                    batchVatAmount = BigDecimal.valueOf(batchChargeVat.getVatAmount());
                }

            } else {
                return TransactionResponse.builder().error(true).message("Payer Details Not found!").build();
            }
            InterCustomerFundTransferEntity transactions = this.createInterCustomerFundTransfer(request.getTxnTypeCode(), userInfoDetails, batchCharge, batchChargeAmount, batchVatAmount);
            if (transactions == null) {
                return TransactionResponse.builder().error(true).message("Outward creation failed!").build();
            }
            for (CustomerFndTransferBatchTxn item : request.getCustomerFndTransferTxns()) {
                Optional<ShadowAccountEntity> shadowAccountEntity = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(item.getBenBankId(), request.getCurrencyId());
                if (!shadowAccountEntity.isPresent()) {
                    return TransactionResponse.builder().error(true).message("Shadow Account Not Found for BIC: " + item.getBenBankBic()).build();
                }
                Boolean validAmount = validationService.validateRtgsBalance(request.getTxnTypeCode(), request.getCurrencyId(), item.getAmount());

                if (validAmount && payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode()))
                    validationService.validateCustomerTxnInputs(item.getBenName().replace("&", "and"), payerName);

                CustomerFndTransferEntity outC2CTxn = new CustomerFndTransferEntity();
                BankResponse beneficiaryBank;
                BankResponse payerBank;
                BranchResponse beneficiaryBranch;
                if (request.getTxnTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
                    outC2CTxn.setRmtCustOfficeCode(item.getRmtCustOfficeCode());
                    outC2CTxn.setRmtRegYear(Integer.parseInt(item.getRmtRegYear()));
                    outC2CTxn.setRmtRegNum(item.getRmtRegNum());
                    outC2CTxn.setRmtDeclareCode(item.getRmtDeclareCode());
                    outC2CTxn.setRmtCusCellNo(item.getRmtCusCellNo());
                }

                if (!request.getTxnTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {

                    beneficiaryBank = bankService.getBankByBicCode(item.getBenBankBic());
                    if (beneficiaryBank != null && !beneficiaryBank.equals(bankService.getOwnerBank())) {
                        outC2CTxn.setBenBankId(beneficiaryBank.getId());
                    } else {
                        csvFileError.set(csvFileError + "Beneficiary Bank BIC Is Not Valid at row number " + item.getIndexNum() + ". ");
                    }

                    beneficiaryBranch = branchService.getBranchByRoutingNumber(item.getBenBranchRoutingNo());
                    if (beneficiaryBranch == null) {
                        csvFileError.set(csvFileError + "Beneficiary Branch Is Not Valid at row number " + item.getIndexNum() + ". ");
                    } else if (!branchService.isValidBranch(beneficiaryBank.getId(), item.getBenBranchRoutingNo())) {
                        csvFileError.set(csvFileError + "Beneficiary Branch Is Not Valid at row number " + item.getIndexNum() + ". ");
                    } else {
                        outC2CTxn.setBenBranchId(beneficiaryBranch.getId());
                    }

                    outC2CTxn.setChargeGl(departmentAccountResponse.getChargeAccNumber());
                    outC2CTxn.setVatGl(departmentAccountResponse.getVatAccNumber());
                }

                payerBank = bankService.getBankByBicCode(bankService.getOwnerBank().getBic());
                if (payerBank != null) {
                    outC2CTxn.setPayerBankId(payerBank.getId());
                } else {
                    csvFileError.set(csvFileError + "Payer Bank BIC is not found. ");
                }

                if (payerBranchId != null) {
                    outC2CTxn.setPayerBranchId(payerBranchId);
                } else {
                    csvFileError.set(csvFileError + "Payer Branch is not found. ");
                }

                if (!request.getTxnTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
                    if ((long) item.getBenAccNo().length() <= accMaxLength && (long) item.getBenAccNo().length() >= accMinLength) {
                        outC2CTxn.setBenAccNo(item.getBenAccNo());
                    } else {
                        csvFileError.set(csvFileError + "Account number should be between" + accMinLength + " & " + accMaxLength + " digits at row number " + item.getIndexNum() + ". ");
                    }
                    outC2CTxn.setBenName(ValidationUtility.validateText(item.getBenName(), 70));
                }

                if (!currency.getShortCode().equals("BDT")) {

                    if (item.getBenAccType() != null) {
                        outC2CTxn.setFcRecAccountType(item.getBenAccType());
                    } else {
                        csvFileError.set(csvFileError + "Beneficiary a/c type is not found " + item.getIndexNum() + ". ");
                    }
                }

                if (!currency.getShortCode().equals("BDT")) {
                    outC2CTxn.setLcNumber(item.getLcNumber());
                }
                outC2CTxn.setAmount(item.getAmount());
                outC2CTxn.setCharge(BigDecimal.ZERO);
                outC2CTxn.setVat(BigDecimal.ZERO);
                outC2CTxn.setBenAccNo(item.getBenAccNo());
                outC2CTxn.setBenName(item.getBenName());
                outC2CTxn.setBenBankId(item.getBenBankId());
                outC2CTxn.setBenBranchId(item.getBenBranchId());
                outC2CTxn.setCurrencyId(request.getCurrencyId());
                outC2CTxn.setDepartmentId(userInfoDetails.getDept().getId());
                outC2CTxn.setDepartmentAccountId(departmentAccountResponse.getId());
                outC2CTxn.setTxnGlAccount(departmentAccountResponse.getAccountNumber());
                outC2CTxn.setCreatedAt(new Date());
                outC2CTxn.setCreatedBy(currentUser.getId());
                outC2CTxn.setParentBatchNumber(transactions.getBatchNumber());
                outC2CTxn.setTransactions(transactions.getId());
                outC2CTxn.setNarration(ValidationUtility.narrationValidation(item.getNarration()));
                outC2CTxn.setBatchTxn(true);
                outC2CTxn.setPriorityCode(request.getPriorityCode());
                outC2CTxn.setRoutingType(RoutingType.Outgoing);
                outC2CTxn.setPayerName(payerName);
                outC2CTxn.setPayerBranchId(payerBranchId);
                outC2CTxn.setPayerAccNo(request.getPayerAccNo());
                outC2CTxn.setFcOrgAccountType(item.getPayerAccType());
                outC2CTxn.setTransactionStatus(TransactionStatus.Submitted.toString());
                outC2CTxn.setVerificationStatus(TransactionVerificationStatus.Submitted.toString());
                try {
                    ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
                    outC2CTxn.setReferenceNumber(referenceNo.getTxnRefNo());
                    outC2CTxn.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
                    outC2CTxn.setSentMsgId(referenceNo.getMessageId());
                    outC2CTxn.setInstrId(referenceNo.getInstrId());
                    outC2CTxn.setEndToEndId(referenceNo.getInstrId());
                    customerFndTransferRepository.save(outC2CTxn);
                } catch (Exception e) {
                    log.error("Customer To customer Batch Txn Failed! {}", e.getMessage());
                }
                log.info("Customer To customer fund transfer Reference Number : {} is saved", outC2CTxn.getReferenceNumber());
            }

            return TransactionResponse.builder().error(false).message("Customer To customer fund transfer has been created successfully").body(transactions.getBatchNumber()).build();
        }
    }

    public TransactionResponse createCustomerFundTransferTransaction(CustomerFndTransfer request, UserInfoEntity user) {
        InterCustomerFundTransferEntity transactions;
        UserInfoEntity userInfoDetails;
        ChargeSetupResponse batchChargeVat;
        PayerDetailsResponse payerDetailsResponse = null;
        BranchResponse payerBranch;
        Long payerBranchId = 0L;
        String payerName = "";
        BigDecimal batchChargeAmount = BigDecimal.ZERO;
        BigDecimal batchVatAmount = BigDecimal.ZERO;
        boolean batchCharge = false;
        if (Objects.isNull(user)) {
            UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

            userInfoDetails = userInfoService.getEntityById(currentUser.getId());

        } else {
            userInfoDetails = user;
        }

        if (userInfoDetails == null) {

            return TransactionResponse.builder().error(true).message("User not found!").build();
        }

        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());

        if (currency == null) {

            return TransactionResponse.builder().error(true).message("Currency not found!").build();
        }

        DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);

        if (departmentAccountResponse == null) {
            return TransactionResponse.builder().error(true).message("Department account not found!").build();
        }

        List<CustomerFndTransferTxn> customerFndTransferTxns = request.getCustomerFndTransferTxns();

        if (customerFndTransferTxns.isEmpty()) {
            return TransactionResponse.builder().error(true).message("Empty transaction list!").build();
        } else {

            if (!(customerFndTransferTxns.get(0).getPayerAccNo() == null || customerFndTransferTxns.get(0).getPayerAccNo().equalsIgnoreCase("undefined") || customerFndTransferTxns.get(0).getPayerAccNo().trim().isEmpty())) {

                payerDetailsResponse = customerAccountDetailsService.getAccountDetails(customerFndTransferTxns.get(0).getPayerAccNo());

                if (payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {

                    if (!currency.getShortCode().equals(payerDetailsResponse.getCurrencyCode())) {
                        return TransactionResponse.builder().error(true).message("Payer Account And Transaction Currency Not Same!. ").build();

                    }
                    payerName = payerDetailsResponse.getPayerName().replace("&", "and");
                    if (payerDetailsResponse.getPayerBranchName() != null) {
                        String[] payerBranchNameRouting = payerDetailsResponse.getPayerBranchName().split("-");
                        payerBranch = branchService.getBranchByRouting(payerBranchNameRouting[0]);
                        payerBranchId = payerBranch.getId();
                    } else {
                        payerBranchId = userInfoDetails.getBranch().getId();
                    }
                    if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode()) && customerFndTransferTxns.get(0).isBatchTxn()) {
                        boolean batchChargeEnabled = customerAccountDetailsService.getChargeEnabled(customerFndTransferTxns.get(0).getPayerAccNo(), payerDetailsResponse.getSchemeCode());
                        if (batchChargeEnabled) {
                            batchCharge = true;
                            batchChargeVat = validationService.chargeVatCalculation(customerFndTransferTxns.get(0).getPayerAccNo(), request.getCurrencyId(), customerFndTransferTxns.get(0).getAmount(), request.getTxnTypeCode(), payerDetailsResponse.getSchemeCode());
                            batchChargeAmount = BigDecimal.valueOf(batchChargeVat.getChargeAmount());
                            batchVatAmount = BigDecimal.valueOf(batchChargeVat.getVatAmount());
                        }
                    }

                } else {
                    return TransactionResponse.builder().error(true).message("Payer Details Not found!").build();
                }
            }
            //   transactions = createInterCustomerFundTransfer(request.getTxnTypeCode(), userInfoDetails, batchCharge, batchChargeAmount, batchVatAmount);

//            if (transactions == null) {
//                return TransactionResponse.builder().error(true).message("Outward creation failed!").build();
//            }
        }
        List<CustomerFndTransferEntity> txnList = new ArrayList<>();
        for (CustomerFndTransferTxn item : customerFndTransferTxns) {
            try {
                ChargeSetupResponse chargeSetupResponse;
                Boolean validAmount = validationService.validateRtgsBalance(request.getTxnTypeCode(), request.getCurrencyId(), item.getAmount());
                Optional<ShadowAccountEntity> shadowAccountEntity = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(item.getBenBankId(), request.getCurrencyId());
                if (!shadowAccountEntity.isPresent()) {
                    return TransactionResponse.builder().error(true).message("Shadow Account Not Found for BIC: " + item.getBenBankBic()).build();
                }
                if (validAmount) {

                    CustomerFndTransferEntity entity = mapper.domainToCustomerFndEntityBatchTxn(item);

                    if (Objects.nonNull(payerDetailsResponse) && payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        chargeSetupResponse = validationService.chargeVatCalculation(item.getPayerAccNo(), request.getCurrencyId(), item.getAmount(), request.getTxnTypeCode(), payerDetailsResponse.getSchemeCode());
                        entity.setPayerBranchId(payerBranchId);
                    } else {
                        chargeSetupResponse = chargeAccountSetupService.getChargeVatByCurrency(request.getCurrencyId(), item.getAmount());
                        if (Objects.nonNull(chargeSetupResponse)) {
                            chargeSetupResponse.setChargeAmount(chargeSetupResponse.getChargeAmount()).setVatAmount(chargeSetupResponse.getVatAmount());
                        } else {
                            chargeSetupResponse.setChargeAmount(BigDecimal.ZERO.doubleValue()).setVatAmount(BigDecimal.ZERO.doubleValue());
                        }
                    }

                    if (validationService.validateName(item.getBenName().replace("&", "and"), payerName)) {
                        return TransactionResponse.builder().error(true).message("Invalid Payer/Beneficiary name!").build();
                    } else {
                        entity.setPayerName(payerName);
                    }

                    entity.setFcOrgAccountType(item.getPayerAccType());
                    entity.setPayerAccNo(item.getPayerAccNo());
                    entity.setPayerBankId(bankService.getOwnerBank().getId());
                    if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
                        entity.setChargeGl(departmentAccountResponse.getChargeAccNumber());
                        entity.setVatGl(departmentAccountResponse.getVatAccNumber());
                        entity.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
                        entity.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
                    }

                    entity.setCurrencyId(request.getCurrencyId());
                    entity.setPriorityCode(request.getPriorityCode());
                    // entity.setParentBatchNumber(transactions.getBatchNumber());
                    //  entity.setTransactions(transactions.getId());
                    entity.setDepartmentId(userInfoDetails.getDept().getId());
                    entity.setDepartmentAccountId(departmentAccountResponse.getId());
                    entity.setTxnGlAccount(departmentAccountResponse.getAccountNumber());
                    entity.setCreatedAt(new Date());
                    entity.setCreatedBy(userInfoDetails.getId());
                    entity.setFcRecAccountType(item.getBenAccType());
                    entity.setTransactionStatus(TransactionStatus.Submitted.toString());
                    entity.setVerificationStatus(TransactionVerificationStatus.Submitted.toString());
                    ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
                    entity.setReferenceNumber(referenceNo.getTxnRefNo());
                    entity.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
                    entity.setSentMsgId(referenceNo.getMessageId());
                    entity.setInstrId(referenceNo.getInstrId());
                    entity.setEndToEndId(referenceNo.getInstrId());
                    entity.setSourceReference(item.getSourceReference());
                    entity.setSourceType(item.getSourceType());
                    txnList.add(entity);
                    //  customerFndTransferRepository.save(entity);
                    log.info("Customer To customer fund transfer Reference Number : {} is saved", entity.getReferenceNumber());

                } else {
                    //interCustomerFundTransferRepository.delete(transactions);
                    return TransactionResponse.builder().error(true).message("Outward Balance and Transaction RTGS Balance Validation Error").build();
                }

            } catch (Exception e) {
                return TransactionResponse.builder().error(true).message(e.getMessage()).build();
            }
        }

        // for loop end
        transactions = createInterCustomerFundTransfer(request.getTxnTypeCode(), userInfoDetails, batchCharge, batchChargeAmount, batchVatAmount);
        // Iterate over each transaction in txnList and modify it
        for (CustomerFndTransferEntity txn : txnList) {
            // Modify the transaction object as needed
            txn.setParentBatchNumber(transactions.getBatchNumber());
            txn.setTransactions(transactions.getId());
            // Add more modifications as required
        }
        customerFndTransferRepository.saveAll(txnList);
        return TransactionResponse.builder().error(false).body(transactions.getBatchNumber()).build();
    }

    public InterCustomerFundTransferEntity createInterCustomerFundTransfer(String txnTypeCode, UserInfoEntity userInfoEntity, Boolean batchCharge, BigDecimal chargeAmount, BigDecimal vatAmount) {

        try {
            InterCustomerFundTransferEntity entity = mapper.domainToEntity(txnTypeCode);
            entity.setBranchId(userInfoEntity.getBranch().getId());
            entity.setVerificationStatus(0); // transaction status make =0, confirmed =5, reject=2, failed=4, pending=3
            entity.setCreatedAt(new Date());
            entity.setCreatedBy(userInfoEntity.getId());
            entity.setEntryUser(userInfoEntity.getUsername());
            entity.setTransactionStatus(TransactionStatus.Submitted);
            entity.setTxnVerificationStatus(TransactionVerificationStatus.Submitted);
            entity.setBatchChargeEnable(batchCharge);
            entity.setChargeAmount(chargeAmount);
            entity.setVatAmount(vatAmount);
            ReferenceGenerateResponse batchNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTA.name());
            entity.setBatchNumber(batchNo.getBatchRefNo());
            entity = interCustomerFundTransferRepository.save(entity);
            log.info("Customer To customer fund transfer BatchNumber : {} is saved", entity.getBatchNumber());
            return entity;
        } catch (Exception e) {
            log.error("{}", e.getMessage());
        }
        return null;
    }


    public void deleteOne(Long id) {
        InterCustomerFundTransferEntity entity = this.getEntityById(id);
        interCustomerFundTransferRepository.delete(entity);
        log.info("Customer to customer fund transfer {} Deleted", id);
    }

    public InterCustomerFundTransferEntity getEntityById(Long id) {
        return interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Transaction not found"));
    }

    public boolean existOne(Long id) {
        return interCustomerFundTransferRepository.existsById(id);
    }

    public TransactionResponse rejectTransaction(Long id, String rejectReason) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(LoggedInUserDetails.getUserInfoDetails().getId());
        List<CustomerFndTransferEntity> outC2CTxns = new ArrayList<>();
        Optional<InterCustomerFundTransferEntity> _interC2COut = interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id);
        if (!_interC2COut.isPresent()) {
            return TransactionResponse.builder().error(true).message("rejectTransaction(): Inter Customer Outward request not found").build();
        }
        InterCustomerFundTransferEntity interC2COut = _interC2COut.get();
        if (interC2COut.getVerificationStatus() == 3) {
            return TransactionResponse.builder().error(true).message("Transaction already Approved!").build();
        }
        if (interC2COut.getVerificationStatus() == 2) {
            return TransactionResponse.builder().error(true).message("Transaction already Rejected!").build();
        }
        for (RoleEntity role : userInfoDetails.getRoles()) {
            if (role.getName().contains("Admin")) {
                outC2CTxns = customerFndTransferRepository.getAllByTransactionsIsDeletedFalse(id);
                break;
            } else {
                outC2CTxns = customerFndTransferRepository.getAllByTransactionsAndDeptIdIsDeletedFalse(id, userInfoDetails.getDept().getId());
            }
        }

        if (outC2CTxns.isEmpty()) {
            return TransactionResponse.builder().error(true).message("Customer To Customer Outward Txn request not found").build();
        }
        for (CustomerFndTransferEntity outC2CTxn : outC2CTxns) {
            outC2CTxn.setRejectedUser(currentUser.getUsername()).setRejectionDateTime(new Date()).setRejectReason(rejectReason).setTransactionStatus(TransactionStatus.Failed.toString()).setVerificationStatus(TransactionVerificationStatus.Rejected.toString()).setTransactions(id).setParentBatchNumber(interC2COut.getBatchNumber()).setId(outC2CTxn.getId());
            try {
                customerFndTransferRepository.save(outC2CTxn);
            } catch (Exception e) {
                return TransactionResponse.builder().error(true).message(e.getMessage()).build();
            }
        }
        interC2COut.setRejectedUser(currentUser.getUsername()).setRejectionDateTime(new Date()).setRejectReason(rejectReason).setVerificationStatus(2)//rejected
                .setTransactionStatus(TransactionStatus.Failed).setTxnVerificationStatus(TransactionVerificationStatus.Rejected).setId(id);

        try {
            interCustomerFundTransferRepository.save(interC2COut);
        } catch (Exception e) {
            return TransactionResponse.builder().error(true).message(e.getMessage()).build();
        }
        log.info("Reject Customer To Customer Outward" + interC2COut.getBatchNumber());
        return TransactionResponse.builder().error(false).message("Reject Customer To Customer Outward" + interC2COut.getBatchNumber()).build();
    }


    public TransactionResponse createTxn(CustomerFndTransfer request) {

        if (!request.getCustomerFndTransferTxns().isEmpty()) {
            return createCustomerFundTransferTransaction(request, null);
        }

        return TransactionResponse.builder().error(true).message("Empty list!").build();
    }

    public CustomerFndTransferResponse getById(Long id) {
        InterCustomerFundTransferEntity interC2C = interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Customer fund transfer not found"));
        List<CustomerFndTransferEntity> C2cTxns = customerFndTransferRepository.findAllByTransactionsAndIsDeletedFalse(interC2C.getId());
        CustomerFndTransferResponse response = mapper.entityToDomainResponse(interC2C, C2cTxns);
        response.setRejectReason(interC2C.getRejectReason());
        response.setRejectDateTime(interC2C.getRejectionDateTime());
        response.setBatchChargeEnable(interC2C.isBatchChargeEnable());
        response.setChargeAmount(interC2C.getChargeAmount());
        response.setVatAmount(interC2C.getVatAmount());
        return response;
    }

    public void returnCustomerFndTransfer(Long id) {
        InterCustomerFundTransferEntity entity = this.getEntityById(id);
        interCustomerFundTransferRepository.delete(entity);
        log.info("Customer to customer fund transfer {} Deleted", id);
    }

    public InterCustomerFundTransferEntity approveInwardTransaction(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity txn) {
        boolean error = false;
        String errorMessage = "";
        String accountDetailName = "";
        var cbsTxnExist = cbsTransactionLogService.cbsTransactionExists(txn.getReferenceNumber());
        if (!cbsTxnExist) {
            CbsResponse cbsResponse;
            SettlementAccountEntity settlementAccount = null;
            var currency = "";
            try {
                var optCurrency = currencyRepository.findById(txn.getCurrencyId());
                if (optCurrency.isPresent()) {
                    currency = optCurrency.get().getShortCode();
                } else {
                    error = true;
                    errorMessage = "Currency Not Found";
                }
                Optional<SettlementAccountEntity> optionalSettlementAccount = settlementAccountRepository.findByCurrencyIdAndIsDeletedFalse(txn.getCurrencyId());
                if (optionalSettlementAccount.isPresent()) {
                    settlementAccount = optionalSettlementAccount.get();
                } else {
                    error = true;
                    errorMessage = "Settlement Account Not Found";
                }
                String cbsName = customerAccountDetailsService.getCbsName(txn.getBenAccNo());
                DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(txn.getDepartmentId(), txn.getCurrencyId(), RoutingType.Incoming);
                if (departmentAccount == null) {
                    error = true;
                    errorMessage = errorMessage + " " + "Department Account Not Found";
                }
                txn.setBatchTxnChargeWaived(true);

                if (cbsName.equals(CbsName.CARD.toString())) {
                    CardDetailsResponse cardDetailsResponse = customerAccountDetailsService.getCardDetails(txn.getBenAccNo());
                    if (!cardDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        error = true;
                        errorMessage = cardDetailsResponse.getResponseMessage();
                    } else {
                        accountDetailName = cardDetailsResponse.getCardHolderName();
                        var verifyCustomer = this.verifyCustomerName(accountDetailName, txn.getBenName());
                        if (verifyCustomer.isError()) {
                            error = true;
                            errorMessage = errorMessage + " " + verifyCustomer.getMessage();
                        }
                    }
                } else {
                    PayerDetailsResponse beneficiaryDetails = customerAccountDetailsService.getAccountDetails(txn.getBenAccNo());
                    if (!beneficiaryDetails.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        error = true;
                        errorMessage = errorMessage + " " + "Account Details Not found";
                    } else {
                        accountDetailName = beneficiaryDetails.getPayerName();
                        var verifyCustomer = this.verifyCustomerName(accountDetailName, txn.getBenName());
                        if (verifyCustomer.isError()) {
                            error = true;
                            errorMessage = errorMessage + " " + verifyCustomer.getMessage();
                        }
                        //routing number check
                        Long benBranchId = branchService.getBenBranchIdByRouting(beneficiaryDetails.getPayerBranchName());
                        if (routingNumberConfigService.getRoutingNumberSetup().getIsInwardTxn()) {
                            // benBranchId = entity.getId();
                            if (!benBranchId.equals(txn.getBenBranchId())) {
                                error = true;
                                errorMessage = errorMessage + " " + "Routing Number Mismatch";
                            }
                        }
                    }
                }

                if (!error) {
                    TransactionRequest transactionRequest = new TransactionRequest();
                    transactionRequest.setVat(BigDecimal.ZERO).setCharge(BigDecimal.ZERO).setAmount(txn.getAmount()).setCbsName(cbsName).setChargeEnabled(false).setNarration(narrationMappingService.getC2CTransactionNarration(txn, false, false)).setDrAccount(txn.getTxnGlAccount()).setCrAccount(txn.getBenAccNo()).setChargeAccount(StringUtils.isNotBlank(departmentAccount.getChargeAccNumber()) ? departmentAccount.getChargeAccNumber() : txn.getTxnGlAccount()).setVatAccount(StringUtils.isNotBlank(departmentAccount.getVatAccNumber()) ? departmentAccount.getVatAccNumber() : txn.getTxnGlAccount()).setSettlementAccountId(settlementAccount.getId()).setCurrencyId(txn.getCurrencyId()).setCurrencyCode(currency).setRtgsRefNo(txn.getReferenceNumber()).setParticular2(StringUtils.isNotBlank(txn.getLcNumber()) ? txn.getLcNumber().trim() : narrationMappingService.getC2CTransactionNarration(txn, false, false)).setRemarks(txn.getReferenceNumber() + "-" + bankService.getBankById(txn.getPayerBankId()).getBic()).setChargeRemarks(txn.getParentBatchNumber()).setAbabilRequestId(txn.getAbabilReferenceNumber());
                    cbsResponse = cbsTransactionService.cbsInwardTransaction(transactionRequest, txn.getBenAccNo());
                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        //PendingTransaction
                        txn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                        fundTransfer.setApprovalDateTime(new Date()).setApprover("SYSTEM").setVerificationStatus(3).setVerifyDateTime(new Date()).setVerifier("SYSTEM").setId(fundTransfer.getId()).setTransactionStatus(TransactionStatus.Confirmed);
                        txn.setCbsName(cbsName).setSettlementDate(new Date()).setTransactionDate(new Date()).setTransactionStatus(TransactionStatus.Confirmed.toString());
                        registerService.doRegister(txn.getRoutingType(), txn.getCurrencyId(), txn.getAmount(), cbsResponse.getTransactionRefNumber(), txn.getPayerBankId(), txn.getBenBankId(), txn.getPayerBranchId(), txn.getBenBranchId(), new Date(), new Date(), txn.getReferenceNumber(), txn.getBenAccNo(), txn.getNarration(), txn.getCbsName());
                        departmentAccountService.departmentAccountTransaction(txn.getAmount(), txn.getRoutingType(), txn.getDepartmentId(), FundTransferType.CustomerToCustomer, false, txn.getCharge(), txn.getVat(), txn.getReferenceNumber(), txn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                    } else {
                        error = true;
                        errorMessage = cbsResponse.getResponseMessage();
                    }
                }
            } catch (Exception e) {
                error = true;
                errorMessage = errorMessage + " " + e.getMessage();
            } finally {
                if (error) {
                    ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.IN.name());
                    fundTransfer.setApprovalDateTime(new Date());
                    fundTransfer.setApprover("SYSTEM");
                    fundTransfer.setVerificationStatus(4);
                    fundTransfer.setTransactionStatus(TransactionStatus.Failed);
                    fundTransfer.setFailedReason(errorMessage);
                    fundTransfer.setId(txn.getTransactions());
                    txn.setSettlementDate(new Date());
                    txn.setTransactionDate(new Date());
                    txn.setTransactionStatus(TransactionStatus.Failed.toString());
                    txn.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
                    txn.setFailedReason(errorMessage);
                    txn.setErrorMsg(errorMessage);
                }
                fundTransfer = interCustomerFundTransferRepository.save(fundTransfer);
                txn = customerFndTransferRepository.save(txn);

            }
            log.info("Inward customer fund transfer Approved " + txn.getReferenceNumber());
        } else {
            log.info("Inward customer fund transfer Already Approved " + txn.getReferenceNumber());
        }
        return fundTransfer;
    }

    public void updateStatus(InterCustomerFundTransferEntity interc2c, CustomerFndTransferEntity c2cTxn, TransactionStatus status, String msgId) {
        interc2c.setTransactionStatus(status);
        c2cTxn.setTransactionStatus(status.toString());
        interc2c = interCustomerFundTransferRepository.save(interc2c);
        log.info("Update Status complete Inter Customer Fund Transfer :" + msgId);
        c2cTxn = customerFndTransferRepository.save(c2cTxn);
        log.info("Update Status complete in Customer Fund Transfer Txn:" + msgId);
        log.info("Update Status complete in audit table:");
    }

    public void updateBenAccNo(Long id, String benAccNo) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        InterCustomerFundTransferEntity interC2C = interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Customer fund transfer not found"));
        List<CustomerFndTransferEntity> C2cTxns = customerFndTransferRepository.getAllByTransactionsAndParentBatchNumber(interC2C.getId(), interC2C.getBatchNumber());

        if (!C2cTxns.isEmpty()) {
            CustomerFndTransferEntity entity = C2cTxns.get(0);
            entity.setBenAccNoOrg(C2cTxns.get(0).getBenAccNo());
            entity.setBenAccNo(ValidationUtility.validateText(benAccNo, 17));

            entity.setId(entity.getId());
            entity.setCreatedAt(entity.getCreatedAt());
            entity.setCreatedBy(entity.getCreatedBy());
            entity.setUpdatedBy(currentUser.getId());
            entity.setUpdatedAt(new Date());
            customerFndTransferRepository.save(entity);
        }
        log.info("Update Beneficiary Account Number in Customer Fund Transfer Txn:" + benAccNo);
    }

//    public ResponseDTO createCustomerFundTransferTransaction(CustomerFundTransfer request, UserInfoEntity currentUser) {
//        BranchResponse payerBranch;
//        Long payerBranchId = 0L;
//        String payerName = "";
//        CustomerFundTransferTransaction item = request.getCustomerFundTransferTransaction();
//        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
//
//        Boolean validAmount = validationService.validateRtgsBalance(request.getTransactionTypeCode(), request.getCurrencyId(), item.getAmount());
//
//        if (validAmount) {
//
//            CustomerFndTransferEntity entity = CustomerFundTransferTransaction.toEntityModel(item);
//            entity.setPayerBankId(bankService.getOwnerBank().getId());
//
//            if (!(item.getPayerAccNo() == null || item.getPayerAccNo().equalsIgnoreCase("undefined") || item.getPayerAccNo().trim().isEmpty())) {
//
//                PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(item.getPayerAccNo());
//
//                if (payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
//
//                    if (validationService.isPayerNameInvalid(payerDetailsResponse.getPayerName().replace("&", "and"))) {
//
//                        return ResponseDTO.builder().error(true).body("Invalid Payer Name!").build();
//                    }
//                    payerName = payerDetailsResponse.getPayerName().replace("&", "and");
//                    if (payerDetailsResponse.getPayerBranchName() != null) {
//                        String[] payerBranchNameRouting = payerDetailsResponse.getPayerBranchName().split("-");
//                        payerBranch = branchService.getBranchByRouting(payerBranchNameRouting[0]);
//                        payerBranchId = payerBranch.getId();
//                    } else {
//                        payerBranchId = userInfoDetails.getBranch().getId();
//                    }
//                } else {
//                    return ResponseDTO.builder().error(true).message("Payer Details Not found!").build();
//                }
//                entity.setFcOrgAccountType(payerDetailsResponse.getAcctType());
//                entity.setPayerAccNo(item.getPayerAccNo());
//            }
//            entity.setPayerBranchId(payerBranchId);
//            entity.setPayerName(payerName);
//            DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);
//
//            if (request.getTransactionTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
//
//                ChargeSetupResponse chargeSetupResponse = chargeAccountSetupService.getChargeSetupResponseByCurrency(request.getCurrencyId(), item.getAmount());
//
//                entity.setChargeGl(departmentAccountResponse.getChargeAccNumber());
//                entity.setVatGl(departmentAccountResponse.getVatAccNumber());
//                entity.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
//                entity.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
//            }
//            InterCustomerFundTransferEntity interCustomerFundTransferEntity = createInterCustomerFundTransfer(request.getTransactionTypeCode(), userInfoDetails, false, BigDecimal.ZERO, BigDecimal.ZERO);
//
//            entity.setCurrencyId(request.getCurrencyId());
//            entity.setPriorityCode(request.getPriorityCode());
//            entity.setParentBatchNumber(interCustomerFundTransferEntity.getBatchNumber());
//            entity.setTransactions(interCustomerFundTransferEntity.getId());
//            entity.setDepartmentId(userInfoDetails.getDept().getId());
//            entity.setDepartmentAccountId(departmentAccountResponse.getId());
//            entity.setTxnGlAccount(departmentAccountResponse.getAccountNumber());
//            entity.setCreatedAt(new Date());
//            entity.setCreatedBy(currentUser.getId());
//            entity.setFcOrgAccountType(item.getPayerAccType());
//            entity.setFcRecAccountType(item.getBenAccType());
//            entity.setRoutingType(RoutingType.Outgoing);
//            entity.setSourceType(1L);
//            ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
//            entity.setReferenceNumber(referenceNo.getTxnRefNo());
//            entity.setSentMsgId(referenceNo.getMessageId());
//            entity.setInstrId(referenceNo.getInstrId());
//            entity.setEndToEndId(referenceNo.getInstrId());
//            CustomerFndTransferEntity customerFndTransferEntity = customerFndTransferRepository.save(entity);
//
//            return ResponseDTO.builder().error(false).body(customerFndTransferEntity).build();
//        }
//
//        return ResponseDTO.builder().error(true).message("Outward Balance and Transaction RTGS Balance Validation Error").build();
//    }

    public ResponseDTO executeTransaction(Long id, String siAmountType, UserInfoEntity currentUser) {

        boolean error = false;
        CbsResponse cbsResponse = new CbsResponse();
        Optional<InterCustomerFundTransferEntity> optional = interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id);

        if (optional.isEmpty()) {

            return ResponseDTO.builder().error(true).message("Inter Customer Fund Transfer Entity Not Found").build();
        }

        InterCustomerFundTransferEntity interC2COut = optional.get();
        CustomerFndTransferEntity outC2CTxn = customerFndTransferRepository.findByTransactionsAndIsDeletedFalse(id).get(0);

        try {

            Optional<ShadowAccountEntity> shadowAccountEntity = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(outC2CTxn.getBenBankId(), outC2CTxn.getCurrencyId());

            if (shadowAccountEntity.isEmpty()) {

                return ResponseDTO.builder().error(true).message("Shadow Account Not Found").build();
            }

            PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(outC2CTxn.getPayerAccNo());

            if (payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {

                boolean chargeEnabled = false;

                DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(currentUser.getDept().getId(), outC2CTxn.getCurrencyId(), RoutingType.Outgoing);

                if (StringUtils.isNotBlank(departmentAccount.getChargeAccNumber()) && StringUtils.isNotBlank(departmentAccount.getVatAccNumber())) {

                    chargeEnabled = customerAccountDetailsService.getChargeEnabled(outC2CTxn.getPayerAccNo(), payerDetailsResponse.getSchemeCode());
                    outC2CTxn.setBatchTxnChargeWaived(!chargeEnabled);
                }

                if (interC2COut.getTxnTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode()) || interC2COut.getTxnTypeCode().equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode())) {
                    outC2CTxn.setBatchTxnChargeWaived(true);
                    chargeEnabled = false;
                }

                CurrencyResponse currency = currencyService.getById(outC2CTxn.getCurrencyId());
                ChargeSetupResponse chargeSetupResponse = validationService.chargeVatCalculation(outC2CTxn.getPayerAccNo(), currency.getId(), outC2CTxn.getAmount(), interC2COut.getTxnTypeCode(), payerDetailsResponse.getSchemeCode());

                boolean transactionFlag = true;
                TransactionRequest transactionRequest = new TransactionRequest();
                BigDecimal totalAmount;
                switch (siAmountType) {
                    case "FIXEDAMOUNT" -> {
                        if (chargeEnabled) {
                            transactionRequest.setAmount(outC2CTxn.getAmount());
                            transactionRequest.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
                            transactionRequest.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
                        } else {
                            transactionRequest.setAmount(outC2CTxn.getAmount());
                            transactionRequest.setCharge(BigDecimal.ZERO);
                            transactionRequest.setVat(BigDecimal.ZERO);
                        }
                    }
                    case "FULLBALANCE" -> {
                        totalAmount = BigDecimal.valueOf(Double.parseDouble(payerDetailsResponse.getAvailBalance()));
                        if (chargeEnabled) {
                            totalAmount = totalAmount.subtract(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount())).subtract(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));

                            transactionRequest.setAmount(totalAmount);
                            transactionRequest.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
                            transactionRequest.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
                        } else {
                            transactionRequest.setAmount(totalAmount);
                            transactionRequest.setCharge(BigDecimal.ZERO);
                            transactionRequest.setVat(BigDecimal.ZERO);
                        }
                    }
                    case "RESTAMOUNT" -> {
                        totalAmount = BigDecimal.valueOf(Double.parseDouble(payerDetailsResponse.getAvailBalance())).subtract(outC2CTxn.getAmount());
                        if (chargeEnabled) {
                            totalAmount = totalAmount.subtract(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount())).subtract(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
                            transactionRequest.setAmount(totalAmount);
                            transactionRequest.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
                            transactionRequest.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
                        } else {
                            transactionRequest.setAmount(totalAmount);
                            transactionRequest.setCharge(BigDecimal.ZERO);
                            transactionRequest.setVat(BigDecimal.ZERO);
                        }
                    }
                }

                if (transactionFlag) {
                    SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(outC2CTxn.getCurrencyId());
                    String cbsName = customerAccountDetailsService.getCbsName(outC2CTxn.getPayerAccNo());
                    ChargeSetupEntity charge;
                    if (cbsName.equals(CbsName.ABABIL.toString())) {
                        Optional<ChargeSetupEntity> optCharge = chargeSetupRepository.findByCurrencyIdAndIsDeletedFalse(outC2CTxn.getCurrencyId());
                        if (optCharge.isPresent()) {
                            charge = optCharge.get();
                            transactionRequest.setChargeAccount(charge.getChargeGl()).setVatAccount(charge.getVatGl());
                        }
                    } else {
                        transactionRequest.setChargeAccount(departmentAccount.getChargeAccNumber()).setVatAccount(departmentAccount.getVatAccNumber());
                    }
                    transactionRequest.setNarration(ValidationUtility.narrationValidation(narrationMappingService.getC2CTransactionNarration(outC2CTxn, false, false))).setCbsName(cbsName).setChargeEnabled(chargeEnabled).setDrAccount(outC2CTxn.getPayerAccNo()).setCrAccount(outC2CTxn.getTxnGlAccount()).setSettlementAccountId(settlementAccount.getId()).setCurrencyId(outC2CTxn.getCurrencyId()).setCurrencyCode(currency.getShortCode()).setRtgsRefNo(outC2CTxn.getReferenceNumber()).setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(outC2CTxn.getLcNumber()) ? outC2CTxn.getLcNumber() : outC2CTxn.getNarration()))).setRemarks(outC2CTxn.getReferenceNumber() + "-" + bankService.getBankById(outC2CTxn.getBenBankId()).getBic()).setChargeRemarks(outC2CTxn.getParentBatchNumber()).setAbabilRequestId(outC2CTxn.getAbabilReferenceNumber());

                    cbsResponse = cbsTransactionService.cbsTransaction(transactionRequest);
                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        interC2COut
                                .setApprovalDateTime(new Date())
                                .setApprover(currentUser.getUsername())
                                .setVerificationStatus(3)
                                .setTxnVerificationStatus(TransactionVerificationStatus.Approved)
                                .setTransactionStatus(TransactionStatus.Pending)
                                .setVerifyDateTime(new Date())
                                .setVerifier(currentUser.getUsername())
                                .setSettlementDate(new Date())
                                .setTransactionDate(new Date())
                                .setId(outC2CTxn.getTransactions());
                        outC2CTxn
                                .setVoucherNumber(cbsResponse.getTransactionRefNumber())
                                //.setCbsName(customerAccountDetailsService.getCbsName(customerFndTransferEntity.getPayerAccNo()))
                                .setCbsName(cbsName)
                                .setId(outC2CTxn.getId())
                                .setSettlementDate(new Date())
                                .setTransactionDate(new Date())
                                .setTransactionStatus(TransactionStatus.Pending.toString())
                                .setVerificationStatus(TransactionVerificationStatus.Approved.toString());
                        CustomerFndTransferEntity update = customerFndTransferRepository.save(outC2CTxn);
                        if (update.getId() > 0) {
                            interCustomerFundTransferRepository.save(interC2COut);
                            messageGenerateService.processPACS008OutwardRequest(interC2COut, outC2CTxn, currentUser.getId());
                            registerService.doRegister(outC2CTxn.getRoutingType(), outC2CTxn.getCurrencyId(), outC2CTxn.getAmount(), outC2CTxn.getVoucherNumber(), outC2CTxn.getPayerBankId(), outC2CTxn.getBenBankId(), outC2CTxn.getPayerBranchId(), outC2CTxn.getBenBranchId(), new Date(), new Date(), outC2CTxn.getReferenceNumber(), outC2CTxn.getBenAccNo(), outC2CTxn.getNarration(), outC2CTxn.getCbsName());
                            departmentAccountService.departmentAccountTransaction(outC2CTxn.getAmount(), RoutingType.Outgoing, currentUser.getDept().getId(), FundTransferType.CustomerToCustomer, false, outC2CTxn.getCharge(), outC2CTxn.getVat(), outC2CTxn.getReferenceNumber(), outC2CTxn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                        }
                    } else {
                        error = true;
                        interC2COut.setVerificationStatus(4);
                        interC2COut.setFailedReason("Cbs Transaction Failed Reason : " + cbsResponse.getResponseMessage());
                        interC2COut.setId(outC2CTxn.getTransactions());
                        outC2CTxn.setId(outC2CTxn.getId());
                        outC2CTxn.setTransactionDate(new Date());
                        outC2CTxn.setTransactionStatus(TransactionStatus.Failed.toString());
                        outC2CTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                        outC2CTxn.setErrorMsg("CbsError");
                        outC2CTxn.setFailedReason(cbsResponse.getResponseMessage());
                        outC2CTxn.setVerificationStatus(TransactionVerificationStatus.Failed.toString());
                        ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
                        outC2CTxn.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
                        customerFndTransferRepository.save(outC2CTxn);
                        interCustomerFundTransferRepository.save(interC2COut);
                    }

//                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
//
//                        Date date = new Date();
//
//                        customerFndTransferEntity
//                                .setVoucherNumber(cbsResponse.getTransactionRefNumber())
//                                .setCbsName(cbsName)
//                                .setId(customerFndTransferEntity.getId())
//                                .setSettlementDate(new Date())
//                                .setTransactionDate(date)
//                                .setTransactionStatus(TransactionStatus.Pending.toString());
//
//                        interC2COut
//                                .setApprovalDateTime(date)
//                                .setApprover(currentUser.getUsername())
//                                .setVerificationStatus(3)
//                                .setVerifyDateTime(date)
//                                .setVerifier(currentUser.getUsername())
//                                .setId(customerFndTransferEntity.getTransactions());
//
//                        // customerFndTransferEntity.setSentMsgId(messageGenerateService.processPACS008OutwardRequest(interC2COut, customerFndTransferEntity, currentUser.getId()));
//                        messageGenerateService.processPACS008OutwardRequest(interC2COut, customerFndTransferEntity, currentUser.getId());
//
//                        registerService.doRegister(customerFndTransferEntity.getRoutingType(), customerFndTransferEntity.getCurrencyId(), customerFndTransferEntity.getAmount(), customerFndTransferEntity.getVoucherNumber(), customerFndTransferEntity.getPayerBankId(), customerFndTransferEntity.getBenBankId(), customerFndTransferEntity.getPayerBranchId(), customerFndTransferEntity.getBenBranchId(), date, date, customerFndTransferEntity.getReferenceNumber(), customerFndTransferEntity.getBenAccNo(), customerFndTransferEntity.getNarration(), customerFndTransferEntity.getCbsName());
//                        departmentAccountService.departmentAccountTransaction(customerFndTransferEntity.getAmount(), RoutingType.Outgoing, currentUser.getDept().getId(), FundTransferType.CustomerToCustomer, false, customerFndTransferEntity.getCharge(), customerFndTransferEntity.getVat(), customerFndTransferEntity.getReferenceNumber(), customerFndTransferEntity.getCurrencyId(), cbsResponse.getTransactionRefNumber());
//                    } else {
//                        error = true;
//                    }
                } else {
                    error = true;
                }
            } else {
                error = true;
            }
        } catch (Exception e) {
            error = true;
            log.error("{}", e.getMessage());
        }
        if (error) {
            interC2COut.setVerificationStatus(4);
            interC2COut.setFailedReason(cbsResponse.getResponseMessage());
            interC2COut.setId(outC2CTxn.getTransactions());
            outC2CTxn.setId(outC2CTxn.getId());
            outC2CTxn.setTransactionDate(new Date());
            outC2CTxn.setTransactionStatus(TransactionStatus.Failed.toString());
            outC2CTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
            outC2CTxn.setVerificationStatus(TransactionVerificationStatus.Failed.toString());
            customerFndTransferRepository.save(outC2CTxn);
            interCustomerFundTransferRepository.save(interC2COut);
            return ResponseDTO.builder().error(true).message("Transaction Failed!").body(cbsResponse).build();
        } else {
            return ResponseDTO.builder().error(false).message("Transaction Successful!").body(cbsResponse).build();
        }
    }


    public Map<String, Object> getC2CTxnAuditListByReference(String reference) {
        Map<String, Object> response = new HashMap<>();
        List<TxnAuditReport> c2cTxnAudList = new ArrayList<>(custTxnAudRepo.getC2CTxnAuditListByReference(reference));
        response.put("result", c2cTxnAudList);
        return response;
    }

    public CustomerFndTransferEntity save(CustomerFndTransferEntity customerFndTransferEntity) {

        return customerFndTransferRepository.save(customerFndTransferEntity);
    }

    public TransactionResponse updateTxn(Long id, CustomerFndTransfer request) {
        InterCustomerFundTransferEntity fundTransfer;

        Optional<InterCustomerFundTransferEntity> optional = interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id);

        if (optional.isEmpty()) {

            return TransactionResponse.builder().error(true).message("Inter Customer Fund Transfer Entity Not Found").build();
        }

        fundTransfer = optional.get();

        if (!request.getCustomerFndTransferTxns().isEmpty()) {
            return updateCheckerRejectedC2cTxn(fundTransfer, request);
        }

        return TransactionResponse.builder().error(true).message("Empty list!").build();
    }

    public TransactionResponse updateCheckerRejectedC2cTxn(InterCustomerFundTransferEntity transactions, CustomerFndTransfer request) {
        ChargeSetupResponse batchChargeVat;
        PayerDetailsResponse payerDetailsResponse = null;
        BranchResponse payerBranch;
        Long payerBranchId = 0L;
        String payerName = "";
        BigDecimal batchChargeAmount = BigDecimal.ZERO;
        BigDecimal batchVatAmount = BigDecimal.ZERO;
        boolean batchCharge = false;

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());

        if (userInfoDetails == null) {

            return TransactionResponse.builder().error(true).message("User not found!").build();
        }

        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());

        if (currency == null) {

            return TransactionResponse.builder().error(true).message("Currency not found!").build();
        }

        DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);

        if (departmentAccountResponse == null) {
            return TransactionResponse.builder().error(true).message("Department account not found!").build();
        }

        List<CustomerFndTransferTxn> customerFndTransferTxns = request.getCustomerFndTransferTxns();
        if (customerFndTransferTxns.isEmpty()) {
            return TransactionResponse.builder().error(true).message("Empty transaction list!").build();
        } else {

            if (!(customerFndTransferTxns.get(0).getPayerAccNo() == null || customerFndTransferTxns.get(0).getPayerAccNo().equalsIgnoreCase("undefined") || customerFndTransferTxns.get(0).getPayerAccNo().trim().isEmpty())) {

                payerDetailsResponse = customerAccountDetailsService.getAccountDetails(customerFndTransferTxns.get(0).getPayerAccNo());

                if (payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {

                    payerName = payerDetailsResponse.getPayerName().replace("&", "and");
                    if (payerDetailsResponse.getPayerBranchName() != null) {
                        String[] payerBranchNameRouting = payerDetailsResponse.getPayerBranchName().split("-");
                        payerBranch = branchService.getBranchByRouting(payerBranchNameRouting[0]);
                        payerBranchId = payerBranch.getId();
                    } else {
                        payerBranchId = userInfoDetails.getBranch().getId();
                    }
                    if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode()) && customerFndTransferTxns.get(0).isBatchTxn()) {
                        boolean batchChargeEnabled = customerAccountDetailsService.getChargeEnabled(customerFndTransferTxns.get(0).getPayerAccNo(), payerDetailsResponse.getSchemeCode());
                        if (batchChargeEnabled) {
                            batchChargeVat = validationService.chargeVatCalculation(customerFndTransferTxns.get(0).getPayerAccNo(), request.getCurrencyId(), customerFndTransferTxns.get(0).getAmount(), request.getTxnTypeCode(), payerDetailsResponse.getSchemeCode());
                            batchCharge = true;
                            batchChargeAmount = BigDecimal.valueOf(batchChargeVat.getChargeAmount());
                            batchVatAmount = BigDecimal.valueOf(batchChargeVat.getVatAmount());
                        }
                    }

                } else {
                    return TransactionResponse.builder().error(true).message("Payer Details Not found!").build();
                }
            }
        }

        List<CustomerFndTransferEntity> txnEntities = customerFndTransferRepository.findAllByTransactionsAndIsDeletedFalse(transactions.getId());
        txnEntities.forEach(deleteEntity -> this.deleteCustomerFndTransferTxn(deleteEntity.getId()));
        for (CustomerFndTransferTxn item : customerFndTransferTxns) {
            try {
                BigDecimal chargeAmount = item.getCharge();
                BigDecimal vatAmount = item.getVat();
                Boolean validAmount = validationService.validateRtgsBalance(request.getTxnTypeCode(), request.getCurrencyId(), item.getAmount());
                Optional<ShadowAccountEntity> shadowAccountEntity = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(item.getBenBankId(), request.getCurrencyId());
                if (!shadowAccountEntity.isPresent()) {
                    return TransactionResponse.builder().error(true).message("Shadow Account Not Found for BIC: " + item.getBenBankBic()).build();
                }
                if (validAmount) {
                    CustomerFndTransferEntity entity = mapper.domainToCustomerFndEntityBatchTxn(item);
                    entity.setPayerName(StringUtils.isNotBlank(payerName) ? payerName : "");
                    entity.setFcOrgAccountType(Objects.nonNull(payerDetailsResponse) && StringUtils.isNotBlank(payerDetailsResponse.getAcctType()) ? payerDetailsResponse.getAcctType() : "");
                    entity.setPayerAccNo(item.getPayerAccNo());
                    entity.setPayerBranchId(payerBranchId);
                    entity.setPayerBankId(bankService.getOwnerBank().getId());
                    if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode()) && !item.isBatchTxn()) {
                        entity.setChargeGl(departmentAccountResponse.getChargeAccNumber());
                        entity.setVatGl(departmentAccountResponse.getVatAccNumber());
                        entity.setCharge(chargeAmount);
                        entity.setVat(vatAmount);
                    } else {
                        entity.setCharge(BigDecimal.ZERO);
                        entity.setVat(BigDecimal.ZERO);
                    }

                    entity.setCurrencyId(request.getCurrencyId());
                    entity.setPriorityCode(request.getPriorityCode());
                    entity.setParentBatchNumber(transactions.getBatchNumber());
                    entity.setTransactions(transactions.getId());
                    entity.setDepartmentId(userInfoDetails.getDept().getId());
                    entity.setDepartmentAccountId(departmentAccountResponse.getId());
                    entity.setTxnGlAccount(departmentAccountResponse.getAccountNumber());
                    entity.setCreatedAt(new Date());
                    entity.setCreatedBy(currentUser.getId());
                    entity.setFcOrgAccountType(item.getPayerAccType());
                    entity.setFcRecAccountType(item.getBenAccType());
                    entity.setTransactionStatus(TransactionStatus.Submitted.toString());
                    entity.setVerificationStatus(TransactionVerificationStatus.Submitted.toString());
                    ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
                    entity.setReferenceNumber(referenceNo.getTxnRefNo());
                    entity.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
                    entity.setSentMsgId(referenceNo.getMessageId());
                    entity.setInstrId(referenceNo.getInstrId());
                    entity.setEndToEndId(referenceNo.getInstrId());
                    customerFndTransferRepository.save(entity);

                    transactions.setBatchNumber(transactions.getBatchNumber());
                    transactions.setBranchId(transactions.getBranchId());
                    transactions.setVerificationStatus(0); // transaction status make =0, confirmed =5, reject=2, failed=4, pending=3
                    transactions.setCreatedBy(transactions.getCreatedBy());
                    transactions.setEntryUser(transactions.getEntryUser());
                    transactions.setUpdatedAt(new Date());
                    transactions.setUpdatedBy(currentUser.getId());
                    transactions.setTransactionStatus(TransactionStatus.Submitted);
                    transactions.setTxnVerificationStatus(TransactionVerificationStatus.Submitted);
                    transactions.setId(transactions.getId());
                    transactions.setBatchChargeEnable(batchCharge);
                    transactions.setChargeAmount(batchChargeAmount);
                    transactions.setVatAmount(batchVatAmount);
                    interCustomerFundTransferRepository.save(transactions);
                    log.info("Customer To customer fund transfer Reference Number : {} is Updated", entity.getReferenceNumber());
                } else {
                    return TransactionResponse.builder().error(true).message("Outward Balance and Transaction RTGS Balance Validation Error").build();
                }

            } catch (Exception e) {

                return TransactionResponse.builder().error(true).message(e.getMessage()).build();
            }
        }

        return TransactionResponse.builder().error(false).body(transactions.getBatchNumber()).build();
    }

    public void deleteCustomerFndTransferTxn(Long id) {
        Optional<CustomerFndTransferEntity> optional = customerFndTransferRepository.findByIdAndIsDeletedFalse(id);
        if (optional.isPresent()) {
            customerFndTransferRepository.delete(optional.get());
            log.info("Customer to customer fund transfer Txn {} Deleted", id);
        }
    }

    private TransactionResponse doBatchChargeVatTransaction(InterCustomerFundTransferEntity interC2COut, CustomerFndTransferEntity outC2CTxn, UserInfoEntity userInfoDetails) {
        CbsResponse cbsResponse;
        CurrencyResponse currency = currencyService.getById(outC2CTxn.getCurrencyId());
        String cbsName = customerAccountDetailsService.getCbsName(outC2CTxn.getPayerAccNo());
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(outC2CTxn.getCurrencyId());
        DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), outC2CTxn.getCurrencyId(), RoutingType.Outgoing);

        if (interC2COut.isBatchChargeEnable()) {
            BigDecimal charge = interC2COut.getChargeAmount();
            BigDecimal vat = interC2COut.getVatAmount();
            TransactionRequest transactionRequest = new TransactionRequest();
            if (cbsName.equals(CbsName.ABABIL.toString())) {
                Optional<ChargeSetupEntity> optCharge = chargeSetupRepository.findByCurrencyIdAndIsDeletedFalse(outC2CTxn.getCurrencyId());
                if (optCharge.isPresent()) {
                    var chargeEntity = optCharge.get();
                    transactionRequest.setChargeAccount(chargeEntity.getChargeGl()).setVatAccount(chargeEntity.getVatGl());
                }
            } else {
                transactionRequest.setChargeAccount(departmentAccount.getChargeAccNumber()).setVatAccount(departmentAccount.getVatAccNumber());
            }
            transactionRequest.setNarration(("RTGS Service Charge and VAT")).setAmount(charge.add(vat)).setCbsName(cbsName).setChargeEnabled(false).setDrAccount(outC2CTxn.getPayerAccNo()).setCrAccount("").setCharge(charge).setVat(vat).setSettlementAccountId(settlementAccount.getId()).setCurrencyId(outC2CTxn.getCurrencyId()).setCurrencyCode(currency.getShortCode()).setRtgsRefNo(outC2CTxn.getParentBatchNumber()).setRemarks(outC2CTxn.getParentBatchNumber()).setChargeRemarks(outC2CTxn.getParentBatchNumber()).setParticular2("C2C Bulk Transaction");
            cbsResponse = cbsTransactionService.batchChargeVatTransaction(transactionRequest);
            if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                if (cbsName.equals(CbsName.FINACLE.toString())) {
                    // department acc balance update
                    departmentAccountService.departmentAccountChargeVatTransaction(RoutingType.Outgoing, userInfoDetails.getDept().getId(), FundTransferType.CustomerToCustomer, outC2CTxn.getCharge(), outC2CTxn.getVat(), outC2CTxn.getReferenceNumber(), outC2CTxn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                }
                log.info("Batch Txn Charge vat Deduction Successful {}", outC2CTxn.getParentBatchNumber());
                return TransactionResponse.builder().error(false).message("Batch Txn Charge vat Deduction Successful").build();
            } else {
                log.info(" Batch Txn Charge vat Deduction failed {}", outC2CTxn.getParentBatchNumber());
                return TransactionResponse.builder().error(true).message("Batch Txn Charge vat Deduction failed").build();
            }
        } else {
            return TransactionResponse.builder().error(true).message("Batch Charge Waved").build();
        }

    }


    public TransactionResponse executeTransaction(Long id) {
        List<CustomerFndTransferEntity> OutC2CTxns;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(LoggedInUserDetails.getUserInfoDetails().getId());
        boolean error = false;
        CbsResponse cbsResponse;
        Optional<InterCustomerFundTransferEntity> interC2COutOptional = interCustomerFundTransferRepository.findByIdAndIsDeletedFalse(id);

        if (!interC2COutOptional.isPresent()) {
            return TransactionResponse.builder().error(true).message("ApproveTransaction(): Inter Customer txn request not found").build();
        }

        InterCustomerFundTransferEntity interC2COut = interC2COutOptional.get();
        OutC2CTxns = customerFndTransferRepository.getAllByTransactions(id);
        BigDecimal txnAmount = customerFndTransferRepository.getTxnAmount(id).getAmount();

        if (OutC2CTxns.isEmpty()) {
            return TransactionResponse.builder().error(true).message("Customer To Customer Txn request not found").build();
        }
        CurrencyResponse currency = currencyService.getById(OutC2CTxns.get(0).getCurrencyId());

        boolean isOutwardActive = txnCfgSetupService.txnPossible(interC2COut.getTxnTypeCode(), OutC2CTxns.get(0).getCurrencyId());
        if (!isOutwardActive) {
            return TransactionResponse.builder().error(true).message("Transaction is not possible now. Please contact with your admin.").build();
        }

        if (interC2COut.getVerificationStatus() == 3) {
            error = true;

            return TransactionResponse.builder().error(error).message("Transaction already Approved!").build();
        }
        if (interC2COut.getVerificationStatus() == 2) {
            error = true;
            return TransactionResponse.builder().error(error).message("Transaction already Rejected!").build();
        }
        if (interC2COut.getRoutingType().equals(RoutingType.Outgoing)) {
            List<TransactionRequest> txnList = new ArrayList<>();
            PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(OutC2CTxns.get(0).getPayerAccNo());
            if (!payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                return TransactionResponse.builder().error(true).message("Payer Details not found: " + OutC2CTxns.get(0).getPayerAccNo()).build();
            }
            var balanceValidation = validationService.validateCustomerBalance(interC2COut, txnAmount, payerDetailsResponse.getAvailBalance());
            if (balanceValidation) {
                for (CustomerFndTransferEntity OutC2CTxn : OutC2CTxns) {

                    Optional<ShadowAccountEntity> shadowAccountEntity = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(OutC2CTxn.getBenBankId(), OutC2CTxn.getCurrencyId());
                    if (shadowAccountEntity.isEmpty()) {
                        return TransactionResponse.builder().error(true).message("Shadow Account Not Found ").build();
                    }
                    try {

                        boolean chargeEnabled;

                        if (OutC2CTxn.getRoutingType().equals(RoutingType.Outgoing)) {

                            SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(OutC2CTxn.getCurrencyId());
                            String cbsName = customerAccountDetailsService.getCbsName(OutC2CTxn.getPayerAccNo());
                            DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), OutC2CTxn.getCurrencyId(), RoutingType.Outgoing);
                            chargeEnabled = customerAccountDetailsService.getChargeEnabled(OutC2CTxn.getPayerAccNo(), payerDetailsResponse.getSchemeCode());

                            if (OutC2CTxn.isBatchTxn()) {
                                OutC2CTxn.setBatchTxnChargeWaived(!chargeEnabled);
                                chargeEnabled = false;

                            } else {
                                OutC2CTxn.setBatchTxnChargeWaived(false);
                                if (interC2COut.getTxnTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode()) || interC2COut.getTxnTypeCode().equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode()) || interC2COut.getTxnTypeCode().equals(TransactionTypeCodeEnum.GOVERNMENT_PAYMENTS.getCode())) {
                                    chargeEnabled = false;
                                }
                            }
                            TransactionRequest transactionRequest = new TransactionRequest();
                            if (cbsName.equals(CbsName.ABABIL.toString())) {
                                Optional<ChargeSetupEntity> optCharge = chargeSetupRepository.findByCurrencyIdAndIsDeletedFalse(OutC2CTxn.getCurrencyId());
                                if (optCharge.isPresent()) {
                                    var chargeSetup = optCharge.get();
                                    transactionRequest.setChargeAccount(chargeSetup.getChargeGl()).setVatAccount(chargeSetup.getVatGl());
                                }
                            } else {
                                transactionRequest.setChargeAccount(departmentAccount.getChargeAccNumber()).setVatAccount(departmentAccount.getVatAccNumber());
                            }

                            transactionRequest.setNarration(ValidationUtility.validateText(narrationMappingService.getC2CTransactionNarration(OutC2CTxn, false, false), 50)).setVat(OutC2CTxn.getVat() != null ? OutC2CTxn.getVat() : BigDecimal.ZERO).setCharge(OutC2CTxn.getCharge() != null ? OutC2CTxn.getCharge() : BigDecimal.ZERO).setAmount(OutC2CTxn.getAmount()).setCbsName(cbsName).setChargeEnabled(chargeEnabled).setDrAccount(OutC2CTxn.getPayerAccNo()).setCrAccount(OutC2CTxn.getTxnGlAccount()).setSettlementAccountId(settlementAccount.getId()).setCurrencyId(OutC2CTxn.getCurrencyId()).setCurrencyCode(currency.getShortCode()).setRtgsRefNo(OutC2CTxn.getReferenceNumber()).setParticular2(ValidationUtility.validateText(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(OutC2CTxn.getLcNumber()) ? OutC2CTxn.getLcNumber() : OutC2CTxn.getNarration()), 50)).setRemarks(OutC2CTxn.getReferenceNumber() + "-" + bankService.getBankById(OutC2CTxn.getBenBankId()).getBic()).setChargeRemarks(OutC2CTxn.getParentBatchNumber()).setAbabilRequestId(OutC2CTxn.getAbabilReferenceNumber());
                            txnList.add(transactionRequest);
                        }
                    } catch (Exception e) {
                        return TransactionResponse.builder().error(true).message(e.getMessage()).build();
                    }

                }
                cbsResponse = cbsTransactionService.cbsBulkTransactionFI(txnList);
                if (!OutC2CTxns.isEmpty() && cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    if (interC2COut.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
                        if (OutC2CTxns.get(0).isBatchTxn()) {
                            var response = doBatchChargeVatTransaction(interC2COut, OutC2CTxns.get(0), userInfoDetails);
                            if (!response.isError()) {
                                interC2COut.setBatchChargeEnable(true);
                            }
                        }
                    }
                }
                for (CustomerFndTransferEntity OutC2CTxn : OutC2CTxns) {
                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        interC2COut.setApprovalDateTime(new Date()).setApprover(currentUser.getUsername()).setVerificationStatus(3).setTxnVerificationStatus(TransactionVerificationStatus.Approved).setTransactionStatus(TransactionStatus.Pending).setVerifyDateTime(new Date()).setVerifier(currentUser.getUsername()).setSettlementDate(new Date()).setTransactionDate(new Date()).setId(OutC2CTxn.getTransactions());
                        OutC2CTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber()).setCbsName(customerAccountDetailsService.getCbsName(OutC2CTxn.getPayerAccNo())).setId(OutC2CTxn.getId()).setSettlementDate(new Date()).setTransactionDate(new Date()).setTransactionStatus(TransactionStatus.Pending.toString()).setVerificationStatus(TransactionVerificationStatus.Approved.toString());
                        // String sentMsgId = messageGenerateService.processPACS008OutwardRequest(interC2COut, OutC2CTxn, currentUser.getId());
                        messageGenerateService.processPACS008OutwardRequest(interC2COut, OutC2CTxn, currentUser.getId());
                        // OutC2CTxn.setSentMsgId(sentMsgId);
                        // interC2COut.setMsgId(sentMsgId);
                        registerService.doRegister(OutC2CTxn.getRoutingType(), OutC2CTxn.getCurrencyId(), OutC2CTxn.getAmount(), OutC2CTxn.getVoucherNumber(), OutC2CTxn.getPayerBankId(), OutC2CTxn.getBenBankId(), OutC2CTxn.getPayerBranchId(), OutC2CTxn.getBenBranchId(), new Date(), new Date(), OutC2CTxn.getReferenceNumber(), OutC2CTxn.getBenAccNo(), OutC2CTxn.getNarration(), OutC2CTxn.getCbsName());
                        departmentAccountService.departmentAccountTransaction(OutC2CTxn.getAmount(), RoutingType.Outgoing, userInfoDetails.getDept().getId(), FundTransferType.CustomerToCustomer, false, OutC2CTxn.getCharge(), OutC2CTxn.getVat(), OutC2CTxn.getReferenceNumber(), OutC2CTxn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                    } else {
                        error = true;
                        interC2COut.setVerificationStatus(4);
                        interC2COut.setFailedReason("Cbs Transaction Failed Reason : " + cbsResponse.getResponseMessage());
                        interC2COut.setId(OutC2CTxn.getTransactions());
                        OutC2CTxn.setId(OutC2CTxn.getId());
                        OutC2CTxn.setTransactionDate(new Date());
                        OutC2CTxn.setTransactionStatus(TransactionStatus.Failed.toString());
                        OutC2CTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                        OutC2CTxn.setErrorMsg("CbsError");
                        OutC2CTxn.setFailedReason(cbsResponse.getResponseMessage());
                        OutC2CTxn.setVerificationStatus(TransactionVerificationStatus.Failed.toString());
                        ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
                        OutC2CTxn.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
                    }
                    customerFndTransferRepository.save(OutC2CTxn);
                }
                try {
                    interC2COut = interCustomerFundTransferRepository.save(interC2COut);
                    if (error) {
                        return TransactionResponse.builder().error(true).message("Cbs Transaction Failed" + cbsResponse.getResponseMessage()).build();
                    }
                } catch (Exception e) {
                    return TransactionResponse.builder().error(true).message(e.getMessage()).build();
                }
            } else {
                return TransactionResponse.builder().error(true).message("Balance Validation Error!").build();
            }
        }
        if (interC2COut.getRoutingType().equals(RoutingType.Incoming)) {
            for (CustomerFndTransferEntity OutC2CTxn : OutC2CTxns) {
                if (interC2COut.getInwardActionStatus() == 1) {
                    ResponseDTO response = returnFundTransferService.sendPaymentReturnMessage(OutC2CTxn, interC2COut.getReturnCode());
                    if (response.isError()) {
                        return TransactionResponse.builder().error(true).message(response.getMessage()).build();
                    }
                } else {
                    //inward Manual cbs transaction

                    ResponseDTO response = this.approveInwardManualTransaction(interC2COut, OutC2CTxn, currentUser.getUsername());
                    if (response.isError()) {
                        try {
                            interC2COut.setFailedReason("Reason : " + response.getMessage());
                            interCustomerFundTransferRepository.save(interC2COut);
                            customerFndTransferRepository.save(OutC2CTxn);

                        } catch (Exception e) {

                            return TransactionResponse.builder().error(true).message(e.getMessage()).build();
                        }
                        return TransactionResponse.builder().error(true).message(response.getMessage()).build();
                    }
                }
            }
        }

        return TransactionResponse.builder().error(false).message("Approve Customer To Customer txn " + interC2COut.getBatchNumber()).build();
    }

    public ResponseDTO approveInwardManualTransaction(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity txn, String userName) {
        ResponseDTO response = new ResponseDTO();

        var cbsTxnExist = cbsTransactionLogService.cbsTransactionExists(txn.getReferenceNumber());
        if (!cbsTxnExist) {
            CbsResponse cbsResponse;
            SettlementAccountEntity settlementAccount = null;
            CurrencyResponse currency = currencyService.getById(txn.getCurrencyId());
            boolean error = false;
            String errorMessage = "";
            String accountDetailName = "";
            try {
                Optional<SettlementAccountEntity> optionalSettlementAccount = settlementAccountRepository.findByCurrencyIdAndIsDeletedFalse(txn.getCurrencyId());
                if (optionalSettlementAccount.isPresent()) {
                    settlementAccount = optionalSettlementAccount.get();
                } else {
                    error = true;
                    errorMessage = "Settlement Account Not Found";
                }
                String cbsName = customerAccountDetailsService.getCbsName(txn.getBenAccNo());
                DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(txn.getDepartmentId(), txn.getCurrencyId(), RoutingType.Incoming);
                if (departmentAccount == null) {
                    error = true;
                    errorMessage = "Department Account Not Found";
                }
                txn.setBatchTxnChargeWaived(true);
                if (cbsName.equals(CbsName.CARD.toString())) {
                    CardDetailsResponse cardDetailsResponse = customerAccountDetailsService.getCardDetails(txn.getBenAccNo());
                    if (!cardDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        error = true;
                        errorMessage = cardDetailsResponse.getResponseMessage();
                    } else {
                        accountDetailName = cardDetailsResponse.getCardHolderName();
                        var verifyCustomer = this.verifyCustomerName(accountDetailName, txn.getBenName());
                        if (verifyCustomer.isError()) {
                            error = true;
                            errorMessage = errorMessage + " " + verifyCustomer.getMessage();
                        }
                    }
                } else {
                    PayerDetailsResponse beneficiaryDetails = customerAccountDetailsService.getAccountDetails(txn.getBenAccNo());
                    if (!beneficiaryDetails.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        error = true;
                        errorMessage = errorMessage + " " + "Account Details Not found";
                    } else {
                        accountDetailName = beneficiaryDetails.getPayerName();
                        var verifyCustomer = this.verifyCustomerName(accountDetailName, txn.getBenName());
                        if (verifyCustomer.isError()) {
                            error = true;
                            errorMessage = errorMessage + " " + verifyCustomer.getMessage();
                        }
                        //routing number check
                        Long benBranchId = branchService.getBenBranchIdByRouting(beneficiaryDetails.getPayerBranchName());
                        if (routingNumberConfigService.getRoutingNumberSetup().getIsInwardTxn()) {
                            // benBranchId = entity.getId();
                            if (!benBranchId.equals(txn.getBenBranchId())) {
                                error = true;
                                errorMessage = errorMessage + " " + "Routing Number Mismatch";
                            }
                        }
                    }
                }

                if (!error) {
                    TransactionRequest transactionRequest = new TransactionRequest();
                    transactionRequest.setVat(BigDecimal.ZERO).setCharge(BigDecimal.ZERO).setAmount(txn.getAmount()).setCbsName(cbsName).setChargeEnabled(false).setNarration(narrationMappingService.getC2CTransactionNarration(txn, false, false)).setDrAccount(txn.getTxnGlAccount()).setCrAccount(txn.getBenAccNo()).setChargeAccount(StringUtils.isNotBlank(departmentAccount.getChargeAccNumber()) ? departmentAccount.getChargeAccNumber() : txn.getTxnGlAccount()).setVatAccount(StringUtils.isNotBlank(departmentAccount.getVatAccNumber()) ? departmentAccount.getVatAccNumber() : txn.getTxnGlAccount()).setSettlementAccountId(settlementAccount.getId()).setCurrencyId(txn.getCurrencyId()).setCurrencyCode(currency.getShortCode()).setRtgsRefNo(txn.getReferenceNumber()).setParticular2(StringUtils.isNotBlank(txn.getLcNumber()) ? txn.getLcNumber().trim() : narrationMappingService.getC2CTransactionNarration(txn, false, false)).setRemarks(txn.getReferenceNumber() + "-" + bankService.getBankById(txn.getPayerBankId()).getBic()).setChargeRemarks(txn.getParentBatchNumber()).setAbabilRequestId(txn.getAbabilReferenceNumber());
                    cbsResponse = cbsTransactionService.cbsInwardTransaction(transactionRequest, txn.getBenAccNo());
                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        //PendingTransaction
                        txn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                        fundTransfer.setApprovalDateTime(new Date()).setApprover(userName).setVerificationStatus(3).setVerifyDateTime(new Date()).setVerifier(userName).setId(txn.getTransactions()).setTransactionStatus(TransactionStatus.Confirmed);

                        txn.setId(txn.getId()).setCbsName(cbsName).setSettlementDate(new Date()).setTransactionDate(new Date()).setTransactionStatus(TransactionStatus.Confirmed.toString());
                        interCustomerFundTransferRepository.save(fundTransfer);
                        customerFndTransferRepository.save(txn);
                        registerService.doRegister(txn.getRoutingType(), txn.getCurrencyId(), txn.getAmount(), cbsResponse.getTransactionRefNumber(), txn.getPayerBankId(), txn.getBenBankId(), txn.getPayerBranchId(), txn.getBenBranchId(), new Date(), new Date(), txn.getReferenceNumber(), txn.getBenAccNo(), txn.getNarration(), txn.getCbsName());
                        departmentAccountService.departmentAccountTransaction(txn.getAmount(), txn.getRoutingType(), txn.getDepartmentId(), FundTransferType.CustomerToCustomer, false, txn.getCharge(), txn.getVat(), txn.getReferenceNumber(), txn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                        response.setError(false);
                        response.setMessage("CBS Transaction Successful");
                    } else {
                        errorMessage = StringUtils.isNotBlank(cbsResponse.getResponseMessage()) ? cbsResponse.getResponseMessage() + " - Code: " + cbsResponse.getResponseCode() : "CBS Transaction Failed!";
                        response.setError(true);
                        response.setMessage(errorMessage);
                        interCustomerFundTransferRepository.save(fundTransfer);
                        customerFndTransferRepository.save(txn);
                    }
                } else {
                    response.setError(true);
                    response.setMessage(errorMessage);
                }
            } catch (Exception e) {
                errorMessage = errorMessage + " " + e.getMessage();
                response.setError(true);
                response.setMessage(errorMessage);
            }
        } else {
            response.setError(false);
            response.setMessage("Transaction Already Completed!");
        }
        return response;
    }

    private ResponseDTO verifyCustomerName(String accountDetailName, String beneficiaryName) {
        ResponseDTO responseDTO = new ResponseDTO();
        InwardTransactionConfigurationResponse inwardTransactionConfiguration = inwardTransactionConfigurationService.getAllList().get(0);
        if (inwardTransactionConfiguration.getMatchBenificiaryName()) {
            int matchPercentage = ValidationUtility.matchPercentage(accountDetailName, beneficiaryName);
            System.out.println("Cbs A/C Name : " + accountDetailName);
            System.out.println("Txn A/C Name : " + beneficiaryName);
            System.out.println("Match value : " + matchPercentage);
            if (matchPercentage < inwardTransactionConfiguration.getMatchingPercentage()) {
                responseDTO.setError(true);
                responseDTO.setMessage("Beneficiary Name : " + accountDetailName + " not matched");
            } else {
                responseDTO.setError(false);
                responseDTO.setMessage("Beneficiary Name matched");
            }
        } else {
            responseDTO.setError(false);
            responseDTO.setMessage("Beneficiary Name matched");
        }
        return responseDTO;
    }

}