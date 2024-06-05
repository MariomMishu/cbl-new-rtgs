package com.cbl.cityrtgs.models.dto.dashboard;

import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OutwardB2BTransactionsResponse {

    private String beneficiaryBank;
    private String beneficiaryBranch;
    private BigDecimal amount;
    private String referenceNumber;
    private String currency;
    private String status;
    private Date time;
    private String narration;
    private String billNumber;
    private String lcNumber;
    private String partyName;
    private String beneficiaryReceiverBranch;

    public static OutwardB2BTransactionsResponse toDTO(BankFndTransferEntity entity, String beneficiaryBank, String beneficiaryBranch,
                                                       String beneficiaryReceiverBranch, String currency){

        return OutwardB2BTransactionsResponse.builder()
                .beneficiaryBank(beneficiaryBank)
                .beneficiaryBranch(beneficiaryBranch)
                .amount(entity.getAmount())
                .referenceNumber(entity.getReferenceNumber())
                .currency(currency)
                .status(entity.getTransactionStatus().name())
                .time(entity.getCreatedAt())
                .narration(entity.getNarration())
                .billNumber(entity.getBillNumber())
                .lcNumber(entity.getLcNumber())
                .partyName(currency.equalsIgnoreCase("USD") ? entity.getBenName() : null)
                .beneficiaryReceiverBranch(beneficiaryReceiverBranch)
                .build();
    }
}
