package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.*;
import lombok.experimental.Accessors;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class CustomerFndTransferBatchResponse {

    private String settlementDate;

    private String payerAccNo;

    private String priorityCode;

    private String txnTypeCode;

    private Long currencyId;

    private List<CustomerFndTransferBatchTxn> customerFndTransferTxns;

    private List<CustomerFndTransferErrorList> errorList;

    private boolean error;

    private String message;
}
