package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class IbTransactionFilter {

    private String requestReference;

    private String responseReference;

    private String transactionDate;

    private String settlementDate;

    private String narration;

    private String currency;

    private String amount;

    private String benBranchRoutingNo;

    private String benName;

    private String benAccount;

    private String payerAccount;

    private String payerName;

    private String transactionTypeCode;

    private String binCode;

    private String deliveryChannel;

    private String customsOfficeCode;

    private String registrationNumber;

    private String customerMobileNumber;

    private String transactionStatus;

    private String isError;

    private String errorMessage;
}
