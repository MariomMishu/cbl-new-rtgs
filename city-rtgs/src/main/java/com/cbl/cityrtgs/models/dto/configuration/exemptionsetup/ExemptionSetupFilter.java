package com.cbl.cityrtgs.models.dto.configuration.exemptionsetup;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;


@Getter
@Setter
@Accessors(chain = true)
public class ExemptionSetupFilter {

    private String accountNumber;

    private String accountName;
}
