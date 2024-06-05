package com.cbl.cityrtgs.models.dto.dashboard;

import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import lombok.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class OutwardC2CTransactionsResponse {

    private String fromAccount;
    private String payerBranch;
    private String beneficiaryAccount;
    private String beneficiaryName;
    private String beneficiaryBank;
    private String beneficiaryBranch;
    private BigDecimal amount;
    private BigDecimal vat;
    private BigDecimal charge;
    private String referenceNumber;
    private String currency;
    private String status;
    private String transactionTime;
    private String narration;
    private String lcNumber;
    private String deliveryChannel;

    public static OutwardC2CTransactionsResponse toDTO(CustomerFndTransferEntity entity,
                                                       String payerBranch,
                                                       String beneficiaryBank,
                                                       String beneficiaryBranch,
                                                       String currency){

        return OutwardC2CTransactionsResponse.builder()
                .fromAccount(entity.getPayerAccNo())
                .payerBranch(payerBranch)
                .beneficiaryAccount(entity.getBenAccNo())
                .beneficiaryName(entity.getBenName())
                .beneficiaryBank(beneficiaryBank)
                .beneficiaryBranch(beneficiaryBranch)
                .amount(entity.getAmount())
                .vat(entity.getVat())
                .charge(entity.getCharge())
                .referenceNumber(entity.getReferenceNumber())
                .currency(currency)
                .status(entity.getTransactionStatus() != null ? entity.getTransactionStatus(): "")
                .transactionTime(entity.getTransactionDate() != null ? entity.getTransactionDate().toString() : null)
                .narration(entity.getNarration())
                .lcNumber(entity.getLcNumber())
                .deliveryChannel(entity.getDeliveryChannel())
                .build();
    }
}
