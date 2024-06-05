package com.cbl.cityrtgs.models.entitymodels.si;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = EntityConstant.SI_UPCOMING_ITEM)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode
@ToString
public class SiUpcomingItem {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "SI_CONFIGURATION_ID")
    private SiConfiguration siConfiguration;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CUSTOMER_FUND_TRANSFER_ID", nullable = true)
    private CustomerFndTransferEntity customerFundTransfer;

//    @Column(name = "CUSTOMER_FUND_TRANSFER_ID")
//    private Long customerFundTransferId;
//
    @Column(name = "NAME")
    private String name;

    @Column(name = "PAYER_ACCOUNT_ID")
    private String payerAccountId;

    @Column(name = "PAYER_NAME")
    private String payerName;

    @Column(name = "PAYER_ACCOUNT_NO")
    private String payerAccountNo;

    @Column(name = "PAYER_ACCOUNT_TYPE")
    private String payerAccountType;

    @Column(name = "PAYER_STATUS")
    private Boolean payerStatus;

    @Column(name = "TRANSACTION_TYPE_CODE")
    private String transactionTypeCode;

    @Column(name = "BENEFICIARY_NAME")
    private String beneficiaryName;

    @Column(name = "BENEFICIARY_ACCOUNT_NO")
    private String beneficiaryAccountNo;

    @Column(name = "BENEFICIARY_ACCOUNT_TYPE")
    private String beneficiaryAccountType;

    @Column(name = "BENEFICIARY_BANK_ID")
    private Long beneficiaryBankId;

    @Column(name = "BENEFICIARY_BRANCH_ID")
    private Long beneficiaryBranchId;

    @Column(name = "BENEFICIARY_BRANCH_ROUTING_NO")
    private String beneficiaryBranchRoutingNo;

    @Column(name = "BENEFICIARY_BANK_BIC")
    private String beneficiaryBankBic;

    @Column(name = "NARRATION")
    private String narration;

    @Column(name = "ACCOUNT_BALANCE")
    private BigDecimal accountBalance;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "LC_NO")
    private String lcNo;

    @Column(name = "EXECUTION_DATE")
    private LocalDate executionDate;

    @Column(name = "DEFERRED_DATE")
    private LocalDate deferredDate;

    @Column(name = "IS_ACTIVE")
    private Boolean isActive;

    @Column(name = "IS_FIRED")
    private Boolean isFired;

    @Column(name = "EXECUTION_STATE")
    private String executionState;

    @OneToOne
    @JoinColumn(name = "APPROVER_ID")
    private UserInfoEntity approver;

    @Column(name = "APPROVED_TIME")
    private LocalDate approvedTime;

    @OneToOne
    @JoinColumn(name = "REJECTER_ID")
    private UserInfoEntity rejecter;

    @Column(name = "REJECTION_TIME")
    private LocalDate rejectionTime;

    @Column(name = "REJECT_REASON")
    private String rejectReason;

    @OneToOne
    @JoinColumn(name = "CANCELLER_ID")
    private UserInfoEntity canceller;

    @Column(name = "CANCEL_TIME")
    private LocalDate cancelTime;
}
