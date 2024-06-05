package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = EntityConstant.CBS_TXN_LOG)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CbsTxnLogEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CBSREFERENCENUMBER")
    private String cbsReferenceNumber;

    @Column(name = "RTGSREFERENCENUMBER")
    private String rtgsReferenceNumber;

    @Column(name = "ACCOUNT_NO")
    private String accountNo;

    @Column(name = "TXNDATE")
    private LocalDateTime txnDate;

    @Column(name = "TXNTIME")
    private LocalDateTime txnTime;

    @Column(name = "CBSRESPONSECODE")
    private String cbsResponseCode;

    @Column(name = "CBSRESPONSEMSG")
    private String cbsResponseMessage;
}
