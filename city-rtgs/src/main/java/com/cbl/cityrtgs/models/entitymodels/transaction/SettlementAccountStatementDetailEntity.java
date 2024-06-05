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
@Table(name = EntityConstant.RTGS_ACC_ST_DTL)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SettlementAccountStatementDetailEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "TRANSACTIONID")
    private String transactionId;

    @Column(name = "TRANSACTIONCODE")
    private String transactionCode;

    @Column(name = "TRANSACTIONREF")
    private String transactionRef;

    @Column(name = "TRANSACTIONSTATUS")
    private String transactionStatus;

    @Column(name = "VALUEDATE")
    @Temporal(TemporalType.DATE)
    private Date valueDate;

    @Column(name = "CREDITAMOUNT")
    private BigDecimal creditAmount;

    @Column(name = "DEBITAMOUNT")
    private BigDecimal debitAmount;

    @Column(name = "BATCH")
    private boolean batch;

    @Column(name = "BATCHMESSAGEID")
    private String batchMessageId;

    @Column(name = "BOOKINGDATE")
    @Temporal(TemporalType.DATE)
    private Date bookingDate;

    @ManyToOne
    @JoinColumn(name = "STATEMENT_ID")
    private SettlementAccountStatementEntity statement;
}
