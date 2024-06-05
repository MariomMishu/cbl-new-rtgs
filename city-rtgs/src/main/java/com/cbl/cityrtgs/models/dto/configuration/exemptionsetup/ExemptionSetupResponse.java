package com.cbl.cityrtgs.models.dto.configuration.exemptionsetup;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ExemptionSetupResponse {

    private Long id;

    private String accountNumber;

    private String accountName;

}
