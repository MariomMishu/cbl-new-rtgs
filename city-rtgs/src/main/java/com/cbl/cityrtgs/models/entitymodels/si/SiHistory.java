package com.cbl.cityrtgs.models.entitymodels.si;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = EntityConstant.SI_HISTORY)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SiHistory {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CURRENCY_ID", referencedColumnName = "ID")
    private CurrencyEntity currency;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "FREQUENCY_ID", referencedColumnName = "ID")
    private SiFrequency siFrequency;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "AMOUNT_TYPE_ID", referencedColumnName = "ID")
    private SiAmountType siamountType;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CUSTOMER_FUND_TRANSFER_ID", referencedColumnName = "ID")
    private CustomerFndTransferEntity customerFundTransfer;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "CREATED_BY")
    private UserInfoEntity createdBy;

    @Column(name = "EXECUTION_DATE")
    private LocalDate executionDate;

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

    @Column(name = "TRANSACTION_TYPE_CODE")
    private String transactionTypeCode;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "DUE_DATE")
    private LocalDate dueDate;

    @Column(name = "RESPONSE_CODE")
    private String responseCode;

    @Column(name = "MESSAGE")
    private String message;

    @Column(name = "IS_FIRED")
    private Boolean isFired;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CREATE_DATE")
    private Date createDate = new Date();
}
