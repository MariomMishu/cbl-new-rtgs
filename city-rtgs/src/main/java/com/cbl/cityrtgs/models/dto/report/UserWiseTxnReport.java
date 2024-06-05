package com.cbl.cityrtgs.models.dto.report;
import java.math.BigDecimal;
import java.util.Date;

public interface UserWiseTxnReport {
    String getMaker();

    Long getMaker_branch();

    Long getMaker_department();

    String getChecker();

    Long getChecker_branch();

    Long getChecker_department();

    String getMaker_name();

    String getChecker_name();

    Date getTransactiondate();

    String getPayer_acc_no();

    String getPayer_name();

    String getPayer_bank_name();

    String getBen_acc_no();

    String getBen_name();

    String getBen_bank_name();

    BigDecimal getAmount();

    String getReferencenumber();

    String getCurrency();

    String getParentbatchnumber();

    String getPayerbranch();

    String getBeneficiarybranch();

    String getTransactionstatus();

    String getRoutingtype();

    String getFundtransfer_type();

    String getNarration();

    String getReturncode();

    String getReturnreason();

}
