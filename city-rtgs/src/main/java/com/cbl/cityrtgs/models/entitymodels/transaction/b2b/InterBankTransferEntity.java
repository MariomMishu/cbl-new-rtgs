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
@Table(name = EntityConstant.INTERBANKFUNDTRANSFER, uniqueConstraints = @UniqueConstraint(columnNames = {"BATCHNUMBER"}))
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class InterBankTransferEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "APPROVALDATETIME")
    private Date approvalDateTime;

    @Column(name = "APPROVER")
    private String approver;

    @Column(name = "BATCHNUMBER", unique = true, nullable = false)
    private String batchNumber;

    @Column(name = "BRANCHID")
    private Long branchId;

    @Column(name = "CREATEDATE")
    private Date createDate;

    @Column(name = "CREATETIME")
    private Date createTime;

    @Column(name = "ENTRYUSER")
    private String entryUser;

    @Column(name = "EVENTID")
    private String eventId;

    @Column(name = "FAILEDREASON")
    private String failedReason;

    @Column(name = "INWARDACTIONSTATUS", length = 10)
    private Long inwardActionStatus;

    @Column(name = "REJECTREASON")
    private String rejectReason;

    @Column(name = "REJECTEDUSER")
    private String rejectedUser;

    @Column(name = "REJECTIONDATETIME")
    private Date rejectionDateTime;

    @Column(name = "RETURNDATETIME")
    private Date returnDateTime;

    @Column(name = "RETURNREASON")
    private String returnReason;

    @Column(name = "RETURNUSER")
    private String returnUser;

    @Column(name = "ROUTINGTYPE")
    @Enumerated(EnumType.STRING)
    private RoutingType routingType;

    @Column(name = "SETTLEMENTDATE")
    private Date settlementDate;

    @Column(name = "TRANSACTIONDATE")
    private Date transactionDate;

    @Column(name = "TYPE", length = 10)
    private String type;

    @Column(name = "VERIFICATIONSTATUS", length = 10)
    private int verificationStatus;

    @Column(name = "TRANSACTIONSTATUS")
    @Enumerated(EnumType.STRING)
    private TransactionStatus transactionStatus;

    @Column(name = "TXNVERIFICATIONSTATUS", length = 10)
    @Enumerated(EnumType.STRING)
    private TransactionVerificationStatus txnVerificationStatus;

    @Column(name = "VERIFIER")
    private String verifier;

    @Column(name = "VERIFYDATETIME")
    private Date verifyDateTime;

    @Column(name = "MSGID")
    private String msgId;

    @Column(name = "RETURNCODE")
    private String returnCode;

    @Column(name = "TXNTYPECODE", length = 10)
    private String txnTypeCode;

    @Column(name = "BANKTOBANKTYPE", length = 20)
    private String bankToBankType;

    @Column(name = "ORIGINALBATCHNUMBER")
    private String originalBatchNumber;

    @Column(name = "RETURNCHARGE", length = 10)
    private BigDecimal returnCharge = BigDecimal.ZERO;
}
