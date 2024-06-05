package com.cbl.cityrtgs.services.scheduler.implementation;

import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.services.inward.factory.InwardFactory;
import com.cbl.cityrtgs.common.utility.XMLParser;
import com.cbl.cityrtgs.services.scheduler.abstraction.SchedulerService;
import com.cbl.cityrtgs.services.transaction.MessageGenerateService;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;

import java.util.List;
import java.util.Map;

import com.cbl.cityrtgs.services.inward.factory.Inward;

@Slf4j
@RequiredArgsConstructor
@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final MsgLogRepository msgLogRepository;
    private final MsgLogService msgLogService;
    private final MessageGenerateService messageGenerateService;

    @Override
    public void initiateInwardScheduler() {

        List<MsgLogEntity> messageLogs = msgLogRepository.findAllByProcessStatusQueued();

        if (!messageLogs.isEmpty()) {
            messageLogs.forEach(messageLog -> {
                msgLogService.updateRtgsMsgLog(messageLog.getId(), String.valueOf(MessageProcessStatus.PROCESSING), "");
                String block4 = messageLog.getMxMessage();
                Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);

                Inward inward = InwardFactory.getService(messageLog.getMessageType());
                inward.processInward(messageLog.getId(), map.get("document"));
            });
        }
    }

    @Override
    public void initiateOutwardScheduler() {

        List<MsgLogEntity> messageLogs = msgLogRepository.findAllByProcessStatusUnProcessed();

        if (!messageLogs.isEmpty()) {

            messageLogs.forEach(messageLog -> {
                msgLogService.updateRtgsMsgLog(messageLog.getId(), String.valueOf(MessageProcessStatus.PROCESSING), "");
                messageGenerateService.dispatchOutwardRequest(messageLog);
            });
        }

//        List<MsgLogEntity> msgLogEntityList =  msgLogRepository.getBangladeshBankOpeningForToday();
//
//        if(msgLogEntityList.size() > 0){
//
//            msgLogEntityList.forEach(messageLog -> {
//
//                String block4 = messageLog.getMxMessage();
//                Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);
//                Document document = (iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document) map.get("document");
//
//                String dbtrBank = document.getFICdtTrf().getCdtTrfTxInf().getDbtr().getFinInstnId().getBICFI();
//
//                Optional<BankEntity> optional = bankRepository.findByBicAndSettlementBankIsTrue(dbtrBank);
//
//                if(optional.isPresent()){
//                    String ccy = document.getFICdtTrf().getCdtTrfTxInf().getIntrBkSttlmAmt().getCcy();
//                    CurrencyEntity currencyEntity = currencyRepository.findByShortCodeAndIsDeletedFalse(ccy);
//
//                    if(currencyEntity != null){
//
//                        long currencyId = currencyEntity.getId();
//                        Optional<TxnCfgSetupEntity> optionalTxnCfgSetupEntity = txnCfgSetupRepository.findByCurrencyIdAndIsDeletedFalse(currencyId);
//
//                        if(optionalTxnCfgSetupEntity.isPresent()){
//
//                            TxnCfgSetupEntity txnCfgSetupEntity = optionalTxnCfgSetupEntity.get();
//
//                            if(!txnCfgSetupEntity.getTxnActive()){
//
//                                txnCfgSetupEntity.setCurrencyId(currencyId);
//                                txnCfgSetupEntity.setTxnActive(true);
//                                txnCfgSetupRepository.save(txnCfgSetupEntity);
//                            }
//                        }
//
//                    }
//                }
//            });
//        }
    }
}
