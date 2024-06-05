package com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargeSetupResponse {
    private Long id;

    private Long currencyId;

    private String currencyName;

    private Double fromAmount;

    private Double toAmount;

    private Long chargeCurrencyId;

    private String chargeCurrencyName;

    private Double chargeAmount;

    private Double vatPercent;

    private Double vatAmount;

    private String chargeGl;

    private String vatGl;

    private Double conversionRate;

    private Boolean status;
}
