package com.cbl.cityrtgs.services.transaction.b2b;

import com.cbl.cityrtgs.common.enums.TransactionTypeCodeEnum;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.TransactionResponse;
import com.cbl.cityrtgs.models.dto.transaction.b2b.*;
import com.cbl.cityrtgs.models.entitymodels.configuration.BranchEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentAccountEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.configuration.BranchRepository;
import com.cbl.cityrtgs.services.configuration.*;
import com.cbl.cityrtgs.services.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.cbl.cityrtgs.common.utility.ValidationUtility.validateText;

@Slf4j
@RequiredArgsConstructor
@Service
public class B2BOutwardValidationService {
    private final BranchRepository branchRepository;
    private final LimitProfileService limitProfileService;
    private final CurrencyService currencyService;
    private final BankService bankService;
    private final BranchService branchService;
    private final UserInfoService userInfoService;
    private final DepartmentAccountService departmentAccountService;
    private String NARRATION_PATTERN = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.,'\\+ ]{1,35}";

    public TransactionResponse validateOutwardB2B(BankFundTransferRequest outB2B) {
        TransactionResponse response = new TransactionResponse();
        if (outB2B.getPriorityCode() != null && !outB2B.getPriorityCode().trim().isEmpty()) {
        } else {
            response.setError(true);
            response.setMessage("Priority Code Required");
        }
        return response;
    }

    public TransactionResponse validateOutwardB2BTransaction(BankFundTransferDetails outB2BTxn, Long currencyId, Long profileId) {
        TransactionResponse response = new TransactionResponse();
        if (outB2BTxn.getBenBankId() != null) {
            if (outB2BTxn.getBenBranchId() != null) {
                if (Objects.nonNull(outB2BTxn.getAmount())) {
                    if (outB2BTxn.getAmount() == null) {
                        response.setError(true);
                        response.setMessage("Amount is not provided");
                    }
                    if (outB2BTxn.getAmount().compareTo(BigDecimal.ZERO) == 0 || outB2BTxn.getAmount().compareTo(BigDecimal.ZERO) == -1) {
                        response.setError(true);
                        response.setMessage("Amount is must be greater than zero");
                    }

                    if (!limitProfileService.checkTxnLimitProfile(currencyId, outB2BTxn.getAmount(), profileId)) {
                        response.setError(true);
                        response.setMessage("Please Check profile limit!");
                    }
                } else {
                    response.setError(true);
                    response.setMessage("Amount Required");
                }
                if (outB2BTxn.getBenBranchRoutingNo() != null && !outB2BTxn.getBenBranchRoutingNo().trim().isEmpty()) {
                    if (outB2BTxn.getBenBranchRoutingNo() != null) {
                        BranchEntity branchCheck = this.branchRepository.findByRoutingNumberAndIsDeletedFalse(outB2BTxn.getBenBranchRoutingNo()).get();
                        if (branchCheck == null || branchCheck.getBank().isOwnerBank()) {
                            response.setError(true);
                            response.setMessage("Invalid Beneficiary Branch!");
                        }
                    }

                } else {
                    response.setError(true);
                    response.setMessage("Beneficiary Branch Required!");
                }

            }
        } else {
            response.setError(true);
            response.setMessage("Beneficiary bank Required!");
        }
        return response;
    }

    public BankFundTransferBatchResponse batchValidation(BankFundTransferBatch request) {
        BankFundTransferBatchResponse response;
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();

        UserInfoEntity userInfoDetails = userInfoService.getEntityById(currentUser.getId());
        if (userInfoDetails == null) {

            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("User not found!")
                    .build();
        }

        CurrencyResponse currency = currencyService.getById(request.getCurrencyId());

        if (currency == null) {

            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("Currency not found!")
                    .build();
        }

        DepartmentAccountEntity departmentAccountResponse = departmentAccountService.getDepartmentAccEntity(userInfoDetails.getDept().getId(), request.getCurrencyId(), RoutingType.Outgoing);

        if (departmentAccountResponse == null) {
            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("Department account not found!")
                    .build();
        }

        if (request.getFundTransferTxnList().size() > 0) {

            response = this.batchTxnValidation(request, userInfoDetails, currency);
            if (response.isError()) {
                response.setError(true);
                response.setMessage(response.getMessage());
            } else {
                response.setError(false);
                response.setMessage("Batch Validation Successful.");
            }

        } else {
            return BankFundTransferBatchResponse.builder()
                    .error(true)
                    .message("Empty Txn list!")
                    .build();
        }
        return response;
    }

