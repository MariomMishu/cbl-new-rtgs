package com.cbl.cityrtgs.mapper.message;

import com.cbl.cityrtgs.common.utility.XMLParser;
import com.cbl.cityrtgs.kafka.service.MessageProducerService;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.MessageDirectionsType;
import com.cbl.cityrtgs.models.dto.message.MessageProcessStatus;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.services.transaction.MsgLogService;
import iso20022.iso.std.iso._20022.tech.xsd.head_001_002.BusinessApplicationHeaderV02;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class InwardTransactionHandlerService {

    private final MsgLogService msgLogService;
    private final MessageProducerService messageProducerService;


    //    @Async
    public void handleInwardBlock4Message(String block4) {

        Date dateTime = new Date();

        try {

            Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);
            Map<String, String> values = getMessageType(map.get("document"));
            BusinessApplicationHeaderV02 appHeader = (BusinessApplicationHeaderV02) map.get("header");
            log.info("New Message {} is coming ", block4);

            if (!msgLogService.logExists(appHeader.getBizMsgIdr())) {
                MsgLogEntity msgLogEntity = MsgLogEntity.builder()
                        .messageDirections(MessageDirectionsType.INWARD.toString())
                        .messageFormat("MX")
                        .messageSender(appHeader.getFr().getFIId().getFinInstnId().getBICFI().concat("AXXX"))
                        .messageReceiver(appHeader.getTo().getFIId().getFinInstnId().getBICFI().concat("XRTG"))
                        .messageType(values.get("messageType"))
                        .messageUserReference(values.get("userReference"))
                        .uuid(values.get("uuid"))
                        .businessMessageId(appHeader.getBizMsgIdr())
                        .processStatus(MessageProcessStatus.QUEUED.toString())
                        .messageNetMir(appHeader.getBizMsgIdr())
                        .msgDate(dateTime)
                        .msgTime(dateTime)
                        .mxMessage(block4)
                        .build();
                msgLogEntity.setCreatedAt(new Date());

                MsgLogEntity entity = msgLogService.save(msgLogEntity);
                if (entity.getId() > 0) {
                    messageProducerService.sendMessage("INWARD", entity);
                }

                log.info("New Message {} is saved in rtgs message Table", entity.getBusinessMessageId());
            } else {
                throw new RuntimeException("Duplicate Message.");
            }

        } catch (Exception e) {
            log.error("{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    private Map<String, String> getMessageType(Object document) {

        String messageType = "";
        String userReference = "";
        String uuid = "";

        Map<String, String> values = new HashMap<>();

        if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) {

            iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document doc = (iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) document;
            messageType = MessageDefinitionIdentifier.PACS008.value();
            userReference = doc.getFIToFICstmrCdtTrf().getCdtTrfTxInf().getPmtId().getTxId();
            uuid = doc.getFIToFICstmrCdtTrf().getCdtTrfTxInf().getPmtId().getUETR();

            values.put("messageType", messageType);
            values.put("userReference", userReference);
            values.put("uuid", uuid);

        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document) {

            iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document doc = (iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document) document;
            messageType = MessageDefinitionIdentifier.PACS009.value();
            userReference = doc.getFICdtTrf().getCdtTrfTxInf().getPmtId().getTxId();

            values.put("messageType", messageType);
            values.put("userReference", userReference);

        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.Document) {

            iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.Document doc = (iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.Document) document;
            messageType = MessageDefinitionIdentifier.CAMT054.value();
            userReference = doc.getBkToCstmrDbtCdtNtfctn().getNtfctn().getNtry().getNtryRef();

            values.put("messageType", messageType);
            values.put("userReference", userReference);

        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.Document) {

            messageType = MessageDefinitionIdentifier.CAMT052.value();

            values.put("messageType", messageType);
            values.put("userReference", "");

        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.Document) {

            messageType = MessageDefinitionIdentifier.CAMT053.value();

            values.put("messageType", messageType);
            values.put("userReference", "");

        } else if (document instanceof iso20022.swift.xsd.camt_019_001.Document) {

            messageType = MessageDefinitionIdentifier.CAMT019.value();

            values.put("messageType", messageType);
            values.put("userReference", "");

        } else if (document instanceof iso20022.swift.xsd.camt_025_001.Document) {

            messageType = MessageDefinitionIdentifier.CAMT025.value();

            values.put("messageType", messageType);
            values.put("userReference", "");

        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.Document) {
            messageType = MessageDefinitionIdentifier.PACS002.value();

            values.put("messageType", messageType);
            values.put("userReference", "");
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) {

            messageType = MessageDefinitionIdentifier.PACS004.value();

            values.put("messageType", messageType);
            values.put("userReference", "");
        } else {
            log.error("Unknown document type!");
            throw new RuntimeException("Unknown document type!");
        }

        return values;
    }
}
