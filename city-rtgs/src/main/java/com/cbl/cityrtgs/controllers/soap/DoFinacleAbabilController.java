package com.cbl.cityrtgs.controllers.soap;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import com.cbl.cityrtgs.services.transaction.DoFinacleAbabilService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import com.cbl.cityrtgs.common.utility.DateUtility;


@Slf4j
@RestController
@RequestMapping("/soap")
@RequiredArgsConstructor
public class DoFinacleAbabilController {

    private final DoFinacleAbabilService service;
    private final DateUtility dateUtility;

    @GetMapping("/do-finacle")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getFinacleResponse() {
        TransactionRequest request = new TransactionRequest();
//        String debitAccount = "2302437723001";
//        String creditAccount = "1401577160001";
//        String chargeAccount = "1000177000009";
//        String vatAccount = "1000149146024";
//        Boolean chargeEnabled = true;
        // city Gl Account
      //  BigDecimal amount = new BigDecimal(360.32);
        CbsResponse response = null;
        request.setDrAccount("1000241000099");
        request.setCrAccount("1401577160001");
        request.setNarration("Rtgs service Transaction 1000241000099");
        request.setRtgsRefNo("219536692203452");
        request.setAmount(new BigDecimal(1000000000));
        request.setCurrencyCode("BDT");
        request.setChargeEnabled(false);
        response = service.getFinacleFIInwardTransactionResponse(request);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }

    @GetMapping("/do-ababil")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAbabilResponse() {
        TransactionRequest request = new TransactionRequest();

//        String currency = "BDT";
//        String debitAccount = "1781330018621";
//        String creditAccount = "1000196300001"; // city Gl Account
//        BigDecimal amount = new BigDecimal(46);

//        request.setDrAccount("1781330018621");
//        request.setCrAccount("1000196300001");
//        request.setNarration("Rtgs service Transaction");
//        request.setAmount(new BigDecimal(46));
//        request.setCurrencyCode("BDT");
//        request.setChargeEnabled(false);

        request.setChargeAccount("1000179212001");
        request.setVatAccount("1000149207033");
        request.setDrAccount("1781330018621");
        request.setCrAccount("1000196300001");
        request.setNarration("Rtgs service Transaction");
        request.setAmount(new BigDecimal(219));
        request.setCurrencyCode("BDT");
        request.setChargeEnabled(false);
        request.setRtgsRefNo("219536692203452");
        request.setAbabilRequestId("219536692203452");
        request.setCharge(new BigDecimal(86.96));
        request.setVat(new BigDecimal(13.04));

        CbsResponse response = service.getAbabilTransactionResponse(request, "1000196300001");
        CbsResponse reverseResponse = service.getAbabilReverseTxn(request.getRtgsRefNo(), request.getRtgsRefNo());

        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, reverseResponse);
    }

    @GetMapping("/getDateTime")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getDtTime() {
        DateUtility.creDt();
        DateUtility.creDtTm();
        DateUtility.sttlmDt();
        dateUtility.getXMLdate();
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, null);
    }

    @GetMapping("/do-card")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getCardResponse() {
        TransactionRequest request = new TransactionRequest();

        // city Gl Account
        BigDecimal amount = new BigDecimal(100511);
        CbsResponse response = null;
        request.setDrAccount("1000141000206");
        request.setCrAccount("376948112452491");
        request.setNarration("Rtgs Transaction: RTGS From SIFATUR RAHMAN KHAN OR QUAZI KHAD");
        request.setAmount(new BigDecimal(100511));
        request.setCurrencyCode("BDT");
        request.setChargeEnabled(false);
        response = service.doCardTransaction(request);
        return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
    }


}
