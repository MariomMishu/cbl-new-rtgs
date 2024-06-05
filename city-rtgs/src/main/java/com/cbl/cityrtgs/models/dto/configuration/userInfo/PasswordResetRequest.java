package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordResetRequest {
    private String employeeId;
    private String cellNo;
}
