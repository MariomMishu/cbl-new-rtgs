package com.cbl.cityrtgs.controllers.reconcile;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.reconcile.ReconcileRequest;
import com.cbl.cityrtgs.models.dto.reconcile.DeptAccReconcileResponse;
import com.cbl.cityrtgs.models.dto.reconcile.ReconcileResponse;
import com.cbl.cityrtgs.models.dto.transaction.ApprovalEventResponse;
import com.cbl.cityrtgs.services.reconcile.ReconcileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/reconcile")
@RequiredArgsConstructor
public class ReconcileController {

    private final ReconcileService service;

    @PostMapping("/department-account-wise-txn-amount")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> getDeptAccountTxnAmount(@RequestBody ReconcileRequest request) {
        DeptAccReconcileResponse response = new DeptAccReconcileResponse();
        try {
            response = service.getDeptAccountWiseTxnAmount(request);
            return ResponseHandler.generateResponse(
                    "Department Account Reconcile",
                    HttpStatus.OK,
                    response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    response);
        }
    }

    @PostMapping("/department-account-settlement")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> doDepartmentAccountSettlementTxn(@RequestBody ReconcileRequest request) {
        try {
            service.doDepartmentAccountSettlementTxn(request);
            return ResponseHandler.generateResponse(
                    "Department Account Reconcile Successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }

    @PostMapping("/department-charge-settlement")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> doDepartmentChargeSettlementTxn(@RequestBody ReconcileRequest request) {
        try {
            service.doDepartmentChargeSettlementTxn(request);
            return ResponseHandler.generateResponse(
                    "Department Account Charge Reconcile Successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }

    @PostMapping("/department-vat-settlement")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> doDepartmentVatSettlementTxn(@RequestBody ReconcileRequest request) {
        try {
            service.doDepartmentVatSettlementTxn(request);
            return ResponseHandler.generateResponse(
                    "Department Account Vat Reconcile Successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> getFinalReconciledList(@RequestBody ReconcileRequest request) {
        List<ReconcileResponse> response = new ArrayList<>();
        try {
            response = service.getSettlementTxnList(request.getAccountNumber());
            return ResponseHandler.generateResponse(
                    "Get Final Reconciled and Unreconciled List ",
                    HttpStatus.OK,
                    response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    response);
        }
    }

    @PostMapping("/returnsettlementbalance")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> returnSettlementBalance(@RequestParam String accountNo) {
        try {
            service.returnSettlementBalance(accountNo);
            return ResponseHandler.generateResponse(
                    "Settlement Account Reconcile Successfully",
                    HttpStatus.OK,
                    accountNo);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }

    @GetMapping("/pendingfndtransfers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllPendingFundTransfers() {
        List<ApprovalEventResponse> responseList= new ArrayList<>();
        try {
            responseList = service.getAllPendingFundTransfers();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, responseList);
        }
    }

    @GetMapping("/pendingfndtransfers/reject")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> rejectAllPendingFundTransfers() {
        try {
            service.rejectAllPendingFundTransfers();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, null);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

}
