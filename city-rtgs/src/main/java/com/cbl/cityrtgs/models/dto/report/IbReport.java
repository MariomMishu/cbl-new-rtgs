package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface IbReport {

    String getFundTransferType();

    String getRoutingType();

    Date getTransactionDate();

    String getRequestReference();

    String getPayerAccNo();

    String getPayerName();

    String getBeneficiaryAccountNo();

    String getBeneficiaryName();

    String getBeneficiaryBank();

    String getResponseReference();

    String getVoucherNumber();

    BigDecimal getAmount();


}
