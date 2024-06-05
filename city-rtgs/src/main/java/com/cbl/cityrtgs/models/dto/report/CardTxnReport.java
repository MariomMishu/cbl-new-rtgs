package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface CardTxnReport {

    Date getTransactionDate();

    String getPayerAccNo();

    String getPayerName();

    String getPayerBankName();

    String getBeneficiaryCardNo();

    String getBeneficiaryName();

    String getReference();

    BigDecimal getAmount();

    String getCurrency();

}
