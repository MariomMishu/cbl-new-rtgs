package com.cbl.cityrtgs.models.dto.si;

import com.cbl.cityrtgs.models.entitymodels.si.SiConfiguration;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import com.cbl.cityrtgs.services.si.utility.SiUtility;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class SiEditRequest {

    private Long id;
    private Long customerFundTransferId;
    private String currencyShortCode;
    private Long frequencyId;
    private Long amountTypeId;
    private String startDate;
    private String expiryDate;
    private Integer fireDay;
    private Integer monthlyFireDay;
    private String name;
    private String payerName;
    private String payerAccountNo;
    private String payerAccountId;
    private String payerAccountType;
    private Boolean payerStatus;
    private String beneficiaryName;
    private String beneficiaryAccountNo;
    private String beneficiaryAccountType;
    private String beneficiaryBranchRoutingNo;
    private String beneficiaryBankBic;
    private Long beneficiaryBankId;
    private Long beneficiaryBranchId;
    private String narration;
    private String lcNo;
    private String transactionTypeCode;
    private BigDecimal amount;
    private BigDecimal accountBalance;
    private Boolean active;

    public static SiConfiguration toSiConfigurationModel(SiConfiguration siConfiguration, SiEditRequest request){

        siConfiguration.setStartDate(LocalDate.parse(request.getStartDate()));
        siConfiguration.setExpiryDate(LocalDate.parse(request.getExpiryDate()));
        siConfiguration.setFireDay(request.getFireDay());
        siConfiguration.setMonthlyFireDay(request.getMonthlyFireDay());
        siConfiguration.setUpdatedOn(SiUtility.getCurrentTime());

        return siConfiguration;
    }

    public static SiUpcomingItem toSiUpcomingItemModel(SiUpcomingItem oldSi, SiEditRequest siEditRequest){

        oldSi.setTransactionTypeCode(siEditRequest.getTransactionTypeCode());
        oldSi.setName(siEditRequest.getName());
        oldSi.setPayerAccountId(siEditRequest.getPayerAccountId());
        oldSi.setPayerName(siEditRequest.getPayerName());
        oldSi.setPayerAccountNo(siEditRequest.getPayerAccountNo());
        oldSi.setPayerStatus(siEditRequest.getPayerStatus());
        oldSi.setBeneficiaryName(siEditRequest.getBeneficiaryName());
        oldSi.setBeneficiaryAccountNo(siEditRequest.getBeneficiaryAccountNo());
        oldSi.setBeneficiaryBankId(siEditRequest.getBeneficiaryBankId());
        oldSi.setBeneficiaryBranchId(siEditRequest.getBeneficiaryBranchId());
        oldSi.setBeneficiaryBranchRoutingNo(siEditRequest.getBeneficiaryBranchRoutingNo());
        oldSi.setBeneficiaryBankBic(siEditRequest.getBeneficiaryBankBic());
        oldSi.setBeneficiaryAccountType(siEditRequest.getBeneficiaryAccountType());
        oldSi.setNarration(siEditRequest.getNarration());
        oldSi.setAccountBalance(siEditRequest.getAccountBalance());
        oldSi.setAmount(siEditRequest.getAmount());
        oldSi.setLcNo(siEditRequest.getLcNo());
        oldSi.setIsActive(siEditRequest.getActive());
        oldSi.setIsFired(false);
        oldSi.setExecutionState("SUBMITTED");

        return oldSi;
    }
}
