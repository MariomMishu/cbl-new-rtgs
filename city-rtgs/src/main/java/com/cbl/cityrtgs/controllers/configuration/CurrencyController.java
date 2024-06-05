package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyRequest;
import com.cbl.cityrtgs.models.dto.configuration.currency.CurrencyResponse;
import com.cbl.cityrtgs.services.configuration.CurrencyService;
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
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/currency")
@RequiredArgsConstructor
public class CurrencyController {
    private final CurrencyService service;
    protected String CODE = "[a-zA-Z/\\-\\?:\\(\\)\\.\\n\\r,'\\+ ]{1,3}";

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createCurrency(@RequestBody @Valid CurrencyRequest request) {
        try {
            if (request.getShortCode() != null && !request.getShortCode().trim().isEmpty()) {
                if (!ValidationUtility.valid(this.CODE, request.getShortCode())) {
                    return ResponseHandler.generateResponse(
                            "Short Code should be 3 Digit Alphabets",
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null);
                }
            }
            service.createCurrency(request);
            return ResponseHandler.
                    generateResponse("Currency has been created successfully",
                            HttpStatus.OK,
                            request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable, @RequestParam(value = "unPaged", required = false) final boolean unPaged) {
        try {
            Page<CurrencyResponse> currencyResponseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, currencyResponseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long currencyId) {
        try {
            CurrencyResponse response = service.getById(currencyId);
            if (Objects.nonNull(response)) {
                return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
            } else {
                return ResponseHandler.generateResponse("Currency Not Found", HttpStatus.NOT_FOUND, null);
            }
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateCurrency(@PathVariable Long id, @RequestBody @Valid CurrencyRequest request) {
        try {
            if (request.getShortCode() != null && !request.getShortCode().trim().isEmpty()) {
                if (!ValidationUtility.valid(this.CODE, request.getShortCode())) {
                    return ResponseHandler.generateResponse(
                            "Short Code should be 3 Digit Alphabets",
                            HttpStatus.INTERNAL_SERVER_ERROR,
                            null);
                }
            }
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Currency has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteCurrency(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Currency Deleted Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/list")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCurrencyList() {
        List<CurrencyResponse> response = service.getCurrencyList();
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }
}

