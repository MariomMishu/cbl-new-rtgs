package com.cbl.cityrtgs.models.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TransactionFeedback {
    private TransactionAction transactionAction;
    private String reason;
}
