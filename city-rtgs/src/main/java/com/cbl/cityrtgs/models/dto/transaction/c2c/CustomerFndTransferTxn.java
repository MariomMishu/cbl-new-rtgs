package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@Builder
public class CustomerFndTransferTxn {

    private Long id;

    @NotEmpty(message = "Beneficiary Name can't be empty")
    private String benName;

    @NotNull(message = "Beneficiary Account can't be empty")
    private String benAccNo;

    private String benAccType;

    @NotNull(message = "Bank Name can't be empty")
    private Long benBankId;

    @NotNull(message = "Branch Name can't be empty")
    private Long benBranchId;

    private String benBankBic;

    private String benBranchRoutingNo;

    private boolean batchTxn;

    private String binCode;

    private String commissionerateEconomicCode;

    @NotNull(message = "Payer Account Number can't be empty")
    private String payerAccNo;

    private String payerName;

    private String payerInsNo;

    private String payerAccType;

    private Long payerBranchId;

    private Long payerBankId;

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

    private int rmtRegYear;

    private String rmtRegNum;

    private String rmtDeclareCode;

    private String rmtCusCellNo;

    private Long sourceReference;

    private Long sourceType;

}
