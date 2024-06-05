package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.transaction.c2c.CardDetailsResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.PayerDetailsResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.SignatureInfoResponse;
import com.cbl.cityrtgs.services.soap.CardDetailsSoapService;
import com.cbl.cityrtgs.services.transaction.CustomerAccountDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
@RequestMapping("/account-details")
@RequiredArgsConstructor
public class AccountDetailsController {
    private final CustomerAccountDetailsService service;
    private final CardDetailsSoapService cardDetailsSoapService;

    @GetMapping("/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getPayerDetailsByAccountNumber(@PathVariable String accountNumber) {
        PayerDetailsResponse response = service.getAccountDetails(accountNumber);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/card/{cardNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getClientCardDetails(@PathVariable String cardNumber) {
        CardDetailsResponse response = cardDetailsSoapService.getCardDetailsFromSoap(cardNumber);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/signature/{accountNumber}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getPayerSignatureDetailsByAccountNumber(@PathVariable String accountNumber) {
        SignatureInfoResponse response = service.getPayerSignatureDetailsByAccountNumber(accountNumber);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

}
