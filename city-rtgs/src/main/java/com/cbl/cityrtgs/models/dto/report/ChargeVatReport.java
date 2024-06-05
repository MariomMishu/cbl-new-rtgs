package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface ChargeVatReport {

    Date getTransactionDate();

    String getReference();

    String getBatchNumber();

    String getPayerAccNo();

    String getPayerName();

    String getPayerBankName();

    String getPayerBranchName();

    String getBeneficiaryAccNo();

    String getBeneficiaryName();

    String getBeneficiaryBankName();

    String getBeneficiaryBranchName();

    String getCurrency();

    BigDecimal getAmount();

    BigDecimal getCharge();

    BigDecimal getVat();

    String getDeptName();
}
