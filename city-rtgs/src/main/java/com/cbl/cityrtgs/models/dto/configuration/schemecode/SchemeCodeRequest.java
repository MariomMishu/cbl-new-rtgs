package com.cbl.cityrtgs.models.dto.configuration.schemecode;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SchemeCodeRequest {

//    @NotBlank(message = "Scheme Code can't be empty")
//    private ConfigurationKey schemeCodeKey;

    @NotBlank(message = "Scheme Code can't be empty")
    private String schemeCodeValue;
}
