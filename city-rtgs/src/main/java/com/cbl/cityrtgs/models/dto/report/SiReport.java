package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface SiReport {

    String getAcNumber();
    String getAcName();
    LocalDate getDateTime();
    String getReferenceNumber();
    String getMaker();
    String getVoucher();
    BigDecimal getAmount();
    String getBank();
    String getBranch();
    String getReceiverAccount();
    String getReceiverName();
    String getStatus();
}
