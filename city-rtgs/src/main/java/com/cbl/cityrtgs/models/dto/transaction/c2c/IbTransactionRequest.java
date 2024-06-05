package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class IbTransactionRequest {
    @NotBlank(message = "User Name can't be empty")
    private String userName;

    @NotBlank(message = "password can't be empty")
    private String password;

    @NotBlank(message = "Reference Number can't be empty")
    private String refNumber;

    private Long settlementDate;

    private String narration;

    @NotBlank(message = "Currency can't be empty")
    private String currency;

    @NotNull(message = "Amount can't be empty")
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotBlank(message = "Beneficiary Branch Routing Number can't be empty")
    private String benBranchRoutingNo;

    @NotBlank(message = "Beneficiary name can't be empty")
    private String benName;

    @NotBlank(message = "Beneficiary Account Number can't be empty")
    private String benAccount;

    @NotBlank(message = "Payer Account Number can't be empty")
    private String payerAccount;

    @NotBlank(message = "Payer name can't be empty")
    private String payerName;

    @NotBlank(message = "Transaction Type Code can't be empty")
    private String transactionTypeCode;

    private String binCode;

    private String commissionerateEconomicCode;

    @NotBlank(message = "Delivery Channel can't be empty")
    private String deliveryChannel;

    private String customsOfficeCode;

    private int registrationYear;

    private String registrationNumber;

    private String declarantCode;

    private String customerMobileNumber;

}
