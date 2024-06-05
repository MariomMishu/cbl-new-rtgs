package com.cbl.cityrtgs.models.dto.menu.response;

import com.cbl.cityrtgs.models.entitymodels.menu.GroupAccess;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupAccessResponse {

    private Long id;
    private Long groupId;
    private String groupName;
    private List<Long> users;

    public static GroupAccessResponse toDTO(GroupAccess response){

        List<Long> users = new ArrayList<>();

        if(!response.getUsers().isEmpty()){

            response.getUsers().forEach(user -> {

                if(users.isEmpty()){
                    users.add(user.getId());
                }
            });
        }

        return GroupAccessResponse.builder()
                .id(response.getId())
                .groupId(response.getGroup().getId())
                .groupName(response.getGroup().getName())
                .users(users)
                .build();
    }
}
