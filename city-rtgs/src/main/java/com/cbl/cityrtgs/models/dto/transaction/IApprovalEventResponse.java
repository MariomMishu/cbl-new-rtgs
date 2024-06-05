package com.cbl.cityrtgs.models.dto.transaction;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import java.math.BigDecimal;
import java.util.Date;

public interface IApprovalEventResponse {
    Long getId();

    String getEventId();

    String getEventName();

    String getStatus();

    String getParentBatchNumber();

    String getReferenceText();

    String getFundTransferType();

    RoutingType getRoutingType();

    String getEntryUser();

    Date getCreatedAt();

    Long getBranchId();

    String getCurrency();

    BigDecimal getAmount();

    String getPayerName();

    int getBatchChargeEnable();

    BigDecimal getChargeAmount();

    BigDecimal getVatAmount();
}
