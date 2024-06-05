package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface DepartmentWiseReport {
    Long getId();

    String getNarration();

    String getPayerAccNo();

    String getPayerName();

    Long getPayerBranchId();

    String getPayerBranch();

    Long getBeneficiaryBankId();

    Long getBeneficiaryBranchId();

    String getBeneficiaryBank();

    String getBeneficiaryBranch();

    String getBeneficiaryAccountNo();

    String getBeneficiaryName();

    String getReferenceNumber();

    String getVoucherNumber();

    BigDecimal getAmount();

    BigDecimal getCharge();

    BigDecimal getVat();

    Date getTransactionDate();
    String getDeptName();
    String getDeptId();
}
