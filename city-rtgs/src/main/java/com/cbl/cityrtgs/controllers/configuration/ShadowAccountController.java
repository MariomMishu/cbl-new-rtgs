package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountFilter;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.shadowaccount.ShadowAccountResponse;
import com.cbl.cityrtgs.services.configuration.ShadowAccountService;
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

@Slf4j
@RestController
@RequestMapping("/shadow-account")
@RequiredArgsConstructor
public class ShadowAccountController {
    private final ShadowAccountService service;
    protected String CODE = "^[\\d]{1,17}";
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createShadowAccount(@RequestBody @Valid ShadowAccountRequest request) {
        try {
            if (!ValidationUtility.valid(this.CODE, request.getRtgsSettlementAccount())) {
                return ResponseHandler.generateResponse(
                        "RTGS Settlement Account should be numeric",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }

            if (!ValidationUtility.valid(this.CODE, request.getOutgoingGl())) {
                return ResponseHandler.generateResponse(
                        "Outgoing GL should be numeric",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }

            if (!ValidationUtility.valid(this.CODE, request.getIncomingGl())) {
                return ResponseHandler.generateResponse(
                        "Incoming GL should be numeric",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            service.createShadowAccount(request);
            return ResponseHandler.
                    generateResponse("Shadow Account has been created successfully",
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
                                    @RequestParam(value = "rtgsSettlementAccount", required = false) final String rtgsSettlementAccount,
                                    @RequestParam(value = "incomingGl", required = false) final String incomingGl,
                                    @RequestParam(value = "outgoingGl", required = false) final String outgoingGl,
                                    @RequestParam(value = "bank", required = false) final String bank,
                                    @RequestParam(value = "currency", required = false) final String currency) {
        ShadowAccountFilter filter = new ShadowAccountFilter();
        filter.setRtgsSettlementAccount(rtgsSettlementAccount);
        filter.setIncomingGl(incomingGl);
        filter.setOutgoingGl(outgoingGl);
        filter.setBank(bank);
        filter.setCurrency(currency);
        try {
            Page<ShadowAccountResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ShadowAccountResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateShadowAccount(@PathVariable Long id, @RequestBody @Valid ShadowAccountRequest request) {
        try {
            if (!ValidationUtility.valid(this.CODE, request.getRtgsSettlementAccount())) {
                return ResponseHandler.generateResponse(
                        "RTGS Settlement Account accepts only numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }

            if (!ValidationUtility.valid(this.CODE, request.getOutgoingGl())) {
                return ResponseHandler.generateResponse(
                        "Outgoing GL  Account accepts only numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }

            if (!ValidationUtility.valid(this.CODE, request.getIncomingGl())) {
                return ResponseHandler.generateResponse(
                        "Incoming GL accepts only numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Shadow Account has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteShadowAccount(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Shadow Account Deleted Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/details")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getShadowAcc(@RequestParam(value = "bankId") final Long bankId,
                                          @RequestParam(value = "currencyId") final Long currencyId) {
        ShadowAccountResponse response = service.getShadowAcc(bankId, currencyId);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }
}
