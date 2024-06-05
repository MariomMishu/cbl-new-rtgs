package com.cbl.cityrtgs.models.entitymodels.configuration;

import com.cbl.cityrtgs.common.constant.EntityConstant;
import com.cbl.cityrtgs.models.entitymodels.base.BaseEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = EntityConstant.CURRENCY)
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyEntity extends BaseEntity {

    @Id
    @Column(name = "ID")
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "SHORTCODE", length = 50)
    private String shortCode;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "B2BMINAMOUNT")
    private BigDecimal b2bMinAmount = BigDecimal.ZERO;

    @Column(name = "C2CMINAMOUNT")
    private BigDecimal c2cMinAmount = BigDecimal.ZERO;


    @Column(name = "ISB2BMANUALTXN", length = 1)
    private boolean b2bManualTxn;

    @Column(name = "ISC2CMANUALTXN", length = 1)
    private boolean c2cManualTxn;

}
