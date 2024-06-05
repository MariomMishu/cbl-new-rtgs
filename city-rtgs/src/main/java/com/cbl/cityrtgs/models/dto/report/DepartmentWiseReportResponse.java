package com.cbl.cityrtgs.models.dto.report;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
public class DepartmentWiseReportResponse {
    private Long id;
    private String narration;
    private String payerAcc;
    private String payerName;
    private long payerBranch;
    private long beneficiaryBankId;
    private long beneficiaryBranchId;
    private String beneficiaryAccountNo;
    private String beneficiaryName;
    private String referenceNumber;
    private String voucherNumber;
    private BigDecimal amount;
    private BigDecimal charge;
    private BigDecimal vat;
    private Date transactionDate;

    public DepartmentWiseReportResponse(Long id, String narration, String payerAcc, String payerName, Long payerBranch, Long beneficiaryBankId, Long beneficiaryBranchId,
                                        String beneficiaryAccountNo, String beneficiaryName, String referenceNumber, String voucherNumber,
                                        BigDecimal amount, BigDecimal charge, BigDecimal vat, Date transactionDate) {
        this.id = id;
        this.narration = narration;
        this.payerAcc = payerAcc;
        this.payerName = payerName;
        this.payerBranch = payerBranch;
        this.beneficiaryBankId = beneficiaryBankId;
        this.beneficiaryBranchId = beneficiaryBranchId;
        this.beneficiaryAccountNo = beneficiaryAccountNo;
        this.beneficiaryName = beneficiaryName;
        this.referenceNumber = referenceNumber;
        this.voucherNumber = voucherNumber;
        this.amount = amount;
        this.charge = charge;
        this.vat = vat;
        this.transactionDate = transactionDate;
    }

}
