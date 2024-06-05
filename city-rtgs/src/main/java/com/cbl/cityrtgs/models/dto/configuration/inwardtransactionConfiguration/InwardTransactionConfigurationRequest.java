package com.cbl.cityrtgs.models.dto.configuration.inwardtransactionConfiguration;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class InwardTransactionConfigurationRequest {

    private Boolean autoGeneratereTurnOnError;

    private Boolean isManualTxn;

    private Boolean matchBenificiaryName;

    private int maximumProcessingTime;

    private int matchingPercentage;

}
