package com.cbl.cityrtgs.models.dto.transaction;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@Accessors(chain = true)
public class RejectedOutwardTransactions {
    private Long id;
    private String status;
    private String batchNumber;
    private Date createdDate;
    private Date createdTime;
    private String entryUser;
    private String FundTransferType;
}
