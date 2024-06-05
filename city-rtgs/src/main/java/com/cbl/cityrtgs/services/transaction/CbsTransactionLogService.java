package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.models.entitymodels.transaction.CbsTxnLogEntity;
import com.cbl.cityrtgs.repositories.transaction.CbsTxnLogRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class CbsTransactionLogService {

    private final CbsTxnLogRepository cbsTxnLogRepository;

    public void save(String cbsTransactionId, String cbsResponseCode, String cbsResponseMsg, String referenceNumber, String accountNo) {

        CbsTxnLogEntity cbsTxnLog = new CbsTxnLogEntity();

        cbsTxnLog
                .setCbsReferenceNumber(cbsTransactionId)
                .setCbsResponseCode(cbsResponseCode)
                .setCbsResponseCode(cbsResponseCode)
                .setCbsResponseMessage(cbsResponseMsg)
                .setRtgsReferenceNumber(referenceNumber)
                .setAccountNo(accountNo)
                .setTxnDate(LocalDateTime.now())
                .setTxnTime(LocalDateTime.now())
                .setCreatedAt(new Date());
        try {
            cbsTxnLogRepository.save(cbsTxnLog);
        } catch (Exception e) {
            log.info("Insert in Cbs Txn Log Failed " + referenceNumber);

        }
        log.info("Insert in Cbs Txn Log  " + referenceNumber);
    }

    public CbsTxnLogEntity save(CbsTxnLogEntity cbsTxnLogEntity) {

        log.info("Insert in Cbs Txn Log {}", cbsTxnLogEntity.getCbsReferenceNumber());
        return cbsTxnLogRepository.save(cbsTxnLogEntity);
    }

    public boolean cbsTransactionExists(String rtgsReferenceNumber) {
        var count = cbsTxnLogRepository.cbsTransactionExists(rtgsReferenceNumber);
        return count > 0L;
    }
}
