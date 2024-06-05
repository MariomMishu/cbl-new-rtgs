package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
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
@Table(name = EntityConstant.RTGS_ACCOUNTS)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class SettlementAccountEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "ACCOUNTSTATUS")
    private String accountStatus;

    @Column(name = "BALANCECCY")
    private BigDecimal balanceCCY;

    @Column(name = "BALANCELCY")
    private BigDecimal balanceLCY;

    @Column(name = "CLOSINGDATE")
    private Date closingDate;

    @Column(name = "OPENDATE")
    private Date openDate;

    @Column(name = "ACCOUNTTYPE_ID")
    private Long accountType;

    @OneToOne(fetch = FetchType.LAZY)
    private BankEntity bank;

    private Long branchId;

    @OneToOne(fetch = FetchType.EAGER)
    private CurrencyEntity currency;
}
