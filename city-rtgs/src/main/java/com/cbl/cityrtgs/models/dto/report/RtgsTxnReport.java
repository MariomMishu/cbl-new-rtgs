package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface RtgsTxnReport {

    Date getTransactionDate();

    String getPayerAccNo();

    String getPayerName();

    String getPayerBank();

    String getPayerBranch();

    String getBeneficiaryAccNo();

    String getBeneficiaryName();

    String getBeneficiaryBank();

    String getBeneficiaryBranch();

    String getReference();

    BigDecimal getAmount();

    String getCurrency();

    String getMaker();

    String getChecker();

    String getVoucher();

    String getTxnStatus();
}
