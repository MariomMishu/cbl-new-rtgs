package com.cbl.cityrtgs.models.entitymodels.transaction.notification;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = EntityConstant.STATSTXNS)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class StatusTransactionEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String orgnlInstId;
    private String orgnlEndToEndId;
    private String orgnlTxId;
    private String txnSts;

    @ManyToOne
    @JoinColumn(name = "stsNotification_id")
    private ReportStatusNotificationEntity stsNotification;
}
