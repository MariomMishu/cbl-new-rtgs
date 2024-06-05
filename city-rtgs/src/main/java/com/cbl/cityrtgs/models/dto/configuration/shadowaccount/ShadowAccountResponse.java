package com.cbl.cityrtgs.models.dto.configuration.shadowaccount;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ShadowAccountResponse {

    private Long id;

    private String rtgsSettlementAccount;

    private String incomingGl;

    private String outgoingGl;

    private Long bankId;

    private String bankName;

    private Long currencyId;

    private String currencyDescription;

    private String currencyCode;
}
