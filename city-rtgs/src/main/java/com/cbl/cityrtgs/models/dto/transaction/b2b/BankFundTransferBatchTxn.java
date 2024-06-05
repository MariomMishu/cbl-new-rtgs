package com.cbl.cityrtgs.models.dto.transaction.b2b;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class BankFundTransferBatchTxn {

    private int indexNum;

    private Long benBankId;

    private Long benBranchId;

    @NotBlank(message = "Bank Bic Code can't be empty")
    private String benBankBic;

    private String benBankName;

    @NotBlank(message = "Branch Routing name can't be empty")
    private String benBranchRoutingNo;

    private String benBranchName;

    private boolean batchTxn;

    ///Payment Details
    @NotNull(message = "Amount can't be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotEmpty(message = "Narration can't be empty")
    private String narration;

    private String lcNumber;

    private String billNumber;

    private String partyName;

    private Long fcRecBranchId;

    private String fcRecAccountType;

    private String fcRecRoutingNo;


}
