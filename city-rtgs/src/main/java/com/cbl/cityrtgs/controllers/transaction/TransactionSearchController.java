package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.projection.BankFundTransferOutwardProjection;
import com.cbl.cityrtgs.models.dto.projection.BankFundTransferProjection;
import com.cbl.cityrtgs.models.dto.transaction.TransactionSearchFilter;
import com.cbl.cityrtgs.models.dto.transaction.c2c.C2CTxnTransactionResponse;
import com.cbl.cityrtgs.services.transaction.TransactionSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/search")
@RequiredArgsConstructor
public class TransactionSearchController {

    private final TransactionSearchService service;

    @GetMapping("/bank/inward-txn")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllB2BInward(@RequestParam(value = "bank", required = false) final String bank,
                                             @RequestParam(value = "transactionStatus", required = false) final String transactionStatus,
                                             @RequestParam(value = "voucher", required = false) final String voucher,
                                             @RequestParam(value = "reference", required = false) final String reference,
                                             @RequestParam(value = "batchNumber", required = false) final String batchNumber,
                                             @RequestParam(value = "currency", required = false) final String currency,
                                             @RequestParam(value = "fromDate", required = false) final String fromDate,
                                             @RequestParam(value = "toDate", required = false) final String toDate) {
        TransactionSearchFilter filter = new TransactionSearchFilter();
        filter.setBank(bank);
        filter.setRoutingType(RoutingType.Incoming);
        filter.setCurrency(currency);
        filter.setTransactionStatus(transactionStatus);
        filter.setVoucher(voucher);
        filter.setReference(reference);
        filter.setBatchNumber(batchNumber);
        filter.setFromDate(fromDate);
        filter.setToDate(toDate);
        try {
            List<BankFundTransferProjection> transactionList = service.getAllBankTxn(filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    transactionList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/bank/outward-txn")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllB2BOutward(@RequestParam(value = "bank", required = false) final String bank,
                                              @RequestParam(value = "transactionStatus", required = false) final String transactionStatus,
                                              @RequestParam(value = "voucher", required = false) final String voucher,
                                              @RequestParam(value = "reference", required = false) final String reference,
                                              @RequestParam(value = "batchNumber", required = false) final String batchNumber,
                                              @RequestParam(value = "currency", required = false) final String currency,
                                              @RequestParam(value = "dept", required = false) final String dept,
                                              @RequestParam(value = "fromDate", required = false) final String fromDate,
                                              @RequestParam(value = "toDate", required = false) final String toDate) {
        TransactionSearchFilter filter = new TransactionSearchFilter();
        filter.setRoutingType(RoutingType.Outgoing);
        filter.setBank(bank);
        filter.setCurrency(currency);
        filter.setDept(dept);
        filter.setTransactionStatus(transactionStatus);
        filter.setVoucher(voucher);
        filter.setReference(reference);
        filter.setBatchNumber(batchNumber);
        filter.setFromDate(fromDate);
        filter.setToDate(toDate);

        try {
            List<BankFundTransferOutwardProjection> transactionList = service.getAllBankTxnOutward(filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    transactionList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/customer/inward-txn")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllC2CInward(@RequestParam(value = "bank", required = false) final String bank,
                                             @RequestParam(value = "transactionStatus", required = false) final String transactionStatus,
                                             @RequestParam(value = "voucher", required = false) final String voucher,
                                             @RequestParam(value = "reference", required = false) final String reference,
                                             @RequestParam(value = "batchNumber", required = false) final String batchNumber,
                                             @RequestParam(value = "payerAccount", required = false) final String payerAccount,
                                             @RequestParam(value = "benAccount", required = false) final String benAccount,
                                             @RequestParam(value = "currency", required = false) final String currency,
                                             @RequestParam(value = "fromDate", required = false) final String fromDate,
                                             @RequestParam(value = "toDate", required = false) final String toDate) {

        try {
            List<C2CTxnTransactionResponse> transactionList = service.getAllInwardCustomerTxn(fromDate, toDate, bank, currency, transactionStatus, voucher, reference, batchNumber, payerAccount, benAccount);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    transactionList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/customer/outward-txn")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllC2COutward(@RequestParam(value = "bank", required = false) final String bank,
                                              @RequestParam(value = "transactionStatus", required = false) final String transactionStatus,
                                              @RequestParam(value = "voucher", required = false) final String voucher,
                                              @RequestParam(value = "reference", required = false) final String reference,
                                              @RequestParam(value = "batchNumber", required = false) final String batchNumber,
                                              @RequestParam(value = "payerAccount", required = false) final String payerAccount,
                                              @RequestParam(value = "benAccount", required = false) final String benAccount,
                                              @RequestParam(value = "currency", required = false) final String currency,
                                              @RequestParam(value = "dept", required = false) final String dept,
                                              @RequestParam(value = "fromDate", required = false) final String fromDate,
                                              @RequestParam(value = "toDate", required = false) final String toDate) {

        try {
            List<C2CTxnTransactionResponse> transactionList = service.getAllOutwardCustomerTxn(fromDate, toDate, bank, currency, dept, transactionStatus, voucher, reference, batchNumber, payerAccount, benAccount);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    transactionList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

}
