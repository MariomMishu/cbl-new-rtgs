package com.cbl.cityrtgs.models.dto.configuration.shadowaccount;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ShadowAccountFilter {

    private String rtgsSettlementAccount;

    private String incomingGl;

    private String outgoingGl;

    private String bank;

    private String currency;

    private Long bankId;

    private Long currencyId;
}
