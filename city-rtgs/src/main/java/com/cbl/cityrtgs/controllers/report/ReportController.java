package com.cbl.cityrtgs.controllers.report;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.models.dto.report.*;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.report.ReportServiceExport;
import com.cbl.cityrtgs.services.report.abstraction.ReportService;
import com.cbl.cityrtgs.services.transaction.b2b.BankFundTransferService;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.JRException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/report")
public class ReportController {

    private final ReportService reportService;
    private final CustomerFundTransferService customerFundTransferService;
    private final BankFundTransferService bankFundTransferService;
    private final ReportServiceExport reportServiceExport;

    @GetMapping("/departmentwise")
    public APIResponse getDepartmentWiseReport(@RequestParam(value = "fromDate") String fromDate,
                                               @RequestParam(value = "toDate") String toDate,
                                               @RequestParam(value = "routing") String routing,
                                               @RequestParam(value = "currency") String currency,
                                               @RequestParam(value = "status") String status,
                                               @RequestParam(value = "fromAmount", required = false) String fromAmount,
                                               @RequestParam(value = "toAmount", required = false) String toAmount,
                                               @RequestParam(value = "dept", required = false) String dept,
                                               @RequestParam(value = "cbsName", required = false) String cbsName) {

        Map<String, Object> responses = reportService.getDepartmentWiseReport(fromDate, toDate, routing, currency, status, fromAmount, toAmount, dept, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<DepartmentWiseReportResponse> deptWiseList = (List<DepartmentWiseReportResponse>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(deptWiseList.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/departmentwise/export")
    public ResponseEntity<byte[]> getDepartmentWiseReportExport(@RequestParam(value = "fromDate", required = false) String fromDate,
                                                                @RequestParam(value = "toDate", required = false) String toDate,
                                                                @RequestParam(value = "routingType", required = false) String routingType,
                                                                @RequestParam(value = "currency", required = false) String currency,
                                                                @RequestParam(value = "status") String status,
                                                                @RequestParam(value = "fromAmount", required = false) String fromAmount,
                                                                @RequestParam(value = "toAmount", required = false) String toAmount,
                                                                @RequestParam(value = "dept", required = false) String dept,
                                                                @RequestParam(value = "cbsName", required = false) String cbsName,
                                                                @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportDepartmentWiseReport(fromDate, toDate, routingType, currency, status, fromAmount, toAmount, dept, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/ib")
    public APIResponse getIBTransactionReport(@RequestParam(value = "fromDate") String fromDate,
                                              @RequestParam(value = "toDate") String toDate,
                                              @RequestParam(value = "currency") String currency,
                                              @RequestParam(value = "status") String status,
                                              @RequestParam(value = "deliveryChannel") String deliveryChannel) {

        Map<String, Object> responses = reportService.getIBTransactionReport(fromDate, toDate, currency, status, deliveryChannel);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<IbReport> ibReport = (List<IbReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(ibReport.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/ib/export")
    public ResponseEntity<byte[]> exportIBTransactionReport(@RequestParam(value = "fromDate") String fromDate,
                                                            @RequestParam(value = "toDate") String toDate,
                                                            @RequestParam(value = "currency") String currency,
                                                            @RequestParam(value = "status") String status,
                                                            @RequestParam(value = "deliveryChannel") String deliveryChannel,
                                                            @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportIBTransactionReport(fromDate, toDate, currency, status, deliveryChannel, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }


    }

    @GetMapping("/card")
    public APIResponse getCardTransactionReport(@RequestParam(value = "fromDate") String fromDate,
                                                @RequestParam(value = "toDate") String toDate,
                                                @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getCardTransactionReport(fromDate, toDate, currency);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<CardTxnReport> cardReport = (List<CardTxnReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(cardReport.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/card/export")
    public ResponseEntity<byte[]> exportCardReport(@RequestParam(value = "fromDate") String fromDate,
                                                   @RequestParam(value = "toDate") String toDate,
                                                   @RequestParam(value = "currency") String currency,
                                                   @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportCardReport(fromDate, toDate, currency, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/chargevat")
    public APIResponse getChargeVatReport(@RequestParam(value = "fromDate") String fromDate,
                                          @RequestParam(value = "toDate") String toDate,
                                          @RequestParam(value = "currency") String currency,
                                          @RequestParam(value = "cbsName") String cbsName) {

        Map<String, Object> responses = reportService.getChargeVatReport(fromDate, toDate, currency, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<ChargeVatReport> chargeVatReports = (List<ChargeVatReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(chargeVatReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/chargevat/export")
    public ResponseEntity<byte[]> exportChargeVatReport(@RequestParam(value = "fromDate") String fromDate,
                                                        @RequestParam(value = "toDate") String toDate,
                                                        @RequestParam(value = "currency") String currency,
                                                        @RequestParam(value = "cbsName") String cbsName,
                                                        @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportChargeVatReport(fromDate, toDate, currency, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/ib/chargevat")
    public APIResponse getIBChargeVatReport(@RequestParam(value = "fromDate") String fromDate,
                                            @RequestParam(value = "toDate") String toDate,
                                            @RequestParam(value = "deliveryChannel") String deliveryChannel,
                                            @RequestParam(value = "currency") String currency,
                                            @RequestParam(value = "cbsName", required = false) String cbsName) {

        Map<String, Object> responses = reportService.getIBChargeVatReport(fromDate, toDate, deliveryChannel, currency, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<ChargeVatReport> chargeVatReports = (List<ChargeVatReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(chargeVatReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/ib/chargevat/export")
    public ResponseEntity<byte[]> exportIBChargeVatReport(@RequestParam(value = "fromDate") String fromDate,
                                                          @RequestParam(value = "toDate") String toDate,
                                                          @RequestParam(value = "deliveryChannel") String deliveryChannel,
                                                          @RequestParam(value = "currency") String currency,
                                                          @RequestParam(value = "cbsName", required = false) String cbsName,
                                                          @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportIBChargeVatReport(fromDate, toDate, deliveryChannel, currency, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/transaction")
    public APIResponse getTransactionReport(@RequestParam(value = "fromDate") String fromDate,
                                            @RequestParam(value = "toDate") String toDate,
                                            @RequestParam(value = "branch", required = false) String branch,
                                            @RequestParam(value = "currency") String currency,
                                            @RequestParam(value = "cbsName", required = false) String cbsName) {

        Map<String, Object> responses = reportService.getTransactionReport(fromDate, toDate, branch, currency, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RtgsTxnReport> txnReports = (List<RtgsTxnReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/userwisereport")
    public APIResponse getUserWiseReport(@RequestParam(value = "fromDate", required = false) String fromDate,
                                         @RequestParam(value = "toDate", required = false) String toDate,
                                         @RequestParam(value = "routingType", required = false) String routingType,
                                         @RequestParam(value = "currency", required = false) String currency,
                                         @RequestParam(value = "user", required = false) String user) {

        Map<String, Object> responses = reportService.getUserWiseReport(fromDate, toDate, routingType, currency, user);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<UserWiseTxnReport> userWiseTxns = (List<UserWiseTxnReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(userWiseTxns.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/userwisereport/export")
    public ResponseEntity<byte[]> getUserWiseReportExport(@RequestParam(value = "fromDate", required = false) String fromDate,
                                                          @RequestParam(value = "toDate", required = false) String toDate,
                                                          @RequestParam(value = "routingType", required = false) String routingType,
                                                          @RequestParam(value = "currency", required = false) String currency,
                                                          @RequestParam(value = "user", required = false) String user,
                                                          @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportUserWiseReport(fromDate, toDate, routingType, currency, user, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/txnsummary")
    public APIResponse getFundTransferSummaryReport(@RequestParam(value = "txnDate") String txnDate,
                                                    @RequestParam(value = "fundTransferType") String fundTransferType,
                                                    @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getFundTransferSummaryReport(txnDate, fundTransferType, currency);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<TxnSummaryReport> summaryReports = (List<TxnSummaryReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(summaryReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/txnsummary/export")
    public ResponseEntity<byte[]> exportFundTransferSummaryReport(@RequestParam(value = "txnDate") String txnDate,
                                                                  @RequestParam(value = "fundTransferType") String fundTransferType,
                                                                  @RequestParam(value = "currency") String currency,
                                                                  @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportFundTransferSummaryReport(txnDate, fundTransferType, currency, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/customduty")
    public APIResponse getCustomDutyReport(@RequestParam(value = "fromDate") String fromDate,
                                           @RequestParam(value = "toDate") String toDate,
                                           @RequestParam(value = "dept") String dept,
                                           @RequestParam(value = "cbsName") String cbsName) {

        Map<String, Object> responses = reportService.getCustomDutyReport(fromDate, toDate, dept, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<CustomDutyReport> customDutyReport = (List<CustomDutyReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(customDutyReport.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/customduty/export")
    public ResponseEntity<byte[]> exportCustomDutyReport(@RequestParam(value = "fromDate") String fromDate,
                                                         @RequestParam(value = "toDate") String toDate,
                                                         @RequestParam(value = "dept", required = false) String dept,
                                                         @RequestParam(value = "cbsName", required = false) String cbsName,
                                                         @RequestParam(value = "exportType") final ExportType exportType) {


        try {
            return reportServiceExport.exportCustomDutyReport(fromDate, toDate, dept, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/acknowledgement")
    public APIResponse getAcknowledgementSlip(@RequestParam(value = "fromDate") String fromDate,
                                              @RequestParam(value = "toDate") String toDate,
                                              @RequestParam(value = "reference", required = false) String reference,
                                              @RequestParam(value = "payerAcc", required = false) String payerAcc,
                                              @RequestParam(value = "benAcc", required = false) String benAcc,
                                              @RequestParam(value = "routingType", required = false) String routingType,
                                              @RequestParam(value = "currency", required = false) String currency) {

        Map<String, Object> responses = reportService.getTransactionsForAcknowledgementSlip(fromDate, toDate, reference, payerAcc, benAcc, routingType, currency);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<AcknowledgementSlip> acknowledgementSlips = (List<AcknowledgementSlip>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(acknowledgementSlips.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/acknowledgement-slip/export")
    public ResponseEntity<byte[]> exportAcknowledgementSlip(@RequestParam(value = "fromDate") String fromDate,
                                              @RequestParam(value = "toDate") String toDate,
                                              @RequestParam(value = "reference", required = false) String reference,
                                              @RequestParam(value = "payerAcc", required = false) String payerAcc,
                                              @RequestParam(value = "benAcc", required = false) String benAcc,
                                              @RequestParam(value = "routingType", required = false) String routingType,
                                              @RequestParam(value = "currency", required = false) String currency,
                                                            @RequestParam(value = "exportType") final ExportType exportType) {
        try {
            return reportServiceExport.exportTransactionsForAcknowledgementSlip(fromDate, toDate, reference, payerAcc, benAcc, routingType, currency, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/settlement")
    public APIResponse getSettlementAccountRegisterReport(@RequestParam(value = "fromDate") String fromDate,
                                                          @RequestParam(value = "toDate") String toDate,
                                                          @RequestParam(value = "reference", required = false) String reference,
                                                          @RequestParam(value = "voucher", required = false) String voucher,
                                                          @RequestParam(value = "accounting", required = false) String accounting,
                                                          @RequestParam(value = "settlementAcc") String settlementAcc) {

        Map<String, Object> responses = reportService.getSettlementAccountRegisterReport(fromDate, toDate, reference, voucher, accounting, settlementAcc);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<SettlementStatementReport> settlementStatementReports = (List<SettlementStatementReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(settlementStatementReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/settlement/export")
    public ResponseEntity<byte[]> exportSettlementAccountRegisterReport(@RequestParam(value = "fromDate") String fromDate,
                                                                        @RequestParam(value = "toDate") String toDate,
                                                                        @RequestParam(value = "reference", required = false) String reference,
                                                                        @RequestParam(value = "voucher", required = false) String voucher,
                                                                        @RequestParam(value = "accounting", required = false) String accounting,
                                                                        @RequestParam(value = "settlementAcc") String settlementAcc,
                                                                        @RequestParam(value = "exportType") final ExportType exportType) {
        try {
            return reportServiceExport.exportSettlementAccountRegisterReport(fromDate, toDate, reference, voucher, accounting, settlementAcc, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }

    }

    @GetMapping("/bbsettlement")
    public APIResponse getBbSettlementReport(@RequestParam(value = "txnDate") String txnDate,
                                             @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getBbSettlementReport(txnDate, currency);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<BBSettlementReport> summaryReports = (List<BBSettlementReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(summaryReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/bbsettlement/export")
    public ResponseEntity<byte[]> exportBbSettlementReport(@RequestParam(value = "txnDate") String txnDate,
                                                           @RequestParam(value = "currency") String currency,
                                                           @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportBbSettlementReport(txnDate, currency, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/branch/c2c")
    public APIResponse getBranchC2CReport(@RequestParam(value = "fromDate") String fromDate,
                                          @RequestParam(value = "toDate") String toDate,
                                          @RequestParam(value = "routingType") String routingType,
                                          @RequestParam(value = "status") String status,
                                          @RequestParam(value = "cbsName", required = false) String cbsName,
                                          @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getBranchWiseCustomerTxnReport(fromDate, toDate, currency, routingType, status, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<BranchTxnReport> txnReports = (List<BranchTxnReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/branch/c2c/export")
    public ResponseEntity<byte[]> exportBranchC2CReport(@RequestParam(value = "fromDate") String fromDate,
                                                        @RequestParam(value = "toDate") String toDate,
                                                        @RequestParam(value = "routingType") String routingType,
                                                        @RequestParam(value = "status") String status,
                                                        @RequestParam(value = "cbsName", required = false) String cbsName,
                                                        @RequestParam(value = "currency") String currency,
                                                        @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportBranchwiseC2CTxnReport(fromDate, toDate, routingType, status, cbsName, currency, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/branch/b2b")
    public APIResponse getBranchB2BReport(@RequestParam(value = "fromDate") String fromDate,
                                          @RequestParam(value = "toDate") String toDate,
                                          @RequestParam(value = "routingType") String routingType,
                                          @RequestParam(value = "status") String status,
                                          @RequestParam(value = "cbsName", required = false) String cbsName,
                                          @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getBranchWiseBankTxnReport(fromDate, toDate, currency, routingType, status, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<BranchTxnReport> txnReports = (List<BranchTxnReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/branch/b2b/export")
    public ResponseEntity<byte[]> exportBranchB2BReport(@RequestParam(value = "fromDate") String fromDate,
                                                        @RequestParam(value = "toDate") String toDate,
                                                        @RequestParam(value = "routingType") String routingType,
                                                        @RequestParam(value = "status") String status,
                                                        @RequestParam(value = "cbsName", required = false) String cbsName,
                                                        @RequestParam(value = "currency") String currency,
                                                        @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportBranchwiseB2BTxnReport(fromDate, toDate, currency, routingType, status, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/error")
    public APIResponse getErrorReport(@RequestParam(value = "fromDate") String fromDate,
                                      @RequestParam(value = "toDate") String toDate) {

        Map<String, Object> responses = reportService.getErrorReport(fromDate, toDate);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<ErrorReport> errorList = (List<ErrorReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(errorList.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/error/export")
    public ResponseEntity<byte[]> exportErrorReport(@RequestParam(value = "fromDate") String fromDate,
                                                    @RequestParam(value = "toDate") String toDate,
                                                    @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportErrorReport(fromDate, toDate, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/c2cTxn")
    public APIResponse getReportForCustomerTxn(@RequestParam(value = "fromDate") String fromDate,
                                               @RequestParam(value = "toDate") String toDate,
                                               @RequestParam(value = "bank", required = false) String bank,
                                               @RequestParam(value = "routingType") String routingType,
                                               @RequestParam(value = "status") String status,
                                               @RequestParam(value = "cbsName") String cbsName,
                                               @RequestParam(value = "dept", required = false) String dept,
                                               @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getReportForCustomerTxn(fromDate, toDate, bank, currency, routingType, status, dept, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RtgsTransactionReport> txnReports = (List<RtgsTransactionReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/c2cTxn/export")
    public ResponseEntity<byte[]> exportReportForCustomerTxn(@RequestParam(value = "fromDate") String fromDate,
                                                             @RequestParam(value = "toDate") String toDate,
                                                             @RequestParam(value = "bank", required = false) String bank,
                                                             @RequestParam(value = "routingType") String routingType,
                                                             @RequestParam(value = "status") String status,
                                                             @RequestParam(value = "cbsName") String cbsName,
                                                             @RequestParam(value = "dept", required = false) String dept,
                                                             @RequestParam(value = "currency") String currency,
                                                             @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportReportForCustomerTxn(fromDate, toDate, bank, currency, routingType, status, dept, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/b2bTxn")
    public APIResponse getReportForBankTxn(@RequestParam(value = "fromDate") String fromDate,
                                           @RequestParam(value = "toDate") String toDate,
                                           @RequestParam(value = "bank", required = false) String bank,
                                           @RequestParam(value = "routingType") String routingType,
                                           @RequestParam(value = "status") String status,
                                           @RequestParam(value = "cbsName") String cbsName,
                                           @RequestParam(value = "dept", required = false) String dept,
                                           @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getReportForBankTxn(fromDate, toDate, bank, currency, routingType, status, dept, cbsName);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RtgsTransactionReport> txnReports = (List<RtgsTransactionReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/b2bTxn/export")
    public ResponseEntity<byte[]> exportReportForBankTxn(@RequestParam(value = "fromDate") String fromDate,
                                                         @RequestParam(value = "toDate") String toDate,
                                                         @RequestParam(value = "bank", required = false) String bank,
                                                         @RequestParam(value = "routingType") String routingType,
                                                         @RequestParam(value = "status") String status,
                                                         @RequestParam(value = "cbsName") String cbsName,
                                                         @RequestParam(value = "dept", required = false) String dept,
                                                         @RequestParam(value = "currency") String currency,
                                                         @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportReportForBankTxn(fromDate, toDate, bank, currency, routingType, status, dept, cbsName, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/reconciled-department")
    public APIResponse getReconciledReport(@RequestParam(value = "fromDate") String fromDate,
                                           @RequestParam(value = "toDate") String toDate,
                                           @RequestParam(value = "dept", required = false) String dept,
                                           @RequestParam(value = "currency") String currency) {

        Map<String, Object> responses = reportService.getReconciledReport(fromDate, toDate, currency, dept);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<ReconciledReport> txnReports = (List<ReconciledReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/reconciled-department/export")
    public ResponseEntity<byte[]> exportReconciledReport(@RequestParam(value = "fromDate") String fromDate,
                                                         @RequestParam(value = "toDate") String toDate,
                                                         @RequestParam(value = "dept", required = false) String dept,
                                                         @RequestParam(value = "currency") String currency,
                                                         @RequestParam(value = "exportType") final ExportType exportType) {

        try {
            return reportServiceExport.exportReconciledReport(fromDate, toDate, currency, dept, exportType);
        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/ababil")
    public APIResponse getAbabilReport(@RequestParam(value = "fromDate") String fromDate,
                                       @RequestParam(value = "toDate") String toDate,
                                       @RequestParam(value = "currency") String currency,
                                       @RequestParam(value = "routingType") String routingType) {

        Map<String, Object> responses = reportService.getAbabilReport(fromDate, toDate, currency, routingType);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RtgsTransactionReport> txnReports = (List<RtgsTransactionReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/banktxns")
    public APIResponse getBankAuditTxns(@RequestParam(value = "fromDate") String fromDate,
                                        @RequestParam(value = "toDate") String toDate,
                                        @RequestParam(value = "routingType", required = false) String routingType,
                                        @RequestParam(value = "reference", required = false) String reference) {

        Map<String, Object> responses = reportService.getReportForBankTxn(fromDate, toDate, routingType, reference);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RtgsTransactionReport> txnReports = (List<RtgsTransactionReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/customertxns")
    public APIResponse getCustomerAuditTxns(@RequestParam(value = "fromDate") String fromDate,
                                            @RequestParam(value = "toDate") String toDate,
                                            @RequestParam(value = "routingType", required = false) String routingType,
                                            @RequestParam(value = "reference", required = false) String reference) {

        Map<String, Object> responses = reportService.getReportForCustomerTxn(fromDate, toDate, routingType, reference);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RtgsTransactionReport> txnReports = (List<RtgsTransactionReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/audit/c2cTxns/{reference}")
    public APIResponse getCustomerAuditTxns(@PathVariable String reference) {
        Map<String, Object> responses = customerFundTransferService.getC2CTxnAuditListByReference(reference);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<TxnAuditReport> txnReports = (List<TxnAuditReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/audit/b2bTxns/{reference}")
    public APIResponse getBankAuditTxns(@PathVariable String reference) {
        Map<String, Object> responses = bankFundTransferService.getB2BTxnAuditListByReference(reference);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<TxnAuditReport> txnReports = (List<TxnAuditReport>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/si")
    public APIResponse getSIReport(@RequestParam(value = "fromDate", required = true) String fromDate,
                                   @RequestParam(value = "toDate", required = true) String toDate,
                                   @RequestParam(value = "branch", required = false) String branch,
                                   @RequestParam(value = "currency", required = true) String currency) {

        ResponseDTO responses = reportService.getSIReport(fromDate, toDate, branch, currency);

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(responses.getMessage())
                .body(responses.getBody())
                .build();
    }
}
