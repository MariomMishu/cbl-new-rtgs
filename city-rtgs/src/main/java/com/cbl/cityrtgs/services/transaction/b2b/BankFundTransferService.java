package com.cbl.cityrtgs.services.transaction.b2b;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.models.dto.report.TxnAuditReport;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.common.exception.ResourceNotFoundException;
import com.cbl.cityrtgs.mapper.transaction.BankFundTransferMapper;
import com.cbl.cityrtgs.models.dto.transaction.*;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferBatchResponse;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferDetails;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferRequest;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.AccountTypesRepository;
import com.cbl.cityrtgs.repositories.configuration.ShadowAccountRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferAudRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.BankFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.*;
import com.cbl.cityrtgs.services.transaction.c2c.C2COutwardValidationService;
import com.cbl.cityrtgs.services.user.UserInfoService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cbl.cityrtgs.common.utility.ValidationUtility.validateText;

@Slf4j
@RequiredArgsConstructor
@Service
public class BankFundTransferService {
    private final AccountTransactionRegisterService registerService;
    private final B2BOutwardValidationService b2BOutwardValidationService;
    private final UserInfoService userInfoService;
    private final DepartmentAccountService departmentAccountService;
    private final BankFundTransferMapper mapper;
    private final CurrencyService currencyService;
    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final InterBankFundTransferRepository repository;
    private final BankFndTransferRepository bankFndTransferRepository;
    private final BankService bankService;
    private final SettlementAccountService settlementAccountService;
    private final CbsTransactionService cbsTransactionService;
    private final ShadowAccountService shadowAccountService;
    private final MessageGenerateService messageGenerateService;
    private final BankFndTransferAudRepository bankFndTransferAudRepository;
    private final BranchService branchService;
    private final AccountTypesRepository accountTypesRepository;
    private final C2COutwardValidationService validationService;
    private final ShadowAccountRepository shadowAccountRepository;
    private final UiAppConfigurationService uiConfigurationService;
    private final NarrationMappingService narrationMappingService;
    private final CbsTransactionLogService cbsTransactionLogService;
    private final TxnCfgSetupService txnCfgSetupService;
    private final String NARRATION_PATTERN = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]{1,35}";

    public BankFundTransferResponse create(BankFundTransferRequest request) {
        BankFundTransferResponse response = new BankFundTransferResponse();
        TransactionResponse response1 = b2BOutwardValidationService.validateOutwardB2B(request);
        response.setError(response1.isError());
        response.setErrorMessage(response1.getMessage());
        if (request.getBankFundTransferDetails().size() > 0) {
            response = this.createBankFundTransferTxn(request);
        }
        return response;
    }

    public BankFundTransferResponse createBankFundTransferTxn(BankFundTransferRequest request) {
        BankFundTransferResponse response = new BankFundTransferResponse();
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());
        List<BankFndTransferEntity> items = new ArrayList<>();
        String batchNumber;
        String errorNote = null;
        for (int i = 0; i < request.getBankFundTransferDetails().size(); i++) {
            BankFundTransferDetails item = request.getBankFundTransferDetails().get(i);

            BranchResponse fcRecBranch;
            ReferenceGenerateResponse batchNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTA.name());
            ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
            batchNumber = batchNo.getBatchRefNo();
            InterBankTransferEntity interB2bOut = mapper.domainToEntity(request);
            interB2bOut.setEntryUser(currentUser.getUsername());
            interB2bOut.setCreatedBy(currentUser.getId());
            interB2bOut.setBatchNumber(batchNumber);
            interB2bOut.setBranchId(userInfoDetails.getBranch().getId());
            interB2bOut.setVerificationStatus(0);
            interB2bOut.setTransactionStatus(TransactionStatus.Submitted);
            interB2bOut.setTxnVerificationStatus(TransactionVerificationStatus.Submitted);

            BankFndTransferEntity b2bOutTxn = mapper.domainToBankFndEntity(item);
            if (b2bOutTxn.getAmount() == null) {
                errorNote = errorNote + "Amount is not provided,";
                response.setError(true);
                response.setErrorMessage(errorNote);
                b2bOutTxn.setErrorNote(errorNote);
            } else {
                b2bOutTxn.setAmount(BigDecimal.valueOf(b2bOutTxn.getAmount().doubleValue()));
            }

            if (b2bOutTxn.getAmount().compareTo(BigDecimal.ZERO) == 0 || b2bOutTxn.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                errorNote = errorNote + "Amount is must be greater than zero,";
                response.setError(true);
                response.setErrorMessage(errorNote);
                b2bOutTxn.setErrorNote(errorNote);
            }

            DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);
            ShadowAccountResponse shadowAcc = shadowAccountService.getShadowAcc(b2bOutTxn.getBenBankId(), request.getCurrencyId());
            if (shadowAcc != null) {
                b2bOutTxn.setPayerAccNo(shadowAcc.getRtgsSettlementAccount());
                b2bOutTxn.setPayerCbsAccNo(shadowAcc.getOutgoingGl());
            } else {
                errorNote = errorNote + " No Shadow Account found for this currency and bank,";
                response.setError(true);
                response.setErrorMessage(errorNote);
                b2bOutTxn.setErrorNote(errorNote);
            }
            if (currency.getShortCode() != null && !currency.getShortCode().equals("BDT")) {
                if (StringUtils.isNotBlank(item.getFcRecRoutingNo())) {
                    if (!item.isBatchTxn() && StringUtils.isNotBlank(item.getFcRecBranchId().toString())) {
                        fcRecBranch = this.branchService.getBranchById(item.getFcRecBranchId());
                        if (fcRecBranch == null) {
                            errorNote = errorNote + "Beneficiary Receiving Branch Is Not Valid,";
                            response.setError(true);
                            response.setErrorMessage(errorNote);
                            b2bOutTxn.setErrorNote(errorNote);
                        } else {
                            b2bOutTxn.setFcRecBranchId(fcRecBranch.getId());
                        }
                    }
                    if (item.isBatchTxn() && StringUtils.isNotBlank(item.getFcRecRoutingNo())) {
                        fcRecBranch = this.branchService.getBranchByRoutingNumber(item.getFcRecRoutingNo());
                        if (fcRecBranch == null) {
                            errorNote = errorNote + "Beneficiary Receiving Branch Is Not Valid,";
                            response.setError(true);
                            response.setErrorMessage(errorNote);
                            b2bOutTxn.setErrorNote(errorNote);
                        } else {
                            b2bOutTxn.setFcRecBranchId(fcRecBranch.getId());
                        }
                    }
                }
                if (StringUtils.isNotBlank(item.getBillNumber())) {
                    if (valid(NARRATION_PATTERN, item.getBillNumber())) {
                        b2bOutTxn.setBillNumber(validateText(item.getBillNumber(), 35));
                    } else {
                        b2bOutTxn.setErrorNote("Bill Number is not valid.");
                        response.setError(true);
                        response.setErrorMessage("Bill Number is not valid.");
                    }
                }
                if (StringUtils.isNotBlank(item.getLcNumber())) {
                    if (valid(NARRATION_PATTERN, item.getLcNumber())) {
                        b2bOutTxn.setLcNumber(validateText(item.getLcNumber(), 35));
                    } else {
                        b2bOutTxn.setErrorNote("LC number is not valid,");
                        response.setError(true);
                        response.setErrorMessage("LC number is not valid");
                    }
                }
                if (StringUtils.isNotBlank(item.getPartyName())) {
                    if (valid(NARRATION_PATTERN, item.getPartyName())) {
                        b2bOutTxn.setFcRecAccountType(validateText(item.getPartyName(), 35));
                    } else {
                        b2bOutTxn.setErrorNote("Party name is not valid");
                        response.setError(true);
                        response.setErrorMessage("Party name is not valid");
                    }
                }
            }
            TransactionResponse response1 = b2BOutwardValidationService.validateOutwardB2BTransaction(item, request.getCurrencyId(), userInfoDetails.getProfile().getId());

            if (response1.isError()) {
                response.setError(true);
                response.setErrorMessage(response1.getMessage());
                return response;
            }
            b2bOutTxn
                    .setReferenceNumber(referenceNo.getTxnRefNo())
                    .setSentMsgId(referenceNo.getMessageId())
                    .setInstrId(referenceNo.getInstrId())
                    .setEndToEndId(referenceNo.getInstrId())
                    .setBenName(StringUtils.isNoneBlank(bankService.getBankById(item.getBenBankId()).getName()) ? bankService.getBankById(item.getBenBankId()).getName() : "Not Found")
                    .setDepartmentId(userInfoDetails.getDept().getId())
                    .setDepartmentAccountId(departmentAccount.getId())
                    .setTransactionGlAccount(departmentAccount.getAccountNumber())
                    .setPayerBankId(bankService.getOwnerBank().getId())
                    .setPayerBranchId(interB2bOut.getBranchId())
                    .setPayerName(bankService.getOwnerBank().getName())
                    .setParentBatchNumber(interB2bOut.getBatchNumber())
                    .setPriorityCode(request.getPriorityCode())
                    .setCurrencyId(request.getCurrencyId())
                    .setVerificationStatus(TransactionVerificationStatus.Submitted)
                    .setTransactionStatus(TransactionStatus.Submitted)
                    .setCreatedBy(currentUser.getId());
            if (!response.isError()) {
                interB2bOut = repository.save(interB2bOut);
                b2bOutTxn.setCreatedAt(new Date());
                b2bOutTxn.setTransactions(interB2bOut.getId());
                items.add(b2bOutTxn);
                log.info("Bank To Bank fund transfer txn BatchNumber : {} is saved", batchNumber);
                response.setParentBatchNumber(batchNumber);
            } else {
                return response;
            }
        }
        if (!response.isError()) {
            bankFndTransferRepository.saveAll(items);
        }
        return response;
    }

    public BankFundTransferResponse getById(Long id) {
        InterBankTransferEntity interB2B = repository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> new ResourceNotFoundException("Bank fund transfer not found"));
        BankFndTransferEntity b2bTxn = bankFndTransferRepository.findByTransactionsAndParentBatchNumberAndIsDeletedFalse(interB2B.getId(), interB2B.getBatchNumber()).orElseThrow(() -> new ResourceNotFoundException("Bank fund transfer not found"));
        BankFundTransferResponse response = mapper.entityToDomain(interB2B, b2bTxn);
        response.setRejectReason(interB2B.getRejectReason());
        response.setRejectDateTime(interB2B.getRejectionDateTime());
        return response;
    }


    public boolean existOne(Long id) {
        return repository.existsById(id);
    }

    public ResponseDTO rejectTransaction(Long id, String message) {
        Date date = new Date();

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        Optional<InterBankTransferEntity> _interB2bOut;
        Optional<BankFndTransferEntity> _b2bOutTxn;

        _interB2bOut = repository.findByIdAndIsDeletedFalse(id);
        if (_interB2bOut.isEmpty()) {
            return ResponseDTO
                    .builder()
                    .error(true)
                    .message("Inter Bank Outward request not found")
                    .build();
        }

        _b2bOutTxn = bankFndTransferRepository.findByTransactionsAndIsDeletedFalse(id);
        if (_b2bOutTxn.isEmpty()) {
            return ResponseDTO
                    .builder()
                    .error(true)
                    .message("Bank To Bank Outward request not found")
                    .build();
        }

        InterBankTransferEntity interB2bOut = _interB2bOut.get();
        if (interB2bOut.getVerificationStatus() == 3) {
            return ResponseDTO
                    .builder()
                    .error(true)
                    .message("Transaction already Approved!")
                    .build();
        }
        if (interB2bOut.getVerificationStatus() == 2) {

            return ResponseDTO
                    .builder()
                    .error(true)
                    .message("Transaction already Rejected!")
                    .build();
        }
        BankFndTransferEntity b2bOutTxn = _b2bOutTxn.get();

        interB2bOut.setRejectedUser(currentUser.getUsername())
                .setRejectionDateTime(date)
                .setRejectReason(message)
                .setVerificationStatus(2)
                .setTransactionStatus(TransactionStatus.Failed)
                .setTxnVerificationStatus(TransactionVerificationStatus.Rejected)
                .setId(id);

        b2bOutTxn.setRejectedUser(currentUser.getUsername())
                .setRejectionDateTime(date)
                .setRejectReason(message)
                .setTransactionStatus(TransactionStatus.Failed)
                .setVerificationStatus(TransactionVerificationStatus.Rejected)
                .setId(b2bOutTxn.getId());
        repository.save(interB2bOut);
        b2bOutTxn = bankFndTransferRepository.save(b2bOutTxn);
        log.info("Reject B2B Outward  " + b2bOutTxn.getParentBatchNumber());
        return ResponseDTO
                .builder()
                .error(false)
                .message("Bank To Bank Transaction Rejected Successfully")
                .build();
    }

    public ResponseDTO approveBankFndTransfer(Long id) {
        Date date = new Date();
        boolean error = false;
        String errorMessage = "";
        Optional<BankFndTransferEntity> _b2bOutTxn;
        Optional<InterBankTransferEntity> _interB2bOut;
        CbsResponse cbsResponse = new CbsResponse();
        // String referenceNumber;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        _interB2bOut = repository.findByIdAndIsDeletedFalse(id);
        if (_interB2bOut.isEmpty()) {
            return ResponseDTO.builder()
                    .error(true)
                    .message("Inter Bank Outward request not found")
                    .build();
        }

        _b2bOutTxn = bankFndTransferRepository.findByTransactionsAndIsDeletedFalse(id);
        if (_b2bOutTxn.isEmpty()) {
            return ResponseDTO.builder()
                    .error(true)
                    .message("Bank To Bank Outward request not found")
                    .build();
        }

        InterBankTransferEntity interB2bOut = _interB2bOut.get();
        if (interB2bOut.getVerificationStatus() == 3) {
            error = true;
            errorMessage = "Transaction already Approved!";
            return ResponseDTO.builder()
                    .error(error)
                    .message(errorMessage)
                    .build();
        }
        if (interB2bOut.getVerificationStatus() == 2) {
            error = true;
            errorMessage = "Transaction already Rejected!";
            return ResponseDTO.builder()
                    .error(error)
                    .message(errorMessage)
                    .build();
        }
        BankFndTransferEntity b2bTxn = _b2bOutTxn.get();
        if (b2bTxn.getRoutingType() == RoutingType.Outgoing) {
            boolean isOutwardActive = txnCfgSetupService.b2bOutwardTxnPossible(b2bTxn.getCurrencyId());
            if (!isOutwardActive) {
                return ResponseDTO.builder()
                        .error(true)
                        .message("Transaction is not possible now. Please contact with your admin.")
                        .build();
            }
            try {
                Optional<ShadowAccountEntity> _shadowAcc = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(b2bTxn.getBenBankId(), b2bTxn.getCurrencyId());
                if (_shadowAcc.isEmpty()) {
                    return ResponseDTO.builder()
                            .error(true)
                            .message("Shadow Account Not Found ")
                            .build();
                }
                ShadowAccountEntity shadowAcc = _shadowAcc.get();
                CurrencyResponse currency = currencyService.getById(b2bTxn.getCurrencyId());
                if (StringUtils.isBlank(b2bTxn.getPayerCbsAccNo())) {
                    b2bTxn.setPayerCbsAccNo(shadowAcc.getOutgoingGl());
                }
                if (StringUtils.isBlank(b2bTxn.getPayerAccNo())) {
                    b2bTxn.setPayerAccNo(shadowAcc.getRtgsSettlementAccount());
                }
                b2bTxn.setUpdatedAt(new Date());
                b2bTxn.setUpdatedBy(currentUser.getId());
                b2bTxn.setSettlementDate(interB2bOut.getSettlementDate());

                DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), b2bTxn.getCurrencyId(), RoutingType.Outgoing);
                if (departmentAccount == null) {
                    return ResponseDTO.builder()
                            .error(true)
                            .message("Department Account Not Found ")
                            .build();
                }
                if (b2bTxn.getPayerCbsAccNo() != null && departmentAccount.getAccountNumber() != null) {
                    String cbsName = CbsName.FINACLE.toString();
                    String txnRemarks = StringUtils.isNotBlank(b2bTxn.getLcNumber()) ? b2bTxn.getLcNumber() : b2bTxn.getBillNumber();
                    TransactionRequest transactionRequest = new TransactionRequest();
                    transactionRequest
                            .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getB2BTransactionNarration(b2bTxn, false, false)))
                            .setVat(b2bTxn.getVat() != null ? b2bTxn.getVat() : BigDecimal.ZERO)
                            .setCharge(b2bTxn.getCharge() != null ? b2bTxn.getCharge() : BigDecimal.ZERO)
                            .setAmount(b2bTxn.getAmount())
                            .setCbsName(cbsName)
                            .setChargeEnabled(false)
                            .setDrAccount(b2bTxn.getPayerCbsAccNo())
                            .setCrAccount(departmentAccount.getAccountNumber())
                            .setChargeAccount(null)
                            .setVatAccount(null)
                            .setSettlementAccountId(null)
                            .setCurrencyId(b2bTxn.getCurrencyId())
                            .setCurrencyCode(currency.getShortCode())
                            .setParticular2(StringUtils.isNotBlank(txnRemarks) ? txnRemarks : b2bTxn.getParentBatchNumber())
                            .setRtgsRefNo(b2bTxn.getReferenceNumber())
                            .setRemarks(b2bTxn.getReferenceNumber())
                            .setChargeRemarks("NA");
                    cbsResponse = cbsTransactionService.cbsTransaction(transactionRequest);
                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                        //PendingTransaction
                        b2bTxn
                                .setCbsName(cbsName)
                                .setId(b2bTxn.getId())
                                .setTransactionDate(date)
                                .setVoucherNumber(cbsResponse.getTransactionRefNumber())
                                .setTransactionStatus(TransactionStatus.Pending)
                                .setVerificationStatus(TransactionVerificationStatus.Approved);

                        interB2bOut
                                .setApprovalDateTime(date)
                                .setTxnVerificationStatus(TransactionVerificationStatus.Approved)
                                .setTransactionStatus(TransactionStatus.Pending)
                                .setApprover(currentUser.getUsername())
                                .setVerificationStatus(3) // for pending status
                                .setVerifyDateTime(date)
                                .setVerifier(currentUser.getUsername())
                                .setId(id);

                        departmentAccountService.departmentAccountTransaction(b2bTxn.getAmount(), b2bTxn.getRoutingType(), b2bTxn.getDepartmentId(), FundTransferType.BankToBank, false, BigDecimal.ZERO, BigDecimal.ZERO, b2bTxn.getReferenceNumber(), b2bTxn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                        registerService.doRegister(RoutingType.Outgoing, b2bTxn.getCurrencyId(), b2bTxn.getAmount(), cbsResponse.getTransactionRefNumber(), b2bTxn.getPayerBankId(), b2bTxn.getBenBankId(), b2bTxn.getPayerBranchId(), b2bTxn.getBenBranchId(), b2bTxn.getTransactionDate(), b2bTxn.getSettlementDate(), b2bTxn.getReferenceNumber(), b2bTxn.getTransactionGlAccount(), b2bTxn.getNarration(), CbsName.FINACLE.toString());
                        messageGenerateService.processPACS009OutwardRequest(interB2bOut, b2bTxn);
                    } else {
                        error = true;
                        errorMessage = errorMessage + "Cbs Transaction Failed Reason : " + cbsResponse.getResponseMessage();
                        interB2bOut.setApprovalDateTime(date);
                        interB2bOut.setApprover(currentUser.getUsername());
                        interB2bOut.setVerificationStatus(4);
                        interB2bOut.setFailedReason("Cbs Transaction Failed Reason : " + cbsResponse.getResponseMessage());
                        interB2bOut.setId(b2bTxn.getTransactions());

                        b2bTxn.setId(b2bTxn.getId());
                        b2bTxn.setTransactionDate(new Date());
                        b2bTxn.setTransactionStatus(TransactionStatus.Failed);
                        b2bTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                        b2bTxn.setFailedReason(cbsResponse.getResponseMessage());
                        b2bTxn.setErrorMsg("CbsError");
                        b2bTxn.setVerificationStatus(TransactionVerificationStatus.Failed);
                    }
                }
            } catch (Exception e) {
                error = true;
                errorMessage = errorMessage + e.getMessage();

            } finally {
                if (error) {
                    interB2bOut.setApprovalDateTime(date);
                    interB2bOut.setApprover(currentUser.getUsername());
                    interB2bOut.setVerificationStatus(4);
                    interB2bOut.setFailedReason("Cbs Transaction Failed");
                    interB2bOut.setId(id);
                    b2bTxn.setTransactionDate(date);
                    b2bTxn.setTransactionStatus(TransactionStatus.Failed);
                    b2bTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                    b2bTxn.setReturnCode("CbsError");
                    b2bTxn.setReturnReason(cbsResponse.getResponseMessage());
                    b2bTxn.setVerificationStatus(TransactionVerificationStatus.Failed);
                    return ResponseDTO.builder()
                            .error(true)
                            .message(errorMessage)
                            .build();
                }
            }
            try {
                interB2bOut = repository.save(interB2bOut);
                b2bTxn = bankFndTransferRepository.save(b2bTxn);
            } catch (Exception e) {
                return ResponseDTO.builder()
                        .error(true)
                        .message(e.getMessage())
                        .build();
            }
        }
        if (b2bTxn.getRoutingType() == RoutingType.Incoming) {
            ResponseDTO inwardResponse = this.approveInwardManualBankFndTransfer(interB2bOut, b2bTxn, currentUser.getUsername());
            if (inwardResponse.isError()) {
                return ResponseDTO.builder()
                        .error(true)
                        .message(inwardResponse.getMessage())
                        .build();
            }
        }
        return ResponseDTO.builder()
                .error(false)
                .message("Transaction Approved Successfully")
                .build();
    }


    public void updateStatus(InterBankTransferEntity interB2bOut, BankFndTransferEntity b2bOutTxn, TransactionStatus status, String msgId) {
        interB2bOut.setTransactionStatus(status);
        b2bOutTxn.setTransactionStatus(status);
        repository.save(interB2bOut);
        log.info("Update Status complete Inter Bank Transfer :" + msgId);
        bankFndTransferRepository.save(b2bOutTxn);
        log.info("Update Status complete in Bank Transfer Txn:" + msgId);

    }

    public InterBankTransferEntity approveInwardBankFndTransfer(InterBankTransferEntity interB2bInward, BankFndTransferEntity b2bInwardTxn) {
        var cbsTxnExist = cbsTransactionLogService.cbsTransactionExists(b2bInwardTxn.getReferenceNumber());
        if (!cbsTxnExist) {
            Date date = new Date();
            boolean error = false;
            String errorMessage = null;
            AccountTypeEntity accType;
            CbsResponse cbsResponse = new CbsResponse();
            ShadowAccountResponse shadowAcc = shadowAccountService.getShadowAcc(b2bInwardTxn.getPayerBankId(), b2bInwardTxn.getCurrencyId());
            if (shadowAcc == null) {
                error = true;
                errorMessage = "Shadow A/C Not Found";
            } else {
                if (StringUtils.isBlank(b2bInwardTxn.getPayerCbsAccNo())) {
                    b2bInwardTxn.setPayerCbsAccNo(shadowAcc.getIncomingGl());
                }
                if (StringUtils.isBlank(b2bInwardTxn.getPayerAccNo())) {
                    b2bInwardTxn.setPayerAccNo(shadowAcc.getRtgsSettlementAccount());
                }
            }
            CurrencyResponse currency = currencyService.getById(b2bInwardTxn.getCurrencyId());
            var payerBank = bankService.getEntityById(b2bInwardTxn.getPayerBankId());


            try {
                b2bInwardTxn.setUpdatedAt(new Date());
                b2bInwardTxn.setSettlementDate(interB2bInward.getSettlementDate());
                DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(b2bInwardTxn.getDepartmentId(), b2bInwardTxn.getCurrencyId(), RoutingType.Incoming);
                if (b2bInwardTxn.getRoutingType() == RoutingType.Incoming) {

                    if (b2bInwardTxn.getPayerCbsAccNo() != null && departmentAccount.getAccountNumber() != null) {
                        String cbsName = CbsName.FINACLE.toString();
                        TransactionRequest transactionRequest = new TransactionRequest();
                        if (payerBank.isSettlementBank()) {
                            var settlementAcc = settlementAccountService.getEntityByCurrencyId(b2bInwardTxn.getCurrencyId());
                            Optional<AccountTypeEntity> _accType = accountTypesRepository.findByRtgsAccountIdAndCbsNameAndIsDeletedFalse(settlementAcc.getId(), CbsName.FINACLE);

                            if (_accType.isPresent()) {
                                accType = _accType.get();
                                transactionRequest.setDrAccount(accType.getCbsAccountNumber());
                            }
                            //bb fund Dr-> Account Type table er CBS ACCOUNT Number
                            // Cr->b2bInwardTxn.getPayerCbsAccNo() from shadow
                            /// error message , accType is empty MSG unprocss
                        } else {
                            transactionRequest.setDrAccount(departmentAccount.getAccountNumber());
                        }
                        var remarks = StringUtils.isNotBlank(b2bInwardTxn.getLcNumber()) ? b2bInwardTxn.getLcNumber() : b2bInwardTxn.getNarration();

                        transactionRequest
                                .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getB2BTransactionNarration(b2bInwardTxn, false, false)))
                                .setVat(BigDecimal.ZERO)
                                .setCharge(BigDecimal.ZERO)
                                .setAmount(b2bInwardTxn.getAmount())
                                .setCbsName(cbsName)
                                .setChargeEnabled(false)
                                .setCrAccount(b2bInwardTxn.getPayerCbsAccNo())
                                .setChargeAccount(null)
                                .setVatAccount(null)
                                .setSettlementAccountId(null)
                                .setCurrencyId(b2bInwardTxn.getCurrencyId())
                                .setCurrencyCode(currency.getShortCode())
                                .setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(remarks) ? remarks : interB2bInward.getBatchNumber())))
                                .setRtgsRefNo(b2bInwardTxn.getReferenceNumber())
                                .setRemarks(b2bInwardTxn.getReferenceNumber())
                                .setChargeRemarks("NA");
                        cbsResponse = cbsTransactionService.cbsTransaction(transactionRequest);
                        if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                            //PendingTransaction
                            b2bInwardTxn.setId(b2bInwardTxn.getId()).setTransactionDate(date).setVoucherNumber(cbsResponse.getTransactionRefNumber()).setTransactionStatus(TransactionStatus.Confirmed).setVerificationStatus(TransactionVerificationStatus.Approved);

                            interB2bInward.setApprovalDateTime(date).setApprover("SYSTEM").setVerificationStatus(3) // for pending status
                                    .setVerifyDateTime(date).setVerifier("SYSTEM").setId(b2bInwardTxn.getTransactions()).setTransactionStatus(TransactionStatus.Confirmed);
                            if (!payerBank.isSettlementBank()) {
                                departmentAccountService.departmentAccountTransaction(b2bInwardTxn.getAmount(), b2bInwardTxn.getRoutingType(), b2bInwardTxn.getDepartmentId(), FundTransferType.BankToBank, false, BigDecimal.ZERO, BigDecimal.ZERO, b2bInwardTxn.getReferenceNumber(), b2bInwardTxn.getCurrencyId(), cbsResponse.getTransactionId());
                            }
                            registerService.doRegister(RoutingType.Incoming, b2bInwardTxn.getCurrencyId(), b2bInwardTxn.getAmount(), cbsResponse.getTransactionRefNumber(), b2bInwardTxn.getPayerBankId(), b2bInwardTxn.getBenBankId(), b2bInwardTxn.getPayerBranchId(), b2bInwardTxn.getBenBranchId(), b2bInwardTxn.getTransactionDate(), b2bInwardTxn.getSettlementDate(), b2bInwardTxn.getReferenceNumber(), b2bInwardTxn.getTransactionGlAccount(), b2bInwardTxn.getNarration(), CbsName.FINACLE.toString());
                        } else {
                            error = true;
                            errorMessage = "Cbs Transaction Failed";
                        }
                    }
                }
            } catch (Exception e) {
                error = true;
                errorMessage = e.getMessage();
            } finally {
                if (error) {
                    interB2bInward.setApprovalDateTime(date);
                    interB2bInward.setApprover("SYSTEM");
                    interB2bInward.setVerificationStatus(4);
                    interB2bInward.setTransactionStatus(TransactionStatus.Failed);
                    interB2bInward.setTxnVerificationStatus(TransactionVerificationStatus.Approved);
                    interB2bInward.setFailedReason(errorMessage);
                    interB2bInward.setId(b2bInwardTxn.getTransactions());
                    b2bInwardTxn.setId(b2bInwardTxn.getId());
                    b2bInwardTxn.setTransactionDate(date);
                    b2bInwardTxn.setTransactionStatus(TransactionStatus.Failed);
                    b2bInwardTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                    b2bInwardTxn.setFailedReason(cbsResponse.getResponseMessage());
                    b2bInwardTxn.setErrorMsg("CbsError");
                    b2bInwardTxn.setReturnReason(cbsResponse.getResponseMessage());
                    b2bInwardTxn.setVerificationStatus(TransactionVerificationStatus.Approved);

                }
                interB2bInward = repository.save(interB2bInward);
                bankFndTransferRepository.save(b2bInwardTxn);
            }
        } else {
            log.info("Inward Bank fund transfer Already Approved " + b2bInwardTxn.getReferenceNumber());
        }
        return interB2bInward;
    }

    public Map<String, Object> getB2BTxnAuditListByReference(String reference) {
        Map<String, Object> response = new HashMap<>();
        List<TxnAuditReport> b2bTxnAudList = new ArrayList<>();
        b2bTxnAudList.addAll(bankFndTransferAudRepository.getB2BTxnAuditListByReference(reference));
        response.put("result", b2bTxnAudList);
        return response;
    }


    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse updateTxn(Long id, BankFundTransferRequest request) {
        Optional<InterBankTransferEntity> optional = repository.findByIdAndIsDeletedFalse(id);

        if (optional.isEmpty()) {

            return TransactionResponse.builder()
                    .error(true)
                    .message("Fund Transfer  Not Found")
                    .build();
        }

        InterBankTransferEntity fundTransfer = optional.get();

        if (request.getBankFundTransferDetails().size() > 0) {
            return updateBankTransferTransaction(fundTransfer, request);
        }

        return TransactionResponse.builder()
                .error(true)
                .message("Empty list!")
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponse updateBankTransferTransaction(InterBankTransferEntity transactions, BankFundTransferRequest request) {

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        BranchResponse fcRecBranch;
        String errorNote = null;
        boolean error = false;
        if (userInfoDetails == null) {

            return TransactionResponse.builder()
                    .error(true)
                    .message("User not found!")
                    .build();
        }

        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());

        if (currency == null) {

            return TransactionResponse.builder()
                    .error(true)
                    .message("Currency not found!")
                    .build();
        }

        DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);

        if (departmentAccountResponse == null) {
            return TransactionResponse.builder()
                    .error(true)
                    .message("Department account not found!")
                    .build();
        }

        List<BankFundTransferDetails> b2bTxns = request.getBankFundTransferDetails();
        TransactionResponse response = b2BOutwardValidationService.validateOutwardB2B(request);
        if (response.isError()) {
            return TransactionResponse.builder().error(true).message(response.getMessage()).build();
        }
        Optional<BankFndTransferEntity> b2bTxnOp = bankFndTransferRepository.findByTransactionsAndIsDeletedFalse(transactions.getId());

        for (BankFundTransferDetails item : b2bTxns) {
            BankFndTransferEntity entity = b2bTxnOp.get();
            try {
                TransactionResponse response1 = b2BOutwardValidationService.validateOutwardB2BTransaction(item, request.getCurrencyId(), userInfoDetails.getProfile().getId());
                if (response1.isError()) {
                    return TransactionResponse.builder().error(true).message(response1.getMessage()).build();
                }
                Boolean validAmount = validationService.validateRtgsBalance(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode(), request.getCurrencyId(), item.getAmount());
                Optional<ShadowAccountEntity> shadowAcc = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(item.getBenBankId(), request.getCurrencyId());
                if (!shadowAcc.isPresent()) {
                    return TransactionResponse.builder()
                            .error(true)
                            .message("Shadow Account Not Found for BIC: " + item.getBenBankBic())
                            .build();
                }
                if (validAmount) {

                    if (currency.getShortCode() != null && !currency.getShortCode().equals("BDT")) {
                        if (StringUtils.isNotBlank(item.getFcRecBranchId().toString())) {
                            fcRecBranch = this.branchService.getBranchById(item.getFcRecBranchId());
                            if (fcRecBranch == null) {
                                errorNote = errorNote + "Beneficiary Receiving Branch Is Not Valid,";
                                response.setError(true);
                                response.setMessage(errorNote);
                                entity.setErrorNote(errorNote);
                            } else {
                                entity.setFcRecBranchId(fcRecBranch.getId());
                            }
                        }

                        if (StringUtils.isNotBlank(item.getBillNumber())) {
                            if (valid(NARRATION_PATTERN, item.getBillNumber())) {
                                entity.setBillNumber(validateText(item.getBillNumber(), 35));
                            } else {
                                entity.setErrorNote("Bill Number is not valid.");
                                response.setError(true);
                                response.setMessage("Bill Number is not valid.");
                            }
                        }
                        if (StringUtils.isNotBlank(item.getLcNumber())) {
                            if (valid(NARRATION_PATTERN, item.getLcNumber())) {
                                entity.setLcNumber(validateText(item.getLcNumber(), 35));
                            } else {
                                entity.setErrorNote("LC number is not valid,");
                                response.setError(true);
                                response.setMessage("LC number is not valid");
                            }
                        }
                        if (StringUtils.isNotBlank(item.getPartyName())) {
                            if (valid(NARRATION_PATTERN, item.getPartyName())) {
                                entity.setFcRecAccountType(validateText(item.getPartyName(), 35));
                            } else {
                                entity.setErrorNote("Party name is not valid");
                                response.setError(true);
                                response.setMessage("Party name is not valid");
                            }
                        }
                    }

                    entity
                            .setId(b2bTxnOp.get().getId())
                            .setReferenceNumber(b2bTxnOp.get().getReferenceNumber())
                            .setEndToEndId(b2bTxnOp.get().getEndToEndId())
                            .setInstrId(b2bTxnOp.get().getInstrId())
                            .setSentMsgId(b2bTxnOp.get().getSentMsgId())
                            .setAmount(item.getAmount())
                            .setNarration(item.getNarration())
                            .setBatchTxn(b2bTxnOp.get().getBatchTxn())
                            .setBenBankId(item.getBenBankId())
                            .setBenBranchId(item.getBenBranchId())
                            .setRoutingType(RoutingType.Outgoing)
                            .setBatchTransactionChargeWaived(false)
                            .setPayerAccNo(shadowAcc.get().getRtgsSettlementAccount())
                            .setPayerCbsAccNo(shadowAcc.get().getOutgoingGl())
                            .setDepartmentId(userInfoDetails.getDept().getId())
                            .setDepartmentAccountId(departmentAccountResponse.getId())
                            .setTransactionGlAccount(departmentAccountResponse.getAccountNumber())
                            .setPayerBankId(bankService.getOwnerBank().getId())
                            .setPayerBranchId(transactions.getBranchId())
                            .setPayerName(bankService.getOwnerBank().getName())
                            .setParentBatchNumber(transactions.getBatchNumber())
                            .setPriorityCode(request.getPriorityCode())
                            .setCurrencyId(request.getCurrencyId())
                            .setVerificationStatus(TransactionVerificationStatus.Submitted)
                            .setTransactions(transactions.getId())
                            .setTransactionStatus(TransactionStatus.Submitted)
                            .setCreatedBy(currentUser.getId());
                    entity.setCreatedAt(new Date());
                    bankFndTransferRepository.save(entity);
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
                    repository.save(transactions);
                    log.info("Bank To Bank fund transfer Batch Number : {} is Updated", transactions.getBatchNumber());
                } else {
                    error = true;
                    errorNote = "Outward Balance and Transaction RTGS Balance Validation Error";
                }

            } catch (Exception e) {
                error = true;
                errorNote = errorNote + e.getMessage();
            } finally {
                if (error) {
                    return TransactionResponse.builder()
                            .error(true)
                            .message(errorNote)
                            .build();
                }
            }
        }

        return TransactionResponse.builder()
                .error(false)
                .body(transactions.getBatchNumber())
                .build();
    }

    @Transactional(rollbackFor = Exception.class)
    public BankFundTransferBatchResponse createBatch(BankFundTransferRequest request) {
        BankFundTransferBatchResponse response;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());
        DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);

        if (currency == null) {

            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("Currency not found!")
                    .build();
        }

        if (departmentAccountResponse == null) {
            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("Department account not found!")
                    .build();
        }

        if (request.getBankFundTransferDetails().size() > 0) {
            if (request.getBankFundTransferDetails().size() > uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem()) {
                return BankFundTransferBatchResponse.builder()
                        .error(true)
                        .message("CSV File error. MAX Number Of Transaction in One File: " + this.uiConfigurationService.getUiConfiguration().get(0).getCsvFileMaxItem() + ". ")
                        .build();
            } else {
                response = createBankFundTransferBatchTxn(request, userInfoDetails, currency, departmentAccountResponse);
                response.setError(false);
                response.setMessage("Fund Transfer successfully send for verification");
            }

            return BankFundTransferBatchResponse.builder()
                    .error(response.isError())
                    .message(response.getMessage())
                    .fundTransferTxnList(response.getFundTransferTxnList())
                    .build();
        } else {
            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("Empty Txn list!")
                    .build();
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public BankFundTransferBatchResponse createBankFundTransferBatchTxn(BankFundTransferRequest request, UserInfoEntity userInfoDetails, CurrencyResponse currency, DepartmentAccountEntity departmentAccount) {
        BankFundTransferBatchResponse response = new BankFundTransferBatchResponse();
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        List<BankFndTransferEntity> items = new ArrayList<>();

        request.getBankFundTransferDetails().forEach(item -> {
            TransactionResponse response1 = b2BOutwardValidationService.validateOutwardB2BTransaction(item, request.getCurrencyId(), userInfoDetails.getProfile().getId());
            if (response1.isError()) {
                response.setError(true);
                response.setMessage(response1.getMessage());
            }
            BranchResponse fcRecBranch;
            InterBankTransferEntity interB2bOut = mapper.domainToEntity(request);
            interB2bOut.setEntryUser(currentUser.getUsername());
            interB2bOut.setCreatedBy(currentUser.getId());
            interB2bOut.setBranchId(userInfoDetails.getBranch().getId());
            interB2bOut.setVerificationStatus(0);
            interB2bOut.setTransactionStatus(TransactionStatus.Submitted);
            interB2bOut.setTxnVerificationStatus(TransactionVerificationStatus.Submitted);
            ReferenceGenerateResponse batchNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTA.name());
            interB2bOut.setBatchNumber(batchNo.getBatchRefNo());
            interB2bOut = repository.save(interB2bOut);

            Boolean validAmount = b2BOutwardValidationService.validateRtgsBalance(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode(), request.getCurrencyId(), item.getAmount());

            BankFndTransferEntity b2bOutTxn = mapper.domainToBankFndEntity(item);

            if (validAmount) {

                if (item.isBatchTxn()) {
                    BankResponse beneficiaryBank = this.bankService.getBankByBic(item.getBenBankBic());
                    BranchResponse beneficiaryBranch = this.branchService.getBranchByRouting(item.getBenBranchRoutingNo());
                    if (beneficiaryBank == null) {
                        b2bOutTxn.setErrorNote("Beneficiary Bank BIC Is Not Valid,");
                        response.setError(true);
                        response.setMessage("Beneficiary Bank BIC Is Not Valid");
                    } else {
                        b2bOutTxn.setBenBankId(beneficiaryBank.getId());
                    }
                    if (beneficiaryBranch == null) {
                        b2bOutTxn.setErrorNote("Beneficiary Branch Is Not Valid,");
                        response.setError(true);
                        response.setMessage("Beneficiary Branch Is Not Valid");
                    } else {
                        b2bOutTxn.setBenBranchId(beneficiaryBranch.getId());
                    }

                    if (currency.getShortCode() == null) {
                        b2bOutTxn.setErrorNote("Currency Not Valid,");
                        response.setError(true);
                        response.setMessage("Currency Not Valid");
                    }
                }
                ShadowAccountResponse shadowAcc = shadowAccountService.getShadowAcc(b2bOutTxn.getBenBankId(), request.getCurrencyId());
                if (shadowAcc != null) {
                    b2bOutTxn.setPayerAccNo(shadowAcc.getRtgsSettlementAccount());
                    b2bOutTxn.setPayerCbsAccNo(shadowAcc.getOutgoingGl());
                } else {
                    b2bOutTxn.setErrorNote("No Shadow Account found for this currency and bank,");
                    response.setError(true);
                    response.setMessage("No Shadow Account found for this currency and bank");
                }
                if (currency.getShortCode() != null && !currency.getShortCode().equals("BDT")) {
                    if (!item.isBatchTxn() && StringUtils.isNotBlank(item.getFcRecBranchId().toString())) {
                        fcRecBranch = branchService.getBranchById(item.getFcRecBranchId());
                        if (fcRecBranch == null) {
                            response.setError(true);
                            response.setMessage("Beneficiary Receiving Branch Is Not Valid");
                            b2bOutTxn.setErrorNote("Beneficiary Receiving Branch Is Not Valid");
                        } else {
                            b2bOutTxn.setFcRecBranchId(fcRecBranch.getId());
                        }
                    }
                    if (StringUtils.isNotBlank(item.getBillNumber())) {
                        if (valid(NARRATION_PATTERN, item.getBillNumber())) {
                            b2bOutTxn.setBillNumber(validateText(item.getBillNumber(), 35));
                        } else {
                            b2bOutTxn.setErrorNote("Bill Number is not valid.");
                            response.setError(true);
                            response.setMessage("Bill Number is not valid.");
                        }
                    }
                    if (StringUtils.isNotBlank(item.getLcNumber())) {
                        if (valid(NARRATION_PATTERN, item.getLcNumber())) {
                            b2bOutTxn.setLcNumber(validateText(item.getLcNumber(), 35));
                        } else {
                            b2bOutTxn.setErrorNote("LC number is not valid,");
                            response.setError(true);
                            response.setMessage("LC number is not valid");
                        }
                    }
                    if (StringUtils.isNotBlank(item.getPartyName())) {
                        if (valid(NARRATION_PATTERN, item.getPartyName())) {
                            b2bOutTxn.setFcRecAccountType(validateText(item.getPartyName(), 35));
                        } else {
                            b2bOutTxn.setErrorNote("Party name is not valid");
                            response.setError(true);
                            response.setMessage("Party name is not valid");
                        }
                    }
                }
                ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
                b2bOutTxn
                        .setReferenceNumber(referenceNo.getTxnRefNo())
                        .setSentMsgId(referenceNo.getMessageId())
                        .setInstrId(referenceNo.getInstrId())
                        .setEndToEndId(referenceNo.getInstrId())
                        .setBenName(StringUtils.isNoneBlank(bankService.getBankById(item.getBenBankId()).getName()) ? bankService.getBankById(item.getBenBankId()).getName() : "Not Found")
                        .setDepartmentId(userInfoDetails.getDept().getId())
                        .setDepartmentAccountId(departmentAccount.getId())
                        .setTransactionGlAccount(departmentAccount.getAccountNumber())
                        .setPayerBankId(bankService.getOwnerBank().getId())
                        .setPayerBranchId(interB2bOut.getBranchId())
                        .setPayerName(bankService.getOwnerBank().getName())
                        .setParentBatchNumber(interB2bOut.getBatchNumber())
                        .setPriorityCode(request.getPriorityCode())
                        .setCurrencyId(request.getCurrencyId())
                        .setVerificationStatus(TransactionVerificationStatus.Submitted)
                        .setTransactions(interB2bOut.getId())
                        .setTransactionStatus(TransactionStatus.Submitted)
                        .setCreatedBy(currentUser.getId());
                b2bOutTxn.setCreatedAt(new Date());
                items.add(b2bOutTxn);
                log.info("Bank To Bank fund transfer txn BatchNumber : {} is saved", interB2bOut.getBatchNumber());
            } else {
                response.setError(true);
                response.setMessage("Outward Balance and Transaction RTGS Balance Validation Error");
            }
        });
        try {
            bankFndTransferRepository.saveAll(items);
            log.info("Bank To Bank fund transfer saved");
        } catch (Exception e) {
            log.error("Bank To Bank Batch Txn Failed! {}", e.getMessage());
            response.setError(true);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    private boolean valid(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO approveInwardManualBankFndTransfer(InterBankTransferEntity interB2bInward, BankFndTransferEntity b2bInwardTxn, String userName) {
        ResponseDTO response = new ResponseDTO();
        var cbsTxnExist = cbsTransactionLogService.cbsTransactionExists(b2bInwardTxn.getReferenceNumber());
        if (!cbsTxnExist) {
            boolean error;
            String errorMessage;
            AccountTypeEntity accType;
            CbsResponse cbsResponse;
            ShadowAccountResponse shadowAcc = shadowAccountService.getShadowAcc(b2bInwardTxn.getPayerBankId(), b2bInwardTxn.getCurrencyId());
            if (shadowAcc == null) {
                error = true;
                errorMessage = "Shadow A/C Not Found";
                response.setError(error);
                response.setMessage(errorMessage);
            } else {
                if (StringUtils.isBlank(b2bInwardTxn.getPayerCbsAccNo())) {
                    b2bInwardTxn.setPayerCbsAccNo(shadowAcc.getIncomingGl());
                }
                if (StringUtils.isBlank(b2bInwardTxn.getPayerAccNo())) {
                    b2bInwardTxn.setPayerAccNo(shadowAcc.getRtgsSettlementAccount());
                }
            }
            CurrencyResponse currency = currencyService.getById(b2bInwardTxn.getCurrencyId());
            var payerBank = bankService.getEntityById(b2bInwardTxn.getPayerBankId());


            try {
                b2bInwardTxn.setUpdatedAt(new Date());
                b2bInwardTxn.setSettlementDate(interB2bInward.getSettlementDate());
                DepartmentAccountEntity departmentAccount = departmentAccountService.getDepartmentAccEntity(b2bInwardTxn.getDepartmentId(), b2bInwardTxn.getCurrencyId(), RoutingType.Incoming);
                if (b2bInwardTxn.getPayerCbsAccNo() != null && departmentAccount.getAccountNumber() != null) {
                    String cbsName = CbsName.FINACLE.toString();
                    TransactionRequest transactionRequest = new TransactionRequest();
                    if (payerBank.isSettlementBank()) {
                        var settlementAcc = settlementAccountService.getEntityByCurrencyId(b2bInwardTxn.getCurrencyId());
                        Optional<AccountTypeEntity> _accType = accountTypesRepository.findByRtgsAccountIdAndCbsNameAndIsDeletedFalse(settlementAcc.getId(), CbsName.FINACLE);
                        if (_accType.isPresent()) {
                            accType = _accType.get();
                            transactionRequest.setDrAccount(accType.getCbsAccountNumber());
                        } else {
                            error = true;
                            errorMessage = "Account Type Not Found";
                            response.setError(error);
                            response.setMessage(errorMessage);
                        }
                    } else {
                        transactionRequest.setDrAccount(departmentAccount.getAccountNumber());
                    }
                    var remarks = StringUtils.isNotBlank(b2bInwardTxn.getLcNumber()) ? b2bInwardTxn.getLcNumber() : b2bInwardTxn.getNarration();

                    transactionRequest
                            .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getB2BTransactionNarration(b2bInwardTxn, false, false)))
                            .setVat(b2bInwardTxn.getVat() != null ? b2bInwardTxn.getVat() : BigDecimal.ZERO)
                            .setCharge(b2bInwardTxn.getCharge() != null ? b2bInwardTxn.getCharge() : BigDecimal.ZERO)
                            .setAmount(b2bInwardTxn.getAmount())
                            .setCbsName(cbsName)
                            .setChargeEnabled(false)
                            .setCrAccount(b2bInwardTxn.getPayerCbsAccNo())
                            .setChargeAccount(null)
                            .setVatAccount(null)
                            .setSettlementAccountId(null)
                            .setCurrencyId(b2bInwardTxn.getCurrencyId())
                            .setCurrencyCode(currency.getShortCode())
                            .setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(remarks) ? remarks : interB2bInward.getBatchNumber())))
                            .setRemarks(b2bInwardTxn.getReferenceNumber())
                            .setRtgsRefNo(b2bInwardTxn.getReferenceNumber())
                            .setChargeRemarks("NA");
                    cbsResponse = cbsTransactionService.cbsTransaction(transactionRequest);
                    if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {

                        b2bInwardTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                        interB2bInward
                                .setApprovalDateTime(new Date())
                                .setApprover(userName)
                                .setVerificationStatus(3)
                                .setVerifyDateTime(new Date())
                                .setVerifier(userName)
                                .setId(b2bInwardTxn.getTransactions())
                                .setTransactionStatus(TransactionStatus.Confirmed);

                        //PendingTransaction
                        b2bInwardTxn
                                .setId(b2bInwardTxn.getId())
                                .setSettlementDate(new Date())
                                .setTransactionDate(new Date())
                                .setVoucherNumber(cbsResponse.getTransactionRefNumber())
                                .setTransactionStatus(TransactionStatus.Confirmed)
                                .setVerificationStatus(TransactionVerificationStatus.Approved);

                        interB2bInward = repository.save(interB2bInward);
                        b2bInwardTxn = bankFndTransferRepository.save(b2bInwardTxn);
                        if (!payerBank.isSettlementBank()) {
                            departmentAccountService.departmentAccountTransaction(b2bInwardTxn.getAmount(), b2bInwardTxn.getRoutingType(), b2bInwardTxn.getDepartmentId(), FundTransferType.BankToBank, false, BigDecimal.ZERO, BigDecimal.ZERO, b2bInwardTxn.getReferenceNumber(), b2bInwardTxn.getCurrencyId(), cbsResponse.getTransactionId());
                        }
                        registerService.doRegister(RoutingType.Incoming, b2bInwardTxn.getCurrencyId(), b2bInwardTxn.getAmount(), cbsResponse.getTransactionRefNumber(), b2bInwardTxn.getPayerBankId(), b2bInwardTxn.getBenBankId(), b2bInwardTxn.getPayerBranchId(), b2bInwardTxn.getBenBranchId(), b2bInwardTxn.getTransactionDate(), b2bInwardTxn.getSettlementDate(), b2bInwardTxn.getReferenceNumber(), b2bInwardTxn.getTransactionGlAccount(), b2bInwardTxn.getNarration(), CbsName.FINACLE.toString());
                        response.setError(false);
                        response.setMessage("CBS Transaction Successful");
                    } else {
                        error = true;
                        errorMessage = "CBS Transaction Failed";
                        response.setError(error);
                        response.setMessage(errorMessage);
                    }
                }
            } catch (Exception e) {
                error = true;
                errorMessage = e.getMessage();
                response.setError(error);
                response.setMessage(errorMessage);
            }
        } else {
            response.setError(false);
            response.setMessage("Transaction Already Completed!");
        }
        return response;
    }
}
