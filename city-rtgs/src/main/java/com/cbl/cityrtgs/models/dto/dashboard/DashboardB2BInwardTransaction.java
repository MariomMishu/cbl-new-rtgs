package com.cbl.cityrtgs.models.dto.dashboard;

import java.math.BigDecimal;
import java.util.Date;

public interface DashboardB2BInwardTransaction {

    Date getTime();

    String getPayerBank();

    String getBeneficiaryBank();

    String getReferenceNo();

    BigDecimal getAmount();

    String getCurrency();

    String getNarration();

    String getVoucher();

    String getStatus();

    String getDepartment();

    String getRoutingType();

    String getBeneficiaryBranch();

    String getPayerBranch();

    String getBillNumber();

    String getPartyName();

    String getLcNumber();
}

