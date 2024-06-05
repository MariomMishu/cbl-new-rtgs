package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;

@Entity
@Table(name = EntityConstant.TRANSACTIONTYPECODE)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransactionTypeCodeEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "MESSAGETYPE")
    private String messageType;

    @Column(name = "BALANCEVALIDATION")
    private Boolean balanceValidation;

    @Column(name = "BATCHTRANSACTION")
    private Boolean batchTransaction;

    @Column(name = "CHARGEAPPLICABLE")
    private Boolean chargeApplicable;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "STATUS")
    private Boolean status;

    @Column(name = "APIACTIVESTATUS")
    private Boolean apiActivitiesStatus;

    @Column(name = "EXTRAENDTIME")
    private Boolean extraEndTime;

    @Column(name = "CHANNEL")
    private String deliveryChannel;

    @Column(name = "CLOSETIME")
    private int closeTime;

    @Column(name = "SITRANSACTION")
    private Boolean siTransaction;

}
