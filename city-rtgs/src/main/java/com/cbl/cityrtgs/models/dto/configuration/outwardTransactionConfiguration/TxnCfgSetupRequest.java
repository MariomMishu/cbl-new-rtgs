package com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration;

import lombok.*;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TxnCfgSetupRequest {

    @NotNull(message = "Currency is mandatory")
    private Long currencyId;

    private String startTime;

    private String endTime;

    private Boolean timeRestricted;

    @NotNull(message = "Transaction Active Status is mandatory")
    private Boolean txnActive;

}
