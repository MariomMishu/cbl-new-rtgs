package com.cbl.cityrtgs.models.dto.menu.response;

import com.cbl.cityrtgs.models.entitymodels.menu.Group;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupResponse {

    private Long id;
    private String name;

    public static GroupResponse toDTO(Group group){

        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .build();
    }
}
