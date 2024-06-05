package com.cbl.cityrtgs.models.dto.si;

import com.cbl.cityrtgs.models.entitymodels.si.SiConfiguration;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import lombok.AllArgsConstructor;
import lombok.Getter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@Getter
public class SiRequest {

    private String currencyShortCode;
    private Long frequencyId;
    private Long amountTypeId;
    private String startDate;
    private String expiryDate;
    private Integer fireDay;
    private Integer monthlyFireDay;
    private String transactionTypeCode;
    private String name;
    private String payerName;
    private String payerAccountId;
    private String payerAccountNo;
    private String payerAccountType;
    private Boolean payerStatus;
    private String beneficiaryName;
    private String beneficiaryAccountNo;
    private String beneficiaryAccountType;
    private Long beneficiaryBankId;
    private Long beneficiaryBranchId;
    private String beneficiaryBranchRoutingNo;
    private String beneficiaryBankBic;
    private String narration;
    private BigDecimal amount;
    private BigDecimal accountBalance;
    private String lcNo;
    private Boolean active;

    public static SiConfiguration toSiConfigurationModel(SiRequest request){

        return SiConfiguration.builder()
                .startDate(LocalDate.parse(request.getStartDate()))
                .expiryDate(LocalDate.parse(request.getExpiryDate()))
                .createdOn(new Date())
                .fireDay(request.getFireDay())
                .monthlyFireDay(request.getMonthlyFireDay())
                .build();
    }

    public static SiUpcomingItem toModel(SiRequest request){

        return SiUpcomingItem.builder()
                .transactionTypeCode(request.getTransactionTypeCode())
                .name(request.getName())
                .payerAccountId(request.getPayerAccountId())
                .payerName(request.getPayerName())
                .payerAccountNo(request.getPayerAccountNo())
                .payerStatus(request.getPayerStatus())
                .payerAccountType(request.getPayerAccountType())
                .beneficiaryName(request.getBeneficiaryName())
                .beneficiaryAccountNo(request.getBeneficiaryAccountNo())
                .beneficiaryBankId(request.getBeneficiaryBankId())
                .beneficiaryBranchId(request.getBeneficiaryBranchId())
                .beneficiaryBranchRoutingNo(request.getBeneficiaryBranchRoutingNo())
                .beneficiaryBankBic(request.getBeneficiaryBankBic())
                .beneficiaryAccountType(request.getBeneficiaryAccountType())
                .narration(request.getNarration())
                .accountBalance(request.getAccountBalance())
                .amount(request.getAmountTypeId() != 2 ? request.getAmount() : BigDecimal.ZERO)
                .lcNo(request.getLcNo())
                .isActive(request.getActive())
                .isFired(false)
                .executionState("SUBMITTED")
                .build();
    }
}
