package com.cbl.cityrtgs.models.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionVerificationStatus {
    Submitted,
    Verified,
    Rejected,
    Approved,
    Failed,
    Returned,
    Unverified,
    Arrived;
}
