package com.cbl.cityrtgs.models.dto.transaction.c2c;

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
public class CustomerFndTransferBatchTxn {

    private int indexNum;

    @NotEmpty(message = "Beneficiary Name can't be empty")
    private String benName;

    @NotNull(message = "Beneficiary Account can't be empty")
    private String benAccNo;

    private String benAccType;

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

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal charge;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal vat;

    @NotEmpty(message = "Narration can't be empty")
    private String narration;

    private String lcNumber;
    ///Custom Duty Vat Payments
    private String rmtCustOfficeCode;

    private String rmtRegYear;

    private String rmtRegNum;

    private String rmtDeclareCode;

    private String rmtCusCellNo;

    private String payerAccType;
}
