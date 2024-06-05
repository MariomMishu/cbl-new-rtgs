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
@Table(name = EntityConstant.UIAPPCONFIG)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UiAppConfigurationEntity extends BaseEntity {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "ACCNUMBERMAXLENGTH")
    private int accNumberMaxLength;

    @Column(name = "ACCNUMBERMINLENGTH")
    private int accNumberMinLength;

    @Column(name = "MINAMOUNT")
    private BigDecimal minAmount;

    @Column(name = "CSVFILEMAXITEM")
    private int csvFileMaxItem;

    @Column(name = "BATCHOUTENABLED")
    private Boolean batchOutEnabled;

    @Column(name = "DISABLEUI")
    private Boolean disableUi;

    @Column(name = "VALIDATEBALANCE")
    private Boolean validateBalance;

    @Column(name = "CROSSCURRENCYSUPPORT")
    private Boolean crossCurrencySupport;

    @Column(name = "RTGSBALANCEVALIDATE")
    private Boolean rtgsBalanceValidate;

}
