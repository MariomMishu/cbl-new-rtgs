package com.cbl.cityrtgs.models.dto.transaction.c2c;

public interface C2CTxnTransactionResponse {

    String getId();

    String getFcRecBranchId();

    String getFcOrgAccountType();

    String getFcRecAccountType();

    String getLcNumber();

    String getBillNumber();

    String getEntryUser();

    String getApproveBy();

    String getAmount();

    String getBatchTxn();

    String getBenAccNo();

    String getBenBankId();

    String getBenBankName();

    String getBenBranchId();

    String getBenBranchName();

    String getBenName();

    String getPayerBankId();


    String getPayerBankName();

    String getPayerBranchId();

    String getPayerBranchName();

    String getReferenceNumber();

    String getRoutingType();

    String getTransactionStatus();

    String getTxnGlAccount();

    String getDepartmentId();

    String getDepartmentName();

    String getDepartmentAccountId();

    String getBatchTxnChargeWaived();

    String getVatGl();

    String getChargeGl();

    String getVat();

    String getCharge();

    String getCbsName();

    String getNarration();

    String getFundTransferType();

    String getFailedReason();

    String getRmtCusCellNo();

    String getRmtDeclareCode();

    String getRmtCustOfficeCode();

    String getRmtRegYear();

    String getRmtRegNum();

    String getTransactionDate();

    String getPayerAccNo();

    String getPayerName();

    String getBenBankBic();

    String getBenBranchRouting();

    String getCurrency();

    String getParentBatchNumber();

    String getReturnReason();

    String getStatus();

    String getBenAccType();

    String getPayerAccType();

}
