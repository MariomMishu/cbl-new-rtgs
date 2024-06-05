package com.cbl.cityrtgs.models.dto.configuration.department;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DepartmentRequest {
    @NotBlank(message = "Branch name is mandatory")
    private String name;
}
