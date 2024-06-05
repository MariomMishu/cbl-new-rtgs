package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupFilter;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupRequest;
import com.cbl.cityrtgs.models.dto.configuration.exemptionsetup.ExemptionSetupResponse;
import com.cbl.cityrtgs.services.configuration.ExemptionChargeSetupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/exemption-setup")
@RequiredArgsConstructor
public class ExemptionListController {
    private final ExemptionChargeSetupService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createExemptionSetup(@RequestBody @Valid ExemptionSetupRequest request) {
        try {
            service.createExemptionSetup(request);
            return ResponseHandler.
                    generateResponse("Exemption Setup has been created successfully",
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
                                    @RequestParam(value = "accountNumber", required = false) final String accountNumber,
                                    @RequestParam(value = "accountName", required = false) final String accountName) {
        ExemptionSetupFilter filter = new ExemptionSetupFilter();
        filter.setAccountName(accountName);
        filter.setAccountNumber(accountNumber);
        try {
            Page<ExemptionSetupResponse> responseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable Long id) {
        ExemptionSetupResponse response = service.getById(id);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> updateExemptionSetup(@PathVariable Long id, @RequestBody @Valid ExemptionSetupRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse("Exemption Setup has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteExemptionSetup(@PathVariable Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse("Exemption Setup Deleted Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/report")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllExemptionList( @RequestParam(value = "accountNumber", required = false) final String accountNumber,
                                    @RequestParam(value = "accountName", required = false) final String accountName) {
        List<ExemptionSetupResponse> responseList= new ArrayList<>();
        ExemptionSetupFilter filter = new ExemptionSetupFilter();
        filter.setAccountName(accountName);
        filter.setAccountNumber(accountNumber);
        try {
            responseList = service.getAllExemptionList(filter);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, responseList);
        }
    }
}
