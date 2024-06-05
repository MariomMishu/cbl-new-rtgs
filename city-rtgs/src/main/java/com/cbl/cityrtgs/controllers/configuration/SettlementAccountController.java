package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountFilter;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.SettlementAccountResponse;
import com.cbl.cityrtgs.services.configuration.SettlementAccountService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/settlement-account")
@RequiredArgsConstructor
public class SettlementAccountController {
    private final SettlementAccountService service;
    protected String NAME = "[0-9a-zA-Z/\\-\\?:\\(\\)\\.\\n\\r,'\\+ ]{1,50}";
    protected String CODE = "^[\\d]{1,17}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createSettlementAccount(@RequestBody @Valid SettlementAccountRequest request) {
        try {

            if (!ValidationUtility.valid(this.CODE, request.getCode())) {
                return ResponseHandler.generateResponse(
                        "Code accepts only numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            if (!ValidationUtility.valid(this.NAME, request.getName())) {
                return ResponseHandler.generateResponse(
                        "Name accepts Alpha numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            service.createSettlementAccount(request);
            return ResponseHandler.
                    generateResponse("Settlement Account has been created successfully",
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
                                    @RequestParam(value = "code", required = false) final String code,
                                    @RequestParam(value = "name", required = false) final String name) {

        SettlementAccountFilter filter = new SettlementAccountFilter();
        filter.setCode(code);
        filter.setName(name);

        try {
            Page<SettlementAccountResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        SettlementAccountResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateSettlementAccount(@PathVariable Long id, @RequestBody @Valid SettlementAccountRequest request) {
        try {
            if (!ValidationUtility.valid(this.CODE, request.getCode())) {
                return ResponseHandler.generateResponse(
                        "Code accepts only numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            if (!ValidationUtility.valid(this.NAME, request.getName())) {
                return ResponseHandler.generateResponse(
                        "Name accepts Alpha numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Settlement Account has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteSettlementAccount(Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Settlement Account Deleted Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/currency/{currencyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getByCurrencyId(@PathVariable(value = "currencyId") Long currencyId) {
        SettlementAccountResponse response = service.getEntityByCurrencyId(currencyId);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSettlementAccList() {
        List<SettlementAccountResponse> response = service.getSettlementAccList();
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }
}
