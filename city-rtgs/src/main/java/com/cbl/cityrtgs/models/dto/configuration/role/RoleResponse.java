package com.cbl.cityrtgs.models.dto.configuration.role;

import com.cbl.cityrtgs.models.dto.configuration.rights.RightsResponse;
import com.cbl.cityrtgs.models.entitymodels.configuration.RoleEntity;
import lombok.*;
import lombok.experimental.Accessors;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class RoleResponse {

    private Long id;
    private String name;
    private Set<RightsResponse> rightsResponses;
    public static RoleResponse toDTO(RoleEntity role, Set<RightsResponse> rightsResponses){

        return RoleResponse.builder()
                .id(role.getId())
                .name(role.getName())
                .rightsResponses(rightsResponses)
                .build();
    }
}
