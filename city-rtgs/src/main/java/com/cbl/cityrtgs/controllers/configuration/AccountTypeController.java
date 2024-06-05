package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.*;
import com.cbl.cityrtgs.models.dto.configuration.settlementaccount.CBSAccountTypes;
import com.cbl.cityrtgs.services.configuration.AccountTypeService;
import com.cbl.cityrtgs.services.configuration.CBSAccountTypesService;
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
@RequestMapping("/account-type")
@RequiredArgsConstructor
public class AccountTypeController {
    private final AccountTypeService service;
    private final CBSAccountTypesService cbsAccountTypesService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createAccountType(@RequestBody @Valid AccountTypeRequest request) {
        try {
            service.createAccountType(request);
            return ResponseHandler.
                    generateResponse("Account Type has been created successfully",
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
                                    @RequestParam(value = "accountingType", required = false) final AccountingType accountingType,
                                    @RequestParam(value = "code", required = false) final String code,
                                    @RequestParam(value = "cbsAccountNumber", required = false) final String cbsAccountNumber,
                                    @RequestParam(value = "cbsName", required = false) final CbsName cbsName,
                                    @RequestParam(value = "cbsAccountType", required = false) final Long cbsAccountType) {
        AccountTypeFilter filter = new AccountTypeFilter();
        filter.setCode(code);
        filter.setAccountingType(accountingType);
        filter.setCbsAccountNumber(cbsAccountNumber);
        filter.setCbsName(cbsName);
        filter.setCbsAccountType(cbsAccountType);
        try {
            Page<AccountTypeResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        AccountTypeResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateAccountType(@PathVariable Long id, @RequestBody @Valid AccountTypeRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Account Type has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteAccountType(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Account Type Deleted Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/cbs-type")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll() {

        try {
            List<CBSAccountTypes> responseList = cbsAccountTypesService.getAll();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }
}
