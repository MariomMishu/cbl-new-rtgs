package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.TransactionAction;
import com.cbl.cityrtgs.models.dto.transaction.TransactionResponse;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferBatch;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferBatchResponse;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferRequest;
import com.cbl.cityrtgs.models.dto.transaction.b2b.BankFundTransferResponse;
import com.cbl.cityrtgs.services.transaction.b2b.B2BOutwardValidationService;
import com.cbl.cityrtgs.services.transaction.b2b.BankFundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/b2b-outward")
@RequiredArgsConstructor
public class BankFndTransferController {
    private final B2BOutwardValidationService validationService;
    private final BankFundTransferService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse create(@RequestBody @Valid BankFundTransferRequest request) {
        BankFundTransferResponse response = service.create(request);
        if (response.isError()) {
            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(response.getErrorMessage())
                    .body(response)
                    .build();
        }
        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Bank To Bank fund transfer has been created successfully")
                .body(response.getParentBatchNumber())
                .build();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        BankFundTransferResponse response;
        response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}/command")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse approveRejectTransaction(@PathVariable Long id, @RequestParam TransactionAction action, @RequestParam(value = "message", required = false) String message) {
        ResponseDTO response = new ResponseDTO();

        if (action.equals(TransactionAction.APPROVE)) {
            response = service.approveBankFndTransfer(id);
            if (response.isError()) {
                return APIResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(500)
                        .message(response.getMessage())
                        .build();
            }
        }

        if (action.equals(TransactionAction.REJECT)) {
            response = service.rejectTransaction(id, message);
            if (response.isError()) {
                return APIResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(500)
                        .message(response.getMessage())
                        .build();
            }
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message(response.getMessage())
                .build();
    }

    @PutMapping("/{id}")
    public APIResponse updateTxn(@PathVariable Long id, @RequestBody @Valid BankFundTransferRequest request) {

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
                .message("Bank To Bank fund transfer has been updated successfully")
                .body(response.getBody())
                .build();
    }

    @PostMapping("/batch-upload")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse b2bValidation(@RequestBody @Valid BankFundTransferBatch request) {
        BankFundTransferBatchResponse response;
        response = validationService.batchValidation(request);

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
                .message("Bank To Bank Batch Txn Validation Successful")
                .body(response)
                .build();
    }

    @PostMapping("/batch")
    @ResponseStatus(HttpStatus.CREATED)
    public APIResponse createBatchTxn(@RequestBody @Valid BankFundTransferRequest request) {
        BankFundTransferBatchResponse response;
        response = service.createBatch(request);
        if (response.isError()) {
            return APIResponse.builder()
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .statusCode(500)
                    .message(response.getMessage())
                    .body(response)
                    .build();

        }
        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Fund Transfer successfully send for verification")
                .body(response)
                .build();
    }
}
