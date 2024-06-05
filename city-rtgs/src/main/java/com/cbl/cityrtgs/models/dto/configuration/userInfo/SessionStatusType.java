package com.cbl.cityrtgs.models.dto.configuration.userInfo;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SessionStatusType {
    Pending, Processed, Locked, LOGGEDIN;
}

