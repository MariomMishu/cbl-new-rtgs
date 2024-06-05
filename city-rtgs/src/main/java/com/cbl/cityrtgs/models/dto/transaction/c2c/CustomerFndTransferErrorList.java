package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerFndTransferErrorList {
    private int indexNum;

    private String benName;

    private String benAccNo;

    private String benAccType;

    private String payerAccType;

    private Long benBankId;

    private Long benBranchId;

    private String benBankBic;

    private String benBankName;

    private String benBranchRoutingNo;

    private String benBranchName;

    private BigDecimal amount;

    private String narration;

    private String lcNumber;

    private String errorMessage;

    private String rmtCustOfficeCode;

    private String rmtRegYear;

    private String rmtRegNum;

    private String rmtDeclareCode;

    private String rmtCusCellNo;

}
