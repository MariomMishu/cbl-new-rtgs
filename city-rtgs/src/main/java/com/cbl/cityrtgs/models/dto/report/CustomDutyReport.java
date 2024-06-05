package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface CustomDutyReport {

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
    String getVoucher();

    BigDecimal getAmount();
    BigDecimal getCharge();
    BigDecimal getVat();

    String getCurrency();
    String getCustCellNo();
    String getRegYear();

    String getRegNum();

    String getCustOfficeCode();

    String getDeclarantCode();
    String getTxnStatus();
    String getDeptName();
    String getDeptId();
}
