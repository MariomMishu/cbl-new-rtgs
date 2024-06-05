package com.cbl.cityrtgs.models.dto.configuration.schemecode;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class SchemeCodeResponse {

    private Long id;

    private ConfigurationKey schemeCodeKey;

    private String schemeCodeValue;

}
