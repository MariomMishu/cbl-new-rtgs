package com.cbl.cityrtgs.models.entitymodels.transaction;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.entitymodels.configuration.CurrencyEntity;
import com.cbl.cityrtgs.models.entitymodels.configuration.DepartmentEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
@Entity
@Table(name = EntityConstant.RECON_DEPT_ACC)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ReconcileDepartmentAccountEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    private String accountNumber;

    private Long departmentId;

    private Long currencyId;

    private BigDecimal amount;

    private LocalDateTime reconcileDate;

    private BigDecimal chargeAmount;

    private BigDecimal vatAmount;

    private String voucherNumber;

    private String reconcileUser;

    private LocalDateTime reconcileTime;

    private String chargeAccountNumber;

    private String chargeVoucherNumber;

    private String chargeReconcileUser;

    private LocalDateTime chargeReconcileTime;

    private String vatAccountNumber;

    private String vatVoucherNumber;

    private String vatReconcileUser;

    private LocalDateTime vatReconcileTime;

    @Column(nullable = true)
    private Long confirmTxnNo;

    @Enumerated(EnumType.STRING)
    private RoutingType routingType;
}
