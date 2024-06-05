package com.cbl.cityrtgs.models.entitymodels.transaction.c2c;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = EntityConstant.CUSTOMERFNDTRANSFERTXN, uniqueConstraints = @UniqueConstraint(columnNames = {"REFERENCENUMBER", "SENTMSGID"}))
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerFndTransferEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

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
    private String transactionStatus;

    @Column(name = "VOUCHERNUMBER")
    private String voucherNumber;

    @Column(name = "CURRENCY_ID")
    private Long currencyId;

    @Column(name = "TRANSACTIONS")
    private Long transactions;

    @Column(name = "ERRORMSG")
    private String errorMsg;

    @Column(name = "STATUS")
    private Long status;

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
    private String verificationStatus;

    @Column(name = "PAYER_ACC_NO", length = 50)
    private String payerAccNo;

    @Column(name = "PAYER_CBS_ACC_NO", length = 50)
    private String payerCbsAccNo;

    @Column(name = "PAYER_NAME")
    private String payerName;

    @Column(name = "PAYER_ADDRESS")
    private String payerAddress;

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

    @Column(name = "BEN_ADDRESS")
    private String benAddress;

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

    @Column(name = "RMT_CUST_OFFICE_CODE", length = 3)
    private String rmtCustOfficeCode;

    @Column(name = "RMT_REG_YEAR", length = 4)
    private int rmtRegYear;

    @Column(name = "RMT_REG_NUM", length = 12)
    private String rmtRegNum;

    @Column(name = "RMT_DECLARANT_CODE", length = 18)
    private String rmtDeclareCode;

    @Column(name = "RMT_CUS_CELL_NO", length = 11)
    private String rmtCusCellNo;

    @Column(name = "CHARGE")
    private BigDecimal charge = BigDecimal.ZERO;

    @Column(name = "VAT")
    private BigDecimal vat = BigDecimal.ZERO;

    @Column(name = "BATCHTXN")
    private boolean batchTxn;

    @Column(name = "ENDTOENDID")
    private String endToEndId;

    @Column(name = "INSTRID")
    private String instrId;

    @Column(name = "DELIVERYCHANNEL")
    private String deliveryChannel;

    @Column(name = "TXNGLACCOUNT")
    private String txnGlAccount;

    @Column(name = "BIN_CODE")
    private String binCode;

    @Column(name = "COMM_ECONOMIC_CODE", length = 13)
    private String commissionerateEconomicCode;

    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    @Column(name = "DEPARTMENTACCOUNT_ID")
    private Long departmentAccountId;

    @Column(name = "BATCHTXNCHARGEWAIVED")
    private boolean batchTxnChargeWaived;

    @Column(name = "CHARGEGL", length = 30)
    private String chargeGl;

    @Column(name = "VATGL", length = 30)
    private String vatGl;

    @Column(name = "CBSNAME")
    private String cbsName;

    @Column(name = "FCORGACCOUNTTYPE")
    private String fcOrgAccountType;

    @Column(name = "FCRECACCOUNTTYPE")
    private String fcRecAccountType;

    @Column(name = "LCNUMBER")
    private String lcNumber;

    @Column(name = "BILLNUMBER")
    private String billNumber;

    @Column(name = "FC_REC_BRANCH_ID")
    private Long fcRecBranchId;

    @Column(name = "IS_IB_TXN")
    private boolean isIbTxn = false;

    @Column(name = "ABABILREFERENCENUMBER")
    private String ababilReferenceNumber;

}
