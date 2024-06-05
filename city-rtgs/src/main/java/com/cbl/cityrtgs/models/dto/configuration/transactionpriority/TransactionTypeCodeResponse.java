package com.cbl.cityrtgs.models.dto.configuration.transactionpriority;

import lombok.*;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class TransactionTypeCodeResponse {

    private Long id;

    private String code;

    private Boolean balanceValidation;

    private Boolean batchTransaction;

    private Boolean chargeApplicable;

    private String description;

    private Boolean status;

    private Boolean apiActivitiesStatus;

    private Boolean extraEndTime;

    private String messageType;

    private String deliveryChannel;

    private Boolean siTransaction;
}
