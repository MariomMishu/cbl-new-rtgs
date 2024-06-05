package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.*;
import com.cbl.cityrtgs.services.transaction.ApprovalEventService;
import com.cbl.cityrtgs.services.transaction.IncomingFundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ApprovalEventController {
    private final ApprovalEventService service;
    private final IncomingFundTransferService incomingFundTransferService;

    @GetMapping("/approvaleventlog")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllForApproveOrReject() {
       // List<ApprovalEventResponse> responseList = new ArrayList<>();
        List<IApprovalEventResponse> responseList;
        try {
            responseList = service.getAll();
            if(responseList.isEmpty()){
                return ResponseHandler.generateResponse("No Data Found", HttpStatus.NOT_FOUND, null);
            }else{
                return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
            }

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/b2b/approvaleventlog")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllB2BTxnForApproveOrReject() {
        List<IApprovalEventResponse> responseList;
        try {
            responseList = service.getAllTxnForB2B();
            if(responseList.isEmpty()){
                return ResponseHandler.generateResponse("No Data Found", HttpStatus.NOT_FOUND, null);
            }else{
                return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
            }

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/c2c/approvaleventlog")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllC2CTxnForApproveOrReject() {
        List<IApprovalEventResponse> responseList;
        try {
            responseList = service.getAllTxnForC2C();
            if(responseList.isEmpty()){
                return ResponseHandler.generateResponse("No Data Found", HttpStatus.NOT_FOUND, null);
            }else{
                return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
            }

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }
    @GetMapping("/rejected/txn")
    public APIResponse getAllRejectedOutwardTransactions() {

        Map<String, Object> responses = service.getAllRejectedOutwardTransactions();

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<RejectedOutwardTransactions> txnReports = (List<RejectedOutwardTransactions>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/confirmed/txn")
    public APIResponse getAllConfirmedInwardTransactions(@RequestParam(value = "fromDate", required = false) String fromDate,
                                                          @RequestParam(value = "toDate", required = false) String toDate,
                                                          @RequestParam(value = "reference", required = false) String reference) {

        Map<String, Object> responses = service.getAllConfirmedInwardTransactions(fromDate, toDate, reference);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<ConfirmedInwardTransactions> txnReports = (List<ConfirmedInwardTransactions>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @GetMapping("/pending/txns")
    public APIResponse getAllPendingOutwardTransactions(@RequestParam(value = "fromDate", required = false) String fromDate,
                                                        @RequestParam(value = "toDate", required = false) String toDate,
                                                        @RequestParam(value = "reference", required = false) String reference) {

        Map<String, Object> responses = service.getAllPendingOutwardTransactions(fromDate, toDate, reference);

        if (responses.isEmpty()) {

            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message("No data found")
                    .body(responses)
                    .build();
        }

        List<PendingOutwardTransactions> txnReports = (List<PendingOutwardTransactions>) responses.get("result");

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(txnReports.size() + " Data Found!")
                .body(responses)
                .build();
    }

    @PostMapping("/manual-return")
    public APIResponse sendTransactionReturn(@RequestBody @Valid ManualTxnReturn manualTxnReturn) {
        ResponseDTO response =  incomingFundTransferService.sendTransactionReturn(manualTxnReturn);
        if(response.isError()){
            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Transaction Return Message Sent Successfully")
                .build();
    }

    @PostMapping("/txn-cancel")
    public APIResponse cancelTransaction(@RequestBody @Valid CancelTxn cancelTxn) {
        ResponseDTO response =  incomingFundTransferService.cancelTransaction(cancelTxn);
        if(response.isError()){
            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Cancel Request Sent Successfully")
                .build();
    }

}
