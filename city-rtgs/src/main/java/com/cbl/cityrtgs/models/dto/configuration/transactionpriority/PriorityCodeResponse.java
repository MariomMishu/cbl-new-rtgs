package com.cbl.cityrtgs.models.dto.configuration.transactionpriority;

import lombok.*;
import lombok.experimental.Accessors;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class PriorityCodeResponse {
    private Long id;

    private String code;

    private TransactionPriorityType type;
}