    public BankFundTransferBatchResponse batchTxnValidation(BankFundTransferBatch request,  UserInfoEntity userInfoDetails, CurrencyResponse currency) {
        BankFundTransferBatchResponse response = new BankFundTransferBatchResponse();
        response.setCurrencyId(request.getCurrencyId());
        response.setPriorityCode(request.getPriorityCode());
        response.setSettlementDate(request.getSettlementDate());
        List<BankFundTransferErrorList> errorTxnList = new ArrayList();
        List<BankFundTransferBatchTxn> fundTransferTxns = new ArrayList();

        int indexNum = 0;
        int errorIndexNum = 0;
        boolean error;
        String errorNote = "";
        BranchResponse fcRecBranch;

        for (int i = 0; i < request.getFundTransferTxnList().size(); i++) {
            var item = request.getFundTransferTxnList().get(i);
            error = false;
            errorNote = "";
            BankFundTransferErrorList errorTxn = new BankFundTransferErrorList();
            BankFundTransferBatchTxn fundTransferTxn = new BankFundTransferBatchTxn();
            BankResponse beneficiaryBank = bankService.getBankByBicCode(item.getBenBankBic());
            BranchResponse beneficiaryBranch = branchService.getBranchByRoutingNumber(item.getBenBranchRoutingNo());
            if (BigDecimal.valueOf(item.getAmount().doubleValue()).compareTo(currency.getB2bMinAmount()) == -1) {
                error = true;
                errorNote = "Balance Validation error!";
                response.setError(true);
                response.setMessage(errorNote);
            }

            if (!limitProfileService.checkTxnLimitProfile(request.getCurrencyId(), item.getAmount(), userInfoDetails.getProfile().getId())) {
                error = true;
                errorNote = errorNote + " Please Check profile limit!";
                response.setError(true);
                response.setMessage(errorNote);
            }
            if (beneficiaryBank != null && !beneficiaryBank.equals(bankService.getOwnerBank())) {
                fundTransferTxn.setBenBankId(beneficiaryBank.getId());
                fundTransferTxn.setBenBankBic(item.getBenBankBic());
                fundTransferTxn.setBenBankName(beneficiaryBank.getName());
            } else {
                error = true;
                errorNote = errorNote + ", Beneficiary Bank Not Found ";
                response.setError(true);
                response.setMessage(errorNote);
            }
            if (beneficiaryBranch == null) {
                error = true;
                errorNote = errorNote + ", Beneficiary Branch Not Found ";
                response.setError(true);
                response.setMessage(errorNote);
            } else {
                fundTransferTxn.setBenBranchId(beneficiaryBranch.getId());
                fundTransferTxn.setBenBranchRoutingNo(beneficiaryBranch.getRoutingNumber());
                fundTransferTxn.setBenBranchName(beneficiaryBranch.getName());
            }
            if (beneficiaryBank != null && !beneficiaryBank.equals(bankService.getOwnerBank()) && beneficiaryBranch != null) {
                if (beneficiaryBranch.getBankId() != beneficiaryBank.getId()) {
                    error = true;
                    errorNote = errorNote + ", Bic Code And Routing Mismatch ";
                    response.setError(true);
                    response.setMessage(errorNote);
                }
                if (!beneficiaryBranch.getRtgsBranch()) {
                    error = true;
                    errorNote = beneficiaryBranch.getRoutingNumber() + ", Not RtGS Enabled Branch ";
                    response.setError(true);
                    response.setMessage(errorNote);
                }
                if (!beneficiaryBranch.getTreasuryBranch()) {
                    error = true;
                    errorNote = beneficiaryBranch.getRoutingNumber() + ", Not Treasury Branch ";
                    response.setError(true);
                    response.setMessage(errorNote);
                }
            }

            if (currency.getShortCode() != null && !currency.getShortCode().equals("BDT")) {
                if (StringUtils.isNotBlank(item.getFcRecRoutingNo())) {
                    fcRecBranch = branchService.getBranchByRoutingNumber(item.getFcRecRoutingNo());
                    if (fcRecBranch == null) {
                        error = true;
                        errorNote = errorNote + ", Beneficiary Receiving Branch Is Not Valid ";
                        response.setError(true);
                        response.setMessage(errorNote);
                    } else {
                        fundTransferTxn.setFcRecBranchId(fcRecBranch.getId());
                        fundTransferTxn.setFcRecRoutingNo(item.getFcRecRoutingNo());
                    }
                }

                if (StringUtils.isNotBlank(item.getBillNumber())) {
                    if (valid(NARRATION_PATTERN, item.getBillNumber())) {
                        fundTransferTxn.setBillNumber(validateText(item.getBillNumber(), 35));
                    } else {
                        error = true;
                        errorNote = errorNote + ", Bill Number is not valid." + item.getBillNumber();
                        response.setError(error);
                        response.setMessage(errorNote);
                    }
                }
                if (StringUtils.isNotBlank(item.getLcNumber())) {
                    if (valid(NARRATION_PATTERN, item.getLcNumber())) {
                        fundTransferTxn.setLcNumber(validateText(item.getLcNumber(), 35));
                    } else {
                        error = true;
                        errorNote = errorNote + ", LC Number is not valid." + item.getLcNumber();
                        response.setError(error);
                        response.setMessage(errorNote);
                    }
                }
                if (StringUtils.isNotBlank(item.getPartyName())) {
                    if (valid(NARRATION_PATTERN, item.getPartyName())) {
                        fundTransferTxn.setPartyName(validateText(item.getPartyName(), 35));
                    } else {
                        error = true;
                        errorNote = errorNote + ", Party Name is not valid." + item.getPartyName();
                        response.setError(error);
                        response.setMessage(errorNote);
                    }
                }
            }

            fundTransferTxn.setBatchTxn(true);
            fundTransferTxn.setAmount(item.getAmount());

            if (valid(NARRATION_PATTERN, item.getNarration()) && StringUtils.isNotBlank(item.getNarration())) {
                fundTransferTxn.setNarration(validateText(item.getNarration(), 35));
            } else {
                error = true;
                errorNote = errorNote + ", Narration is not valid." + item.getNarration();
                response.setError(error);
                response.setMessage(errorNote);
            }
            if (error) {
                errorTxn.setIndexNum(++errorIndexNum);
                errorTxn.setBenBankBic(item.getBenBankBic());
                errorTxn.setBenBankName(StringUtils.isNotBlank(beneficiaryBank.getName()) ? beneficiaryBank.getName() : "Not Found");
                errorTxn.setBenBranchRoutingNo(item.getBenBranchRoutingNo());
                errorTxn.setBenBranchName(StringUtils.isNotBlank(beneficiaryBranch.getName()) ? beneficiaryBranch.getName() : "Not Found");
                errorTxn.setAmount(item.getAmount());
                errorTxn.setNarration(item.getNarration());
                errorTxn.setErrorMessage(errorNote);
                errorTxnList.add(errorTxn);
            } else {
                fundTransferTxn.setIndexNum(++indexNum);
                fundTransferTxns.add(fundTransferTxn);
            }
        }
        if (!errorTxnList.isEmpty()) {
            response.setErrorList(errorTxnList);
        }
        if (!fundTransferTxns.isEmpty()) {
            response.setFundTransferTxnList(fundTransferTxns);
        }
        return response;
    }

    public Boolean validateRtgsBalance(String transactionTypeCode, Long currencyId, BigDecimal amount) {

        if (transactionTypeCode.equals(TransactionTypeCodeEnum.CUSTOMS_OPERATIONS.getCode()) || transactionTypeCode.equals(TransactionTypeCodeEnum.EXCISE_AND_VAT.getCode()) || transactionTypeCode.equals(TransactionTypeCodeEnum.GOVERNMENT_PAYMENTS.getCode())) {

            log.info("UI Outward Balance and Transaction RTGS Balance Validation successful!");

            return true;
        } else {

            CurrencyResponse response = currencyService.getById(currencyId);

            if (response.getB2bMinAmount().doubleValue() <= amount.doubleValue()) {
                log.info("UI Outward Balance and Transaction RTGS Balance Validation successful!");
                return true;
            }
        }
        return false;
    }

    private boolean valid(String regex, String value) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }

}
