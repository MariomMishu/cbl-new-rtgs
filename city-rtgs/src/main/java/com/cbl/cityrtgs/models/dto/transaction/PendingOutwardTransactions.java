package com.cbl.cityrtgs.models.dto.transaction;


import java.time.LocalDate;


public interface PendingOutwardTransactions {
     Long getId();
     String getStatus();
     String getBatchNumber();
     LocalDate getTxnDate();
     LocalDate getSettlementDate();
     String getRefNumber();
     String getRoutingType();
     String getPriorityCode();
     String getVoucher();
     String getNarration();
     String getAmount();
     String getCurrency();

     String getFundTransferType();

}
