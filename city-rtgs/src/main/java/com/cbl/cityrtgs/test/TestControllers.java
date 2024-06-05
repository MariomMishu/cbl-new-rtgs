package com.cbl.cityrtgs.test;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.response.SentSmsResponse;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionStatusResponse;
import com.cbl.cityrtgs.services.si.implementation.SiService;
import com.cbl.cityrtgs.services.transaction.ReferenceNoGenerateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestControllers {

    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final TestService testService;
    private final SiService siService;

    @GetMapping("/{type}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getReferenceNo(@PathVariable String type) {
        try {
            ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(type);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, referenceNo);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.OK, null);
        }

    }

//    @GetMapping("/new-sequence")
//    @ResponseStatus(HttpStatus.OK)
//    public ResponseEntity<?> newSequenceCreate() {
//        try {
//            ReferenceGenerateResponse referenceNo = referenceNoGenerateService.newSequenceCreate();
//            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, referenceNo);
//        } catch (Exception ex) {
//            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.OK, null);
//        }
//
//    }

    @GetMapping("/inward-process")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> processInwardTxn() {
        try {
            testService.processInward();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, "");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.OK, null);
        }
    }

    @GetMapping("/inward-process-file")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> processInwardFile() {
        try {
            testService.processInwardFile();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, "");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.OK, null);
        }
    }

    @PostMapping("/inward-process-data")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> processInwardData(@RequestParam(value = "data") String data) {
        try {
            testService.processInwardData(data);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, "100");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.CONFLICT, "101");
        }
    }

    @GetMapping("/inward-process-recheck")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> processInwardTxnRecheck() {
        try {
            testService.processInwardTxnRecheck();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, "");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.NOT_FOUND, null);
        }
    }

    @GetMapping("/si")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> processSi() {
        try {
            siService.startSiExecution();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, "");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.OK, null);
        }
    }

    @PostMapping("/outward-process")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> processOutward() {
        try {
            testService.processOutward();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, "100");
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.CONFLICT, "101");
        }
    }

    @PostMapping("/outward-sent-sms")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> sentSms(@RequestParam(value = "mobileNo") String mobileNo) {
        try {
            SentSmsResponse sentSmsResponse = testService.sentSms(mobileNo);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, sentSmsResponse);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.CONFLICT, null);
        }
    }

    @PostMapping("/cbs-trx-status")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> cbsTrxStatusCheck(@RequestParam(value = "accountNo") String accountNo,
                                               @RequestParam(value = "refNo") String refNo) {
        try {
            TransactionStatusResponse cbsResponse = testService.cbsTrxStatusCheck(accountNo, refNo);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, cbsResponse);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.CONFLICT, null);
        }
    }
}
