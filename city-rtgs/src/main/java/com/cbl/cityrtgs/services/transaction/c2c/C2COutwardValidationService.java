package com.cbl.cityrtgs.services.transaction.c2c;

import com.cbl.cityrtgs.common.enums.ErrorCodeEnum;
import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.common.exception.*;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionTypeCodeResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.*;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.ShadowAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.TxnCfgSetupEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.IbTransactionEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.repositories.configuration.BranchRepository;
import com.cbl.cityrtgs.repositories.configuration.ShadowAccountRepository;
import com.cbl.cityrtgs.repositories.configuration.TxnCfgSetupRepository;
import com.cbl.cityrtgs.repositories.transaction.DeliveryChannelRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.transaction.CustomerAccountDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.LocalTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RequiredArgsConstructor
@Service
public class C2COutwardValidationService {
    private final CurrencyService currencyService;
    private final BranchRepository branchRepository;
    private final CustomerAccountDetailsService customerAccountDetailsService;
    private final ChargeAccountSetupService chargeAccountSetupService;
    private final TransactionPriorityService transactionPriorityService;
    private final BankService bankService;
    private final BranchService branchService;
    private final UiAppConfigurationService uiConfigurationService;
    private final DeliveryChannelRepository deliveryChannelRepository;
    private final ShadowAccountRepository shadowAccountRepository;
    private final TxnCfgSetupRepository repository;
    @Value("${ib.username}")
    protected String ibUsername;

    @Value("${ib.password}")
    protected String ibPassword;

    private String NAME_PATTERN = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.\\n\\r,'\\+ ]{1,70}";
    private String PHONE = "[0-9]{1,11}";
    private String YEAR = "^[\\d]{1,4}";
    private String CODE = "^[\\d]{1,3}";
    private String NUMBER = "^[\\d]{1,10}";

