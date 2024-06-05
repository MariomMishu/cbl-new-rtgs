package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import com.cbl.cityrtgs.services.soap.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class DoFinacleAbabilService {

    private final DoAbabilSoapService doAbabilSoapService;
    private final CardTransactionService cardTransactionService;
    private final DoFinacleFISoapService doFinacleFISoapService;
    private final DoFinacleBulkService doFinacleBulkService;

    public CbsResponse getAbabilTransactionResponse(TransactionRequest transactionRequest, String crAcc) {
        return doAbabilSoapService.doAbabilNGTransaction(transactionRequest, crAcc);
    }


    public CbsResponse getFinacleFITransactionResponse(TransactionRequest transactionRequest) {
        return doFinacleFISoapService.doFinacleTransactionFI(transactionRequest);
    }

    public CbsResponse getFinacleFIBulkTransactionResponse(List<TransactionRequest> transactionRequest) {
        return doFinacleBulkService.doFinacleTransactionFI(transactionRequest);
    }

    public CbsResponse getFinacleFIReverseTransactionResponse(TransactionRequest transactionRequest) {
        return doFinacleFISoapService.doFinacleReverseTransactionFI(transactionRequest);
    }

    public CbsResponse getFinacleFIBatchChargeVatTxn(TransactionRequest transactionRequest) {
        return doFinacleFISoapService.doFinacleBatchChargeVatTransaction(transactionRequest);
    }

    public CbsResponse getAbabilBatchChargeVatTxn(TransactionRequest transactionRequest, String crAcc) {
        CbsResponse response;
        response = doAbabilSoapService.doAbabilNGBatchChargeVatTxn(transactionRequest, crAcc);
        return response;
    }

    public CbsResponse getAbabilReverseTxn(String orgnlRef, String ababilRequestId) {
        return doAbabilSoapService.doAbabilNGReverseTxn(orgnlRef, ababilRequestId);
    }

    // inward Cbs
    public CbsResponse doCardTransaction(TransactionRequest transactionRequest) {
        String remarks = "Rtgs Transaction";
        if (StringUtils.isNotBlank(transactionRequest.getNarration())) {
            remarks = remarks.concat(": " + transactionRequest.getNarration());
        }
        return cardTransactionService.doCardTransaction(transactionRequest, remarks);
    }

    public CbsResponse getAbabilInwardTransactionResponse(TransactionRequest transactionRequest, String benAccNo, String drAcc) {
        return doAbabilSoapService.doAbabilNGTransactionInward(transactionRequest, benAccNo, drAcc);
    }

    public CbsResponse getFinacleFIInwardTransactionResponse(TransactionRequest transactionRequest) {
        return doFinacleFISoapService.doFinacleInwardTransactionFI(transactionRequest);

    }

}
