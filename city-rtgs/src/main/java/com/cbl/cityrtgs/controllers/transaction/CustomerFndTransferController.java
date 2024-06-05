package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.transaction.TransactionAction;
import com.cbl.cityrtgs.models.dto.transaction.TransactionResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransfer;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransferBatch;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransferBatchResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CustomerFndTransferResponse;
import com.cbl.cityrtgs.services.transaction.c2c.C2COutwardValidationService;
import com.cbl.cityrtgs.services.transaction.c2c.CustomerFundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/c2c-outward")
@RequiredArgsConstructor
public class CustomerFndTransferController {
    private final CustomerFundTransferService service;
    private final C2COutwardValidationService validationService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        CustomerFndTransferResponse response = new CustomerFndTransferResponse();
        try {
            response = service.getById(id);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, response);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Customer To customer fund transfer Deleted Successfully", HttpStatus.OK, null);
    }

    @PutMapping("/{id}/command")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> approveRejectTransaction(@PathVariable Long id, @RequestParam TransactionAction action, @RequestParam(value = "message", required = false) String message) {
        TransactionResponse response;

        if (action.equals(TransactionAction.APPROVE)) {
            response = service.executeTransaction(id);
            if (response.isError()) {
                return ResponseHandler.generateResponse(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            }
        }

        if (action.equals(TransactionAction.REJECT)) {
            response = service.rejectTransaction(id, message);
            if (response.isError()) {
                return ResponseHandler.generateResponse(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
            }
        }
        return ResponseHandler.generateResponse("Transaction " + action + " Successfully", HttpStatus.OK, null);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid CustomerFndTransfer request) {

        TransactionResponse response = service.createTxn(request);

        if (response.isError()) {
            return ResponseHandler.generateResponse(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return ResponseHandler.generateResponse("Customer To customer fund transfer has been created successfully", HttpStatus.OK, response.getBody());
    }

    @PostMapping("/batchTxn")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid CustomerFndTransferBatch request) {

        TransactionResponse response = service.createBatchTxn(request);

        if (response.isError()) {
            return ResponseHandler.generateResponse(response.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return ResponseHandler.generateResponse("Customer To customer fund transfer has been created successfully", HttpStatus.OK, response.getBody());
    }


    @PutMapping("/inward/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> returnPacs008Inward(@PathVariable Long id) {
        boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.returnCustomerFndTransfer(id);
        return ResponseHandler.generateResponse("Customer To customer Inward Returned Successfully", HttpStatus.OK, null);
    }

    @PutMapping("/edit-acc-no/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateBenAccNo(@PathVariable Long id, @RequestParam String benAccNo) {
        try {
            boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateBenAccNo(id, benAccNo);
            return ResponseHandler.generateResponse("Beneficiary Account has been updated successfully", HttpStatus.OK, benAccNo);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PostMapping("/batch-validation")
    public APIResponse validationCheck(@RequestBody @Valid CustomerFndTransferBatch request) {

        CustomerFndTransferBatchResponse response;

        response = validationService.validateBatchTxn(request);
        if (response.isError()) {
            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message(response.getMessage())
                    .body(response)
                    .build();

        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Customer To Customer Batch Txn Validation Successful")
                .body(response)
                .build();

    }


    @PutMapping("/{id}")
    public APIResponse updateTxn(@PathVariable Long id, @RequestBody @Valid CustomerFndTransfer request) {

        TransactionResponse response = service.updateTxn(id, request);

        if (response.isError()) {
            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message(response.getMessage())
                    .body(response)
                    .build();

        }
        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Customer To customer fund transfer has been Updated successfully")
                .body(response.getBody())
                .build();
    }

}