    public Boolean validateRtgsBalance(String transactionTypeCode, Long currencyId, BigDecimal amount) {

        if (transactionTypeCode.equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode()) || transactionTypeCode.equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode()) || transactionTypeCode.equals(TransactionTypeCodeEnum.GOVERNMENT_PAYMENTS.getCode())) {

            log.info("UI Outward Balance and Transaction RTGS Balance Validation successful!");

            return true;
        } else {
            CurrencyResponse response = currencyService.getById(currencyId);
            if (response.getC2cMinAmount().doubleValue() <= amount.doubleValue()) {
                log.info("UI Outward Balance and Transaction RTGS Balance Validation successful!");
                return true;
            } else {
                return false;
            }
        }
    }

    public Boolean validateCustomerBalance(InterCustomerFundTransferEntity fundTransfer, BigDecimal txnAmount, String availableBalance) {
        var availBalance = new BigDecimal(availableBalance);
        var totalTxnAmount = txnAmount.add(fundTransfer.getVatAmount().add(fundTransfer.getChargeAmount()));
        var balanceValidation = uiConfigurationService.getUiConfiguration().get(0).getValidateBalance();
        if (balanceValidation) {
            return availBalance.doubleValue() >= totalTxnAmount.doubleValue();
        } else {
            return true;
        }
    }

    public ChargeSetupResponse chargeVatCalculation(String payerAccNo, Long currencyId, BigDecimal amount, String transactionTypeCode, String customerSchemeCode) {
        ChargeSetupResponse chargeResponse = new ChargeSetupResponse();
        chargeResponse
                .setChargeAmount(0.00)
                .setVatAmount(0.00);

        if (transactionTypeCode.equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode()) || transactionTypeCode.equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode()) || transactionTypeCode.equals(TransactionTypeCodeEnum.GOVERNMENT_PAYMENTS.getCode())) {
            return chargeResponse;
        } else {
            boolean chargeEnabled = customerAccountDetailsService.getChargeEnabled(payerAccNo, customerSchemeCode);
            if (chargeEnabled) {
                chargeResponse = chargeAccountSetupService.getChargeVatByCurrency(currencyId, amount);
                if (Objects.nonNull(chargeResponse)) {
                    chargeResponse
                            .setChargeAmount(chargeResponse.getChargeAmount())
                            .setVatAmount(chargeResponse.getVatAmount());
                    return chargeResponse;
                }
            }

            return chargeResponse;
        }
    }

    public void validateLoginCredentials(IbTransactionRequest outC2CTxn) throws LoginValidationException {
        if (outC2CTxn.getUserName() != null && outC2CTxn.getPassword() != null
                && !outC2CTxn.getUserName().trim().isEmpty()
                && !outC2CTxn.getPassword().trim().isEmpty()
                && outC2CTxn.getUserName().equals(ibUsername)
                && outC2CTxn.getPassword().equals(ibPassword)) {
            log.info("Service login successful!");
        } else {
            throw new LoginValidationException(ErrorCodeEnum.ERROR_AUTH.getCode(), ErrorCodeEnum.ERROR_AUTH.getValue());
        }
    }


    public void validateAccountNumber(String accountNumber) throws AccountNumberValidationException {
        try {
            if (!customerAccountDetailsService.getAccountDetails(accountNumber).getResponseCode().equals("100"))
                throw new AccountNumberValidationException("API100", "Account " + accountNumber + " not found");
        } catch (Exception e) {
            throw new AccountNumberValidationException("API100", "Account " + accountNumber + " not found");
        }
        log.info("Account validation successful!");
    }

    public Boolean validateCustomerTxnInputs(String benName, String payerName) {
        if (benName != null && !this.valid(this.NAME_PATTERN, benName)) {
            return false;
        } else return payerName == null || this.valid(this.NAME_PATTERN, payerName);
    }

    public Boolean validateName(String benName, String payerName) {

        return ((benName != null && !valid(NAME_PATTERN, benName)) || (payerName != null && !valid(NAME_PATTERN, payerName)));
    }

    private boolean valid(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

    public TxnCfgSetupEntity getTransactionCfgSetup(Long currencyId) {
        List<TxnCfgSetupEntity> transactionCfgSetups = repository.findByCurrencyIdAndTxnActiveTrueAndIsDeletedFalse(currencyId);
        return transactionCfgSetups.size() > 0 ? transactionCfgSetups.get(0) : null;
    }

    public void validateTransactionTime(String txnTypeCode, Long currencyId) throws TxnTimeValidationException {
        TxnCfgSetupEntity txnCfgSetup = this.getTransactionCfgSetup(currencyId);
        boolean validTime;
        TransactionTypeCodeResponse txnTypeCodeResponse = transactionPriorityService.getDetailsByTransactionTypeCode(txnTypeCode);
        if (txnCfgSetup != null) {
            if (!txnCfgSetup.getTxnActive()) {
                throw new TxnTimeValidationException(ErrorCodeEnum.ERROR_TRANSACTION_TIME.getCode(), ErrorCodeEnum.ERROR_TRANSACTION_TIME.getValue());
            } else if (txnCfgSetup.getTimeRestricted()) {
                LocalTime now = LocalTime.now();
                LocalTime start = new LocalTime(txnCfgSetup.getStartTime());
                LocalTime end = new LocalTime(txnCfgSetup.getEndTime());
                LocalTime extraEndTime = new LocalTime(txnCfgSetup.getExtraEndTime());
                boolean validStartTime = now.isBefore(start);
                boolean validEndTime = now.isAfter(end);
                boolean validExtraEndTime = now.isAfter(extraEndTime);
                if (!txnTypeCodeResponse.getExtraEndTime()) {
                    validTime = !validEndTime && !validStartTime;
                } else {
                    validTime = !validExtraEndTime && !validStartTime;
                }
                if (!validTime) {
                    throw new TxnTimeValidationException(ErrorCodeEnum.ERROR_BUSINESS_HOUR.getCode(), ErrorCodeEnum.ERROR_BUSINESS_HOUR.getValue());
                }

            } else {
                log.info("Business time Validation Successful");
            }
        } else {
            throw new TxnTimeValidationException(ErrorCodeEnum.ERROR_TRANSACTION_TIME.getCode(), ErrorCodeEnum.ERROR_TRANSACTION_TIME.getValue());
        }

        log.info("Business hour validation successful!");
    }

    public void validateOutwardC2CTransaction(IbTransactionRequest outC2CTxn) throws InputValidationException {
        if (outC2CTxn.getRefNumber() != null && !outC2CTxn.getRefNumber().trim().isEmpty()) {
            if (outC2CTxn.getBenBranchRoutingNo() != null && !outC2CTxn.getBenBranchRoutingNo().trim().isEmpty()) {
                BranchEntity branchcheck = this.branchRepository.findByRoutingNumberAndIsDeletedFalse(outC2CTxn.getBenBranchRoutingNo()).get();
                if (branchcheck.getBank().isOwnerBank()) {
                    throw new InputValidationException("API104", "Invalid Beneficiary Branch");
                }
                if (outC2CTxn.getTransactionTypeCode() != null && !outC2CTxn.getTransactionTypeCode().trim().isEmpty() && outC2CTxn.getTransactionTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode())) {
                    if (outC2CTxn.getBinCode() == null || outC2CTxn.getBinCode().isEmpty()) {
                        throw new InputValidationException("API104", "BIN Code Not Provided");
                    }
                    if (outC2CTxn.getCommissionerateEconomicCode() == null || outC2CTxn.getCommissionerateEconomicCode().isEmpty()) {
                        throw new InputValidationException("API104", "Commission Rate Economic Code Not Provided");
                    }
                }
                String stlmntDateStr = Long.toString(outC2CTxn.getSettlementDate());
                if (stlmntDateStr.length() != 6) {
                    throw new InputValidationException("API104", "Invalid Date Format");
                } else {
                    if (outC2CTxn.getTransactionTypeCode() != null && !outC2CTxn.getTransactionTypeCode().trim().isEmpty() && outC2CTxn.getTransactionTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
                        if (outC2CTxn.getCustomsOfficeCode() == null) {
                            throw new InputValidationException("API104", "Custom Office Code Not Provided");
                        }
                        if (outC2CTxn.getRegistrationNumber() == null || outC2CTxn.getRegistrationNumber().trim().isEmpty()) {
                            throw new InputValidationException("API104", "Registration Number Not Provided");
                        }
                        if (outC2CTxn.getDeclarantCode() == null || outC2CTxn.getDeclarantCode().trim().isEmpty()) {
                            throw new InputValidationException("API104", "Declarent Code Not Provided");
                        }
                        if (outC2CTxn.getCustomerMobileNumber() == null || outC2CTxn.getCustomerMobileNumber().trim().isEmpty()) {
                            throw new InputValidationException("API104", "Customer Mobile Number Not Provided");
                        }
                        if (String.valueOf(outC2CTxn.getRegistrationYear()).length() != 4) {
                            throw new InputValidationException("API104", "Registration Year Not Provided or Length Not Equal 4");
                        }
                    }
                }
            } else {
                throw new InputValidationException("API104", "Beneficiary Branch Routing Number Not Provided");
            }
        } else {
            throw new InputValidationException("API104", "Reference Number Not Provided");
        }
    }


    public CustomerFndTransferBatchResponse validateBatchTxn(CustomerFndTransferBatch request) {
        CustomerFndTransferBatchResponse response = new CustomerFndTransferBatchResponse();
        if (request.getCustomerFndTransferTxns().size() > 0) {
            response = this.validateBatchTransactionList(request);
        }
        return response;
    }

    public CustomerFndTransferBatchResponse validateBatchTransactionList(CustomerFndTransferBatch request) {
        CustomerFndTransferBatchResponse response = new CustomerFndTransferBatchResponse();
        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());

        if (currency == null) {

            return response.builder()
                    .error(true)
                    .message("Currency not found!")
                    .build();
        }

        response.setCurrencyId(request.getCurrencyId());
        response.setPriorityCode(request.getPriorityCode());
        response.setTxnTypeCode(request.getTxnTypeCode());
        response.setPayerAccNo(request.getPayerAccNo());
        response.setSettlementDate(request.getSettlementDate());
        List<CustomerFndTransferErrorList> errorTxnList = new ArrayList<>();
        List<CustomerFndTransferBatchTxn> customerFndTransferTxns = new ArrayList<>();
        long accMaxLength = uiConfigurationService.getUiConfiguration().get(0).getAccNumberMaxLength();
        long accMinLength = uiConfigurationService.getUiConfiguration().get(0).getAccNumberMinLength();
        TransactionTypeCodeResponse txnTypeCode = transactionPriorityService.getDetailsByTransactionTypeCode(request.getTxnTypeCode());

        boolean isError;
        int indexNum = 0;
        int errorIndexNum = 0;
        String errorMessage = null;

        for (int i = 0; i < request.getCustomerFndTransferTxns().size(); i++) {
            var item = request.getCustomerFndTransferTxns().get(i);
            isError = false;
            errorMessage = "";
            CustomerFndTransferErrorList errorTxn = new CustomerFndTransferErrorList();
            CustomerFndTransferBatchTxn customerFndTransferTxn = new CustomerFndTransferBatchTxn();
            BankResponse beneficiaryBank = bankService.getBankByBicCode(item.getBenBankBic());

            if (item.getAmount().compareTo(BigDecimal.ZERO) == 0 || item.getAmount().compareTo(BigDecimal.ZERO) < 0) {
                isError = true;
                errorMessage = "Amount is must be greater than zero,";
                response.setError(isError);
                response.setMessage(errorMessage);
            }
            if (beneficiaryBank != null && !beneficiaryBank.getBic().equals(bankService.getOwnerBank().getBic()) && !beneficiaryBank.getBic().equals(bankService.getSettlementBank().getBic())) {
                customerFndTransferTxn.setBenBankId(beneficiaryBank.getId());
                customerFndTransferTxn.setBenBankBic(item.getBenBankBic());

                BranchResponse beneficiaryBranch = branchService.getBranchByRoutingNumber(item.getBenBranchRoutingNo());

                if (beneficiaryBranch != null) {
                    customerFndTransferTxn.setBenBranchId(beneficiaryBranch.getId());
                    customerFndTransferTxn.setBenBranchRoutingNo(beneficiaryBranch.getRoutingNumber());

                    if (!Objects.equals(beneficiaryBranch.getBankId(), beneficiaryBank.getId())) {
                        isError = true;
                        errorMessage = errorMessage + " Bank BIC And Branch routing Mismatch";
                        response.setError(isError);
                        response.setMessage(errorMessage);
                    }
                    if (!beneficiaryBranch.getRtgsBranch()) {
                        isError = true;
                        errorMessage = beneficiaryBranch.getRoutingNumber() + " is not RTGS enabled branch ";
                        response.setError(isError);
                        response.setMessage(errorMessage);
                    }

                } else {
                    isError = true;
                    errorMessage = errorMessage + ", Ben Branch Not Found " + item.getBenBranchRoutingNo();
                    response.setError(isError);
                    response.setMessage(errorMessage);
                }
                Optional<ShadowAccountEntity> shadowAccountEntity = shadowAccountRepository.findBybankIdAndCurrencyIdAndIsDeletedFalse(beneficiaryBank.getId(), request.getCurrencyId());
                if (shadowAccountEntity.isEmpty()) {
                    isError = true;
                    errorMessage = errorMessage + ", Shadow Account Not Found for BIC: " + item.getBenBankBic();
                    response.setError(isError);
                    response.setMessage(errorMessage);
                }

            } else {
                isError = true;
                errorMessage = errorMessage + ", Beneficiary Bank Not Valid for BIC : " + item.getBenBankBic();
                response.setError(isError);
                response.setMessage(errorMessage);
            }
            if (valid(NAME_PATTERN, item.getBenName()) && StringUtils.isNotBlank(item.getBenName())) {
                customerFndTransferTxn.setBenName(item.getBenName().replace("&", "and"));
            } else {
                isError = true;
                errorMessage = errorMessage + ", Beneficiary Name not valid " + item.getBenName();
                response.setError(isError);
                response.setMessage(errorMessage);
            }
            if ((long) item.getBenAccNo().length() <= accMaxLength && (long) item.getBenAccNo().length() >= accMinLength) {
                customerFndTransferTxn.setBenAccNo(item.getBenAccNo());
            } else {
                isError = true;
                errorMessage = errorMessage + ", Beneficiary Account Not valid " + item.getBenAccNo();
                response.setError(isError);
                response.setMessage(errorMessage);
            }

            if (request.getTxnTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {

                if (valid(CODE, item.getRmtCustOfficeCode()) && StringUtils.isNotBlank(item.getRmtCustOfficeCode())) {
                    customerFndTransferTxn.setRmtCustOfficeCode(item.getRmtCustOfficeCode()); // 3 digit numric
                } else {
                    isError = true;
                    errorMessage = errorMessage + ", Customs Office Code not valid " + item.getRmtCustOfficeCode();
                    response.setError(isError);
                    response.setMessage(errorMessage);
                }

                if (valid(YEAR, item.getRmtRegYear()) && StringUtils.isNotBlank(item.getRmtRegYear())) {
                    customerFndTransferTxn.setRmtRegYear(item.getRmtRegYear()); //4 digit valid year
                } else {
                    isError = true;
                    errorMessage = errorMessage + ", Reg Year not valid " + item.getRmtRegYear();
                    response.setError(isError);
                    response.setMessage(errorMessage);
                }

                if (valid(PHONE, item.getRmtCusCellNo()) && StringUtils.isNotBlank(item.getRmtCusCellNo())) {
                    customerFndTransferTxn.setRmtCusCellNo(item.getRmtCusCellNo()); // 11 digit numeric value and start with 01
                } else {
                    isError = true;
                    errorMessage = errorMessage + ", Mobile Number not valid " + item.getRmtCusCellNo();
                    response.setError(isError);
                    response.setMessage(errorMessage);
                }

                if (valid(NUMBER, item.getRmtDeclareCode()) && StringUtils.isNotBlank(item.getRmtDeclareCode())) {
                    customerFndTransferTxn.setRmtDeclareCode(item.getRmtDeclareCode());//numeric
                } else {
                    isError = true;
                    errorMessage = errorMessage + ", Declaration Code not valid  " + item.getRmtDeclareCode();
                    response.setError(isError);
                    response.setMessage(errorMessage);
                }

                if (valid(NUMBER, item.getRmtRegNum()) && StringUtils.isNotBlank(item.getRmtRegNum())) {
                    customerFndTransferTxn.setRmtRegNum(item.getRmtRegNum());//numeric value
                } else {
                    isError = true;
                    errorMessage = errorMessage + ", Registration number not valid " + item.getRmtRegNum();
                    response.setError(true);
                    response.setMessage(errorMessage);
                }
                customerFndTransferTxn.setBenAccNo(item.getBenAccNo());
                customerFndTransferTxn.setCharge(BigDecimal.ZERO);
                customerFndTransferTxn.setVat(BigDecimal.ZERO);

            }
            if (!request.getTxnTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {

                if (currency.getShortCode() != null && !currency.getShortCode().equalsIgnoreCase("BDT")) {
                    customerFndTransferTxn.setLcNumber(item.getLcNumber());
                    customerFndTransferTxn.setBenAccType(item.getBenAccType());
                    customerFndTransferTxn.setPayerAccType(item.getPayerAccType());
                } else {
                    if (currency.getShortCode().equalsIgnoreCase("BDT")) {
                        if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
                            if (txnTypeCode.getBalanceValidation()) {
                                BigDecimal c2cMinAmount = currencyService.getByCurrencyCode(currency.getShortCode()).getC2cMinAmount();
                                if (BigDecimal.valueOf(item.getAmount().doubleValue()).compareTo(c2cMinAmount) < 0) {
                                    isError = true;
                                    errorMessage = errorMessage + ", Balance Validation error";
                                    response.setError(true);
                                    response.setMessage(errorMessage);
                                }
                            }
                        }
                        if (request.getTxnTypeCode().equals(TransactionTypeCodeEnum.GOVERNMENT_PAYMENTS.getCode())) {
                            customerFndTransferTxn.setCharge(BigDecimal.ZERO);
                            customerFndTransferTxn.setVat(BigDecimal.ZERO);
                        }
                    }
                }
            }
            if (StringUtils.isNotBlank(item.getNarration())) {
                customerFndTransferTxn.setNarration(ValidationUtility.validateText(item.getNarration().trim(), 35)); // maximum 35  character
            } else {
                isError = true;
                errorMessage = errorMessage + ", Narration is not valid." + item.getNarration();
                response.setError(true);
                response.setMessage(errorMessage);
            }

            customerFndTransferTxn.setBatchTxn(true);
            customerFndTransferTxn.setAmount(item.getAmount());
            if (isError) {
                errorTxn.setIndexNum(++errorIndexNum);
                errorTxn.setBenBankBic(item.getBenBankBic());
                errorTxn.setBenAccNo(item.getBenAccNo());
                errorTxn.setBenBranchRoutingNo(item.getBenBranchRoutingNo());
                errorTxn.setBenName(item.getBenName());
                errorTxn.setAmount(item.getAmount());
                errorTxn.setNarration(item.getNarration());
                errorTxn.setBenAccType(item.getBenAccType());
                errorTxn.setPayerAccType(item.getPayerAccType());
                errorTxn.setErrorMessage(errorMessage);
                errorTxn.setRmtCusCellNo(item.getRmtCusCellNo());
                errorTxn.setRmtDeclareCode(item.getRmtDeclareCode());
                errorTxn.setRmtRegYear(item.getRmtRegYear());
                errorTxn.setRmtRegNum(item.getRmtRegNum());
                errorTxn.setRmtCustOfficeCode(item.getRmtCustOfficeCode());
                errorTxnList.add(errorTxn);
            } else {
                customerFndTransferTxn.setIndexNum(++indexNum);
                customerFndTransferTxns.add(customerFndTransferTxn);
            }
        }

        if (!errorTxnList.isEmpty()) {
            response.setErrorList(errorTxnList);
            response.setMessage(errorMessage);
        }
        if (!customerFndTransferTxns.isEmpty()) {
            response.setCustomerFndTransferTxns(customerFndTransferTxns);
        }
        return response;
    }

    public boolean isPayerNameInvalid(String payerName) {

        return (payerName != null && !valid(NAME_PATTERN, payerName));
    }

    public void deliveryChannelValidation(String deliveryChannel) throws DeliveryChannelValidationException {
        if (deliveryChannel != null && !deliveryChannel.trim().isEmpty()) {
            if (deliveryChannelRepository.existsByChannelName(deliveryChannel)) {
                log.info("Delivery Channel Validated Successfully");
            } else {
                throw new DeliveryChannelValidationException("API110", "Invalid Delivery Channel!");
            }
        } else {
            throw new DeliveryChannelValidationException("API110", "Delivery Channel Not Provided!");
        }
    }

    public void validateCurrency(String currency) throws CurrencyValidationException {
        try {
            currencyService.getByCurrencyCode(currency);
        } catch (Exception e) {
            throw new CurrencyValidationException(ErrorCodeEnum.ERROR_CURRENCY.getCode(), ErrorCodeEnum.ERROR_CURRENCY.getValue());
        }
        log.info("Currency validation successful!");
    }

    public void validateOutwardC2CTransaction(IbTransactionEntity outC2CTxn) throws Exception {
        if (outC2CTxn.getRequestReference() != null && !outC2CTxn.getRequestReference().trim().isEmpty()) {
            if (outC2CTxn.getBenAccount() != null && !outC2CTxn.getBenAccount().trim().isEmpty()) {
                if (outC2CTxn.getBenName() != null && !outC2CTxn.getBenName().trim().isEmpty()) {
                    if (outC2CTxn.getCurrency() != null && !outC2CTxn.getCurrency().trim().isEmpty()) {
                        if (outC2CTxn.getAmount() == null) {
                            throw new InputValidationException("API104", "Amount Not Provided");
                        } else if (outC2CTxn.getBenBranchRoutingNo() != null && !outC2CTxn.getBenBranchRoutingNo().trim().isEmpty()) {
                            BranchEntity branchcheck = branchRepository.findByRoutingNumberAndIsDeletedFalse(outC2CTxn.getBenBranchRoutingNo()).get();
                            if (branchcheck.getBank().isOwnerBank()) {
                                throw new InputValidationException("API104", "Invalid Beneficiary Branch");
                            }

                            if (outC2CTxn.getPayerAccount() != null && !outC2CTxn.getPayerAccount().trim().isEmpty()) {
                                if (outC2CTxn.getPayerName() != null && !outC2CTxn.getPayerName().trim().isEmpty()) {
                                    if (outC2CTxn.getTransactionTypeCode() != null && !outC2CTxn.getTransactionTypeCode().trim().isEmpty() && outC2CTxn.getTransactionTypeCode().equalsIgnoreCase(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode())) {
                                        if (outC2CTxn.getBinCode() == null || outC2CTxn.getBinCode().isEmpty()) {
                                            throw new InputValidationException("API104", "BIN Code Not Provided");
                                        }
                                        if (outC2CTxn.getCommissionerateEconomicCode() == null || outC2CTxn.getCommissionerateEconomicCode().isEmpty()) {
                                            throw new InputValidationException("API104", "Commission Rate Economic Code Not Provided");
                                        }
                                    }
                                    if (outC2CTxn.getDeliveryChannel() != null && !outC2CTxn.getDeliveryChannel().trim().isEmpty()) {
                                        if (outC2CTxn.getTransactionTypeCode() != null && !outC2CTxn.getTransactionTypeCode().trim().isEmpty() && outC2CTxn.getTransactionTypeCode().equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode())) {
                                            if (outC2CTxn.getCustomsOfficeCode() == null) {
                                                throw new InputValidationException("API104", "Custom Office Code Not Provided");
                                            }
                                            if (outC2CTxn.getRegistrationNumber() == null || outC2CTxn.getRegistrationNumber().trim().isEmpty()) {
                                                throw new InputValidationException("API104", "Registration Number Not Provided");
                                            }
                                            if (outC2CTxn.getDeclarantCode() == null || outC2CTxn.getDeclarantCode().trim().isEmpty()) {
                                                throw new InputValidationException("API104", "Declarent Code Not Provided");
                                            }
                                            if (outC2CTxn.getCustomerMobileNumber() == null || outC2CTxn.getCustomerMobileNumber().trim().isEmpty()) {
                                                throw new InputValidationException("API104", "Customer Mobile Number Not Provided");
                                            }
                                            if (String.valueOf(outC2CTxn.getRegistrationYear()).length() != 4) {
                                                throw new InputValidationException("API104", "Registration Year Not Provided or Length Not Equal 4");
                                            }
                                        }
                                    } else {
                                        throw new InputValidationException("API104", "Delivery Channel Not Provided");
                                    }
                                } else {
                                    throw new InputValidationException("API104", "Payer Name Not Provided");
                                }
                            } else {
                                throw new InputValidationException("API104", "Payer Account Not Provided");
                            }
                        } else {
                            throw new InputValidationException("API104", "Beneficiary Branch Routing Number Not Provided");
                        }
                    } else {
                        throw new InputValidationException("API107", "Invalid Currency Code");
                    }
                } else {
                    throw new InputValidationException("API104", "Beneficiary Name Not Provided");
                }
            } else {
                throw new InputValidationException("API104", "Beneficiary Account Not Provided");
            }
        } else {
            throw new InputValidationException("API104", "Reference Number Not Provided");
        }
    }

}
