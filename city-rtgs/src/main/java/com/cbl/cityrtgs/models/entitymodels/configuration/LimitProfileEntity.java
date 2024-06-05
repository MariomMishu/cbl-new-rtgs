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

@Entity
@Table(name = EntityConstant.LIMIT_PROFILE)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class LimitProfileEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CREDITLIMIT")
    private BigDecimal creditLimit = BigDecimal.ZERO;

    @Column(name = "DEBITLIMIT")
    private BigDecimal debitLimit = BigDecimal.ZERO;

    @Column(name = "TXNVERIFICATIONLIMIT")
    private BigDecimal txnVerificationLimit = BigDecimal.ZERO;

    @OneToOne(fetch = FetchType.LAZY)
    private ProfileEntity profile;

    @OneToOne(fetch = FetchType.LAZY)
    private CurrencyEntity currency;

}
