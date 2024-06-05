package com.cbl.cityrtgs.services.report.abstraction;

import com.cbl.cityrtgs.models.dto.response.ResponseDTO;

import java.util.Map;

public interface ReportService {
    Map<String, Object> getDepartmentWiseReport(String fromDate, String toDate,
                                                String routing, String currency,
                                                String status, String fromAmount,
                                                String toAmount, String dept, String cbsName);

    Map<String, Object> getIBTransactionReport(String fromDate, String toDate,
                                               String currency, String status, String deliveryChannel);

    Map<String, Object> getCardTransactionReport(String fromDate, String toDate, String currency);

    Map<String, Object> getChargeVatReport(String fromDate, String toDate, String currency, String cbsName);

    Map<String, Object> getIBChargeVatReport(String fromDate, String toDate, String deliveryChannel, String currency, String cbsName);

    Map<String, Object> getTransactionReport(String fromDate, String toDate, String branch, String currency, String cbsName);

    Map<String, Object> getUserWiseReport(String fromDate, String toDate, String routingType, String currency, String user);

    Map<String, Object> getFundTransferSummaryReport(String txnDate, String fundTransferType, String currency);

    Map<String, Object> getCustomDutyReport(String fromDate, String toDate, String dept, String cbsName);

    Map<String, Object> getTransactionsForAcknowledgementSlip(String fromDate, String toDate, String reference, String payerAcc, String benAcc, String routingType, String currency);

    Map<String, Object> getSettlementAccountRegisterReport(String fromDate, String toDate, String reference, String voucher, String accounting, String settlementAcc);

    Map<String, Object> getBbSettlementReport(String txnDate, String currency);

    Map<String, Object> getReportForCustomerTxn(String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName);

    Map<String, Object> getReportForBankTxn(String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName);

    Map<String, Object> getBranchWiseCustomerTxnReport(String fromDate, String toDate, String currency, String routingType, String status, String cbsName);

    Map<String, Object> getBranchWiseBankTxnReport(String fromDate, String toDate, String currency, String routingType, String status,  String cbsName);

    Map<String, Object> getErrorReport(String fromDate, String toDate);

    Map<String, Object> getReconciledReport(String fromDate, String toDate, String currency, String dept);

    Map<String, Object> getAbabilReport(String fromDate, String toDate, String currency, String routingType);
    Map<String, Object> getReportForBankTxn(String fromDate, String toDate, String routingType, String reference);

    Map<String, Object> getReportForCustomerTxn(String fromDate, String toDate, String routingType, String reference);

    ResponseDTO getSIReport(String fromDate, String toDate, String branch, String currencyCode);
}
