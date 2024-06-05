package com.cbl.cityrtgs.models.dto.authentication;

import com.cbl.cityrtgs.models.dto.menu.response.GroupMenuResponse;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.Set;

@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthResponse {

    private Long userId;
    private String username;
    private String accessToken;
    private Set<String> roles;
    private Set<String> rights;
    private String branchName;
    private Long branchId;
    private GroupMenuResponse menuAccess;

    public static AuthResponse toDTO(UserInfoEntity user, String accessToken,
                                     Set<String> roles, Set<String> rights, GroupMenuResponse menuAccess){

        return AuthResponse.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .branchName(user.getBranch().getName())
                .branchId(user.getBranch().getId())
                .accessToken(accessToken)
                .roles(roles)
                .rights(rights)
                .menuAccess(menuAccess)
                .build();
    }
}
