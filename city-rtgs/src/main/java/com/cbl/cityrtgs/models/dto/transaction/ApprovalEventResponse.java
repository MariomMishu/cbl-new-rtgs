package com.cbl.cityrtgs.models.dto.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class ApprovalEventResponse {

    private Long id;
    private String eventId;
    private String eventName;
    private String status;
    private String parentBatchNumber;
    private String referenceText;
    private String FundTransferType;
    private RoutingType routingType;
    private String entryUser;
    private Date createdAt;
    private Long branchId;
    private String currency;
    private BigDecimal amount;
    private String payerName;
    private String benName;
    private Date SettlementDate;
    private boolean batchChargeEnable;
    private BigDecimal chargeAmount;
    private BigDecimal vatAmount;
}
