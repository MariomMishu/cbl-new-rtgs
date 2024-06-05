package com.cbl.cityrtgs.models.dto.report;

public interface TxnAuditReport {

    String getRejectReason();

    String getRejectedUser();

    String getRejectionDateTime();

    String getVerificationStatus();

    String getApprover();

    String getApprovalDateTime();

    String getVerifyDateTime();

    String getVerifier();

    String getBranchId();

    String getEntryUser();


}
