package com.cbl.cityrtgs.controllers.transaction;

import com.cbl.cityrtgs.common.response.apiresponse.ResponseHandler;
import com.cbl.cityrtgs.models.dto.transaction.c2c.IbTransactionFilter;
import com.cbl.cityrtgs.models.dto.transaction.c2c.IbTransactionLiteResponse;
import com.cbl.cityrtgs.models.dto.transaction.c2c.IbTransactionRequest;
import com.cbl.cityrtgs.models.dto.transaction.c2c.IbTransactionResponse;
import com.cbl.cityrtgs.services.transaction.c2c.IbFundTransferService;
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
@RequestMapping("/ibrequest")
@RequiredArgsConstructor
public class IbFndTransferController {
    private final IbFundTransferService service;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@RequestBody @Valid IbTransactionRequest request) {
        IbTransactionLiteResponse response = null;
        try {
            // For test Start
            for (int i = 0; i <= 100; i++) {
                response = service.doOutwardC2CTransaction(request);
            }
            // For test End

            //  IbTransactionLiteResponse response = service.doOutwardC2CTransaction(request);
            if (response.getIsError()) {
                return ResponseHandler.generateResponse(response.getErrorDetail().getErrorMessage(), HttpStatus.OK, response);
            } else {
                return ResponseHandler.
                        generateResponse("Rtgs IB fund transfer has been proceed successfully",
                                HttpStatus.OK,
                                response);
            }
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAll(Pageable pageable,
                                    @RequestParam(value = "unPaged", required = false) final boolean unPaged,
                                    @RequestParam(value = "requestReference", required = false) final String requestReference,
                                    @RequestParam(value = "responseReference", required = false) final String responseReference,
                                    @RequestParam(value = "transactionDate", required = false) final String transactionDate,
                                    @RequestParam(value = "settlementDate", required = false) final String settlementDate,
                                    @RequestParam(value = "benAccount", required = false) final String benAccount,
                                    @RequestParam(value = "benName", required = false) final String benName,
                                    @RequestParam(value = "benBranchRoutingNo", required = false) final String benBranchRoutingNo,
                                    @RequestParam(value = "currency", required = false) final String currency,
                                    @RequestParam(value = "amount", required = false) final String amount,
                                    @RequestParam(value = "payerAccount", required = false) final String payerAccount,
                                    @RequestParam(value = "payerName", required = false) final String payerName,
                                    @RequestParam(value = "narration", required = false) final String narration,
                                    @RequestParam(value = "isError", required = false) final String isError,
                                    @RequestParam(value = "errorMessage", required = false) final String errorMessage,
                                    @RequestParam(value = "transactionStatus", required = false) final String transactionStatus) {
        IbTransactionFilter filter = new IbTransactionFilter();
        filter.setRequestReference(requestReference);
        filter.setResponseReference(responseReference);
        filter.setTransactionDate(transactionDate);
        filter.setSettlementDate(settlementDate);
        filter.setBenAccount(benAccount);
        filter.setBenName(benName);
        filter.setBenBranchRoutingNo(benBranchRoutingNo);
        filter.setCurrency(currency);
        filter.setAmount(amount);
        filter.setPayerAccount(payerAccount);
        filter.setPayerName(payerName);
        filter.setNarration(narration);
        filter.setIsError(isError);
        filter.setErrorMessage(errorMessage);
        filter.setTransactionStatus(transactionStatus);
        try {
            Page<IbTransactionResponse> getIbTransactionList = service.getAll(unPaged ? PageRequest.of(0, Integer.MAX_VALUE, pageable.getSort()) : pageable, filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    getIbTransactionList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

    @PutMapping("/{id}/retry")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> retryIbOutWithoutCbsTxn(@PathVariable Long id) {
        try {
            boolean exist = service.existOne(id);
            if (!exist) return ResponseEntity.notFound().build();
            IbTransactionLiteResponse response = service.retryIbOutWithoutCbsTxn(id);
            if (response.getIsError()) {
                return ResponseHandler.generateResponse(response.getErrorDetail().getErrorMessage(), HttpStatus.OK, response);
            } else {
                return ResponseHandler.generateResponse("IB reprocess successfully", HttpStatus.OK, response);
            }

        } catch (Exception ex) {
            return ResponseHandler.generateResponse(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
    }

    @GetMapping("/{deliveryChannel}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<?> getAllByDeliveryChannel(@PathVariable String deliveryChannel) {
        IbTransactionFilter filter = new IbTransactionFilter();
        filter.setDeliveryChannel(deliveryChannel);
        try {
            List<IbTransactionResponse> getIbTransactionList = service.getAllByDeliveryChannel(filter);
            return ResponseHandler.generateResponse(
                    "Request process Successfully",
                    HttpStatus.OK,
                    getIbTransactionList);
        } catch (Exception ex) {
            return ResponseHandler.generateResponse(
                    ex.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    null);
        }
    }

}
