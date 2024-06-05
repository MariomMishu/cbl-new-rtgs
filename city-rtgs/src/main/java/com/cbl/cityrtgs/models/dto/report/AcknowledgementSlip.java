package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface AcknowledgementSlip {

    Date getTxnDate();

    String getPayerAccNo();

    String getPayerName();

    String getPayerBank();

    String getPayerBranch();

    String getBeneficiaryAccNo();

    String getBeneficiaryName();

    String getBeneficiaryBank();

    String getBeneficiaryBranch();

    String getReference();
    String getVoucher();

    BigDecimal getAmount();

    String getCurrency();

    String getTxnStatus();

    String getNarration();

    String getReturnReason();
}
