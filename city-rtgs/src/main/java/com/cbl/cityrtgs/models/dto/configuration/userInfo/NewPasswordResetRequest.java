package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NewPasswordResetRequest {

    private String phoneNo;
    private String password;
    private String retypePassword;
}
