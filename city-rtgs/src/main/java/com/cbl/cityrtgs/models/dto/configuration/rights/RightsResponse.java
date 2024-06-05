package com.cbl.cityrtgs.models.dto.configuration.rights;


import com.cbl.cityrtgs.models.entitymodels.configuration.RightsEntity;
import lombok.*;
import lombok.experimental.Accessors;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class RightsResponse {

    private Long id;
    private String name;

    public static RightsResponse toDTO(RightsEntity right){

        return RightsResponse.builder()
                .id(right.getId())
                .name(right.getName())
                .build();
    }
}
