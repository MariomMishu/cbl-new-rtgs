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
import java.util.List;

@Entity
@Table(name = EntityConstant.RTGS_ACC_STATEMENT)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SettlementAccountStatementEntity{

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "MESSAGEID")
    private String messageID;

    @Column(name = "STATEMENTID")
    private String statementID;

    @Column(name = "ACCOUNTNUMBER")
    private String accountNumber;

    @Column(name = "PAGENUMBER")
    private String pageNumber;

    @Column(name = "LASTPAGE")
    private boolean lastPage;

    @Column(name = "ELECTRONICSEQNUMBER")
    private int electronicSeqNumber;

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

    @Column(name = "PREVIOUSLYCLOSEDBOOKEDBALANCE")
    private BigDecimal previouslyClosedBookedBalance;

    @Column(name = "CURRENCYCODE")
    private String currancyCode;

    @Column(name = "ACCOUNTOWNER")
    private String accountOwner;

    @Column(name = "RECONCILED")
    @Access(AccessType.PROPERTY)
    private boolean reconciled;

    @OneToMany(mappedBy = "statement")
    private List<SettlementAccountStatementDetailEntity> details;

    @Column(name = "DEBITCREDITINDICATOR")
    private String debitCreditIndicator;

}
