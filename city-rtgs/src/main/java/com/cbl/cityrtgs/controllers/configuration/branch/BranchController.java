package com.cbl.cityrtgs.controllers.configuration.branch;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchFilter;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchRequest;
import com.cbl.cityrtgs.models.dto.configuration.branch.BranchResponse;
import com.cbl.cityrtgs.services.configuration.BranchService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BranchController implements BranchApi {
    private final BranchService service;
    public String ROUTING= "^[\\d]{1,9}";
    @Override
    public ResponseEntity<?> getAllBranch(Pageable pageable, boolean unPaged, String routingNumber, String address, String name) {
        BranchFilter filter = new BranchFilter();
        filter.setRoutingNumber(routingNumber);
        filter.setAddress(address);
        filter.setName(name);
        try {
            Page<BranchResponse> branchResponseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    branchResponseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NO_CONTENT,
                    null);
        }
    }

    @Override
    @PostMapping
    public ResponseEntity<?> createBranch(BranchRequest createRequest) {
        try {
            if (!ValidationUtility.valid(this.ROUTING, createRequest.getRoutingNumber())) {
                return ResponseHandler.generateResponse(
                        "Routing number should be 9 digit numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            service.createBranch(createRequest);
            return ResponseHandler.generateResponse(
                    "Bank has been created successfully",
                    HttpStatus.OK,
                    createRequest);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NO_CONTENT,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getBranch(Long id) {
        BranchResponse response = service.getBranchById(id);
        return ResponseHandler.generateResponse(
                "Request process Successfully",
                HttpStatus.OK,
                response);
    }

    @Override
    public ResponseEntity<?> updateBranch(Long id, BranchRequest updateRequest) {
        try {
            if (!ValidationUtility.valid(this.ROUTING, updateRequest.getRoutingNumber())) {
                return ResponseHandler.generateResponse(
                        "Routing number should be 9 digit numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            service.updateOne(id, updateRequest);
            return ResponseHandler.generateResponse(
                    "Branch has been updated successfully",
                    HttpStatus.OK,
                    updateRequest);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> deleteBranch(Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        service.deleteOne(id);
        return ResponseHandler.generateResponse(
                "Branch Deleted Successfully",
                HttpStatus.OK,
                null);
    }

    @Override
    public ResponseEntity<?> getAllBranchByBankId(Pageable pageable, Long bankId, boolean unPaged, String routingNumber, String address, String name) {
        BranchFilter filter = new BranchFilter();
        filter.setBankId(bankId);
        filter.setRoutingNumber(routingNumber);
        filter.setAddress(address);
        filter.setName(name);
        try {
            Page<BranchResponse> branchResponseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    branchResponseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getBranchByRouting(String routingNumber) {
        BranchResponse response = new BranchResponse();
        try {
            response = service.getBranchByRouting(routingNumber);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    response);
        }
    }

    @Override
    public ResponseEntity<?> getAllBranchListByBankId(Long bankId) {
        List<BranchResponse> branchList= new ArrayList<>();
        BranchFilter filter = new BranchFilter();
        filter.setBankId(bankId);
        try {
            branchList = service.getBranchListByBankId(bankId);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    branchList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    branchList);
        }
    }
}
