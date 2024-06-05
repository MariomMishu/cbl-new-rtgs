package com.cbl.cityrtgs.models.dto.configuration.profile;

import lombok.*;
import lombok.experimental.Accessors;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class ProfileResponse {
    private Long id;

    private String name;

}
