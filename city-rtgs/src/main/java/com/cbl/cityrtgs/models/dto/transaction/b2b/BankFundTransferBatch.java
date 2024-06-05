package com.cbl.cityrtgs.models.dto.transaction.b2b;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class BankFundTransferBatch {

    private String settlementDate;

    @NotNull(message = "Priority Code can't be empty")
    private String priorityCode;

    @NotNull(message = "Currency can't be empty")
    private Long currencyId;

    private List<BankFundTransferBatchTxn> fundTransferTxnList;
}
