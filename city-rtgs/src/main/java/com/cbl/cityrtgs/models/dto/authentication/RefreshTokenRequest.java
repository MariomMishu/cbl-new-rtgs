package com.cbl.cityrtgs.models.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class RefreshTokenRequest {

    private String refreshToken;
    private String username;
}
