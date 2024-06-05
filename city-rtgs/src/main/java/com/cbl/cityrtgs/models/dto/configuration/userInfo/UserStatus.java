package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {
    Active,
    InActive,
    Deleted,
    UnLocked,
    Locked,
    Rejected;
}
