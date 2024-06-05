package com.cbl.cityrtgs.models.dto.report;

import java.math.BigDecimal;
import java.util.Date;

public interface ReconciledReport {

    Date getReconcileDate();
    String getReconcileTime();
    String getRoutingType();
    String getVoucherNumber();
    String getReconcileUser();
    String getConfirmTxnNo();
    BigDecimal getAmount();
    String getCurrency();
    String getChargeAmount();
    String getChargeVoucherNumber();
    String getChargeReconcileUser();
    String getChargeReconcileTime();
    String getVatAmount();
    String getVatVoucherNumber();
    String getVatReconcileUser();
    String getVatReconcileTime();

}
