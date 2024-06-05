package com.cbl.cityrtgs.models.dto.transaction.c2c;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Accessors(chain = true)
public class CustomerFndTransferResponse {

    private Long id;

    private String priorityCode;

    private String txnTypeCode;

    private Long currencyId;

    private String currency;

    private String eventId;

    private String parentBatchNumber;

    private List<CustomerTxnResponse> customerTxnResponseList;

    private Long inwardActionStatus;

    private String returnCode;

    private Date returnDateTime;

    private String returnReason;

    private String rejectReason;

    private Date rejectDateTime;

    private boolean batchChargeEnable;

    private BigDecimal chargeAmount;

    private BigDecimal vatAmount;

}
