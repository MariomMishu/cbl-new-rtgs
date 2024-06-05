package com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
public class ChargeSetupRequest {

    @NotNull(message = "Currency is mandatory")
    private Long currencyId;

    private BigDecimal fromAmount;

    @NotNull(message = "To Amount is mandatory")
    private BigDecimal toAmount;

    @NotNull(message = "Charge Currency is mandatory")
    private Long chargeCurrencyId;

    @NotNull(message = "Charge Amount is mandatory")
    private BigDecimal chargeAmount;

    @NotNull(message = "Vat Percentage is mandatory")
    private BigDecimal vatPercent;

    private BigDecimal vatAmount;

    @NotNull(message = "Charge GL is mandatory")
    private String chargeGl;

    @NotNull(message = "Vat GL is mandatory")
    private String vatGl;

    @NotNull(message = "Conversion Rate is mandatory")
    private BigDecimal conversionRate;

    private Boolean status;

}
