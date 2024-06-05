package com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ChargeSetupFilter {
    private String currency;

    private Long currencyId;

    private BigDecimal fromAmount;

    private BigDecimal toAmount;

    private BigDecimal chargeAmount;

    private BigDecimal vatAmount;

    private String chargeGl;

    private String vatGl;

}
