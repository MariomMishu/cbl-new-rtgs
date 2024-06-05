package com.cbl.cityrtgs.models.dto.si;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@EqualsAndHashCode
@ToString
public class CustomerFundTransfer {

    private String priorityCode;
    private String transactionTypeCode;
    private Long currencyId;
    private CustomerFundTransferTransaction customerFundTransferTransaction;
}
