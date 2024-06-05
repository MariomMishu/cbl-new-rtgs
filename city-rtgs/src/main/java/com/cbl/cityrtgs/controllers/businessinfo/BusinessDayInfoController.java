package com.cbl.cityrtgs.controllers.businessinfo;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoResponse;
import com.cbl.cityrtgs.models.dto.businessinfo.ReturnBusinessDayInfoResponse;
import com.cbl.cityrtgs.services.BusinessDayInformationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/businessinfo")
@RequiredArgsConstructor
public class BusinessDayInfoController {
    private final BusinessDayInformationService service;

    @PostMapping("/sendrequest")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> sendrequest() {
        try {
            BusinessDayInfoResponse response = service.sendRequestForBizDayInfo();
            if(response.getIsError()){
                return ResponseHandler.
                        generateResponse(response.getErrorMessage(),
                                HttpStatus.OK,
                                response);
            }else{
                return ResponseHandler.
                        generateResponse("Business information request sent successfully",
                                HttpStatus.OK,
                                response);
            }

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/returnbizdayinfo/{originalMsgId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getBizDayInfo(@PathVariable(value = "originalMsgId") String originalMsgId) {
        try {
            ReturnBusinessDayInfoResponse response = service.getByOriginalMsgId(originalMsgId);
            if(response !=null){
                return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
            }else{
                return ResponseHandler.generateResponse("Response Not Found Yet", HttpStatus.OK, response);
            }
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    @GetMapping("/returnbizdayinfo/latest")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getLatestReturnBizDayInfo() {
        try {
            ReturnBusinessDayInfoResponse response = service.getLatestReturnBizDayInfo();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    @GetMapping("/today")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getSentBizDayInfo() {
        try {
            ReturnBusinessDayInfoResponse response = service.getSentBizDayInfo();
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

}
