package com.cbl.cityrtgs.services.report;

import com.cbl.cityrtgs.common.exception.BaseException;
import com.cbl.cityrtgs.common.logger.RtgsLogger;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.report.*;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.repositories.transaction.AccountTransactionRegisterRepository;
import com.cbl.cityrtgs.repositories.transaction.ReconcileDepartmentAccountRepository;
import com.cbl.cityrtgs.repositories.transaction.SettlementAccountStatementDetailRepository;
import com.cbl.cityrtgs.repositories.transaction.b2b.InterBankFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.CustomerFndTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.IbTransactionRepository;
import com.cbl.cityrtgs.repositories.transaction.c2c.InterCustomerFundTransferRepository;
import com.cbl.cityrtgs.repositories.transaction.notification.FailedTxnNotificationRepository;
import com.cbl.cityrtgs.services.configuration.BankService;
import com.cbl.cityrtgs.services.configuration.CurrencyService;
import com.cbl.cityrtgs.services.configuration.SettlementAccountService;
import com.cbl.cityrtgs.common.utility.DateUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.cbl.cityrtgs.models.dto.report.ExportType.EXCEL;
import static com.cbl.cityrtgs.models.dto.report.ExportType.PDF;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceExport {

    private final InterCustomerFundTransferRepository interCustFundTransferRepository;
    private final InterBankFundTransferRepository interBankFundTransferRepository;
    private final CustomerFndTransferRepository customerFndTransferRepository;
    private final SettlementAccountService settlementAccountService;
    private final AccountTransactionRegisterRepository registerRepository;
    private final SettlementAccountStatementDetailRepository settlementAccountStatementDetailRepository;
    private final ReconcileDepartmentAccountRepository reconcileDepartmentAccountRepository;
    private final CurrencyService currencyService;
    private final IbTransactionRepository ibTransactionRepository;
    private final BankService bankService;
    private final FailedTxnNotificationRepository failedTxnNotificationRepository;
    private final RtgsLogger rtgsLogger;

    // Start -> common helper methods
    public String getFilePath(ExportType export, String filename) {
        return String.format("classpath*:report/" + filename + "_" + export.toString().toLowerCase() + ".jrxml");
    }

    public File getJRFile(String filePath) throws IOException {
        File jrFile = null;
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        Resource[] resources = new Resource[0];
        try {
            resources = resolver.getResources(filePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Resource r : resources) {
            InputStream inputStream = r.getInputStream();
            jrFile = File.createTempFile(r.getFilename(), ".cxl");
            try {
                FileUtils.copyInputStreamToFile(inputStream, jrFile);
            } finally {
                IOUtils.closeQuietly(inputStream);
            }
        }
        return jrFile;
    }

    private String getExportFileName(ExportType exportType, String fileName) throws UnsupportedEncodingException {
        var formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        var dateTimeNow = LocalDateTime.now().format(formatter);
        var exportFileName =
                URLEncoder.encode(fileName.replaceAll("[^\\w-]+", "_") + "_report_" + dateTimeNow, "UTF-8");
        exportFileName = exportFileName + exportType.getExtension().toLowerCase();
        return exportFileName;
    }

    private byte[] exportReportToExcel(JasperPrint jasperPrint) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputStream));
        exporter.exportReport();
        return outputStream.toByteArray();
    }

    private ResponseEntity<byte[]> generateJasperReport(List<?> dataList, HashMap<String, Object> params, ExportType exportType, String fileName) throws JRException, IOException {
        // Load file and compile it to a jasperReport
        String jrFilePath = getFilePath(exportType, fileName);
        File jrFile = getJRFile(jrFilePath);
        JasperReport jasperReport = JasperCompileManager.compileReport(jrFile.getAbsolutePath());
        var beanColDataSource = new JRBeanCollectionDataSource(dataList);
        // Fill Jasper report
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, beanColDataSource);

        byte[] reportBytes;
        String contentType;
        if (exportType == PDF) {
            reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
            contentType = MediaType.APPLICATION_PDF_VALUE;
        } else if (exportType == EXCEL) {
            reportBytes = exportReportToExcel(jasperPrint);
            contentType = "application/vnd.ms-excel";
        } else {
            throw new IllegalArgumentException("Unsupported format: " + exportType.getExtension());
        }

        String exportFileName = getExportFileName(exportType, fileName);

        // Set the appropriate headers for the response
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(contentType));
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename(exportFileName).build());
        // Return the byte array as a ResponseEntity
        return ResponseEntity.ok().headers(headers).body(reportBytes);
    }

    public List<UserWiseTxnReport> getUserWiseReport(String fromDate, String toDate, String routingType, String currency, String user) {
        List<UserWiseTxnReport> response;
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.isEmpty()) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.isEmpty()) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        rtgsLogger.trace(String.format("fromDate  : %s", fromDate));
        rtgsLogger.trace(String.format("toDate  : %s", toDate));
        rtgsLogger.trace(String.format("routingType  : %s", routingType));
        rtgsLogger.trace(String.format("currency  : %s", currency));
        rtgsLogger.trace(String.format("user  : %s", user));
        response = interCustFundTransferRepository.getUserWiseReport(fromDate1, toDate1, routingType, currency, user);
        rtgsLogger.trace(String.format("getUserWiseReport response : %s", response));
        return response;
    }
    // End -| common helper methods

    public ResponseEntity<byte[]> exportUserWiseReport(
            String fromDate, String toDate, String routingType, String currency, String user, ExportType export) throws JRException, IOException {

        // Create a Header Params
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("CURRENTDATE", new Date().toString());
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("MAKER", user);
        extraParams.put("ROUTETYPE", routingType);
        extraParams.put("CURRENCY", StringUtils.isNotBlank(currency) ? currency : "All");
        extraParams.put("ROUTETYPELABEL", "RTGS " + routingType + " User Wise Transaction Report");
        extraParams.put("title", "User_wise_report");
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");

        // Get report_data by query from DB
        var txnReportDataList = getUserWiseReport(fromDate, toDate, routingType, currency, user);
        return generateJasperReport(txnReportDataList, extraParams, export, "userwise");
    }

    public ResponseEntity<byte[]> exportDepartmentWiseReport(
            String fromDate, String toDate, String routingType, String currency, String status, String fromAmount, String toAmount, String dept, String cbsName, ExportType export) throws IOException, JRException {
        // Create a Header Params
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("CURRENTDATE", new Date().toString());
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        if (StringUtils.isBlank(dept) || dept.equalsIgnoreCase("All")) {
            extraParams.put("DEPTID", "%%");
        } else {
            extraParams.put("DEPTID", "%%");
        }
        extraParams.put("ROUTETYPE", routingType);
        extraParams.put("TXN_STATUS", status);
        extraParams.put("CURRENCY_CODE", "BDT");
        extraParams.put("ROUTETYPELABEL", routingType + " RTGS Transaction Report");
        extraParams.put("CBSNAME", cbsName);
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        // Get report_data by query from DB
        var txnDetailsList = getDepartmentWiseReport(fromDate, toDate, routingType, currency, status, dept, cbsName);
        return generateJasperReport(txnDetailsList, extraParams, export, "department_wise");

    }

    public List<DepartmentWiseReport> getDepartmentWiseReport(String fromDate, String toDate, String routing, String currency, String status, String dept, String cbsName) {

        List<DepartmentWiseReport> responses;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.isEmpty()) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.isEmpty()) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (status.isEmpty()) {
            status = null;
        }
        if (routing.equals("")) {
            routing = null;
        }
        if (currency.equals("")) {
            currency = null;
        }
        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (StringUtils.isBlank(dept) || dept.equalsIgnoreCase("All")) {
            responses = customerFndTransferRepository.getAllDepartmentWiseReport(fromDate1, toDate1, routing, currency, status, cbsName);
        } else {
            responses = customerFndTransferRepository.getDepartmentWiseReport(fromDate1, toDate1, routing, currency, status, dept, cbsName);
        }

        return responses;
    }

    public ResponseEntity<byte[]> exportSettlementAccountRegisterReport(
            String fromDate, String toDate, String reference, String voucher, String accounting, String settlementAcc, ExportType export) throws IOException, JRException {

        // Get report_data by query from DB
        var txnDetailsList = getSettlementAccountRegisterReport(fromDate, toDate, reference, voucher, accounting, settlementAcc);

        HashMap<String, Object> extraParams = new HashMap<>();
        var account = settlementAccountService.getSettlementAccByCode(settlementAcc);
        extraParams.put("title", "A/C Statement");
        extraParams.put("fromDate", fromDate);
        extraParams.put("toDate", toDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("currentDate", new Date().toString());
        extraParams.put("accountNumber", settlementAcc);
        extraParams.put("openDate", account.getOpenDate().toString());
        extraParams.put("name", account.getName());
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "settlement");
    }

    public List<SettlementStatementReport> getSettlementAccountRegisterReport(String fromDate, String toDate, String reference, String voucher, String accounting, String settlementAcc) {

        List<SettlementStatementReport> responses;

        LocalDate fromDate1;
        LocalDate toDate1;
        String routingType = "";
        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(voucher)) {
            voucher = "%%";
        }
        if (StringUtils.isBlank(settlementAcc)) {
            settlementAcc = "%%";
        }
        if (StringUtils.isBlank(accounting) || accounting.equalsIgnoreCase("All")) {
            routingType = "%%";
        } else if (accounting.equalsIgnoreCase("Debit")) {
            routingType = RoutingType.Incoming.toString();
        } else if (accounting.equalsIgnoreCase("Credit")) {
            routingType = RoutingType.Outgoing.toString();
        }

        responses = registerRepository.getSettlementAccountRegisterReport2(fromDate1, toDate1, reference, voucher, routingType, settlementAcc);

        return responses;
    }

    public ResponseEntity<byte[]> exportBbSettlementReport(String txnDate, String currency, ExportType export) throws IOException, JRException {
        var txnDetailsList = getBbSettlementReport(txnDate, currency);

        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("title", "BB Settlement A/C Statement");
        extraParams.put("fromDate", txnDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("currentDate", new Date().toString());

        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "bb_settlement");
    }

    public List<BBSettlementReport> getBbSettlementReport(String txnDate, String currency) {

        List<BBSettlementReport> responses;

        LocalDate txnDate1;

        if (!txnDate.equals("")) {
            txnDate1 = DateUtility.toDate(txnDate);
        } else {
            txnDate1 = LocalDate.now();
        }

        responses = settlementAccountStatementDetailRepository.getBbSettlementReport(txnDate1, currency);

        return responses;
    }

    public ResponseEntity<byte[]> exportReconciledReport(
            String fromDate, String toDate, String currency, String dept, ExportType export) throws IOException, JRException {
        var txnDetailsList = getReconciledReport(fromDate, toDate, currency, dept);

        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("title", "BB Settlement A/C Statement");
        extraParams.put("fromDate", fromDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("currentDate", new Date().toString());
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "reconciled");

    }

    public List<ReconciledReport> getReconciledReport(String fromDate, String toDate, String currency, String dept) {

        List<ReconciledReport> responses;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        responses = reconcileDepartmentAccountRepository.getReconciledReport(fromDate1, toDate1, currency, dept);

        return responses;
    }

    public ResponseEntity<byte[]> exportCustomDutyReport(
            String fromDate, String toDate, String dept, String cbsName, ExportType export) throws IOException, JRException {
        var txnDetailsList = getCustomDutyReport(fromDate, toDate, dept, cbsName);

        HashMap<String, Object> extraParams = new HashMap<>();

        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("DEPTID", "%%");
        extraParams.put("CURRENCY_CODE", "BDT");
        extraParams.put("ROUTETYPELABEL", "Outgoing RTGS Transaction Report");
        extraParams.put("CBSNAME", cbsName);

        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "customduty");
    }

    public List<CustomDutyReport> getCustomDutyReport(String fromDate, String toDate, String dept, String cbsName) {
        List<CustomDutyReport> txnResponse;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.isEmpty()) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.isEmpty()) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (StringUtils.isBlank(dept) || dept.equalsIgnoreCase("All")) {
            dept = "%%";
        }
        txnResponse = interCustFundTransferRepository.exportCustomDutyReport(fromDate1, toDate1, dept, cbsName);
        return txnResponse;
    }

    public ResponseEntity<byte[]> exportIBChargeVatReport(
            String fromDate, String toDate, String deliveryChannel, String currency, String cbsName, ExportType export) throws IOException, JRException {
        var txnDetailsList = getIBChargeVatReport(fromDate, toDate, deliveryChannel, currency, cbsName);

        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PlC");
        extraParams.put("DELIVERY_CHANNEL", deliveryChannel.toUpperCase());
        extraParams.put("CBSNAME", cbsName);
        extraParams.put("CURRENCY_ID", currency);
        extraParams.put("CURRENCY_CODE", currencyService.getById(Long.parseLong(currency)).getShortCode());

        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "ibchargevat");
    }

    public List<ChargeVatReport> getIBChargeVatReport(String fromDate, String toDate, String deliveryChannel, String currency, String cbsName) {
        List<ChargeVatReport> response;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (StringUtils.isBlank(deliveryChannel) || deliveryChannel.equalsIgnoreCase("All")) {
            deliveryChannel = "%%";
        }
        response = customerFndTransferRepository.getIBChargeVatReport(fromDate1, toDate1, deliveryChannel, currency, cbsName);

        return response;
    }

    public ResponseEntity<byte[]> exportIBTransactionReport(
            String fromDate, String toDate, String currency, String status, String deliveryChannel, ExportType export) throws IOException, JRException {
        var txnDetailsList = getIBTransactionReport(fromDate, toDate, deliveryChannel, currency, status);

        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", bankService.getOwnerBank().getName());
        extraParams.put("DELIVERY_CHANNEL", deliveryChannel.toUpperCase());
        extraParams.put("TXN_STATUS", status);
        extraParams.put("CURRENCY_ID", currencyService.getByCurrencyShortCode(currency).getId());
        extraParams.put("CURRENCY_CODE", currency);
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "ibtransaction");
    }

    public List<IbReport> getIBTransactionReport(String fromDate, String toDate, String deliveryChannel, String currency, String status) {
        List<IbReport> response;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (status.equals("")) {
            status = null;
        }

        if (currency.equals("")) {
            currency = null;
        }
        if (deliveryChannel.equals("")) {
            deliveryChannel = null;
        }

        response = ibTransactionRepository.getIBTransactionReport(fromDate1, toDate1, currency, status, deliveryChannel);

        return response;
    }

    public ResponseEntity<byte[]> exportFundTransferSummaryReport(String txnDate, String fundTransferType, String currency, ExportType export) throws JRException, IOException {
        try {
            var txnDetailsList = getFundTransferSummaryReport(txnDate, fundTransferType, currency);

            HashMap<String, Object> extraParams = new HashMap<>();
            extraParams.put("TXNDATE", txnDate);
            extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
            extraParams.put("CURRENCY_ID", currency);
            extraParams.put("CURRENCY_CODE", currencyService.getById(Long.parseLong(currency)).getShortCode());
            var title = "summary_report";
            extraParams.put("title", title);

            if (export == null)
                throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
            return generateJasperReport(txnDetailsList, extraParams, export, "summary");

        } catch (IOException | JRException e) {
            throw new RuntimeException(e);
        }
    }

    public List<TxnSummaryReport> getFundTransferSummaryReport(String txnDate, String fundTransferType, String currency) {
        List<TxnSummaryReport> response = new ArrayList<>();
        LocalDate txnDate1;

        if (!txnDate.equals("")) {
            txnDate1 = DateUtility.toDate(txnDate);
        } else {
            txnDate1 = LocalDate.now();
        }

        if (fundTransferType.equals(FundTransferType.CustomerToCustomer.toString())) {
            response = interCustFundTransferRepository.getC2CFundTransferSummaryReport(txnDate1, currency);
        }

        if (fundTransferType.equals(FundTransferType.BankToBank.toString())) {
            response = interBankFundTransferRepository.getB2BFundTransferSummaryReport(txnDate1, currency);
        }

        return response;
    }

    public ResponseEntity<byte[]> exportChargeVatReport(
            String fromDate, String toDate, String currency, String cbsName, ExportType export) throws IOException, JRException {
        var txnDetailsList = getChargeVatReport(fromDate, toDate, currency, cbsName);

        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("CBSNAME", cbsName);
        extraParams.put("CURRENCY_ID", currency);
        extraParams.put("CURRENCY_CODE", currencyService.getById(Long.parseLong(currency)).getShortCode());
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "chargevat");
    }

    public ResponseEntity<byte[]> exportCardReport(
            String fromDate, String toDate, String currency, ExportType export) throws IOException, JRException {
        var txnDetailsList = getCardTransactionReport(fromDate, toDate, currency);

        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("CURRENCY_ID", currency);
        extraParams.put("CURRENCY_CODE", currencyService.getById(Long.parseLong(currency)).getShortCode());
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "card");
    }

    public List<ChargeVatReport> getChargeVatReport(String fromDate, String toDate, String currency, String cbsName) {
        List<ChargeVatReport> txnResponse = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        List<DepartmentList> deptList = customerFndTransferRepository.getDepartmentList(fromDate1, toDate1, currency, cbsName);
        if (deptList.size() > 0) {
            for (int i = 0; i < deptList.size(); i++) {
                txnResponse = customerFndTransferRepository.getChargeVatReport(fromDate1, toDate1, currency, cbsName, deptList.get(i).getDeptId());
            }
        }
        return txnResponse;
    }


    public List<CardTxnReport> getCardTransactionReport(String fromDate, String toDate, String currency) {
        List<CardTxnReport> responses;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        responses = customerFndTransferRepository.getCardTransactionReport(fromDate1, toDate1, currency);

        return responses;
    }

    public ResponseEntity<byte[]> exportErrorReport(
            String fromDate, String toDate, ExportType export) throws IOException, JRException {
        var txnDetailsList = getErrorReport(fromDate, toDate);
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("currentDate", new Date().toString());
        extraParams.put("FROMDATE", fromDate);
        extraParams.put("TODATE", toDate);
        extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "error");
    }

    public List<ErrorReport> getErrorReport(String fromDate, String toDate) {
        List<ErrorReport> response;

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        response = failedTxnNotificationRepository.getErrorReport(fromDate1, toDate1);

        return response;
    }

    public ResponseEntity<byte[]> exportBranchwiseC2CTxnReport(
            String fromDate, String toDate, String routingType, String status, String cbsName, String currency, ExportType export) throws IOException, JRException {
        var txnDetailsList = getBranchWiseCustomerTxnReport(fromDate, toDate, currency, routingType, status, cbsName);
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("fromDate", fromDate);
        extraParams.put("toDate", toDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("currentDate", new Date().toString());
        extraParams.put("routingType", routingType.toString());
        if (txnDetailsList.size() > 0) {
            extraParams.put("currencyParam", currency);
        } else {
            extraParams.put("currencyParam", "");
        }

        extraParams.put("isReportByGroup", Boolean.TRUE);
        extraParams.put("fundTransferType", "Customer To Customer");
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");

        String fileName = "branchwisetxn_out";
        if (routingType.equalsIgnoreCase(RoutingType.Incoming.toString()))
            fileName = "branchwisetxn_in";

        return generateJasperReport(txnDetailsList, extraParams, export, fileName);
    }

    public ResponseEntity<byte[]> exportBranchwiseB2BTxnReport(
            String fromDate, String toDate, String currency, String routingType, String status, String cbsName, ExportType export) throws IOException, JRException {
        var txnDetailsList = getBranchWiseBankTxnReport(fromDate, toDate, currency, routingType, status);
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("fromDate", fromDate);
        extraParams.put("toDate", toDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("currentDate", new Date().toString());
        extraParams.put("routingType", routingType.toString());
        if (txnDetailsList.size() > 0) {
            extraParams.put("currencyParam", currency);
        } else {
            extraParams.put("currencyParam", "");
        }

        extraParams.put("isReportByGroup", Boolean.TRUE);
        extraParams.put("fundTransferType", "Bank To Bank");
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");

        String fileName = "branchwisetxn_out";
        if (routingType.equalsIgnoreCase(RoutingType.Incoming.toString()))
            fileName = "branchwisetxn_in";

        return generateJasperReport(txnDetailsList, extraParams, export, fileName);
    }

    public List<BranchWiseTxnReport> getBranchWiseCustomerTxnReport(String fromDate, String toDate, String currency, String routingType, String status, String cbsName) {
        List<BranchWiseTxnReport> responses = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(cbsName) || cbsName.equalsIgnoreCase("All")) {
            cbsName = "%%";
        }
        if (routingType.equals(RoutingType.Outgoing.toString())) {
            List<BranchList> branchList = interCustFundTransferRepository.getPayerBranchList(fromDate1, toDate1, currency, status, cbsName);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    responses.addAll(interCustFundTransferRepository.exportBranchWiseReportForOutwardCustomerTxn(fromDate1, toDate1, currency, status, cbsName, branchList.get(i).getBranch()));
                }
            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            List<BranchList> branchList = interCustFundTransferRepository.getBeneficiaryBranchList(fromDate1, toDate1, currency, status, cbsName);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    responses.addAll(interCustFundTransferRepository.exportBranchWiseReportForInwardCustomerTxn(fromDate1, toDate1, currency, status, cbsName, branchList.get(i).getBranch()));
                }
            }
        }

        return responses;
    }

    public List<BranchWiseTxnReport> getBranchWiseBankTxnReport(String fromDate, String toDate, String currency, String routingType, String status) {

        List<BranchWiseTxnReport> responses = new ArrayList<>();

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (routingType.equals(RoutingType.Outgoing.toString())) {
            List<BranchList> branchList = interBankFundTransferRepository.getPayerBranchList(fromDate1, toDate1, currency, status);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    responses.addAll(interBankFundTransferRepository.getBranchWiseReportForOutwardBankTxn(fromDate1, toDate1, currency, status, branchList.get(i).getBranch()));

                }

            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            List<BranchList> branchList = interBankFundTransferRepository.getBeneficiaryBranchList(fromDate1, toDate1, currency, status);
            if (branchList.size() > 0) {

                for (int i = 0; i < branchList.size(); i++) {
                    responses.addAll(interBankFundTransferRepository.getBranchWiseReportForInwardBankTxn(fromDate1, toDate1, currency, status, branchList.get(i).getBranch()));
                }

            }
        }
        return responses;
    }

    public ResponseEntity<byte[]> exportReportForCustomerTxn(
            String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName, ExportType export) throws IOException, JRException {
        var txnDetailsList = getReportForCustomerTxn(fromDate, toDate, bank, currency, routingType, status, dept, cbsName);
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("fromDate", fromDate);
        extraParams.put("toDate", toDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("fundTransferType", "Customer To Customer");
        extraParams.put("currentDate", new Date().toString());
        if (routingType == null) {
            extraParams.put("routingType", "All");
        } else {
            extraParams.put("routingType", routingType.toString());
        }

        if (txnDetailsList.size() > 0) {
            extraParams.put("currencyParam", txnDetailsList.get(0).getCurrency());
        }

        if (bank == null) {
            extraParams.put("isReportByGroup", Boolean.TRUE);
        } else {
            extraParams.put("isReportByGroup", Boolean.FALSE);
        }

        if (dept != null) {
            extraParams.put("dept", dept);
        } else {
            extraParams.put("dept", "All");
        }
        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "customer_txn");
    }

    public List<RtgsTransactionReport> getReportForCustomerTxn(String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName) {

        List<RtgsTransactionReport> txnResponse = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(dept)) {
            dept = "%%";
        }
        if (routingType.equals(RoutingType.Outgoing.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interCustFundTransferRepository.getBeneficiaryBankList(fromDate1, toDate1, currency, status, dept, cbsName);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        var response = interCustFundTransferRepository.getReportForOutwardCustomerTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept, cbsName);
                        if (!response.isEmpty()) {
                            txnResponse.addAll(response);
                        }
                    }
                }
            } else {
                var response = interCustFundTransferRepository.getReportForOutwardCustomerTxn(fromDate1, toDate1, bank, currency, status, dept, cbsName);
                if (!response.isEmpty()) {
                    txnResponse.addAll(response);
                }
            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interCustFundTransferRepository.getPayerBankList(fromDate1, toDate1, currency, status, dept, cbsName);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        var response = interCustFundTransferRepository.getReportForInwardCustomerTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept, cbsName);
                        if (!response.isEmpty()) {
                            txnResponse.addAll(response);
                        }
                    }
                }
            } else {
                var response = interCustFundTransferRepository.getReportForInwardCustomerTxn(fromDate1, toDate1, bank, currency, status, dept, cbsName);
                if (!response.isEmpty()) {
                    txnResponse.addAll(response);
                }
            }
        }
        return txnResponse;
    }

    public ResponseEntity<byte[]> exportReportForBankTxn(
            String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName, ExportType export) throws IOException, JRException {
        var txnDetailsList = getReportForBankTxn(fromDate, toDate, bank, currency, routingType, status, dept, cbsName);
        HashMap<String, Object> extraParams = new HashMap<>();
        extraParams.put("fromDate", fromDate);
        extraParams.put("toDate", toDate);
        extraParams.put("bankName", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
        extraParams.put("fundTransferType", "Bank To Bank");
        extraParams.put("currentDate", new Date().toString());
        if (routingType == null) {
            extraParams.put("routingType", "All");
        } else {
            extraParams.put("routingType", routingType.toString());
        }

        if (txnDetailsList.size() > 0) {
            extraParams.put("currencyParam", txnDetailsList.get(0).getCurrency());
        }

        if (bank == null) {
            extraParams.put("isReportByGroup", Boolean.TRUE);
        } else {
            extraParams.put("isReportByGroup", Boolean.FALSE);
        }

        if (dept != null) {
            extraParams.put("dept", dept);
        } else {
            extraParams.put("dept", "All");
        }

        if (export == null)
            throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
        return generateJasperReport(txnDetailsList, extraParams, export, "customer_txn");
    }

    public List<RtgsTransactionReport> getReportForBankTxn(String fromDate, String toDate, String bank, String currency, String routingType, String status, String dept, String cbsName) {

        List<RtgsTransactionReport> txnResponse = new ArrayList<>();
        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }
        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }
        if (StringUtils.isBlank(dept)) {
            dept = "%%";
        }
        if (routingType.equals(RoutingType.Outgoing.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interCustFundTransferRepository.getBeneficiaryBankList(fromDate1, toDate1, currency, status, dept, cbsName);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        var response = interCustFundTransferRepository.getReportForOutwardCustomerTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept, cbsName);
                        if (!response.isEmpty()) {
                            txnResponse.addAll(response);
                        }
                    }
                }
            } else {
                var response = interCustFundTransferRepository.getReportForOutwardCustomerTxn(fromDate1, toDate1, bank, currency, status, dept, cbsName);
                if (!response.isEmpty()) {
                    txnResponse.addAll(response);
                }
            }
        }
        if (routingType.equals(RoutingType.Incoming.toString())) {
            if (StringUtils.isBlank(bank) || bank.equalsIgnoreCase("All")) {
                List<BankList> bankList = interCustFundTransferRepository.getPayerBankList(fromDate1, toDate1, currency, status, dept, cbsName);
                if (bankList.size() > 0) {
                    for (int i = 0; i < bankList.size(); i++) {
                        var response = interCustFundTransferRepository.getReportForInwardCustomerTxn(fromDate1, toDate1, bankList.get(i).getBank(), currency, status, dept, cbsName);
                        if (!txnResponse.isEmpty()) {
                            txnResponse.addAll(response);
                        }
                    }
                }
            } else {
                var response = interCustFundTransferRepository.getReportForInwardCustomerTxn(fromDate1, toDate1, bank, currency, status, dept, cbsName);
                if (!txnResponse.isEmpty()) {
                    txnResponse.addAll(response);
                }
            }
        }
        return txnResponse;
    }

    public ResponseEntity<byte[]> exportTransactionsForAcknowledgementSlip(
            String fromDate, String toDate, String reference, String payerAcc, String benAcc, String routingType, String currency, ExportType export) throws IOException, JRException {
        var txnDetailsList = getTransactionsForAcknowledgementSlip(fromDate, toDate, reference, payerAcc, benAcc, routingType, currency);
        if (txnDetailsList.size() == 0) {
            throw new BaseException("No data found");
        } else {
            List<?> data = new ArrayList<>();
            HashMap<String, Object> extraParams = new HashMap<>();
            extraParams.put("FROMDATE", fromDate);
            extraParams.put("TODATE", toDate);
            extraParams.put("routingType", routingType);
            extraParams.put("BANK_NAME", StringUtils.isNotBlank(bankService.getOwnerBank().getName()) ? bankService.getOwnerBank().getName() : "City Bank PLC");
            extraParams.put("PRINT_DATE", new Date().toString());
            if (export == null)
                throw new RuntimeException("EXPORT_TYPE_PARAMETER_IS_MISSING");
            return generateJasperReport(data, extraParams, export, "acknowledgement_slip");
        }

    }

    public List<AcknowledgementSlip> getTransactionsForAcknowledgementSlip(String fromDate, String toDate, String reference, String payerAcc, String benAcc, String routingType, String currency) {

        LocalDate fromDate1;
        LocalDate toDate1;

        if (!fromDate.equals("")) {
            fromDate1 = DateUtility.toDate(fromDate);
        } else {
            fromDate1 = LocalDate.now();
        }

        if (!toDate.equals("")) {
            toDate1 = DateUtility.toDate(toDate);
        } else {
            toDate1 = LocalDate.now();
        }

        if (StringUtils.isBlank(reference)) {
            reference = "%%";
        }
        if (StringUtils.isBlank(payerAcc)) {
            payerAcc = "%%";
        }
        if (StringUtils.isBlank(benAcc)) {
            benAcc = "%%";
        }
        if (StringUtils.isBlank(routingType)) {
            routingType = "%%";
        }
        if (StringUtils.isBlank(currency)) {
            currency = "%%";
        }
        var responses = customerFndTransferRepository.getAcknowledgementSlip(fromDate1, toDate1, reference, payerAcc, benAcc, currency, routingType);

        return responses;
    }
}
