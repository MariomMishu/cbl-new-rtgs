package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.*;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class InterCustomerTxnLiteResponse {

    private boolean error;

    private String message;

    private Long id;

    private String batchNumber;

    private String entryUser;


}
