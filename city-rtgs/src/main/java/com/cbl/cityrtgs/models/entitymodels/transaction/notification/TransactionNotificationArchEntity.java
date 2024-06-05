package com.cbl.cityrtgs.models.entitymodels.transaction.notification;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = EntityConstant.TRANSACTIONNOTIFICATION_ARCH)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionNotificationArchEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ACCOUNTID")
    private String accountId;

    @Column(name = "CREDITDEBITIDENTIFICATION", length = 10)
    private Long creditDebitIdentification;

    @Column(name = "ENTRYREFNUMBER")
    private String entryRefNumber;

    @Column(name = "ENTRYSTATUS")
    private String entryStatus;

    @Column(name = "TRANSACTIONAMOUNT")
    private BigDecimal transactionAmount;

    @Column(name = "TRANSACTIONDATE")
    private LocalDateTime transactionDate;

    @Column(name = "TRANSACTIONREFNUMBER")
    private String transactionRefNumber;

    @Column(name = "BENEFICIARYINFORMATION_ID")
    private Long beneficiaryInformationId;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;

    @Column(name = "PAYEEINFORMATION_ID")
    private Long payeeInformationId;

    @Column(name = "PAYER_ACC_NO", length = 50)
    private String payerAccNo;

    @Column(name = "PAYER_CBS_ACC_NO", length = 50)
    private Long payerCbsAccNo;

    @Column(name = "PAYER_NAME")
    private String payerName;

    @Column(name = "PAYER_INS_NO", length = 20)
    private Boolean payerInsNo;

    @Column(name = "PAYER_BRANCH_ID")
    private Long payerBranchId;

    @Column(name = "PAYER_BANK_ID")
    private Long payerBankId;

    @Column(name = "BEN_BANK_ID")
    private Long benBankId;

    @Column(name = "BEN_BRANCH_ID")
    private Long benBranchId;

    @Column(name = "BEN_ACC_NO", length = 50)
    private String benAccNo;

    @Column(name = "BEN_NAME")
    private String benName;

    @Column(name = "BEN_NAME_ORG")
    private String benNameOrg;

    @Column(name = "BEN_ACC_NO_ORG", length = 50)
    private String benAccNoOrg;

    @Column(name = "PAYER_BOOTH_ID")
    private String payerBoothId;

    @Column(name = "FC_REC_BRANCH_ID")
    private Long fcRecBranchId;

}
