package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.common.enums.ResponseCodeEnum;
import com.cbl.cityrtgs.models.dto.configuration.accounttype.CbsName;
import com.cbl.cityrtgs.models.dto.transaction.CbsResponse;
import com.cbl.cityrtgs.models.dto.transaction.TransactionRequest;
import com.cbl.cityrtgs.models.entitymodels.configuration.AccountTypeEntity;
import com.cbl.cityrtgs.services.configuration.AccountTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CbsTransactionService {
    private final DoFinacleAbabilService doFinacleAbabilService;
    private final AccountTypeService accountTypeService;
    private final CbsTransactionLogService cbsTransactionLogService;

    public CbsResponse cbsTransaction(TransactionRequest transactionRequest) {

        CbsResponse cbsResponse = new CbsResponse();

        if (transactionRequest.getCbsName().equals(CbsName.FINACLE.toString())) {

            cbsResponse = doFinacleAbabilService.getFinacleFITransactionResponse(transactionRequest);
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());

        } else if (transactionRequest.getCbsName().equals(CbsName.ABABIL.toString())) {

            AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(transactionRequest.getSettlementAccountId(), CbsName.ABABIL);

            CbsResponse cbsResponseAbabil = doFinacleAbabilService.getAbabilTransactionResponse(transactionRequest, accountTypeEntity.getCbsAccountNumber());
            cbsTransactionLogService.save(cbsResponseAbabil.getAbabilVoucher(), cbsResponseAbabil.getResponseCode(), cbsResponseAbabil.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());

            if (cbsResponseAbabil.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                transactionRequest.setChargeEnabled(false)
                        .setDrAccount(accountTypeEntity.getContraAccNumber());
                cbsResponse = doFinacleAbabilService.getFinacleFITransactionResponse(transactionRequest);
                cbsResponse.setAbabilVoucher(cbsResponseAbabil.getAbabilVoucher());
                cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), accountTypeEntity.getContraAccNumber());
            }
            if (!cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                //do ababil reverse
                var ababilReverseResponse = doFinacleAbabilService.getAbabilReverseTxn(transactionRequest.getRtgsRefNo(), transactionRequest.getAbabilRequestId());
                cbsTransactionLogService.save(ababilReverseResponse.getTransactionRefNumber(), ababilReverseResponse.getResponseCode(), ababilReverseResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());
            }
        } else if (transactionRequest.getCbsName().equals(CbsName.CARD.toString())) {
            var cardCbsResponse = doFinacleAbabilService.doCardTransaction(transactionRequest);
            cbsResponse.setTransactionRefNumber(cardCbsResponse.getTransactionRefNumber());
            cbsResponse.setTransactionDateTime(cardCbsResponse.getTransactionDateTime());
            cbsResponse.setResponseMessage(cardCbsResponse.getResponseMessage());
            cbsResponse.setResponseCode(cardCbsResponse.getResponseCode());
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());
        }
        return cbsResponse;
    }


    public CbsResponse cbsReverseTransaction(TransactionRequest transactionRequest) {
        CbsResponse cbsResponse = new CbsResponse();
        if (transactionRequest.getCbsName().equals(CbsName.FINACLE.toString())) {
            cbsResponse = doFinacleAbabilService.getFinacleFIReverseTransactionResponse(transactionRequest);
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());
        }
        if (transactionRequest.getCbsName().equals(CbsName.ABABIL.toString())) {
            //cr finacle mirror gl
            AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(transactionRequest.getSettlementAccountId(), CbsName.ABABIL);
            transactionRequest.setCrAccount(accountTypeEntity.getContraAccNumber());
            transactionRequest.setChargeEnabled(false);
            cbsResponse = doFinacleAbabilService.getFinacleFIReverseTransactionResponse(transactionRequest);
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());

            if (cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                //do ababil reverse
                CbsResponse ababilReverse = doFinacleAbabilService.getAbabilReverseTxn(transactionRequest.getRtgsRefNo(), transactionRequest.getAbabilRequestId());
                cbsResponse.setResponseCode(ababilReverse.getResponseCode());
                cbsResponse.setResponseMessage(ababilReverse.getResponseMessage());
                cbsTransactionLogService.save(ababilReverse.getTransactionRefNumber(), ababilReverse.getResponseCode(), ababilReverse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());

            }
        }
        return cbsResponse;
    }

    public CbsResponse cbsBulkTransactionFI(List<TransactionRequest> transactionList) {
        CbsResponse cbsResponse = new CbsResponse();
        if (transactionList.get(0).getCbsName().equals(CbsName.FINACLE.toString())) {
            cbsResponse = doFinacleAbabilService.getFinacleFIBulkTransactionResponse(transactionList);
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionList.get(0).getRtgsRefNo(), transactionList.get(0).getDrAccount());

        } else if (transactionList.get(0).getCbsName().equals(CbsName.ABABIL.toString())) {
            List<TransactionRequest> transactionFIList = new ArrayList<>();
            for (TransactionRequest transactionRequest : transactionList) {
                AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(transactionRequest.getSettlementAccountId(), CbsName.ABABIL);
                CbsResponse cbsResponseAbabil = doFinacleAbabilService.getAbabilTransactionResponse(transactionRequest, accountTypeEntity.getCbsAccountNumber());
                cbsTransactionLogService.save(cbsResponseAbabil.getAbabilVoucher(), cbsResponseAbabil.getResponseCode(), cbsResponseAbabil.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());

                if (cbsResponseAbabil.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    transactionRequest.setChargeEnabled(false).setDrAccount(accountTypeEntity.getContraAccNumber());
                    transactionFIList.add(transactionRequest);
                } else {
                    cbsResponse.setResponseMessage(cbsResponseAbabil.getResponseMessage());
                    cbsResponse.setResponseCode(cbsResponseAbabil.getResponseCode());
                }
            }
            if (transactionList.size() == transactionFIList.size()) {
                cbsResponse = doFinacleAbabilService.getFinacleFIBulkTransactionResponse(transactionFIList);
                cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionList.get(0).getRtgsRefNo(), transactionList.get(0).getCrAccount());

                if (!cbsResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    //do ababil reverse
                    for (TransactionRequest transactionRequest : transactionFIList) {
                        var ababilReverseResponse = doFinacleAbabilService.getAbabilReverseTxn(transactionRequest.getRtgsRefNo(), transactionRequest.getAbabilRequestId());
                        cbsTransactionLogService.save(ababilReverseResponse.getTransactionRefNumber(), ababilReverseResponse.getResponseCode(), ababilReverseResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());
                    }
                }
            } else {
                for (TransactionRequest transactionRequest : transactionFIList) {
                    var ababilReverseResponse = doFinacleAbabilService.getAbabilReverseTxn(transactionRequest.getRtgsRefNo(), transactionRequest.getAbabilRequestId());
                    cbsTransactionLogService.save(ababilReverseResponse.getTransactionRefNumber(), ababilReverseResponse.getResponseCode(), ababilReverseResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());
                }
            }
        }
        return cbsResponse;
    }

    public CbsResponse batchChargeVatTransaction(TransactionRequest transactionRequest) {

        CbsResponse cbsResponse = new CbsResponse();

        if (transactionRequest.getCbsName().equals(CbsName.FINACLE.toString())) {

            cbsResponse = doFinacleAbabilService.getFinacleFIBatchChargeVatTxn(transactionRequest);
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());
        } else if (transactionRequest.getCbsName().equals(CbsName.ABABIL.toString())) {

            AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(transactionRequest.getSettlementAccountId(), CbsName.ABABIL);

            CbsResponse cbsResponseAbabil = doFinacleAbabilService.getAbabilBatchChargeVatTxn(transactionRequest, accountTypeEntity.getCbsAccountNumber());
            cbsTransactionLogService.save(cbsResponseAbabil.getTransactionRefNumber(), cbsResponseAbabil.getResponseCode(), cbsResponseAbabil.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getDrAccount());
            cbsResponse.setResponseMessage(cbsResponseAbabil.getResponseMessage());
            cbsResponse.setResponseCode(cbsResponseAbabil.getResponseCode());
            cbsResponse.setAbabilVoucher(cbsResponseAbabil.getAbabilVoucher());
            cbsResponse.setTransactionRefNumber(cbsResponseAbabil.getTransactionRefNumber());
            cbsResponse.setTransactionDateTime(cbsResponseAbabil.getTransactionDateTime());

        }
        return cbsResponse;
    }

    public CbsResponse cbsInwardTransaction(TransactionRequest transactionRequest, String benAccNo) {

        CbsResponse cbsResponse = new CbsResponse();

        if (transactionRequest.getCbsName().equals(CbsName.FINACLE.toString())) {
            cbsResponse = doFinacleAbabilService.getFinacleFIInwardTransactionResponse(transactionRequest);
            cbsTransactionLogService.save(cbsResponse.getTransactionRefNumber(), cbsResponse.getResponseCode(), cbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());

        } else if (transactionRequest.getCbsName().equals(CbsName.ABABIL.toString())) {
            CbsResponse cbsResponseAbabil = new CbsResponse();
            AccountTypeEntity accountTypeEntity = accountTypeService.getAccountByRtgsAccountIdAndCbsName(transactionRequest.getSettlementAccountId(), CbsName.ABABIL);
            transactionRequest.setCrAccount(accountTypeEntity.getContraAccNumber()).setChargeEnabled(false);
            var cbsFinacleResponse = doFinacleAbabilService.getFinacleFIInwardTransactionResponse(transactionRequest);
            cbsTransactionLogService.save(cbsFinacleResponse.getTransactionRefNumber(), cbsFinacleResponse.getResponseCode(), cbsFinacleResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());

            if (cbsFinacleResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                cbsResponseAbabil = doFinacleAbabilService.getAbabilInwardTransactionResponse(transactionRequest, benAccNo, accountTypeEntity.getCbsAccountNumber());
                cbsTransactionLogService.save(cbsResponseAbabil.getAbabilVoucher(), cbsResponseAbabil.getResponseCode(), cbsResponseAbabil.getResponseMessage(), transactionRequest.getRtgsRefNo(), benAccNo);
                cbsResponse.setTransactionRefNumber(cbsResponseAbabil.getAbabilVoucher());
                cbsResponse.setResponseCode(cbsResponseAbabil.getResponseCode());
                cbsResponse.setResponseMessage(cbsResponseAbabil.getResponseMessage());

            }

            if (!cbsResponseAbabil.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                TransactionRequest reverseRequest = new TransactionRequest();
                reverseRequest
                        .setCrAccount(transactionRequest.getDrAccount())
                        .setDrAccount(transactionRequest.getCrAccount())
                        .setAmount(transactionRequest.getAmount())
                        .setCurrencyCode(transactionRequest.getCurrencyCode())
                        .setRemarks(transactionRequest.getRemarks())
                        .setNarration(transactionRequest.getNarration())
                        .setParticular2(transactionRequest.getParticular2());

                var cbsReverseResponse = doFinacleAbabilService.getFinacleFIInwardTransactionResponse(reverseRequest);
                if (cbsReverseResponse.getResponseCode().equals(ResponseCodeEnum.SUCCESS_RESPONSE_CODE.getCode())) {
                    cbsReverseResponse.setResponseCode(ResponseCodeEnum.REVERSE_SUCCESS_RESPONSE_CODE.getCode());
                    cbsReverseResponse.setResponseMessage(cbsReverseResponse.getResponseMessage().concat(" Reverse Success"));
                } else {
                    cbsReverseResponse.setResponseCode(ResponseCodeEnum.REVERSE_FAIL_RESPONSE_CODE.getCode());
                    cbsReverseResponse.setResponseMessage(cbsReverseResponse.getResponseMessage().concat(" Reverse Fail"));
                }
                cbsTransactionLogService.save(cbsReverseResponse.getTransactionRefNumber(), cbsReverseResponse.getResponseCode(), cbsReverseResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());
            }
        } else if (transactionRequest.getCbsName().equals(CbsName.CARD.toString())) {
            var cardCbsResponse = doFinacleAbabilService.doCardTransaction(transactionRequest);
            cbsResponse.setTransactionRefNumber(cardCbsResponse.getTransactionRefNumber());
            cbsResponse.setTransactionDateTime(cardCbsResponse.getTransactionDateTime());
            cbsResponse.setResponseCode(cardCbsResponse.getResponseCode());
            cbsResponse.setResponseMessage(cardCbsResponse.getResponseMessage());
            cbsTransactionLogService.save(cardCbsResponse.getTransactionRefNumber(), cardCbsResponse.getResponseCode(), cardCbsResponse.getResponseMessage(), transactionRequest.getRtgsRefNo(), transactionRequest.getCrAccount());
        }
        return cbsResponse;
    }
}
