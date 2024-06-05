package com.cbl.cityrtgs.models.dto.transaction;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionStatus {
    Accepted,
    Arrived,
    Failed,
    Submitted,
    Confirmed,
    Pending,
    Rejected,
    Reversed,
    Cancelled,
    Returned;
}
