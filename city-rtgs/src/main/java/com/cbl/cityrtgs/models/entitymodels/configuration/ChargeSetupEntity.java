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
@Table(name = EntityConstant.CHARGESETUP)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class ChargeSetupEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "CHARGEAMT")
    private BigDecimal chargeAmount = BigDecimal.ZERO;

    @Column(name = "CHARGEGL")
    private String chargeGl;

    @Column(name = "FROMAMT")
    private BigDecimal fromAmount = BigDecimal.ZERO;

    @Column(name = "STATUS")
    private boolean status;

    @Column(name = "TOAMT")
    private BigDecimal toAmount = BigDecimal.ZERO;

    @Column(name = "VATAMT")
    private BigDecimal vatAmount = BigDecimal.ZERO;

    @Column(name = "VATGL")
    private String vatGl;

    @Column(name = "VATPERCENT")
    private BigDecimal vatPercent = BigDecimal.ZERO;

    @Column(name = "CONVERSIONRATE")
    private BigDecimal conversionRate = BigDecimal.ZERO;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CHARGEVATCURRENCY_ID")
    private CurrencyEntity chargeCurrency;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CURRENCY_ID")
    private CurrencyEntity currency;
}
