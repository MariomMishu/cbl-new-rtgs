package com.cbl.cityrtgs.models.dto.si;

import com.cbl.cityrtgs.models.entitymodels.si.SiConfiguration;
import com.cbl.cityrtgs.models.entitymodels.si.SiUpcomingItem;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiResponse {

    private Long id;
    private Long customerFundTransferId;
    private String amountType;
    private String currencyShortCode;
    private String name;
    private String frequency;
    private Integer fireDay;
    private Integer monthlyFireDay;
    private String beneficiaryAccountNo;
    private BigDecimal accountBalance;
    private BigDecimal amount;
    private LocalDate startDate;
    private LocalDate expiryDate;
    private Boolean isActive;
    private Boolean isFired;
    private Date createdOn;
    private Date updatedOn;
    private Long createdById;
    private String createdBy;
    private String payerName;
    private String payerAccountId;
    private String payerAccountNo;
    private String payerAccountType;
    private Boolean payerStatus;
    private String beneficiaryName;
    private String beneficiaryAccountType;
    private Long beneficiaryBankId;
    private Long beneficiaryBranchId;
    private String beneficiaryBank;
    private String beneficiaryBranch;
    private String beneficiaryBranchRoutingNo;
    private String beneficiaryBankBic;
    private String narration;
    private String lcNo;
    private String executionState;
    private Long approverId;
    private String approver;
    private LocalDate approvedTime;
    private Long rejecterId;
    private String rejecter;
    private LocalDate rejectionTime;
    private String rejectReason;
    private Long cancellerId;
    private String canceller;
    private LocalDate cancelTime;

    public static SiResponse toDto(SiUpcomingItem siUpcomingItem, String beneficiaryBank, String beneficiaryBranch){

        SiConfiguration siConfiguration = siUpcomingItem.getSiConfiguration();
        
        return SiResponse.builder()
                .id(siUpcomingItem.getId())
                .customerFundTransferId(siUpcomingItem.getCustomerFundTransfer() != null ? siUpcomingItem.getCustomerFundTransfer().getId() : null)
                .amountType(siConfiguration.getAmountType().getType())
                .currencyShortCode(siConfiguration.getCurrency().getShortCode())
                .frequency(siConfiguration.getSiFrequency().getFrequency())
                .fireDay(siConfiguration.getFireDay() != null ? siConfiguration.getFireDay() :  null)
                .monthlyFireDay(siConfiguration.getMonthlyFireDay() != null ? siConfiguration.getMonthlyFireDay() : null)
                .name(siUpcomingItem.getName())
                .payerName(siUpcomingItem.getPayerName())
                .payerAccountNo(siUpcomingItem.getPayerAccountNo())
                .payerAccountId(siUpcomingItem.getPayerName())
                .payerAccountType(siUpcomingItem.getPayerAccountType())
                .payerStatus(siUpcomingItem.getPayerStatus())
                .beneficiaryName(siUpcomingItem.getBeneficiaryName())
                .beneficiaryAccountNo(siUpcomingItem.getBeneficiaryAccountNo())
                .beneficiaryAccountType(siUpcomingItem.getBeneficiaryAccountType())
                .beneficiaryBankId(siUpcomingItem.getBeneficiaryBankId())
                .beneficiaryBranchId(siUpcomingItem.getBeneficiaryBranchId())
                .beneficiaryBank(beneficiaryBank)
                .beneficiaryBranch(beneficiaryBranch)
                .beneficiaryBankBic(siUpcomingItem.getBeneficiaryBankBic())
                .beneficiaryBranchRoutingNo(siUpcomingItem.getBeneficiaryBranchRoutingNo())
                .narration(siUpcomingItem.getNarration() != null ? siUpcomingItem.getNarration() : null)
                .accountBalance(siUpcomingItem.getAccountBalance())
                .amount(siUpcomingItem.getAmount())
                .lcNo(siUpcomingItem.getLcNo())
                .startDate(siConfiguration.getStartDate())
                .expiryDate(siConfiguration.getExpiryDate())
                .isActive(siUpcomingItem.getIsActive() != null ? siUpcomingItem.getIsActive() : null)
                .isFired(siUpcomingItem.getIsFired())
                .createdOn(siConfiguration.getCreatedOn())
                .updatedOn(siConfiguration.getUpdatedOn())
                .createdById(siConfiguration.getCreatedBy() != null ? siConfiguration.getCreatedBy().getId() : null)
                .createdBy(siConfiguration.getCreatedBy() !=null ? siConfiguration.getCreatedBy().getUsername() : null)
                .executionState(siUpcomingItem.getExecutionState())
                .approverId(siUpcomingItem.getApprover() != null ? siUpcomingItem.getApprover().getId() : null)
                .approver(siUpcomingItem.getApprover() != null ? siUpcomingItem.getApprover().getUsername() : null)
                .approvedTime(siUpcomingItem.getApprover() != null ? siUpcomingItem.getApprovedTime() : null)
                .rejecterId(siUpcomingItem.getRejecter() != null ? siUpcomingItem.getRejecter().getId() : null)
                .rejecter(siUpcomingItem.getRejecter() != null ? siUpcomingItem.getRejecter().getUsername() : null)
                .rejectReason(siUpcomingItem.getRejectReason() != null ? siUpcomingItem.getRejectReason() : null)
                .rejectionTime(siUpcomingItem.getRejectionTime() != null ? siUpcomingItem.getRejectionTime() : null)
                .cancellerId(siUpcomingItem.getCanceller() != null ? siUpcomingItem.getCanceller().getId() : null)
                .canceller(siUpcomingItem.getCanceller() != null ? siUpcomingItem.getCanceller().getUsername() : null)
                .cancelTime(siUpcomingItem.getCancelTime() != null? siUpcomingItem.getCancelTime() : null)
                .build();
    }
}
