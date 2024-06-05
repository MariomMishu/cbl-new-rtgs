package com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class ChargeReconcileRequest {

    @NotNull(message = "Currency is mandatory")
    private Long currencyId;

    private String currencyName;

    @NotNull(message = "Charge Type can't be empty.")
    private ChargeType chargeType;

    @NotNull(message = "Charge Module can't be empty")
    private ChargeModule chargeModule;

    @NotBlank(message = "Account number can't be empty")
    private String accountNumber;

}
