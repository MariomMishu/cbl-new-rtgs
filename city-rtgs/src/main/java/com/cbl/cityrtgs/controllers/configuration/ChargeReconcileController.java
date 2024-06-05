package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.chargereconcilesetup.*;
import com.cbl.cityrtgs.services.configuration.ChargeReconcileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/charge-reconcile")
@RequiredArgsConstructor
public class ChargeReconcileController {
    private final ChargeReconcileService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createChargeAccountSetup(@RequestBody @Valid ChargeReconcileRequest request) {
        try {
            service.create(request);
            return ResponseHandler.
                    generateResponse("Charge Reconcile Setup has been created successfully",
                            HttpStatus.OK,
                            request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "chargeType", required = false) final ChargeType chargeType,
                                    @RequestParam(value = "chargeModule", required = false) final ChargeModule chargeModule,
                                    @RequestParam(value = "currencyName", required = false) final String currencyName,
                                    @RequestParam(value = "accountNumber", required = false) final String accountNumber,
                                    @RequestParam(value = "currencyId", required = false) final Long currencyId) {

        ChargeReconcileFilter filter = new ChargeReconcileFilter();
        filter.setChargeType(chargeType);
        filter.setChargeModule(chargeModule);
        filter.setCurrencyName(currencyName);
        filter.setAccountNumber(accountNumber);
        filter.setCurrencyId(currencyId);
        try {
            Page<ChargeReconcileResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ChargeReconcileResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateChargeReconcile(@PathVariable Long id, @RequestBody @Valid ChargeReconcileRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Charge Account Setup has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteChargeReconcile(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Charge Account Setup Deleted Successfully", HttpStatus.OK, null);
    }
}
