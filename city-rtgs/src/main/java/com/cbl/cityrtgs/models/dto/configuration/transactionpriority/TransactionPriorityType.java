package com.cbl.cityrtgs.models.dto.configuration.transactionpriority;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionPriorityType {
    LOW,
    URGENT,
    NORMAL;
}
