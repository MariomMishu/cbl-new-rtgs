package com.cbl.cityrtgs.models.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InwardFundTransferActionStatus {
    HONOUR,
    RETURN;
}
