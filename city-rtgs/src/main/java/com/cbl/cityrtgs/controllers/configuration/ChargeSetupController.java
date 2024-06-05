package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.chargeaccountsetup.ChargeSetupResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.PayerDetailsResponse;
import com.cbl.cityrtgs.services.configuration.ChargeAccountSetupService;
import com.cbl.cityrtgs.services.transaction.CustomerAccountDetailsService;
import com.cbl.cityrtgs.services.transaction.c2c.C2COutwardValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/charge-setup")
@RequiredArgsConstructor
public class ChargeSetupController {
    private final ChargeAccountSetupService service;
    private final C2COutwardValidationService c2COutwardValidationService;
    private final CustomerAccountDetailsService customerAccountDetailsService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createChargeSetup(@RequestBody @Valid ChargeSetupRequest request) {
        try {
            service.createChargeSetup(request);
            return ResponseHandler.
                    generateResponse("Charge Setup has been created successfully",
                            HttpStatus.OK,
                            request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll() {
        List<ChargeSetupResponse> chargeSetupListResp = service.getAll();
        try {
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, chargeSetupListResp);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ChargeSetupResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateChargeSetup(@PathVariable Long id, @RequestBody @Valid ChargeSetupRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Charge Setup has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteChargeSetup(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Charge Setup Deleted Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/currency/{currencyId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getByCurrencyId(@PathVariable Long currencyId) {
        ChargeSetupResponse response = service.getChargeSetupResponseByCurrency(currencyId);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/get-frm-amt")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> chargeVatCalculation(
            @RequestParam(value = "currencyId") final Long currencyId) {
        BigDecimal response = BigDecimal.ZERO;
        try {
            response = service.calculateFromAmt(currencyId);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, response);
        }

    }

    @GetMapping("/get-charge-vat")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> chargeVatCalculation(
            @RequestParam(value = "payerAccNo") final String payerAccNo,
            @RequestParam(value = "currencyId") final Long currencyId,
            @RequestParam(value = "amount") final BigDecimal amount,
            @RequestParam(value = "transactionTypeCode") final String transactionTypeCode) {
        ChargeSetupResponse response = new ChargeSetupResponse();
        try {
            PayerDetailsResponse payerDetailsResponse = customerAccountDetailsService.getAccountDetails(payerAccNo);
            response = c2COutwardValidationService.chargeVatCalculation(payerAccNo, currencyId, amount, transactionTypeCode, payerDetailsResponse.getSchemeCode());
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, response);
        }
    }
}
