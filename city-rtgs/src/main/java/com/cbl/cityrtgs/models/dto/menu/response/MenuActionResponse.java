package com.cbl.cityrtgs.models.dto.menu.response;

import com.cbl.cityrtgs.models.entitymodels.menu.Action;
import lombok.*;
import lombok.experimental.Accessors;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Accessors(chain = true)
public class MenuActionResponse {

    private Long id;

    private String name;

    private String action;

    public static MenuActionResponse toDTO(Action entity) {

        return MenuActionResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .action(entity.getAction())
                .build();
    }

}
