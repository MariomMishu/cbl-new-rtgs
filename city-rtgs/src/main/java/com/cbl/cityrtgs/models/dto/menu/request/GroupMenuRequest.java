package com.cbl.cityrtgs.models.dto.menu.request;

import com.cbl.cityrtgs.models.entitymodels.menu.GroupMenu;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMenuRequest {

    private Long groupId;
    private String menu;

    public static GroupMenu toMODEL(GroupMenuRequest request){

        return GroupMenu.builder()
                .menu(request.getMenu())
                .build();
    }
}
