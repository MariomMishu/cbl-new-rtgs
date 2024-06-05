package com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ChargeReconcileResponse {
    private Long id;

    private Long currencyId;

    private String currencyName;

    private ChargeType chargeType;

    private ChargeModule chargeModule;

    private String accountNumber;
}
