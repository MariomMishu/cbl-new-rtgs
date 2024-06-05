package com.cbl.cityrtgs.models.dto.transaction.c2c;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerTxnResponse {

    private Long id;

    private String benName;

    private String benAccNo;

    private Long benBankId;

    private String benBankName;

    private String benBankBic;

    private Long benBranchId;

    private String benBranchName;

    private String benBranchRouting;

    private String referenceNumber;

    private RoutingType routingType;

    private String transactionStatus;

    private String voucherNumber;

    private String sentMsgId;

    private boolean batchTxn;

    private String txnGlAccount;

    private Long departmentId;

    private String departmentName;

    private Long departmentAccountId;

    private boolean batchTxnChargeWaived;

    private String chargeGl;

    private String vatGl;

    private String cbsName;

    private String payerAccNo;

    private String payerName;

    private String payerInsNo;

    private Long payerBranchId;

    private String payerBranchName;

    private Long payerBankId;

    private String payerBankName;

    private BigDecimal amount;

    private BigDecimal charge;

    private BigDecimal vat;

    private String narration;

    private String rmtCustOfficeCode;

    private int rmtRegYear;

    private String rmtRegNum;

    private String rmtDeclareCode;

    private String rmtCusCellNo;

    private String eventName;

    private String status;

    private String referenceText;

    private String FundTransferType;

    private String entryUser;

    private String createdAt;

    private Long branchId;

    private String failedReason;

    private String fcOrgAccountType;

    private String fcRecAccountType;

    private String lcNumber;

    private String billNumber;

    private Long fcRecBranchId;

    private String fcRecBranchName;

    private Date transactionDate;

    private String entryBy;

    private String approveBy;

    private String parentBatchNumber;

    private String currency;

    private String returnReason;

    private String benAccType;

    private String payerAccType;

}
