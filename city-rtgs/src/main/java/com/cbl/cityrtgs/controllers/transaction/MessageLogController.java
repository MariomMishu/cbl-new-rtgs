package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.APIResponse;
import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.message.MsgLogRequest;
import com.cbl.cityrtgs.models.dto.message.MsgLogResponse;
import com.cbl.cityrtgs.models.dto.message.RtgsMessageFilter;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.services.transaction.MessageGenerateService;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
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
@RequestMapping("/message")
@RequiredArgsConstructor
public class MessageLogController {
    private final MessageGenerateService service;
    private final MsgLogService msgLogService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "messageDirections", required = false) final String messageDirections,
                                    @RequestParam(value = "processStatus", required = false) final String processStatus,
                                    @RequestParam(value = "messageType", required = false) final String messageType,
                                    @RequestParam(value = "messageUserReference", required = false) final String messageUserReference,
                                    @RequestParam(value = "msgDate", required = false) final String msgDate,
                                    @RequestParam(value = "anyString", required = false) final String anyString) {
        RtgsMessageFilter filter = new RtgsMessageFilter();

        filter
                .setMessageDirections(messageDirections)
                .setProcessStatus(processStatus)
                .setMessageType(messageType)
                .setMessageUserReference(messageUserReference)
                .setMsgDate(msgDate)
                .setAnyString(anyString);
        try {
            Page<MsgLogResponse> responseList = msgLogService.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    responseList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getById(@PathVariable(value = "id") Long id) {
        try {
            MsgLogResponse response = msgLogService.getById(id);
            return ResponseHandler.generateResponse("Request process Successfully", HttpStatus.OK, response);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody @Valid MsgLogRequest request) {
        try {
            boolean exist = msgLogService.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            msgLogService.update(id, request);
            return ResponseHandler.generateResponse("Message Body has been updated successfully", HttpStatus.OK, request);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @PutMapping("/{id}/reprocess")
    public APIResponse reProcessMessage(@PathVariable Long id) {
        boolean exist = msgLogService.existOne(id);
        if (!exist)
            return APIResponse.builder().status(HttpStatus.NOT_FOUND).statusCode(404).message("Message not Found").build();
        ResponseDTO response = service.resendMessage(id);
        if (response.isError()) {
            return APIResponse.builder()
                    .status(HttpStatus.OK)
                    .statusCode(200)
                    .message(response.getMessage())
                    .build();
        }

        return APIResponse.builder()
                .status(HttpStatus.OK)
                .statusCode(200)
                .message("Message Resend successfully")
                .build();
    }

}

