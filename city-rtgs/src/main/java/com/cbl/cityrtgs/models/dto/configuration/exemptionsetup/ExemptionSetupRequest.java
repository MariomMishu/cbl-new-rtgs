package com.cbl.cityrtgs.models.dto.configuration.exemptionsetup;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class ExemptionSetupRequest {

    @NotBlank(message = "Account Number can't be empty")
    private String accountNumber;

    @NotBlank(message = "Account Name can't be empty")
    private String accountName;
}
