package com.cbl.cityrtgs.models.dto.projection;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public interface ApprovalEventLogProjection {

    String getStatus();
    String getEntryUser();
    String getBatchNumber();
    String getCurrency();
    String getReferenceText();
    String getPayerBranch();
    BigDecimal getAmount();
    String getPayerAccNo();
    String getPayerBank();
    String getBenAccNo();
    String getBenBank();
    LocalDateTime getEntryTime();
}
