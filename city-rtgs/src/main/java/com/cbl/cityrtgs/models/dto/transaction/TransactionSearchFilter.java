package com.cbl.cityrtgs.models.dto.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class TransactionSearchFilter {
    private RoutingType routingType;
    private String bank;
    private String transactionStatus;
    private String voucher;
    private String reference;
    private String batchNumber;
    private String payerAccount;
    private String benAccount;
    private String currency;
    private String dept;
    private String fromDate;
    private String toDate;
}
