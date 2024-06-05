package com.cbl.cityrtgs.models.dto.menu.request;

import com.cbl.cityrtgs.models.entitymodels.menu.Action;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class MenuActionRequest {

    private String name;
    private String action;

    public static Action toMODEL(MenuActionRequest request) {

        return Action.builder()
                .name(request.getName())
                .action(request.getAction())
                .build();
    }
}
