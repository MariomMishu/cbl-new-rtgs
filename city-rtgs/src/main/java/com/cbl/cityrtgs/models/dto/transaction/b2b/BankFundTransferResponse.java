package com.cbl.cityrtgs.models.dto.transaction.b2b;

import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Accessors(chain = true)
public class BankFundTransferResponse {

    private Long id;

    private Date settlementDate;

    private Date transactionDate;

    private String priorityCode;

    private String txnTypeCode;

    private Long currencyId;

    private String currency;

    private Long benBankId;

    private String benBankName;

    private String benBankBic;

    private Long benBranchId;

    private String benBranchName;

    private String benBranchRoutingNo;

    private String rtgsSettlementAccount;

    private String outgoingGlAccount;

    private boolean batchTxn;

    private BigDecimal amount;

    private String narration;

    private String lcNumber;

    private String billNumber;
    private String partyName;

    private Long transactions;

    private String referenceNumber;

    private String voucherNumber;

    private String eventId;

    private String eventName;

    private String status;

    private String parentBatchNumber;

    private String FundTransferType;

    private RoutingType routingType;

    private String entryUser;

    private Date createdAt;

    private Long payerBankId;

    private String payerBankName;

    private Long payerBranchId;

    private String payerBranchName;

    private String failedReason;

    private String returnCode;

    private Date returnDateTime;

    private String returnReason;

    private Long inwardActionStatus;

    private Long fcRecBranchId;

    private String fcRecBranchName;

    private String rejectReason;

    private Date rejectDateTime;

    private String entryBy;

    private String approveBy;

    private boolean error;

    private String errorMessage;
}
