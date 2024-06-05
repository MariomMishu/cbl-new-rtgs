package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {

    private String oldPassword;
    private String password;
    private String retypePassword;
}
