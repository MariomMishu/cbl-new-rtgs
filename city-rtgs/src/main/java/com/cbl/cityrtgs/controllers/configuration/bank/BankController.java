package com.cbl.cityrtgs.controllers.configuration.bank;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankFilter;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankRequest;
import com.cbl.cityrtgs.models.dto.configuration.bank.BankResponse;
import com.cbl.cityrtgs.services.configuration.BankService;
import com.cbl.cityrtgs.common.utility.ValidationUtility;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BankController implements BankApi {
    public String BANK_CODE = "^[\\d]{1,3}";
    private final BankService service;
    @Override
    public ResponseEntity<?> getBanks(Pageable pageable,
                                      boolean unPaged, String bic, String bankCode, String address, String name) {
        BankFilter filter = new BankFilter();
        filter.setBic(bic);
        filter.setBankCode(bankCode);
        filter.setAddress(address);
        filter.setName(name);
        try {
            Page<BankResponse> bankResponseList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    bankResponseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> createBank(BankRequest bankRequest) {
        try {
            if (!ValidationUtility.valid(this.BANK_CODE, bankRequest.getBankCode())) {
                return ResponseHandler.generateResponse(
                        "Bank Code accepts only 3 digit numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            service.createBank(bankRequest);
            return ResponseHandler.generateResponse(
                    "Bank has been created successfully",
                    HttpStatus.OK,
                    bankRequest);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getBank(Long id) {
        BankResponse response  = new BankResponse();
        try {
            response = service.getBankById(id);
            return ResponseHandler.generateResponse(
                    "Request Process successfully",
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
    public ResponseEntity<?> updateBank(Long id, BankRequest updateRequest) {
        BankResponse response  = new BankResponse();
        try {
            if (!ValidationUtility.valid(this.BANK_CODE, updateRequest.getBankCode())) {
                return ResponseHandler.generateResponse(
                        "Bank Code accepts only 3 digit numeric value",
                        HttpStatus.INTERNAL_SERVER_ERROR,
                        null);
            }
            Boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            response = service.updateBank(id, updateRequest);
            return ResponseHandler.generateResponse(
                    "Bank has been updated successfully",
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
    public ResponseEntity<?> deleteBank(Long id) {
        Boolean exist = service.existOne(id);
        if (!exist) return ResponseEntity.notFound().build();
        try {
            service.deleteOne(id);
            return ResponseHandler.generateResponse(
                    "Bank Deleted Successfully",
                    HttpStatus.OK,
                    new BankResponse());
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.NOT_FOUND,
                    null);
        }
    }

    @Override
    public ResponseEntity<?> getOwnerBank() {
        BankResponse response  = new BankResponse();
        try {
            response = service.getOwnerBank();
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
    public ResponseEntity<?> getSattlementBank() {
        BankResponse response = new BankResponse();
        try {
            response = service.getSettlementBank();
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
    public ResponseEntity<?> getAllBenBankList() {
        List<BankResponse> response = new ArrayList<>();
        try {
            response = service.getAllBenBankList();
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
    public ResponseEntity<?> getBankByBic(String bic) {
        BankResponse response = new BankResponse();
        try {
             response = service.getBankByBicCode(bic);
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
}
