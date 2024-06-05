package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.PriorityCodeResponse;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionPriorityType;
import com.cbl.cityrtgs.models.dto.configuration.transactionpriority.TransactionTypeCodeResponse;
import com.cbl.cityrtgs.services.configuration.TransactionPriorityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/txn-priority")
@RequiredArgsConstructor
public class TransactionPriorityController {

    private final TransactionPriorityService service;

    @GetMapping("/txn-code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getTransactionTypeCodeList() {

        try {
            List<TransactionTypeCodeResponse> responseList = service.getTransactionTypeCodeList();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/priority-code/{type}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getPriorityCodeList(@PathVariable TransactionPriorityType type) {
        try {
            List<PriorityCodeResponse> responseList = service.getPriorityCodeList(type);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/priority-type/{code}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getPriorityType(@PathVariable String code) {
        try {
            PriorityCodeResponse response = service.getPriorityTypeByCode(code);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
    @GetMapping("/si-txn-code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSITransactionTypeCodeList() {

        try {
            List<TransactionTypeCodeResponse> responseList = service.getSITransactionTypeCodeList();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
