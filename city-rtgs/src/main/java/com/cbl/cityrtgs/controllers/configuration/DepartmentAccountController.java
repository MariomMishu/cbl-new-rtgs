package com.cbl.cityrtgs.controllers.configuration;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountFilter;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountRequest;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.DepartmentAccountResponse;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.services.configuration.DepartmentAccountService;
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
@RequestMapping("/department-account")
@RequiredArgsConstructor
public class DepartmentAccountController {

    private final DepartmentAccountService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> createDepartment(@RequestBody @Valid DepartmentAccountRequest request) {
        try {
            service.createDepartmentAcc(request);
            return ResponseHandler.generateResponse(
                    "Department Account has been created successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "dept", required = false) final String dept,
                                    @RequestParam(value = "currency", required = false) final String currency,
                                    @RequestParam(value = "accountNumber", required = false) final String accountNumber,
                                    @RequestParam(value = "chargeAccNumber", required = false) final String chargeAccNumber,
                                    @RequestParam(value = "vatAccNumber", required = false) final String vatAccNumber,
                                    @RequestParam(value = "routingType", required = false) final RoutingType routingType) {
        DepartmentAccountFilter filter = new DepartmentAccountFilter();
        filter.setAccountNumber(accountNumber);
        filter.setChargeAccNumber(chargeAccNumber);
        filter.setVatAccNumber(vatAccNumber);
        filter.setRoutingType(routingType);
        filter.setDept(dept);
        filter.setCurrency(currency);
        try {
            Page<DepartmentAccountResponse> departmentResponseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    departmentResponseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long deptId) {
        DepartmentAccountResponse response = service.getById(deptId);
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> updateDepartmentAcc(@PathVariable Long id, @RequestBody @Valid DepartmentAccountRequest request) {
        try {
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, request);
            return ResponseHandler.generateResponse(
                    "Department Account has been updated successfully",
                    HttpStatus.OK,
                    request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @PutMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> deleteDepartmentAcc(Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse(
                "Department Account Deleted Successfully",
                HttpStatus.OK,
                null);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getDepartmentAcc(@RequestParam(value = "deptId") final Long deptId,
                                              @RequestParam(value = "currencyId") final Long currencyId,
                                              @RequestParam(value = "routingType") final RoutingType routingType) {
        DepartmentAccountResponse response = service.getDepartmentAcc(deptId, currencyId, routingType);
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }

}
