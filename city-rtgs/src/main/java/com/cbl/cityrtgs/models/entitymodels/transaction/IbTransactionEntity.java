package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
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
@Table(name = EntityConstant.IB_TRANSACTIONS)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class IbTransactionEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "BENACCOUNT")
    private String benAccount;

    @Column(name = "BENBRANCHROUTINGNO")
    private String benBranchRoutingNo;

    @Column(name = "BENNAME")
    private String benName;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "ERRORMESSAGE")
    private String errorMessage;

    @Column(name = "FUNDTRANSFERTYPE")
    @Enumerated(EnumType.STRING)
    private FundTransferType fundTransferType;

    @Column(name = "ISERROR")
    private Boolean isError;

    @Column(name = "NARRATION")
    private String narration;

    @Column(name = "PAYERACCOUNT")
    private String payerAccount;

    @Column(name = "PAYERNAME")
    private String payerName;

    @Column(name = "REQUESTREFERENCE")
    private String requestReference;

    @Column(name = "RESPONSEREFERENCE")
    private String responseReference;

    @Column(name = "ROUTINGTYPE")
    @Enumerated(EnumType.STRING)
    private RoutingType routingType;

    @Column(name = "SETTLEMENTDATE")
    private Date settlementDate;

    @Column(name = "TXNBRANCHROUTINGNO")
    private String txnBranchRoutingNo;

    @Column(name = "TRANSACTIONDATE")
    private Date transactionDate;

    @Column(name = "TXNSTATUS")
    private String transactionStatus;

    @Column(name = "VERIFICATIONSTATUS")
    private String verificationStatus;

    @Column(name = "DELIVERYCHANNEL")
    private String deliveryChannel;

    @Column(name = "BINCODE")
    private String binCode;

    @Column(name = "COMMISSIONERATEECONOMICCODE")
    private String commissionerateEconomicCode;

    @Column(name = "TXNTYPECODE")
    private String transactionTypeCode;

    @Column(name = "CUSTOMERMOBILENUMBER")
    private String customerMobileNumber;

    @Column(name = "CUSTOMSOFFICECODE")
    private String customsOfficeCode;

    @Column(name = "DECLARANTCODE")
    private String declarantCode;

    @Column(name = "REGISTRATIONNUMBER")
    private String registrationNumber;

    @Column(name = "REGISTRATIONYEAR")
    private int registrationYear;

}
