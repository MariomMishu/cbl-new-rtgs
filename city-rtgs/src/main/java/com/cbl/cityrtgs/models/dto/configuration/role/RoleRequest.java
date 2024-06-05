package com.cbl.cityrtgs.models.dto.configuration.role;

import lombok.*;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleRequest {

    @NotBlank(message = "Name can't be empty")
    private String name;


    @NotNull(message = "At least add one rights.")
    private Set<Long> rightId;

}
