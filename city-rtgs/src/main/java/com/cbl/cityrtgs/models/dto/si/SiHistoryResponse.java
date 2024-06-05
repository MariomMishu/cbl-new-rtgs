package com.cbl.cityrtgs.models.dto.si;

import com.cbl.cityrtgs.models.entitymodels.si.SiHistory;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiHistoryResponse {

    private Long id;
    private Long departmentId;
    private String frequency;
    private String amountType;
    private String currencyShortCode;
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
    private BigDecimal accountBalance;
    private BigDecimal amount;
    private String lcNo;
    private Boolean transactionStatus;
    private String status;
    private String error;
    private LocalDate dueDate;
    private String beneficiaryBank;
    private String branchName;
    private String message;
    private boolean isFired;

    public static SiHistoryResponse toDto(SiHistory siHistory, String beneficiaryBank, String branchName) {

        return SiHistoryResponse.builder()
                .id(siHistory.getId())
               // .departmentId(siHistory.getCreatedBy().getDept().getId())
                .amountType(siHistory.getSiamountType().getType())
                .currencyShortCode(siHistory.getCurrency().getShortCode())
                .frequency(siHistory.getSiFrequency().getFrequency())
                .name(siHistory.getName())
                .payerName(siHistory.getPayerName())
                .payerAccountNo(siHistory.getPayerAccountNo())
                .payerAccountId(siHistory.getPayerName())
                .payerAccountType(siHistory.getPayerAccountType())
                .payerStatus(siHistory.getPayerStatus())
                .beneficiaryName(siHistory.getBeneficiaryName())
                .beneficiaryAccountNo(siHistory.getBeneficiaryAccountNo())
                .beneficiaryAccountType(siHistory.getBeneficiaryAccountType())
                .beneficiaryBankId(siHistory.getBeneficiaryBankId())
                .beneficiaryBranchId(siHistory.getBeneficiaryBranchId())
                .beneficiaryBankBic(siHistory.getBeneficiaryBankBic())
                .beneficiaryBranchRoutingNo(siHistory.getBeneficiaryBranchRoutingNo())
                .beneficiaryBank(beneficiaryBank)
                .branchName(branchName)
                .accountBalance(siHistory.getAccountBalance())
                .amount(siHistory.getAmount())
                .lcNo(siHistory.getLcNo())
                .status(siHistory.getStatus())
                .dueDate(siHistory.getDueDate())
                .error(siHistory.getStatus().equals("FAILED") ? siHistory.getMessage() : null)
                .message(siHistory.getMessage())
                .narration(siHistory.getNarration())
                .isFired(siHistory.getIsFired() != null ? siHistory.getIsFired() : false)
                .build();
    }
}
