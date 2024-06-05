package com.cbl.cityrtgs.models.dto.dashboard;

import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InwardC2CTransactionsResponse {

    private String payerAccount;
    private String payerBranch;
    private String beneficiaryAccount;
    private String beneficiaryName;
    private String beneficiaryBank;
    private String beneficiaryBranch;
    private BigDecimal amount;
    private BigDecimal charge;
    private BigDecimal vat;
    private String referenceNumber;
    private String currency;
    private String status;
    private Date transactionDate;
    private String narration;
    private String lcNumber;
    private String deliveryChannel;
    private String payerBank;

    public static InwardC2CTransactionsResponse toDTO(CustomerFndTransferEntity entity,
                                                      String beneficiaryBank,
                                                      String beneficiaryBranch,
                                                      String payerBranch,
                                                      String currency,
                                                      String payerBank){

        return InwardC2CTransactionsResponse.builder()
                .payerAccount(entity.getPayerAccNo())
                .payerBranch(payerBranch)
                .beneficiaryAccount(entity.getBenAccNo())
                .beneficiaryName(entity.getBenName())
                .beneficiaryBank(beneficiaryBank)
                .beneficiaryBranch(beneficiaryBranch)
                .amount(entity.getAmount())
                .charge(entity.getCharge())
                .vat(entity.getVat())
                .referenceNumber(entity.getReferenceNumber())
                .currency(currency)
                .status(entity.getTransactionStatus())
                .transactionDate(entity.getTransactionDate())
                .narration(entity.getNarration())
                .lcNumber(entity.getLcNumber())
                .deliveryChannel(entity.getDeliveryChannel())
                .payerBank(payerBank)
                .build();
    }
}
