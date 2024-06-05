package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = EntityConstant.RTGS_ACC_REPORT)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class AccountReportEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "MESSAGEID")
    private String messageID;

    @Column(name = "REPORTID")
    private String reportID;

    @Column(name = "ACCOUNTNUMBER")
    private String accountNumber;

    @Column(name = "ELECTRONICSEQNUMBER")
    private BigDecimal electronicSeqNumber;

    @Column(name = "CREATEDATE")
    @Temporal(TemporalType.DATE)
    private Date createDate;

    @Column(name = "CREATETIME")
    @Temporal(TemporalType.TIME)
    private Date createTime;

    @Column(name = "CLOSINGAVAILABLEBALANCE")
    private BigDecimal closingAvailableBalance;

    @Column(name = "CLOSINGBOOKEDBALANCE")
    private BigDecimal closingBookedBalance;

    @Column(name = "CURRENCYCODE")
    private String currancyCode;

    @Column(name = "ACCOUNTOWNER")
    private String accountOwner;

    @Column(name = "FORWARDAVAILABLEBALANCE")
    private BigDecimal forwardAvailableBalance;

    @Column(name = "OPENINGBOOKEDBALANCE")
    private BigDecimal openingBookedBalance;

    @Column(name = "TOTALCREDITENTRIES")
    private String totalCreditEntries;

    @Column(name = "CREDITSUM")
    private BigDecimal creditSum;

    @Column(name = "TOTALDEBITENTRIES")
    private String totalDebitEntries;

    @Column(name = "DEBITSUM")
    private BigDecimal debitSum;

    @Column(name = "DEBITCREDITINDICATOR")
    private String debitCreditIndicator;
}
