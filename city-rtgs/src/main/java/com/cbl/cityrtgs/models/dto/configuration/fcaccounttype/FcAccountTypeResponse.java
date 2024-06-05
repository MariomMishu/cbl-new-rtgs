package com.cbl.cityrtgs.models.dto.configuration.fcaccounttype;

import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class FcAccountTypeResponse {
    private Long id;

    private String name;

    private boolean isActive;

}
