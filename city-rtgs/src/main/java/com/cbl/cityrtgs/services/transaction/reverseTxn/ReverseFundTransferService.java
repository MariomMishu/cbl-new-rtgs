package com.cbl.cityrtgs.services.transaction.reverseTxn;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.transaction.*;
import com.cbl.cityrtgs.models.dto.transaction.c2c.PayerDetailsResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.AccountTransactionRegisterEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.CbsTxnReversalLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.repositories.transaction.AccountTransactionRegisterRepository;
import com.cbl.cityrtgs.repositories.transaction.ReverseTxnRepository;
import com.cbl.cityrtgs.repositories.transaction.TxnReversalLogRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.CbsTransactionService;
import com.cbl.cityrtgs.services.transaction.CustomerAccountDetailsService;
import com.cbl.cityrtgs.services.transaction.NarrationMappingService;
import com.cbl.cityrtgs.services.transaction.c2c.C2COutwardValidationService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReverseFundTransferService {
    private final TxnReversalLogRepository txnReversalLogRepository;
    private final AccountTransactionRegisterRepository registerRepository;
    private final SettlementAccountService settlementAccountService;
    private final AccountTypeService accountTypeService;
    private final CurrencyService currencyService;
    private final ReverseTxnRepository repository;
    private final CbsTransactionService cbsTransactionService;
    private final DepartmentAccountService departmentAccountService;
    private final NarrationMappingService narrationMappingService;
    private final BankService bankService;
    private final C2COutwardValidationService validationService;
    private final CustomerAccountDetailsService customerAccountDetailsService;

   // @Transactional(rollbackFor = Exception.class)
    public TransactionResponse doReversal(CustomerFndTransferEntity txn, String entryUser, String messageDefinitionIdentifier) {
        boolean reverse = true;
        boolean returnTxn = false;
        TransactionResponse response = new TransactionResponse();
        if (initReversal(txn.getReferenceNumber(), txn.getRoutingType())) {

            try {
                CbsResponse cbsResponse;
                boolean chargeEnabled = true;
                var currency = currencyService.getById(txn.getCurrencyId());
                var departmentAccount = departmentAccountService.getById(txn.getDepartmentAccountId());
                var settlementAccount = settlementAccountService.getEntityByCurrencyId(txn.getCurrencyId());
                TransactionRequest transactionRequest = new TransactionRequest();
                if (messageDefinitionIdentifier.equals(MessageDefinitionIdentifier.PACS004.value())) {
                    chargeEnabled = false;
                    reverse = false;
                    returnTxn = true;
                } else {
                    if (txn.getCharge().equals(BigDecimal.ZERO) && txn.getVat().equals(BigDecimal.ZERO)) {
                        chargeEnabled = false;
                    }
                }
                //schedular start
                transactionRequest
                        .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getC2CTransactionNarration(txn, reverse, returnTxn)))
                        .setVat(txn.getVat())
                        .setCharge(txn.getCharge())
                        .setAmount(txn.getAmount())
                        .setCbsName(txn.getCbsName())
                        .setChargeEnabled(chargeEnabled)
                        .setDrAccount(txn.getTxnGlAccount())
                        .setCrAccount(txn.getPayerAccNo())
                        .setChargeAccount(departmentAccount.getChargeAccNumber())
                        .setVatAccount(departmentAccount.getVatAccNumber())
                        .setSettlementAccountId(settlementAccount.getId())
                        .setCurrencyId(txn.getCurrencyId())
                        .setCurrencyCode(currency.getShortCode())
                        .setRtgsRefNo(txn.getReferenceNumber())
                        .setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(txn.getLcNumber()) ? txn.getLcNumber() : txn.getNarration())))
                        .setRemarks(txn.getReferenceNumber() + "-" + bankService.getBankById(txn.getBenBankId()).getBic())
                        .setChargeRemarks(txn.getParentBatchNumber())
                        .setAbabilRequestId(txn.getAbabilReferenceNumber());
                cbsResponse = cbsTransactionService.cbsReverseTransaction(transactionRequest);
                if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    log.info("Reversal transaction Successful.");
                    departmentAccountService.departmentAccountTransaction(txn.getAmount(), txn.getRoutingType(), txn.getDepartmentId(), FundTransferType.CustomerToCustomer, true, txn.getCharge(), txn.getVat(), txn.getReferenceNumber(), txn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                    Optional<AccountTransactionRegisterEntity> registerEntityOptional = registerRepository.findByTransactionReferenceNumberAndRoutingType(txn.getReferenceNumber(), txn.getRoutingType());
                    if (registerEntityOptional.isPresent()) {
                        AccountTransactionRegisterEntity registerEntity = registerEntityOptional.get();
                        registerEntity.setId(registerEntityOptional.get().getId());
                        registerEntity.setValid(false);
                        registerRepository.save(registerEntity);
                    }
                    txnReversalLogRepository.save(createReversalLog(txn, entryUser, messageDefinitionIdentifier, cbsResponse));
                    response.setError(false);
                    response.setMessage(cbsResponse.getResponseMessage());
                } else {
                    response.setError(true);
                    response.setMessage(cbsResponse.getResponseMessage());
                }
                //update reversal log  pending->0, Reversal Attempt->1
                //schedular end
            } catch (Exception e) {
                log.error("Cannot save reversal request.", e);
                response.setError(true);
                response.setMessage(e.getMessage());
            }
        }
        return response;
    }

  //  @Transactional(rollbackFor = Exception.class)
    public void doReversal(BankFndTransferEntity txn, String entryUser) {
        if (initReversal(txn.getReferenceNumber(), txn.getRoutingType())) {
            CbsResponse cbsResponse;
            try {
                var currency = currencyService.getById(txn.getCurrencyId());
                var departmentAccount = departmentAccountService.getById(txn.getDepartmentAccountId());
                var settlementAccount = settlementAccountService.getEntityByCurrencyId(txn.getCurrencyId());
                String txnRemarks = StringUtils.isNotBlank(txn.getLcNumber()) ? txn.getLcNumber() : txn.getBillNumber();
                TransactionRequest transactionRequest = new TransactionRequest();
                transactionRequest
                        .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getB2BTransactionNarration(txn, true, false)))
                        .setVat(BigDecimal.ZERO)
                        .setCharge(BigDecimal.ZERO)
                        .setAmount(txn.getAmount())
                        .setCbsName(txn.getCbsName())
                        .setChargeEnabled(false)
                        .setDrAccount(txn.getTransactionGlAccount())
                        .setCrAccount(txn.getPayerCbsAccNo())
                        .setChargeAccount(departmentAccount.getChargeAccNumber())
                        .setVatAccount(departmentAccount.getVatAccNumber())
                        .setSettlementAccountId(settlementAccount.getId())
                        .setCurrencyId(txn.getCurrencyId())
                        .setCurrencyCode(currency.getShortCode())
                        .setParticular2(StringUtils.isNotBlank(txnRemarks) ? txnRemarks : txn.getParentBatchNumber())
                        .setRtgsRefNo(txn.getReferenceNumber())
                        .setRemarks(txn.getReferenceNumber())
                        .setChargeRemarks("NA");
                cbsResponse = cbsTransactionService.cbsReverseTransaction(transactionRequest);
                if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    txnReversalLogRepository.save(createBankTxnReversalLog(txn, entryUser));
                    departmentAccountService.departmentAccountTransaction(txn.getAmount(), txn.getRoutingType(), txn.getDepartmentId(), FundTransferType.BankToBank, true, BigDecimal.ZERO, BigDecimal.ZERO, txn.getReferenceNumber(), txn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                    Optional<AccountTransactionRegisterEntity> registerEntityOptional = registerRepository.findByTransactionReferenceNumberAndRoutingType(txn.getReferenceNumber(), txn.getRoutingType());
                    if (registerEntityOptional.isPresent()) {
                        AccountTransactionRegisterEntity registerEntity = registerEntityOptional.get();
                        registerEntity.setId(registerEntityOptional.get().getId());
                        registerEntity.setValid(false);
                        registerRepository.save(registerEntity);
                    }
                }

            } catch (Exception ignored) {

            }
        }
    }

    private boolean initReversal(String referenceNumber, RoutingType routingType) {
        boolean reversalExists = txnReversalLogRepository.existsByReferenceNumberAndRoutingType(referenceNumber, routingType);
        if (reversalExists) {
            return false;
        } else {
            return registerRepository.existsByTransactionReferenceNumberAndRoutingType(referenceNumber, routingType);
        }
    }

    private CbsTxnReversalLogEntity createBankTxnReversalLog(BankFndTransferEntity txn, String entryUser) {
        CurrencyResponse currency = currencyService.getById(txn.getCurrencyId());
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(txn.getCurrencyId());
        AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(settlementAccount.getId(), CbsName.FINACLE);

        CbsTxnReversalLogEntity reversal = new CbsTxnReversalLogEntity();
        reversal.setCreatedAt(new Date());
        reversal.setPending(true);
        reversal.setReferenceNumber(txn.getReferenceNumber());
        reversal.setRequestId(txn.getReferenceNumber());
        reversal.setReversalAttempted(0);
        reversal.setRoutingType(txn.getRoutingType());
        reversal.setFundTransferType(FundTransferType.BankToBank);
        reversal.setOrgEntryUser(entryUser);
        reversal.setAmount(txn.getAmount());
        reversal.setCurrencyCode(currency.getShortCode());
        reversal.setNarration(txn.getNarration());
        reversal.setTransactionDate(txn.getTransactionDate());
        reversal.setReturnReversalType(getReturnReversalType(txn.getTransactionStatus().toString()));
        reversal.setReturnCode(txn.getReturnCode());
        if (txn.getReturnReason() == null) {
            reversal.setReturnDescription("Entry Refused by the Receiver");
        } else {
            reversal.setReturnDescription(txn.getReturnReason());
        }
        reversal.setVat(txn.getVat() == null ? BigDecimal.ZERO : txn.getVat());
        reversal.setCharge(txn.getCharge() == null ? BigDecimal.ZERO : txn.getCharge());
        reversal.setDepartmentAccountId(txn.getDepartmentAccountId());
        reversal.setFromAccountNumber(txn.getPayerAccNo());
        reversal.setToAccountNumber(txn.getBenAccNo());
        reversal.setCbsName(accountTypeEntity.getCbsName().toString());
        reversal = repository.save(reversal);
        return reversal;
    }

    private CbsTxnReversalLogEntity createReversalLog(CustomerFndTransferEntity txn, String entryUser, String messageDefinitionIdentifier, CbsResponse cbsResponse) {
        CurrencyResponse currency = currencyService.getById(txn.getCurrencyId());
        SettlementAccountResponse settlementAccount = settlementAccountService.getEntityByCurrencyId(txn.getCurrencyId());
        AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(settlementAccount.getId(), CbsName.valueOf(txn.getCbsName()));
        CbsTxnReversalLogEntity reversal = new CbsTxnReversalLogEntity();
        reversal.setCreatedAt(new Date());
        reversal.setPending(true);
        reversal.setReferenceNumber(txn.getReferenceNumber());
        reversal.setRequestId(txn.getReferenceNumber());
        reversal.setReversalAttempted(0);
        reversal.setRoutingType(txn.getRoutingType());
        reversal.setFundTransferType(FundTransferType.CustomerToCustomer);
        reversal.setOrgEntryUser(entryUser);
        reversal.setAmount(txn.getAmount());
        reversal.setCurrencyCode(currency.getShortCode());
        reversal.setNarration(txn.getNarration());
        reversal.setTransactionDate(txn.getTransactionDate());
        reversal.setReturnReversalType(getReturnReversalType(txn.getTransactionStatus()));
        reversal.setReturnCode(txn.getReturnCode());
        if (txn.getReturnReason() == null) {
            reversal.setReturnDescription("Entry Refused by the Receiver");
        } else {
            reversal.setReturnDescription(txn.getReturnReason());
        }
        if (messageDefinitionIdentifier.equals(MessageDefinitionIdentifier.PACS004.value())) {
            reversal.setVat(BigDecimal.ZERO);
            reversal.setCharge(BigDecimal.ZERO);
        } else {
            reversal.setVat(txn.getVat() == null ? BigDecimal.ZERO : txn.getVat());
            reversal.setCharge(txn.getCharge() == null ? BigDecimal.ZERO : txn.getCharge());
        }
        reversal.setDepartmentAccountId(txn.getDepartmentAccountId());
        reversal.setFromAccountNumber(txn.getTxnGlAccount());
        reversal.setToAccountNumber(txn.getPayerAccNo());
        reversal.setCbsName(accountTypeEntity.getCbsName().toString());
        reversal.setResponseErrorCode(cbsResponse.getResponseCode());
        reversal.setResponseErrorMesssage(cbsResponse.getResponseMessage());
        reversal.setResponseReferenceNumber(cbsResponse.getTransactionRefNumber());
        reversal = repository.save(reversal);
        log.info("Insert in reversal Txn Log...");
        return reversal;
    }

    public void initiateReversalTxnScheduler() {

        log.info("Initiating Reverse Txn Scheduler...");

        List<CbsTxnReversalLogEntity> reversalLogEntityList = repository.findAllByPendindTrueAndDate();
        log.info("Inward Reverse Txn Size: {}", reversalLogEntityList.size());

        if (!reversalLogEntityList.isEmpty()) {

            reversalLogEntityList.forEach(reversalLog -> {

            });
        }
    }

    private ReturnReversalType getReturnReversalType(String txnStatus) {
        if (txnStatus.equals(TransactionStatus.Rejected.toString()) || txnStatus.equals(TransactionStatus.Failed.toString()))
            return ReturnReversalType.REVERSAL;
        else if (txnStatus.equals(TransactionStatus.Reversed.toString())) {
            return ReturnReversalType.RETURN;
        } else {
            return ReturnReversalType.DEFAULT;
        }
    }

   // @Transactional(rollbackFor = Exception.class)
    public TransactionResponse doReversalWithCharge(CustomerFndTransferEntity txn, String entryUser, String messageDefinitionIdentifier) {
        TransactionResponse response = new TransactionResponse();
        if (initReversal(txn.getReferenceNumber(), txn.getRoutingType())) {
            try {
                PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(txn.getPayerAccNo());
                CbsResponse cbsResponse;
                var currency = currencyService.getById(txn.getCurrencyId());
                var departmentAccount = departmentAccountService.getById(txn.getDepartmentAccountId());
                var settlementAccount = settlementAccountService.getEntityByCurrencyId(txn.getCurrencyId());
                TransactionRequest transactionRequest = new TransactionRequest();
                var chargeSetupResponse = validationService.chargeVatCalculation(txn.getPayerAccNo(), txn.getCurrencyId(), txn.getAmount(), TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode(), payerDetailsResponse.getSchemeCode());
                Double charge = chargeSetupResponse.getChargeAmount();
                Double vat = chargeSetupResponse.getVatAmount();

                //schedular start
                transactionRequest
                        .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getC2CTransactionNarration(txn, true, false)))
                        .setVat(BigDecimal.valueOf(vat))
                        .setCharge(BigDecimal.valueOf(charge))
                        .setAmount(txn.getAmount())
                        .setCbsName(txn.getCbsName())
                        .setChargeEnabled(true)
                        .setDrAccount(txn.getTxnGlAccount())
                        .setCrAccount(txn.getPayerAccNo())
                        .setChargeAccount(departmentAccount.getChargeAccNumber())
                        .setVatAccount(departmentAccount.getVatAccNumber())
                        .setSettlementAccountId(settlementAccount.getId())
                        .setCurrencyId(txn.getCurrencyId())
                        .setCurrencyCode(currency.getShortCode())
                        .setRtgsRefNo(txn.getReferenceNumber())
                        .setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(txn.getLcNumber()) ? txn.getLcNumber() : txn.getNarration())))
                        .setRemarks(txn.getReferenceNumber() + "-" + bankService.getBankById(txn.getBenBankId()).getBic())
                        .setChargeRemarks(txn.getParentBatchNumber())
                        .setAbabilRequestId(txn.getAbabilReferenceNumber());
                cbsResponse = cbsTransactionService.cbsReverseTransaction(transactionRequest);
                if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    log.info("Reversal transaction Successful.");
                    txnReversalLogRepository.save(createReversalLog(txn, entryUser, messageDefinitionIdentifier, cbsResponse));
                    departmentAccountService.departmentAccountTransaction(txn.getAmount(), txn.getRoutingType(), txn.getDepartmentId(), FundTransferType.CustomerToCustomer, true, txn.getCharge(), txn.getVat(), txn.getReferenceNumber(), txn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                    Optional<AccountTransactionRegisterEntity> registerEntityOptional = registerRepository.findByTransactionReferenceNumberAndRoutingType(txn.getReferenceNumber(), txn.getRoutingType());
                    if (registerEntityOptional.isPresent()) {
                        AccountTransactionRegisterEntity registerEntity = registerEntityOptional.get();
                        registerEntity.setId(registerEntityOptional.get().getId());
                        registerEntity.setValid(false);
                        registerRepository.save(registerEntity);
                    }
                    response.setError(false);
                    response.setMessage(cbsResponse.getResponseMessage());
                } else {
                    response.setError(true);
                    response.setMessage(cbsResponse.getResponseMessage());
                }
            } catch (Exception e) {
                log.error("Cannot save reversal request.", e);
                response.setError(true);
                response.setMessage(e.getMessage());
            }
        }
        return response;
    }

  //  @Transactional(rollbackFor = Exception.class)
    public TransactionResponse doReversalWithOutCharge(CustomerFndTransferEntity txn, String entryUser, String messageDefinitionIdentifier) {
        TransactionResponse response = new TransactionResponse();
        boolean reverse = true;
        boolean returnTxn = false;
        if (initReversal(txn.getReferenceNumber(), txn.getRoutingType())) {
            try {
                CbsResponse cbsResponse;
                var currency = currencyService.getById(txn.getCurrencyId());
                var departmentAccount = departmentAccountService.getById(txn.getDepartmentAccountId());
                var settlementAccount = settlementAccountService.getEntityByCurrencyId(txn.getCurrencyId());
                TransactionRequest transactionRequest = new TransactionRequest();
                if (messageDefinitionIdentifier.equals(MessageDefinitionIdentifier.PACS004.value())) {
                    reverse = false;
                    returnTxn = true;
                }
                //schedular start
                transactionRequest
                        .setNarration(ValidationUtility.narrationValidation(narrationMappingService.getC2CTransactionNarration(txn, reverse, returnTxn)))
                        .setVat(BigDecimal.ZERO)
                        .setCharge(BigDecimal.ZERO)
                        .setAmount(txn.getAmount())
                        .setCbsName(txn.getCbsName())
                        .setChargeEnabled(false)
                        .setDrAccount(txn.getTxnGlAccount())
                        .setCrAccount(txn.getPayerAccNo())
                        .setChargeAccount(departmentAccount.getChargeAccNumber())
                        .setVatAccount(departmentAccount.getVatAccNumber())
                        .setSettlementAccountId(settlementAccount.getId())
                        .setCurrencyId(txn.getCurrencyId())
                        .setCurrencyCode(currency.getShortCode())
                        .setRtgsRefNo(txn.getReferenceNumber())
                        .setParticular2(ValidationUtility.narrationValidation(narrationMappingService.getTxnRemarks(StringUtils.isNotBlank(txn.getLcNumber()) ? txn.getLcNumber() : txn.getNarration())))
                        .setRemarks(txn.getReferenceNumber() + "-" + bankService.getBankById(txn.getBenBankId()).getBic())
                        .setChargeRemarks(txn.getParentBatchNumber())
                        .setAbabilRequestId(txn.getAbabilReferenceNumber());
                cbsResponse = cbsTransactionService.cbsReverseTransaction(transactionRequest);
                if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    log.info("Reversal transaction Successful.");
                    txnReversalLogRepository.save(createReversalLog(txn, entryUser, messageDefinitionIdentifier, cbsResponse));
                    departmentAccountService.departmentAccountTransaction(txn.getAmount(), txn.getRoutingType(), txn.getDepartmentId(), FundTransferType.CustomerToCustomer, true, txn.getCharge(), txn.getVat(), txn.getReferenceNumber(), txn.getCurrencyId(), cbsResponse.getTransactionRefNumber());
                    Optional<AccountTransactionRegisterEntity> registerEntityOptional = registerRepository.findByTransactionReferenceNumberAndRoutingType(txn.getReferenceNumber(), txn.getRoutingType());
                    if (registerEntityOptional.isPresent()) {
                        AccountTransactionRegisterEntity registerEntity = registerEntityOptional.get();
                        registerEntity.setId(registerEntityOptional.get().getId());
                        registerEntity.setValid(false);
                        registerRepository.save(registerEntity);
                    }
                    response.setError(false);
                    response.setMessage(cbsResponse.getResponseMessage());
                } else {
                    response.setError(true);
                    response.setMessage(cbsResponse.getResponseMessage());
                }

                //update reversal log  pending->0, Reversal Attempt->1
                //schedular end
            } catch (Exception e) {
                log.error("Cannot save reversal request.", e);
                response.setError(true);
                response.setMessage(e.getMessage());
            }
        }
        return response;
    }
}
