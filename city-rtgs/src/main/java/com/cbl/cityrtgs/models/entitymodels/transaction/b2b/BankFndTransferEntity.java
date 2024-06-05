package com.cbl.cityrtgs.models.entitymodels.transaction.b2b;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.TransactionVerificationStatus;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatus;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = EntityConstant.BANKFNDTRANSFERTXN, uniqueConstraints = @UniqueConstraint(columnNames = {"REFERENCENUMBER", "SENTMSGID"}))
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class BankFndTransferEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount = BigDecimal.ZERO;

    @Column(name = "ERRORNOTE")
    private String errorNote;

    @Column(name = "NARRATION")
    private String narration;

    @Column(name = "PARENTBATCHNUMBER")
    private String parentBatchNumber;

    @Column(name = "PRIORITYCODE")
    private String priorityCode;

    @Column(name = "REFERENCENUMBER")
    private String referenceNumber;

    @Column(name = "ROUTINGTYPE")
    @Enumerated(EnumType.STRING)
    private RoutingType routingType;

    @Column(name = "SETTLEMENTDATE")
    private Date settlementDate;

    @Column(name = "TRANSACTIONDATE")
    private Date transactionDate;

    @Column(name = "TRANSACTIONSTATUS")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "VOUCHERNUMBER")
    private String voucherNumber;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;

    @Column(name = "BENEFICIARY_ID")
    private Long beneficiaryId;

    @Column(name = "PAYEEINFORMATION_ID")
    private Long payeeInformationId;

    @Column(name = "PRODUCT_ID")
    private Long productId;

    @Column(name = "TRANSACTIONS")
    private Long transactions;

    @Column(name = "ERRORMSG")
    private String errorMsg;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "FAILEDREASON")
    private String failedReason;

    @Column(name = "rejectReason")
    private String rejectReason;

    @Column(name = "REJECTEDUSER")
    private String rejectedUser;

    @Column(name = "REJECTIONDATETIME")
    private Date rejectionDateTime;

    @Column(name = "RETURNCODE")
    private String returnCode;

    @Column(name = "RETURNDATETIME")
    private Date returnDateTime;

    @Column(name = "RETURNREASON")
    private String returnReason;

    @Column(name = "RETURNUSER")
    private String returnUser;

    @Column(name = "VERIFICATIONSTATUS", length = 10)
    @Enumerated(EnumType.STRING)
    private TransactionVerificationStatus verificationStatus;

    @Column(name = "CDTRACCOUNT")
    private String cdtrAccount;

    @Column(name = "DBTRACCOUNT")
    private String dbtrAccount;

    @Column(name = "DEALRETURNED")
    private Boolean dealReturned;

    @Column(name = "ORIGINALTXNREFERENCE")
    private String originalTransactionReference;

    @Column(name = "PAYER_ACC_NO", length = 50)
    private String payerAccNo;

    @Column(name = "PAYER_CBS_ACC_NO", length = 50)
    private String payerCbsAccNo;

    @Column(name = "PAYER_NAME")
    private String payerName;

    @Column(name = "PAYER_INS_NO", length = 20)
    private String payerInsNo;

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
    private String BenNameOrg;

    @Column(name = "BEN_ACC_NO_ORG", length = 50)
    private String benAccNoOrg;

    @Column(name = "SENTMSGID")
    private String sentMsgId;

    @Column(name = "SOURCEREFERENCE")
    private Long sourceReference;

    @Column(name = "SOURCETYPE", length = 10)
    private Long sourceType;

    @Column(name = "CHARGE")
    private BigDecimal charge = BigDecimal.ZERO;

    @Column(name = "CHARGEGL", length = 30)
    private String chargeGl;

    @Column(name = "VAT")
    private BigDecimal vat = BigDecimal.ZERO;

    @Column(name = "VATGL", length = 30)
    private String vatGl;

    @Column(name = "BATCHTXN")
    private Boolean batchTxn;

    @Column(name = "ENDTOENDID")
    private String endToEndId;

    @Column(name = "INSTRID")
    private String instrId;

    @Column(name = "PAYER_BOOTH_ID")
    private String payerBoothId;

    @Column(name = "DELIVERYCHANNEL")
    private String deliveryChannel;

    @Column(name = "TXNGLACCOUNT")
    private String transactionGlAccount;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @Column(name = "DEPARTMENTACCOUNT_ID")
    private Long departmentAccountId;

    @Column(name = "CBSNAME")
    private String cbsName;

    @Column(name = "FCORGACCOUNTTYPE")
    private String fcOrgAccountType;

    @Column(name = "FCRECACCOUNTTYPE")
    private String fcRecAccountType;

    @Column(name = "LCNUMBER")
    private String lcNumber;

    @Column(name = "BATCHTXNCHARGEWAIVED")
    private Boolean batchTransactionChargeWaived;

    @Column(name = "BILLNUMBER")
    private String billNumber;

    @Column(name = "FC_REC_BRANCH_ID")
    private Long fcRecBranchId;
}
