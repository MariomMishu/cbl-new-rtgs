package com.cbl.cityrtgs.models.dto.transaction.b2b;


import lombok.*;
import lombok.experimental.Accessors;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class BankFundTransferBatchResponse {
    private String settlementDate;

    private String priorityCode;

    private String txnTypeCode;

    private Long currencyId;

    private List<BankFundTransferBatchTxn> fundTransferTxnList;

    private List<BankFundTransferErrorList> errorList;

    private boolean error;
    private String message;
}
