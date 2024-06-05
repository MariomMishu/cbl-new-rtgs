package com.cbl.cityrtgs.models.dto.configuration.department;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class DepartmentResponse {
    private Long id;

    private String name;

}
