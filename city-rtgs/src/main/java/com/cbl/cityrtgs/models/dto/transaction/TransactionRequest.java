package com.cbl.cityrtgs.models.dto.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Getter
@Setter
@Accessors(chain = true)
public class TransactionRequest {
    private Boolean chargeEnabled;
    private String drAccount;
    private String crAccount;
    private String chargeAccount;
    private String vatAccount;
    private BigDecimal amount;
    private BigDecimal charge;
    private BigDecimal vat;
    private String narration;
    private String rtgsRefNo;
    private String cbsName;
    private Long currencyId;
    private String currencyCode;
    private Long settlementAccountId;
    private FundTransferType fundTransferType;
    private String entryUser;
    private RoutingType routingType;
    private Boolean batchCharge;
    private String particular2;
    private String remarks;
    private String chargeRemarks;
    private String ababilRequestId;
}
