package com.cbl.cityrtgs.models.dto.menu.request;

import com.cbl.cityrtgs.models.entitymodels.menu.Group;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupRequest {

    private String name;

    public static Group toMODEL(GroupRequest request){

        return Group.builder()
                .name(request.getName())
                .build();
    }
}
