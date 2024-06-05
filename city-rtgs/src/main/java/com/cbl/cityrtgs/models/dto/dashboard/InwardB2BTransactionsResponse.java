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
public class InwardB2BTransactionsResponse {

    private String payerBank;
    private String payerBranch;
    private BigDecimal amount;
    private String currency;
    private String status;
    private Date time;
    private String narration;
    private String billNumber;
    private String lcNumber;
    private String partyName;
    private String beneficiaryBranch;
    private String referenceNo;

    public static InwardB2BTransactionsResponse toDTO(BankFndTransferEntity entity,
                                                String payerBank,
                                                String payerBranch,
                                                String currency){

        return InwardB2BTransactionsResponse.builder()
                .payerBank(payerBank)
                .payerBranch(payerBranch)
                .amount(entity.getAmount())
                .currency(currency)
                .status(entity.getTransactionStatus().name())
                .time(entity.getCreatedAt())
                .narration(entity.getNarration())
                .billNumber(entity.getBillNumber())
                .lcNumber(entity.getLcNumber())
                .partyName(currency.equalsIgnoreCase("USD") ? entity.getBenName() : null)
                .referenceNo(entity.getReferenceNumber())
                .build();

    }
}
