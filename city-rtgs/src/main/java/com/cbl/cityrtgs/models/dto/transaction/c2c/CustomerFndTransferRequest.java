package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
@RequiredArgsConstructor
public class CustomerFndTransferRequest implements Serializable {

    private String priorityCode;

    private String txnTypeCode;

    private Long currencyId;
    //beneficiary

    private String benName;

    private String benAccNo;

    private String benAccType;

    private Long benBankId;

    private Long benBranchId;

    private String benBankBic;

    private String benBranchRoutingNo;

    private boolean batchTxn;

    private String binCode;

    private String commissionerateEconomicCode;

    private String payerAccNo;

    private String payerName;
    // cheque number
    private String payerInsNo;

    private String payerAccType;

    private Long payerBranchId;

    private Long payerBankId;

    ///Payment Details
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal charge;
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal vat;

    private String narration;

    private String lcNumber;
    ///Custom Duty Vat Payments
    private String rmtCustOfficeCode;

    private int rmtRegYear;

    private String rmtRegNum;

    private String rmtDeclareCode;

    private String rmtCusCellNo;

}
