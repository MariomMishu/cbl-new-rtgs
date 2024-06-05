package com.cbl.cityrtgs.models.dto.configuration.fcaccounttype;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class FcAccountTypeRequest {
    @NotBlank(message = "Name can't be empty")
    private String name;

    private boolean isActive;

}
