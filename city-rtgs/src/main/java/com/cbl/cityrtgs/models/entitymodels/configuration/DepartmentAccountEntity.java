package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = EntityConstant.DEPARTMENT_ACCOUNT)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentAccountEntity extends BaseEntity implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID")
    private CurrencyEntity currency;

    @Column(name = "ACCOUNTNUMBER")
    private String accountNumber;

    @Column(name = "BALANCE")
    private BigDecimal balance = BigDecimal.ZERO;

    @Column(name = "CHARGE")
    private BigDecimal charge = BigDecimal.ZERO;

    @Column(name = "CONFIRMTXNNO")
    private Long confirmTxnNo = 0L;

    @Column(name = "RECONCILE")
    private boolean reconcile;

    @Column(name = "RECONCILEDATE")
    private Date reconcileDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "RECONCILESETTLEMENTDATE")
    private Date reconcileSettlementDate;

    @Column(name = "RECONCILEUSER")
    private String reconcileUser;

    @Column(name = "REFERENCENUMBER")
    private String referenceNumber;

    @Column(name = "ROUTINGTYPE")
    @Enumerated(EnumType.STRING)
    private RoutingType routingType;

    @Column(name = "VAT")
    private BigDecimal vat = BigDecimal.ZERO;

    @Column(name = "VOUCHERNUMBER")
    private String voucherNumber;

    @Column(name = "CHARGEACCNUMBER")
    private String chargeAccNumber;

    @Column(name = "CHARGERECONCILE")
    private boolean chargeReconcile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CHARGERECONCILESETTDATE")
    private Date chargeReconcileSetDate;

    @Column(name = "CHARGERECONCILEUSER")
    private String chargeReconcileUser;

    @Column(name = "CHARGEVOUCHERNUMBER")
    private String chargeVoucherNumber;

    @Column(name = "VATACCNUMBER")
    private String vatAccNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DEPARTMENT_ID")
    private DepartmentEntity dept;

    @Column(name = "VATRECONCILE")
    private boolean vatReconcile;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "VATRECONCILESETTDATE")
    private Date vatReconcileSetDate;

    @Column(name = "VATRECONCILEUSER")
    private String vatReconcileUser;

    @Column(name = "VATVOUCHERNUMBER")
    private String vatVoucherNumber;

    @Column(name = "TOTALAMOUNT")
    private BigDecimal totalAmount = BigDecimal.ZERO;

}
