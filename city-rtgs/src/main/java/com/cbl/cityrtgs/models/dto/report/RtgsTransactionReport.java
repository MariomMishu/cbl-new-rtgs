package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface RtgsTransactionReport {

    Date getTxnDate();

    String getPayerAccNo();

    String getPayerName();

    String getPayerBank();
    String getBeneficiaryAccNo();

    String getBeneficiaryName();

    String getBeneficiaryBank();

    String getReference();

    BigDecimal getAmount();

    String getCurrency();

    String getNarration();

    String getLcNumber();

    String getVoucher();

    String getTxnStatus();

    String getDeptName();

    String getRoutingType();

    String getBeneficiaryBranch();

    String getPayerBranch();
}
