package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerFndTransferBatch {

//    @NotNull(message = "Settlement Date can't be empty")
    private String settlementDate;

    @NotNull(message = "Account No can't be empty")
    private String payerAccNo;

    @NotNull(message = "Priority Code can't be empty")
    private String priorityCode;

    @NotNull(message = "Transaction Type can't be empty")
    private String txnTypeCode;

    @NotNull(message = "Currency can't be empty")
    private Long currencyId;

    private List<CustomerFndTransferBatchTxn> customerFndTransferTxns;
}
