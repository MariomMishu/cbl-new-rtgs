package com.cbl.cityrtgs.kafka.service;

import com.cbl.cityrtgs.common.utility.XMLParser;
import com.cbl.cityrtgs.filereadwrite.write.FileWriteService;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.message.MessageLogDTO;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.inward.factory.InwardFactory;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageConsumerService {

    private final MsgLogService msgLogService;
    private final FileWriteService fileWriteService;


    @KafkaListener(topics = "INWARD", groupId = "rtgs-group-id")
    @KafkaHandler
    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "1",
            autoCreateTopics = "false",
            include = SocketTimeoutException.class
            //  exclude = NullPointerException.class
    )
    public void listenInward(Map<String, Object> requestmap) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            MsgLogEntity messageLog = objectMapper.convertValue(requestmap.get("request"), MsgLogEntity.class);
            if (!msgLogService.isExist(messageLog.getId())) {
                if (!msgLogService.isExistProcessing(messageLog.getId())) {
                    if (!msgLogService.isExistArrived(messageLog.getId())) {
                        msgLogService.updateRtgsMsgLog(messageLog.getId(), MessageProcessStatus.PROCESSING.name(), "");
                        String block4 = messageLog.getMxMessage();
                        Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);

                        Inward inward = InwardFactory.getService(messageLog.getMessageType());

                        inward.processInward(messageLog.getId(), map.get("document"));
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @KafkaListener(topics = "OUTWARD", groupId = "rtgs-group-id")
    @KafkaHandler
    @RetryableTopic(
            backoff = @Backoff(value = 3000L),
            attempts = "1",
            autoCreateTopics = "false",
            include = SocketTimeoutException.class
            //  exclude = NullPointerException.class
    )
    public void listenOutward(Map<String, Object> map) {
        String messageProcessStatus;
        String errorMessage = "";
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            MsgLogEntity msgLogEntity = objectMapper.convertValue(map.get("request"), MsgLogEntity.class);
            if (!msgLogService.isExist(msgLogEntity.getId())) {
                if (!msgLogService.isExistProcessing(msgLogEntity.getId())) {
                    String response = fileWriteService.fileWriteForOutWard(msgLogEntity.getMxMessage(), msgLogEntity.getBusinessMessageId());
                    if (response.equals("OK")) {
                        messageProcessStatus = MessageProcessStatus.PROCESSED.name();
                    } else {
                        messageProcessStatus = MessageProcessStatus.UNPROCESSED.name();
                        errorMessage = "Error File Write in Stp Server.";
                    }
                    msgLogService.updateRtgsMsgLog(msgLogEntity.getId(), messageProcessStatus, errorMessage);
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
