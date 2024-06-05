package com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChargeReconcileFilter {

    private Long currencyId;

    private String currencyName;

    private ChargeType chargeType;

    private ChargeModule chargeModule;

    private String accountNumber;

}
