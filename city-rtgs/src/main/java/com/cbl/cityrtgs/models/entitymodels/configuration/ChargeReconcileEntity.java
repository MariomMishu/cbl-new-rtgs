package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeModule;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.ChargeType;
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
@Table(name = EntityConstant.CHARGERECONCILEACC)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChargeReconcileEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ACCOUNTNO")
    private String accountNo;

    @Column(name = "CHARGEMODULE")
    @Enumerated(EnumType.STRING)
    private ChargeModule chargeModule;

    @Column(name = "CHARGETYPE")
    @Enumerated(EnumType.STRING)
    private ChargeType chargeType;

    @Column(name = "BALANCE")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "LASTBALANCEUPDATEDATE")
    private Date lastBalanceUpdateAt;

    @Column(name = "LASTRECONCILEDATE")
    private Date lastReconciledAt;

    @Column(name = "CURRENCYID")
    private Long currencyId;

    @Column(name = "CURRENCY")
    private String currencyName;

}
