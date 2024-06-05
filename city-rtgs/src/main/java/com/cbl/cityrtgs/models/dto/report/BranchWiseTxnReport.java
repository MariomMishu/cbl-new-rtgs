package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface BranchWiseTxnReport {

    Date getTxnDate();

    String getDebitorAccNo();

    String getPayerName();

    String getPayerBank();

    String getPayerBranch();

    String getCreditorAccNo();

    String getBeneficiaryName();

    String getBeneficiaryBank();

    String getBeneficiaryBranch();

    String getReference();

    BigDecimal getAmount();

    String getCurrency();

    String getMaker();

    String getChecker();

    String getTxnStatus();
}
