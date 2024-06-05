package com.cbl.cityrtgs.models.dto.projection;

import java.math.BigDecimal;
import java.util.Date;

public interface BankFundTransferProjection {
    String getCurrency();

    String getStatus();

    String getParentBatchNumber();

    String getRoutingType();

    String getCreatedAt();

    String getReferenceNumber();

    String getBillNumber();

    String getLcNumber();

    BigDecimal getAmount();

    String getNarration();

    String getReturnReason();

    String getTransactionDate();

    String getTransactions();

    String getVoucherNumber();

    String getFundTransferType();

    String getPartyName();

    String getPayerBranchName();

    String getBenBankName();

    String getBenBranchName();

    String getFcRecBranchName();

    String getApproveBy();

    String getPayerBankName();

    String getEntryBy();

    String getFromAccountNumber();

}
