package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.message.ReturnReason;
import com.cbl.cityrtgs.services.transaction.ReturnReasonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/returnreason")
@RequiredArgsConstructor
public class ReturnReasonController {
    private final ReturnReasonService service;

    @GetMapping("/returncodes")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll() {
        try {
            List<ReturnReason> returnCodeList = service.getAllList();
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    returnCodeList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/code")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> honourReturnInward(@RequestParam String returnCode) {
        try {
            ReturnReason returnReason = service.getReturnReasonByCode(returnCode);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    returnReason);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

}
