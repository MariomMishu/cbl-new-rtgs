package com.cbl.cityrtgs.services.transaction.c2c;

import com.cbl.cityrtgs.common.enums.ErrorCodeEnum;
import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.common.exception.*;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.mapper.transaction.CustomerFundTransferMapper;
import com.cbl.cityrtgs.models.dto.transaction.*;
import com.cbl.cityrtgs.models.dto.transaction.c2c.*;
import com.cbl.cityrtgs.models.entitymodels.configuration.ChargeSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.IbTransactionEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.ChargeSetupRepository;
import com.cbl.cityrtgs.repositories.specification.IbTransactionSpecification;
import com.cbl.cityrtgs.repositories.transaction.c2c.*;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.*;
import com.cbl.cityrtgs.services.user.UserInfoService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import static com.cbl.cityrtgs.common.utility.ValidationUtility.validateText;

@Slf4j
@RequiredArgsConstructor
@Service
public class IbFundTransferService {
    private final CustomerFundTransferMapper mapper;
    private final InterCustomerFundTransferRepository repository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final DepartmentAccountService departmentAccountService;
    private final ChargeAccountSetupService chargeAccountSetupService;
    private final BankService bankService;
    private final IbTransactionRepository ibTransactionRepository;
    private final C2COutwardValidationService validationService;
    private final CustomerAccountDetailsService customerAccountDetailsService;
    private final DepartmentService departmentService;
    private final SettlementAccountService settlementAccountService;
    private final CbsTransactionService cbsTransactionService;
    private final AccountTransactionRegisterService registerService;
    private final BranchService branchService;
    private final MessageGenerateService messageGenerateService;
    private final CurrencyService currencyService;
    private final DeliveryChannelService deliveryChannelService;
    private final UserInfoService userInfoService;
    private final NarrationMappingService narrationMappingService;
    private final ChargeSetupRepository chargeSetupRepository;

