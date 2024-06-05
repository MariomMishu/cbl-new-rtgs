package com.cbl.cityrtgs.models.entitymodels.view;

import java.math.BigDecimal;
import java.util.Date;

public interface ISysDateRegisterSummary {

    String getAccountNo();

    Date getTransactionDate();
    BigDecimal getTotalBalance();
    Long getAccount();
}
