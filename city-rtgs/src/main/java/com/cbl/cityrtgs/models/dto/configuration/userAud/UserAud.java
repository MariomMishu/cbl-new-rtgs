package com.cbl.cityrtgs.models.dto.configuration.userAud;

import com.cbl.cityrtgs.models.dto.configuration.userInfo.UserStatus;
import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserAud {

    private Long id;

    private Long rev;

    private int revType;

    private String fullName;

    private String phone_No;

    private String cell_No;

    private String emailAddr;

    private Long branchId;

    private Long deptId;

    private String employeeId;

    private UserStatus recStatus;

    private String username;

    private String password;

    private int userLevel;

    private Long menuGroupId;

    private Long profileId;

//    private BigDecimal crLimit;

//    private BigDecimal drLimit;

//    private BigDecimal txnVrfLimit;
}
