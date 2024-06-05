package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration.TxnCfgSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.outwardTransactionConfiguration.TxnCfgSetupResponse;
import com.cbl.cityrtgs.services.configuration.TxnCfgSetupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.LocalTime;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/txn-cfg-setup")
@RequiredArgsConstructor
public class TxnCfgSetupController {
    private final TxnCfgSetupService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createTxnCfgSetup(@RequestBody @Valid TxnCfgSetupRequest request) {
        try {

            if (request.getTimeRestricted()) {
                LocalTime now = LocalTime.now();
                LocalTime start = new LocalTime(request.getStartTime());
                LocalTime end = new LocalTime(request.getEndTime());
                if (now.isAfter(end) && now.isBefore(start)) {
                    return ResponseHandler.generateResponse("End Time is Greater then Start Time", HttpStatus.INTERNAL_SERVER_ERROR, request);
                }
            }

            service.createTxnCfgSetup(request);
            return ResponseHandler.
                    generateResponse("Outward Transaction Configuration Setup has been created successfully",
                            HttpStatus.OK,
                            request);

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged) {
        try {
            Page<TxnCfgSetupResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        TxnCfgSetupResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateTxnCfgSetup(@PathVariable Long id, @RequestBody @Valid TxnCfgSetupRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Outward Transaction Configuration Setup has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteTxnCfgSetup(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Outward Transaction Configuration Setup Deleted Successfully", HttpStatus.OK, null);
    }
}
