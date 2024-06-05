package com.cbl.cityrtgs.models.dto.menu.response;

import com.cbl.cityrtgs.models.entitymodels.menu.GroupMenu;
import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupMenuResponse {

    private Long id;
    private Long groupId;
    private String groupName;
    private String menu;

    public static GroupMenuResponse toDTO(GroupMenu response){

        return GroupMenuResponse.builder()
                .id(response.getId())
                .groupId(response.getGroup().getId())
                .groupName(response.getGroup().getName())
                .menu(response.getMenu())
                .build();
    }
}
