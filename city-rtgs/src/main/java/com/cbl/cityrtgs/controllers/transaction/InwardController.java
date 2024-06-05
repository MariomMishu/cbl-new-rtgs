package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.InwardFundTransferActionStatus;
import com.cbl.cityrtgs.models.dto.transaction.c2c.IncomingFundTransferResponse;
import com.cbl.cityrtgs.services.transaction.IncomingFundTransferService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/inward")
@RequiredArgsConstructor
public class InwardController {
    private final IncomingFundTransferService service;

    @GetMapping("/pendingfundtransfers")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll() {
        List<IncomingFundTransferResponse> getIncomingFundTransferList = new ArrayList<>();
        try {
            getIncomingFundTransferList = service.getAllIncomingPending();
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    getIncomingFundTransferList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    getIncomingFundTransferList);
        }
    }

    @PutMapping("/{id}/command")
    @ResponseStatus(HttpStatus.OK)
    public APIResponse honourReturnInward(@PathVariable Long id, @RequestParam FundTransferType fundTransferType, @RequestParam InwardFundTransferActionStatus action, String returnCode) {
        ResponseDTO response = new ResponseDTO();

        if (action.equals(InwardFundTransferActionStatus.HONOUR)) {
            response = service.honourInwardTransaction(id, fundTransferType);
            if (response.isError()) {
                return APIResponse.builder()
                        .status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .statusCode(500)
                        .message(response.getMessage())
                        .build();
            }
        }

        if (action.equals(InwardFundTransferActionStatus.RETURN)) {
            response = service.returnInwardTransaction(id, fundTransferType, returnCode);
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

    @PostMapping("/handle-inward")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> handleInward(@RequestParam String requestMessage) {
        try {
            //schedulerService.initiateInwardScheduler();
            service.handleInward(requestMessage);
            return ResponseHandler.generateResponse("Inward Transaction handle Successfully", HttpStatus.OK, null);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }


    @GetMapping("/incomingrejected")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllRejectedList() {
        List<IncomingFundTransferResponse> getIncomingFundTransferList = new ArrayList<>();
        try {
            getIncomingFundTransferList = service.getAllIncomingRejected();
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    getIncomingFundTransferList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    getIncomingFundTransferList);
        }
    }

}
