package com.cbl.cityrtgs.models.dto.dashboard;

import java.math.BigDecimal;
import java.util.Date;

public interface DashboardC2CInwardTransaction {

    Date getTransactionDate();

    String getPayerAccount();

    String getPayerName();

    String getPayerBank();

    String getBeneficiaryAccount();

    String getBeneficiaryName();

    String getBeneficiaryBank();

    String getReferenceNumber();

    BigDecimal getAmount();

    BigDecimal getVat();

    BigDecimal getCharge();

    String getCurrency();

    String getNarration();

    String getLcNumber();

    String getVoucher();

    String getStatus();

    String getDeliveryChannel();

    String getDepartment();

    String getRoutingType();

    String getBeneficiaryBranch();

    String getPayerBranch();
}