    public IbTransactionLiteResponse doOutwardC2CTransaction(IbTransactionRequest ibRequest) {
        ErrorDetail errorDetail = new ErrorDetail();
        IbTransactionEntity ibTransaction = createIbTransaction(ibRequest, new Date(), errorDetail);

        IbTransactionLiteResponse response = new IbTransactionLiteResponse();
        ResponseDTO responseDTO;
        boolean isError = false;
        BranchResponse payerBranch;
        InterCustomerFundTransferEntity transactions;
        CustomerFndTransferEntity c2cTxn = new CustomerFndTransferEntity();
        DepartmentEntity dept;
        DepartmentAccountEntity departmentAccount = new DepartmentAccountEntity();
        var userInfo = userInfoService.getUserByUserName(ibRequest.getUserName());
        if (Objects.nonNull(userInfo)) {
            var userInfoId = userInfo.getId();
            CurrencyResponse currency = currencyService.getByCurrencyShortCode(ibRequest.getCurrency());
            if (Objects.nonNull(currency)) {
                if (validationService.validateRtgsBalance(ibRequest.getTransactionTypeCode(), currency.getId(), ibRequest.getAmount())) {
                    try {
                        validationService.validateTransactionTime(ibRequest.getTransactionTypeCode(), currency.getId());
                        validationService.validateLoginCredentials(ibRequest);
                        DeliveryChannelResponse deliveryChannelResponse = deliveryChannelService.getByChannelName(ibRequest.getDeliveryChannel());
                        if (Objects.isNull(deliveryChannelResponse)) {
                            errorDetail.setErrorCode(ErrorCodeEnum.ERROR_DELIVERY_CHANNEL.getCode());
                            errorDetail.setErrorMessage(ErrorCodeEnum.ERROR_DELIVERY_CHANNEL.getValue());
                            updateIbTxnById(ibTransaction, "FAILED", true, ErrorCodeEnum.ERROR_DELIVERY_CHANNEL.getValue());
                            throw new DeliveryChannelValidationException("API110", "Invalid Delivery Channel!");

                        }
                        PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(ibRequest.getPayerAccount());
                        if (!payerDetailsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                            errorDetail.setErrorCode(ErrorCodeEnum.ERROR_PAYER_ACC.getCode());
                            errorDetail.setErrorMessage(ErrorCodeEnum.ERROR_PAYER_ACC.getValue());
                            updateIbTxnById(ibTransaction, "FAILED", true, ErrorCodeEnum.ERROR_PAYER_ACC.getValue());
                            throw new AccountNumberValidationException("API100", "Account " + ibRequest.getPayerAccount() + " not found");
                        }
                        validationService.validateOutwardC2CTransaction(ibRequest);
                        BranchResponse branchResponse = branchService.getBranchByRoutingNumber(ibRequest.getBenBranchRoutingNo());
                        if (Objects.isNull(branchResponse)) {
                            errorDetail.setErrorCode("API104");
                            errorDetail.setErrorMessage("Invalid Beneficiary Branch");
                            updateIbTxnById(ibTransaction, "FAILED", true, "Invalid Beneficiary Branch");
                            throw new InputValidationException("API104", "Invalid Beneficiary Branch");
                        }
                        ChargeSetupResponse chargeSetupResponse = chargeAccountSetupService.getChargeVatByCurrency(currency.getId(), ibRequest.getAmount());
                        ibRequest.setNarration(validateText(ibRequest.getNarration(), 30));
                        ibRequest.setBenName(validateText(ibRequest.getBenName(), 69));
                        ibRequest.setPayerName(validateText(ibRequest.getPayerName(), 69));
                        if (StringUtils.isEmpty(ibRequest.getTransactionTypeCode())) {
                            ibRequest.setTransactionTypeCode(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode());
                        }

                        c2cTxn = mapper.domainToCustomerFndEntity(ibRequest, currency.getId());
                        c2cTxn.setPayerBankId(bankService.getOwnerBank().getId());
                        if (payerDetailsResponse.getPayerBranchName() != null) {
                            String[] payerBranchNameRouting = payerDetailsResponse.getPayerBranchName().split("-");
                            payerBranch = branchService.getBranchByRouting(payerBranchNameRouting[0]);
                            c2cTxn.setPayerBranchId(payerBranch.getId());
                            //bank branch validation
                        } else {
                            c2cTxn.setPayerBranchId(4235L);
                        }
                        if (StringUtils.isNotBlank(ibRequest.getDeliveryChannel())) {
                            dept = departmentService.getDeptByName(ibRequest.getDeliveryChannel());
                            if (Objects.isNull(dept)) {
                                errorDetail.setErrorCode("API104");
                                errorDetail.setErrorMessage("Department Not Found.");
                                updateIbTxnById(ibTransaction, "FAILED", true, "Department Not Found.");
                                throw new InputValidationException("API104", "Department Not Found.");
                            } else {
                                c2cTxn.setDepartmentId(dept.getId());
                            }

                            departmentAccount = departmentAccountService.getDepartmentAccEntity(dept.getId(), currency.getId(), RoutingType.Outgoing);

                            if (Objects.isNull(departmentAccount)) {
                                errorDetail.setErrorCode("API104");
                                errorDetail.setErrorMessage("Department Account Not Found.");
                                updateIbTxnById(ibTransaction, "FAILED", true, "Department Account Not Found.");
                                throw new InputValidationException("API104", "Department Account Not Found.");
                            }

                            if (ibRequest.getTransactionTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
                                if (!deliveryChannelResponse.isChargeApplicable()) {
                                    chargeSetupResponse.setVatAmount(BigDecimal.ZERO.doubleValue());
                                    chargeSetupResponse.setChargeAmount(BigDecimal.ZERO.doubleValue());
                                } else {
                                    boolean chargeEnabled = customerAccountDetailsService.getChargeEnabled(ibRequest.getPayerAccount(), payerDetailsResponse.getSchemeCode());
                                    if (chargeEnabled) {
                                        chargeSetupResponse = validationService.chargeVatCalculation(ibRequest.getPayerAccount(), currency.getId(), ibRequest.getAmount(), ibRequest.getTransactionTypeCode(), payerDetailsResponse.getSchemeCode());
                                        c2cTxn.setChargeGl(departmentAccount.getChargeAccNumber());
                                        c2cTxn.setVatGl(departmentAccount.getVatAccNumber());
                                    } else {
                                        chargeSetupResponse.setVatAmount(BigDecimal.ZERO.doubleValue());
                                        chargeSetupResponse.setChargeAmount(BigDecimal.ZERO.doubleValue());
                                    }
                                }
                                c2cTxn.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
                                c2cTxn.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));

                                BigDecimal txnAmount = ibRequest.getAmount().add(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()).add(BigDecimal.valueOf(chargeSetupResponse.getVatAmount())));
                                BigDecimal availableBalance = new BigDecimal(payerDetailsResponse.getAvailBalance());
                                if (txnAmount.compareTo(availableBalance) > 0) {
                                    isError = true;
                                    updateIbTxnById(ibTransaction, "FAILED", true, ErrorCodeEnum.ERROR_INSUFFICIENT.getValue());
                                    throw new AccountNumberValidationException(ErrorCodeEnum.ERROR_INSUFFICIENT.getCode(), ErrorCodeEnum.ERROR_INSUFFICIENT.getValue());
                                }
                            }
                        }

                        c2cTxn.setPriorityCode("0015");
                        c2cTxn.setDeliveryChannel(ibRequest.getDeliveryChannel());
                        c2cTxn.setBenBranchId(branchResponse.getId());
                        c2cTxn.setBenBankId(branchResponse.getBankId());

                        c2cTxn.setDepartmentAccountId(departmentAccount.getId());
                        c2cTxn.setTxnGlAccount(departmentAccount.getAccountNumber());
                        c2cTxn.setCreatedAt(new Date());
                        c2cTxn.setCreatedBy(userInfoId);

                    } catch (ValidationException e1) {
                        response.setIsError(true);
                        isError = true;
                        e1.printStackTrace();
                        errorDetail.setErrorCode(e1.getCode());
                        errorDetail.setErrorMessage(e1.getMessage());
                    } finally {
                        try {
                            //   ibTransaction = createIbTransaction(ibRequest, c2cTxn.getSettlementDate(), errorDetail);
                            if (!isError) {
                                InterCustomerTxnLiteResponse interC2CTxn = createInterCustomerFundTransfer(ibTransaction, c2cTxn.getPayerBranchId(), userInfoId);
                                if (!interC2CTxn.isError()) {
                                    transactions = repository.findById(interC2CTxn.getId()).get();
                                    c2cTxn.setCreatedBy(userInfoId);
                                    c2cTxn.setVerificationStatus(TransactionVerificationStatus.Submitted.toString());
                                    c2cTxn = createCustomerFundTransferTxn(transactions, c2cTxn);
                                    ibTransaction.setResponseReference(c2cTxn.getReferenceNumber());
                                    ibTransaction.setId(ibTransaction.getId());
                                    ibTransactionRepository.save(ibTransaction);
                                    log.info("updateIbTransaction() :: ibTransaction ");
                                    // CBS Transaction
                                    responseDTO = approveIBCustomerFndTransfer(transactions, c2cTxn, userInfoId, currency.getShortCode());
                                    if (responseDTO.isError()) {
                                        errorDetail.setErrorCode(ErrorCodeEnum.ERROR_TXN.getCode());
                                        errorDetail.setErrorMessage(ErrorCodeEnum.ERROR_TXN.getValue());
                                    }
                                    updateIbTxn(c2cTxn.getReferenceNumber(), c2cTxn.getTransactionStatus(), responseDTO.isError(), responseDTO.getMessage());
                                    log.info("Update Ib Txn, {}", c2cTxn.getReferenceNumber());
                                }
                            }
                        } catch (Exception exception) {
                            log.error("doOutwardC2CTransactionFromIB():: IB_TRANSACTIONS table write error", exception.getMessage());
                        }
                    }
                } else {
                    errorDetail.setErrorCode(ErrorCodeEnum.ERROR_AMOUNT.getCode());
                    errorDetail.setErrorMessage(ErrorCodeEnum.ERROR_AMOUNT.getValue());
                    updateIbTxnById(ibTransaction, "FAILED", true, ErrorCodeEnum.ERROR_AMOUNT.getValue());
                }

            } else {
                errorDetail.setErrorCode(ErrorCodeEnum.ERROR_CURRENCY.getCode());
                errorDetail.setErrorMessage(ErrorCodeEnum.ERROR_CURRENCY.getValue());
                updateIbTxnById(ibTransaction, "FAILED", true, ErrorCodeEnum.ERROR_CURRENCY.getValue());
            }
        } else {
            errorDetail.setErrorCode(ErrorCodeEnum.ERROR_AUTH.getCode());
            errorDetail.setErrorMessage(ErrorCodeEnum.ERROR_AUTH.getValue());
            updateIbTxnById(ibTransaction, "FAILED", true, ErrorCodeEnum.ERROR_AUTH.getValue());
        }
        if (errorDetail.getErrorMessage() != null) {
            response.setIsError(true);
            errorDetail.setErrorMessage(errorDetail.getErrorMessage());
            response.setIsError(true);
            response.setTransactionStatus("FAIL");
            response.setRequestReference(ibRequest.getRefNumber());
            response.setErrorDetail(errorDetail);
        } else {
            response.setIsError(false);
            response.setTransactionStatus("SUCCESS");
            response.setRequestReference(ibRequest.getRefNumber());
            response.setResponseReference(c2cTxn.getReferenceNumber());
        }
        log.info("doOutwardC2CTransactionFromIb(): Request Reference: " + ibRequest.getRefNumber() + " | Response Reference : " + c2cTxn.getReferenceNumber() + " | Error Code: " + (response.getErrorDetail() != null ? response.getErrorDetail().getErrorCode() : "") + " | Error Description: " + (response.getErrorDetail() != null ? response.getErrorDetail().getErrorMessage() : ""));
        log.info("doOutwardC2CTransactionFromIb(): Exit");
        return response;
    }


    public InterCustomerTxnLiteResponse createInterCustomerFundTransfer(IbTransactionEntity request, long branchId, Long userInfoId) {
        InterCustomerTxnLiteResponse response = new InterCustomerTxnLiteResponse();
        try {
            InterCustomerFundTransferEntity interc2c = mapper.domainToEntity(request.getTransactionTypeCode());
            interc2c.setBranchId(branchId);
            interc2c.setVerificationStatus(0); // transaction status make =0, confirmed =5, reject=2, failed=4, pending=3
            interc2c.setCreatedAt(new Date());
            interc2c.setCreatedBy(userInfoId);
            interc2c.setEntryUser("SYSTEM");
            interc2c.setTransactionStatus(TransactionStatus.Submitted);
            interc2c.setTxnVerificationStatus(TransactionVerificationStatus.Submitted);
            ReferenceGenerateResponse batchNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTA.name());
            interc2c.setBatchNumber(batchNo.getBatchRefNo());
            interc2c = repository.save(interc2c);
            log.info("IB customer fund transfer BatchNumber : {} is saved", interc2c.getBatchNumber());
            response.setError(false);
            response.setMessage("IB customer fund transfer BatchNumber : {} is saved");
            response.setId(interc2c.getId());
            response.setBatchNumber(interc2c.getBatchNumber());
        } catch (Exception e) {
            response.setError(true);
            response.setMessage(e.getMessage());
        }
        return response;
    }

    public CustomerFndTransferEntity createCustomerFundTransferTxn(InterCustomerFundTransferEntity interC2C, CustomerFndTransferEntity c2cTxn) {
        c2cTxn.setParentBatchNumber(interC2C.getBatchNumber());
        c2cTxn.setTransactions(interC2C.getId());
        c2cTxn.setIbTxn(true);
        c2cTxn.setTransactionStatus(TransactionStatus.Submitted.toString());
        ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
        c2cTxn.setReferenceNumber(referenceNo.getTxnRefNo());
        c2cTxn.setAbabilReferenceNumber(referenceNo.getAbabilRefNo());
        c2cTxn.setSentMsgId(referenceNo.getMessageId());
        c2cTxn.setInstrId(referenceNo.getInstrId());
        c2cTxn.setEndToEndId(referenceNo.getInstrId());
        c2cTxn = customerFndTransferRepository.save(c2cTxn);
        return c2cTxn;
    }


    public IbTransactionEntity createIbTransaction(IbTransactionRequest outC2CTxn, Date settlementDate, ErrorDetail errorDetail) {
        IbTransactionEntity ibTransaction = new IbTransactionEntity();
        if (errorDetail == null) {
            ibTransaction.setTransactionStatus("PENDING");
        } else {
            ibTransaction.setTransactionStatus("FAILED");
        }
        ibTransaction.setSettlementDate(settlementDate);
        ibTransaction.setAmount(outC2CTxn.getAmount());
        ibTransaction.setBenAccount(outC2CTxn.getBenAccount());
        ibTransaction.setBenBranchRoutingNo(outC2CTxn.getBenBranchRoutingNo());
        ibTransaction.setBenName(outC2CTxn.getBenName());
        ibTransaction.setCurrency(outC2CTxn.getCurrency());
        ibTransaction.setFundTransferType(FundTransferType.CustomerToCustomer);
        ibTransaction.setNarration(outC2CTxn.getNarration());
        ibTransaction.setPayerAccount(outC2CTxn.getPayerAccount());
        ibTransaction.setPayerName(outC2CTxn.getPayerName());
        ibTransaction.setRequestReference(outC2CTxn.getRefNumber());
        ibTransaction.setRoutingType(RoutingType.Outgoing);
        ibTransaction.setTransactionDate(new Date());
        ibTransaction.setDeliveryChannel(outC2CTxn.getDeliveryChannel());
        if (outC2CTxn.getTransactionTypeCode().equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode())) {
            ibTransaction.setBinCode(outC2CTxn.getBinCode())
                    .setCommissionerateEconomicCode(outC2CTxn.getCommissionerateEconomicCode());
        }
        if (outC2CTxn.getTransactionTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
            ibTransaction
                    .setCustomerMobileNumber(outC2CTxn.getCustomerMobileNumber())
                    .setCustomsOfficeCode(outC2CTxn.getCustomsOfficeCode())
                    .setDeclarantCode(outC2CTxn.getDeclarantCode())
                    .setRegistrationYear(outC2CTxn.getRegistrationYear())
                    .setRegistrationNumber(outC2CTxn.getRegistrationNumber());
        }
        ibTransaction.setTransactionTypeCode(outC2CTxn.getTransactionTypeCode() != null ? outC2CTxn.getTransactionTypeCode() : TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode());

        if (errorDetail.getErrorMessage() != null) {
            ibTransaction.setIsError(true);
            ibTransaction.setErrorMessage(errorDetail.getErrorMessage());
        }
        ibTransaction.setCreatedAt(new Date());
        ibTransaction = ibTransactionRepository.save(ibTransaction);
        return ibTransaction;
    }

    public ResponseDTO approveIBCustomerFndTransfer(InterCustomerFundTransferEntity transferFund, CustomerFndTransferEntity outC2CTxn, Long userInfoId, String currencyCode) {
        transferFund.setEntryUser("SYSTEM");
        transferFund.setApprover("SYSTEM");
        transferFund.setApprovalDateTime(new Date());
        boolean chargeEnabled;
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            CbsResponse cbsResponse;
            String cbsName = customerAccountDetailsService.getCbsName(outC2CTxn.getPayerAccNo());
            SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(outC2CTxn.getCurrencyId());

            chargeEnabled = outC2CTxn.getCharge() != null && !outC2CTxn.getCharge().equals(BigDecimal.valueOf(0.0));

            if (chargeEnabled) {
                outC2CTxn.setBatchTxnChargeWaived(false);
            } else {
                outC2CTxn.setBatchTxnChargeWaived(chargeEnabled);
            }
            if (transferFund.getTxnTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
                outC2CTxn.setBatchTxnChargeWaived(true);
                chargeEnabled = false;
            }
            TransactionRequest transactionRequest = new TransactionRequest();

            ChargeSetupEntity charge;
            if (cbsName.equals(CbsName.ABABIL.toString())) {
                Optional<ChargeSetupEntity> optCharge = chargeSetupRepository.findByCurrencyIdAndIsDeletedFalse(outC2CTxn.getCurrencyId());
                if (optCharge.isPresent()) {
                    charge = optCharge.get();
                    transactionRequest.setChargeAccount(charge.getChargeGl())
                            .setVatAccount(charge.getVatGl());
                }
            } else {
                transactionRequest.setChargeAccount(outC2CTxn.getChargeGl())
                        .setVatAccount(outC2CTxn.getVatGl());
            }

            transactionRequest
                    .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getC2CTransactionNarration(outC2CTxn, false, false)))
                    .setVat(outC2CTxn.getVat() != null ? outC2CTxn.getVat() : BigDecimal.ZERO)
                    .setCharge(outC2CTxn.getCharge() != null ? outC2CTxn.getCharge() : BigDecimal.ZERO)
                    .setAmount(outC2CTxn.getAmount())
                    .setCbsName(cbsName)
                    .setChargeEnabled(chargeEnabled)
                    .setDrAccount(outC2CTxn.getPayerAccNo())
                    .setCrAccount(outC2CTxn.getTxnGlAccount())
                    .setSettlementAccountId(settlementAccount.getId())
                    .setCurrencyId(outC2CTxn.getCurrencyId())
                    .setCurrencyCode(currencyCode)
                    .setRtgsRefNo(outC2CTxn.getReferenceNumber())
                    .setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(outC2CTxn.getLcNumber()) ? outC2CTxn.getLcNumber() : outC2CTxn.getNarration())))
                    .setRemarks(outC2CTxn.getReferenceNumber() + "-" + bankService.getBankById(outC2CTxn.getBenBankId()).getBic())
                    .setChargeRemarks(outC2CTxn.getParentBatchNumber()).setAbabilRequestId(outC2CTxn.getAbabilReferenceNumber());
            cbsResponse = cbsTransactionService.cbsTransaction(transactionRequest);

            if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                responseDTO.setError(false);
                responseDTO.setMessage("IB CBS Transaction Successful");
                departmentAccountService.departmentAccountTransaction(outC2CTxn.getAmount(), RoutingType.Outgoing, outC2CTxn.getDepartmentId(), FundTransferType.CustomerToCustomer, false, outC2CTxn.getCharge(), outC2CTxn.getVat(), outC2CTxn.getReferenceNumber(), outC2CTxn.getCurrencyId(), cbsResponse.getTransactionId());
                //PendingTransaction
                outC2CTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber())
                        .setCbsName(cbsName)
                        .setId(outC2CTxn.getId())
                        .setSettlementDate(new Date())
                        .setTransactionDate(new Date())
                        .setTransactionStatus(TransactionStatus.Pending.toString())
                        .setVerificationStatus(TransactionVerificationStatus.Approved.toString());

                transferFund.setVerificationStatus(3) // for pending status
                        .setVerifyDateTime(new Date())
                        .setVerifier("SYSTEM")
                        .setId(outC2CTxn.getTransactions())
                        .setTransactionStatus(TransactionStatus.Pending)
                        .setTxnVerificationStatus(TransactionVerificationStatus.Approved);

                registerService.doRegister(outC2CTxn.getRoutingType(), outC2CTxn.getCurrencyId(), outC2CTxn.getAmount(), outC2CTxn.getVoucherNumber(), outC2CTxn.getPayerBankId(), outC2CTxn.getBenBankId(), outC2CTxn.getPayerBranchId(), outC2CTxn.getBenBranchId(), new Date(), new Date(), outC2CTxn.getReferenceNumber(), outC2CTxn.getBenAccNo(), outC2CTxn.getNarration(), outC2CTxn.getCbsName());
                customerFndTransferRepository.save(outC2CTxn);
                repository.save(transferFund);
                this.generatePACS008Message(transferFund, outC2CTxn, userInfoId);
            } else {
                //FailedTransaction
                transferFund.setApprovalDateTime(new Date());
                transferFund.setApprover("SYSTEM");
                transferFund.setVerificationStatus(4);
                transferFund.setFailedReason(cbsResponse.getResponseMessage());
                transferFund.setId(outC2CTxn.getTransactions());
                transferFund.setTxnVerificationStatus(TransactionVerificationStatus.Failed);
                transferFund.setTransactionStatus(TransactionStatus.Failed);
                transferFund.setActive(false);
                transferFund.setDeleted(true);
                outC2CTxn.setId(outC2CTxn.getId());
                outC2CTxn.setSettlementDate(new Date());
                outC2CTxn.setTransactionDate(new Date());
                outC2CTxn.setTransactionStatus(TransactionStatus.Failed.toString());
                outC2CTxn.setVerificationStatus(TransactionVerificationStatus.Failed.toString());
                outC2CTxn.setVoucherNumber(cbsResponse.getTransactionRefNumber());
                outC2CTxn.setFailedReason(cbsResponse.getResponseMessage());
                outC2CTxn.setActive(false);
                outC2CTxn.setDeleted(true);
                responseDTO.setError(true);
                responseDTO.setMessage(cbsResponse.getResponseMessage());
                customerFndTransferRepository.save(outC2CTxn);
                repository.save(transferFund);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return responseDTO;
    }

    public void generatePACS008Message(InterCustomerFundTransferEntity transferFund, CustomerFndTransferEntity outC2CTxn, long userInfoId) {
      //  String sentMsgId = messageGenerateService.processPACS008OutwardRequest(transferFund, outC2CTxn, userInfoId);
     messageGenerateService.processPACS008OutwardRequest(transferFund, outC2CTxn, userInfoId);
       // outC2CTxn.setSentMsgId(sentMsgId);
      //  transferFund.setMsgId(sentMsgId);
        customerFndTransferRepository.save(outC2CTxn);
        repository.save(transferFund);
    }


    public Page<IbTransactionResponse> getAll(Pageable pageable, IbTransactionFilter filter) {
        Page<IbTransactionEntity> entities = ibTransactionRepository.findAll(IbTransactionSpecification.all(filter), pageable);
        return entities.map(mapper::entityToResponse);
    }

    public List<IbTransactionResponse> getAllByDeliveryChannel(IbTransactionFilter filter) {
        List<IbTransactionEntity> entities = ibTransactionRepository.findAll(IbTransactionSpecification.all(filter));
        return entities.stream().map(mapper::entityToResponse).collect(Collectors.toList());
    }

    public boolean existOne(Long id) {
        return ibTransactionRepository.existsById(id);
    }

    public IbTransactionLiteResponse retryIbOutWithoutCbsTxn(Long id) {
        IbTransactionLiteResponse response = new IbTransactionLiteResponse();
        BranchResponse payerBranch;
        ErrorDetail errorDetail = null;
        boolean isError = false;
        InterCustomerFundTransferEntity transactions;
        CustomerFndTransferEntity c2cTxn = null;

        Optional<IbTransactionEntity> optionalEntity = ibTransactionRepository.findById(id);
        if (optionalEntity.isPresent()) {
            IbTransactionEntity ibRequest = optionalEntity.get();
            try {
                validationService.validateCurrency(ibRequest.getCurrency());
                CurrencyResponse currency = currencyService.getByCurrencyCode(ibRequest.getCurrency());
                validationService.validateAccountNumber(ibRequest.getPayerAccount());
                PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(ibRequest.getPayerAccount());
                BranchResponse branchResponse = branchService.getBranchByRouting(ibRequest.getBenBranchRoutingNo());
                ChargeSetupResponse chargeSetupResponse = chargeAccountSetupService.getChargeVatByCurrency(currency.getId(), ibRequest.getAmount());
                if (Objects.nonNull(chargeSetupResponse)) {
                    chargeSetupResponse
                            .setChargeAmount(chargeSetupResponse.getChargeAmount())
                            .setVatAmount(chargeSetupResponse.getVatAmount());
                } else {
                    chargeSetupResponse
                            .setChargeAmount(BigDecimal.ZERO.doubleValue())
                            .setVatAmount(BigDecimal.ZERO.doubleValue());
                }
                String cbsName = customerAccountDetailsService.getCbsName(ibRequest.getPayerAccount());
                c2cTxn = mapper.ibEntityToCustomerFndEntity(ibRequest, currency.getId());
                c2cTxn.setPayerBankId(bankService.getOwnerBank().getId());
                if (payerDetailsResponse.getPayerBranchName() != null) {
                    String[] payerBranchNameRouting = payerDetailsResponse.getPayerBranchName().split("-");
                    payerBranch = branchService.getBranchByRouting(payerBranchNameRouting[0]);
                    c2cTxn.setPayerBranchId(payerBranch.getId());
                } else {
                    c2cTxn.setPayerBranchId(4235L);
                }

                DepartmentEntity dept;
                DepartmentAccountEntity departmentAccount = new DepartmentAccountEntity();
                if (StringUtils.isNotBlank(ibRequest.getDeliveryChannel())) {
                    validationService.deliveryChannelValidation(ibRequest.getDeliveryChannel());
                    DeliveryChannelResponse deliveryChannelResponse = deliveryChannelService.getByChannelName(ibRequest.getDeliveryChannel());
                    if (!deliveryChannelResponse.isChargeApplicable()) {
                        chargeSetupResponse.setVatAmount(BigDecimal.ZERO.doubleValue());
                        chargeSetupResponse.setChargeAmount(BigDecimal.ZERO.doubleValue());
                    }
                    dept = departmentService.getDeptByName(ibRequest.getDeliveryChannel());
                    departmentAccount = departmentAccountService.getDepartmentAccEntity(dept.getId(), currency.getId(), RoutingType.Outgoing);
                    c2cTxn.setDepartmentId(dept.getId());
                }
                if (ibRequest.getTransactionTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
                    c2cTxn.setChargeGl(departmentAccount.getChargeAccNumber());
                    c2cTxn.setVatGl(departmentAccount.getVatAccNumber());
                    c2cTxn.setCharge(BigDecimal.valueOf(chargeSetupResponse.getChargeAmount()));
                    c2cTxn.setVat(BigDecimal.valueOf(chargeSetupResponse.getVatAmount()));
                }
                var userInfoId = ibRequest.getCreatedBy();
                //   validationService.validateTransactionTime(ibRequest.getTransactionTypeCode(), "PACS008",ibRequest.getDeliveryChannel());
                validationService.validateRtgsBalance(ibRequest.getTransactionTypeCode(), currency.getId(), ibRequest.getAmount());
                validationService.validateOutwardC2CTransaction(ibRequest);
                c2cTxn.setSettlementDate(ibRequest.getSettlementDate());
                c2cTxn.setPriorityCode("0015");
                c2cTxn.setBenBranchId(branchResponse.getId());
                c2cTxn.setBenBankId(branchResponse.getBankId());

                c2cTxn.setDepartmentAccountId(departmentAccount.getId());
                c2cTxn.setTxnGlAccount(departmentAccount.getAccountNumber());
                c2cTxn.setCreatedAt(new Date());
                c2cTxn.setCreatedBy(userInfoId);
                c2cTxn.setCbsName(cbsName);
            } catch (ValidationException e1) {
                response.setIsError(true);
                response.setTransactionStatus("FAIL");
                response.setRequestReference(ibRequest.getRequestReference());
                isError = true;
                e1.printStackTrace();
                errorDetail = new ErrorDetail();
                errorDetail.setErrorCode(e1.getCode());
                errorDetail.setErrorMessage(e1.getMessage());
                response.setErrorDetail(errorDetail);
            } catch (Exception e) {
                response.setIsError(true);
                response.setTransactionStatus("FAIL");
                response.setRequestReference(ibRequest.getRequestReference());
                isError = true;
                errorDetail = new ErrorDetail();
                errorDetail.setErrorCode("API504");
                errorDetail.setErrorMessage(e.getMessage());
                response.setErrorDetail(errorDetail);
            } finally {
                if (!isError) {
                    try {
                        InterCustomerTxnLiteResponse interc2cTransaction = createInterCustomerFundTransfer(ibRequest, c2cTxn.getPayerBranchId(), c2cTxn.getCreatedBy());
                        if (!interc2cTransaction.isError()) {
                            transactions = repository.findById(interc2cTransaction.getId()).get();
                            c2cTxn = createCustomerFundTransferTxn(transactions, c2cTxn);
                            IbTransactionEntity ibRequest2 = optionalEntity.get();
                            ibRequest2.setResponseReference(c2cTxn.getReferenceNumber());
                            ibRequest2.setId(optionalEntity.get().getId());
                            ibTransactionRepository.save(ibRequest2);
                            retryIBCustomerFndTransfer(transactions, c2cTxn);
                            response.setResponseReference(c2cTxn.getReferenceNumber()).setRequestReference(ibRequest.getRequestReference()).setIsError(false).setTransactionStatus("SUCCESS").setErrorDetail(errorDetail);
                        }
                    } catch (Exception exception) {
                        log.error("doOutwardC2CTransactionFromIB():: IB_TRANSACTIONS table write error", exception.getMessage());
                    }
                }

            }
        }
        return response;
    }

    public void retryIBCustomerFndTransfer(InterCustomerFundTransferEntity transferFund, CustomerFndTransferEntity outC2CTxn) {
        Date date = new Date();
        transferFund.setEntryUser("SYSTEM");
        transferFund.setApprover("SYSTEM");
        transferFund.setApprovalDateTime(new Date());

        try {
            registerService.doRegister(outC2CTxn.getRoutingType(), outC2CTxn.getCurrencyId(), outC2CTxn.getAmount(), outC2CTxn.getVoucherNumber(), outC2CTxn.getPayerBankId(), outC2CTxn.getBenBankId(), outC2CTxn.getPayerBranchId(), outC2CTxn.getBenBranchId(), date, date, outC2CTxn.getReferenceNumber(), outC2CTxn.getBenAccNo(), outC2CTxn.getNarration(), outC2CTxn.getCbsName());
            customerFndTransferRepository.save(outC2CTxn);
            repository.save(transferFund);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    public void updateIbTxn(String responseRef, String status, Boolean isError, String errorMessage) {
        Optional<IbTransactionEntity> optionalEntity = ibTransactionRepository.findByResponseReferenceAndIsDeletedFalse(responseRef);
        if (optionalEntity.isPresent()) {
            IbTransactionEntity ibRequest = optionalEntity.get();
            ibRequest.setTransactionStatus(status);
            ibRequest.setIsError(isError);
            ibRequest.setErrorMessage(errorMessage);
            ibRequest.setId(optionalEntity.get().getId());
            ibTransactionRepository.save(ibRequest);
        }
    }

    public void updateIbTxnById(IbTransactionEntity ibRequest, String status, Boolean isError, String errorMessage) {
        ibRequest.setTransactionStatus(status);
        ibRequest.setIsError(isError);
        ibRequest.setErrorMessage(errorMessage);
        ibRequest.setId(ibRequest.getId());
        ibTransactionRepository.save(ibRequest);
    }
}

