package com.cbl.cityrtgs.models.dto.transaction.b2b;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class BankFundTransferDetails {

    // private String benAccType;

    @NotNull(message = "Bank Name can't be empty")
    private Long benBankId;

    @NotNull(message = "Rtgs Branch Name can't be empty")
    private Long benBranchId;

    private String benBankBic;

    private String benBranchRoutingNo;

    private String rtgsSettlementAccount;

    private String outgoingGlAccount;

    private boolean batchTxn;

    @NotNull(message = "Amount can't be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    private String narration;

    private String lcNumber;

    private String billNumber;

    private String partyName;

    private Long fcRecBranchId;

    private String fcRecAccountType;

    private String fcRecRoutingNo;
}
