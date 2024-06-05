package com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TxnCfgSetupResponse {

    private Long id;

    private String startTime;

    private String endTime;

    private Boolean timeRestricted;

    private Boolean txnActive;

    private Long currencyId;

    private String currencyCode;

}
