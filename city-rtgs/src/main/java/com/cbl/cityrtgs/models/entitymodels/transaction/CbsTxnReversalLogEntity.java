package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.ReturnReversalType;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = EntityConstant.CBS_TXN_REVERSAL_LOG)
@Getter
@Setter
@Accessors(chain = true)
@AllArgsConstructor
public class CbsTxnReversalLogEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    private boolean pending;
    private int reversalAttempted;
    private String requestId;
    private String referenceNumber;
    private Date transactionDate;
    private String responseReferenceNumber;
    private Date lastExecutionTime;
    private Date creationTime;
    @Enumerated(EnumType.STRING)
    private RoutingType routingType;
    @Enumerated(EnumType.STRING)
    private FundTransferType fundTransferType;
    private String responseErrorCode;
    private String responseErrorMesssage;
    private String orgEntryUser;
    private BigDecimal amount;
    private String currencyCode;
    private String narration;
    private String fromAccountNumber;
    private String toAccountNumber;
    private String branchRoutingNumber;
    @Enumerated(EnumType.STRING)
    private ReturnReversalType returnReversalType;
    private String returnCode;
    private String returnDescription;
    private String reversalCode;
    private String reversalDescription;
    private BigDecimal charge;
    private BigDecimal vat;
    private Long departmentAccountId;
    private String cbsName;

    public CbsTxnReversalLogEntity() {
        this.charge = BigDecimal.ZERO;
        this.vat = BigDecimal.ZERO;
    }
}
