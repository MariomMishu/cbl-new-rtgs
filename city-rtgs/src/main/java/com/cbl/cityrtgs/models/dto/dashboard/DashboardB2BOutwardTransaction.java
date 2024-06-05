package com.cbl.cityrtgs.models.dto.dashboard;

import java.math.BigDecimal;
import java.util.Date;

public interface DashboardB2BOutwardTransaction {

    Date getTime();

    String getPayerBank();

    String getBeneficiaryBank();

    String getReferenceNumber();

    BigDecimal getAmount();

    String getCurrency();

    String getNarration();

    String getLcNumber();

    String getVoucher();

    String getStatus();

    String getDeliveryChannel();

    String getDepartment();

    String getRoutingType();

    String getBeneficiaryReceiverBranch();

    String getBeneficiaryBranch();

    String getBillNumber();

    String getPartyName();
}

