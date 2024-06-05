package com.cbl.cityrtgs.services.transaction;

import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.config.authentication.LoggedInUserDetails;
import com.cbl.cityrtgs.models.dto.businessinfo.BusinessDayInfoRequest;
import com.cbl.cityrtgs.models.dto.message.*;
import com.cbl.cityrtgs.models.dto.transaction.FundTransferType;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.kafka.service.MessageProducerService;
import com.cbl.cityrtgs.models.dto.configuration.departmentaccount.RoutingType;
import com.cbl.cityrtgs.models.dto.response.ResponseDTO;
import com.cbl.cityrtgs.engine.dto.response.STPResponse;
import com.cbl.cityrtgs.engine.service.STPService;
import com.cbl.cityrtgs.common.exception.RtgsMessageException;
import com.cbl.cityrtgs.models.entitymodels.messagelog.MsgLogEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.FailedTxnNotificationEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.BankFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.b2b.InterBankTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.CustomerFndTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.transaction.c2c.InterCustomerFundTransferEntity;
import com.cbl.cityrtgs.models.entitymodels.user.UserInfoEntity;
import com.cbl.cityrtgs.repositories.message.MsgLogRepository;
import com.cbl.cityrtgs.repositories.transaction.notification.FailedTxnNotificationRepository;
import com.cbl.cityrtgs.services.inward.factory.Inward;
import com.cbl.cityrtgs.services.inward.factory.InwardFactory;
import com.cbl.cityrtgs.common.utility.XMLParser;
import com.cbl.cityrtgs.services.outward.*;
import iso20022.iso.std.iso._20022.tech.xsd.head_001_002.BusinessApplicationHeaderV02;
import iso20022.wrapper.DataPDU;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class MessageGenerateService {

    private final ReferenceNoGenerateService referenceNoGenerateService;
    private final STPService stpService;
    private final Pacs008Generator pacs008Generator;
    private final Pacs009Generator pacs009Generator;
    private final Camt018Generator camt018Generator;
    private final MsgLogService messageService;
    private final MsgLogRepository msgLogRepository;
    private final MessageProducerService messageProducerService;
    private final MXMessageGeneratorService mxmessageGenerateService;
    private final Pacs004Generator pacs004Generator;
    private final FailedTxnNotificationRepository failedTxnNotificationRepository;
    private final MsgLogService msgLogService;

    public String processCAMT018OutwardRequest(BusinessDayInfoRequest bizDayInf) {

        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        String sender = "CIBLBDDHAXXX";
        String receiver = "BBHOBDDHXRTG";
        String msgUserReference = "CI0000".concat(String.valueOf(System.currentTimeMillis() / 1000));
        RtgsDocument document = camt018Generator.createMessage(bizDayInf);
        DataPDU dataPDU = mxmessageGenerateService.generate(document);
        BusinessApplicationHeaderV02 AppHdr = (BusinessApplicationHeaderV02) ((JAXBElement) dataPDU.getBody().getContent().get(0)).getValue();
        String contents = XMLParser.convertDataPduToString(dataPDU);

        //stp service calling here
        MessageLogDTO messageLogDTO = MessageLogDTO.builder().build().setMessageSender(sender).setMessageReceiver(receiver).setMessageType(AppHdr.getMsgDefIdr()).setMessageFormat("MX").setMessageNetMir(AppHdr.getBizMsgIdr()).setMessageUserReference(msgUserReference).setBlock4(contents).setMsgPriority("GETBUSINESSDAYPERIOD").setBusinessMessageId(AppHdr.getBizMsgIdr()).setCreatedBy(currentUser.getId());
        messageProducerService.sendMessage("OUTWARD", messageLogDTO);

        return AppHdr.getBizMsgIdr();
    }

    public void processPACS008OutwardRequest(InterCustomerFundTransferEntity interC2Cout, CustomerFndTransferEntity c2cOutTxn, long userInfoId) {

        if (c2cOutTxn.getSentMsgId() != null) {
            iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document document = pacs008Generator.createMessage(c2cOutTxn);
            DataPDU dataPDU = mxmessageGenerateService.generate(document);
            BusinessApplicationHeaderV02 AppHdr = (BusinessApplicationHeaderV02) ((JAXBElement) dataPDU.getBody().getContent().get(0)).getValue();
            String contents = XMLParser.convertDataPduToString(dataPDU);

            messageService.createInOutMessage(document.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId(), interC2Cout.getBatchNumber(), c2cOutTxn.getReferenceNumber(), MessageDefinitionIdentifier.PACS008.value(), RoutingType.Outgoing.toString(), userInfoId);

            MsgLogEntity msgLogEntity = MsgLogEntity.builder()
                    .messageDirections(MessageDirectionsType.OUTWARD.name())
                    .messageFormat("MX")
                    .messageReceiver("BBHOBDDHXRTG")
                    .messageSender("CIBLBDDHAXXX")
                    .messageType(MessageDefinitionIdentifier.PACS008.value())
                    .messageUserReference(c2cOutTxn.getSentMsgId())
                    .mxMessage(contents)
                    .processed("")
                    .uuid(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().getPmtId().getUETR())
                    .msgDate(new Date())
                    .msgTime(new Date())
                    .businessMessageId(AppHdr.getBizMsgIdr())
                    .processStatus(MessageProcessStatus.QUEUED.name())
                    .messageNetMir(AppHdr.getBizMsgIdr())
                    .errorMessage("")
                    .build();
            msgLogEntity.setCreatedBy(userInfoId);
            msgLogEntity.setCreatedAt(new Date());
            if (!msgLogService.logExists(msgLogEntity.getBusinessMessageId())) {
                MsgLogEntity save = msgLogService.save(msgLogEntity);
                if (save.getId() > 0) {
                    messageProducerService.sendMessage("OUTWARD", save);
                }
            }

        }

    }

    public String processPACS009OutwardRequest(InterBankTransferEntity interB2bOut, BankFndTransferEntity b2bOutTxn) {
        if (interB2bOut.getBatchNumber() != null) {
            try {
                UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
                long reference = System.currentTimeMillis() / 1000;
                ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTC.name());
                String msgNetMir = referenceNo.getMsgNetMir();

                iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document document = pacs009Generator.createMessage(b2bOutTxn);

                DataPDU dataPDU = mxmessageGenerateService.generate(document);
                BusinessApplicationHeaderV02 AppHdr = (BusinessApplicationHeaderV02) ((JAXBElement) dataPDU.getBody().getContent().get(0)).getValue();
                String contents = XMLParser.convertDataPduToString(dataPDU);
                String msgUsrRef = "CI0000".concat(Long.toString(reference));
                messageService.createInOutMessage(document.getFICdtTrf().getGrpHdr().getMsgId(), interB2bOut.getBatchNumber(), b2bOutTxn.getReferenceNumber(), MessageDefinitionIdentifier.PACS009.value(), RoutingType.Outgoing.toString(), 0);

                //stp service calling here
                MessageLogDTO messageLogDTO = MessageLogDTO.builder().build().setMessageSender("CIBLBDDHAXXX").setMessageReceiver("BBHOBDDHXRTG").setMessageType(MessageDefinitionIdentifier.PACS009.value()).setMessageFormat("MX").setMessageNetMir(msgNetMir).setMessageUserReference(msgUsrRef).setBlock4(contents).setMsgPriority(b2bOutTxn.getPriorityCode()).setBusinessMessageId(AppHdr.getBizMsgIdr()).setCreatedBy(currentUser.getId());
                messageProducerService.sendMessage("OUTWARD", messageLogDTO);
            } catch (Exception e) {
                log.error("{}", e.getMessage());
            }
            return interB2bOut.getBatchNumber();
        } else {
            return null;
        }
    }

    public void processPacs004OutwardRequest(InterCustomerFundTransferEntity fundTransfer, CustomerFndTransferEntity fundTransferTxn, ReturnReason returnReason) {
        UserInfoEntity currentUser = LoggedInUserDetails.getUserInfoDetails();
        ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTB.name());
        String msgNetMir = referenceNoGenerateService.getReferenceNo(SequenceType.OUTC.name()).getMsgNetMir();
        String sentMsgId = referenceNo.getMessageId();
        String rtrId = referenceNo.getInstrId();
        long reference = System.currentTimeMillis() / 1000;
        String msgUsrRef = "CI0000".concat(Long.toString(reference));
        iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document document = pacs004Generator.createMessage(fundTransfer, fundTransferTxn, returnReason, sentMsgId, rtrId);

        DataPDU dataPDU = mxmessageGenerateService.generate(document);
        BusinessApplicationHeaderV02 AppHdr = (BusinessApplicationHeaderV02) ((JAXBElement) dataPDU.getBody().getContent().get(0)).getValue();
        String contents = XMLParser.convertDataPduToString(dataPDU);

        MessageLogDTO messageLogDTO = MessageLogDTO.builder().build().setMessageSender("CIBLBDDHAXXX").setMessageReceiver("BBHOBDDHXRTG").setMessageType(MessageDefinitionIdentifier.PACS004.value()).setMessageFormat("MX").setMessageNetMir(msgNetMir).setMessageUserReference(msgUsrRef).setBlock4(contents).setMsgPriority(fundTransferTxn.getPriorityCode()).setBusinessMessageId(AppHdr.getBizMsgIdr()).setCreatedBy(currentUser.getId());
        messageProducerService.sendMessage("OUTWARD", messageLogDTO);

        FailedTxnNotificationEntity failedTransactionNotification = new FailedTxnNotificationEntity();
        failedTransactionNotification.setErrorDate(LocalDateTime.now());
        failedTransactionNotification.setErrorCause(returnReason.getDescription());
        failedTransactionNotification.setOriginalMsgId(document.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgId());
        failedTransactionNotification.setPacs004MsgId(document.getPmtRtr().getGrpHdr().getMsgId());
        failedTransactionNotification.setMsgType(FundTransferType.CustomerToCustomer.toString());
        failedTxnNotificationRepository.save(failedTransactionNotification);
    }

    public ResponseDTO resendMessage(long id) {
        return processRequest(id);
    }

    public ResponseDTO processRequest(long id) {
        ResponseDTO response = null;
        Optional<MsgLogEntity> _entity = msgLogRepository.findByIdAndIsDeletedFalse(id);
        if (_entity.isPresent()) {
            MsgLogEntity entity = _entity.get();
            if (entity.getProcessStatus().equals(MessageProcessStatus.PROCESSED.toString())) {
                return ResponseDTO.builder().error(true).message("Message already processed.").build();
            } else {

                if (entity.getMessageDirections().equals("OUTWARD")) {
                    //process outward
                    response = dispatchOutwardRequest(entity);
                }
                if (entity.getMessageDirections().equals("INWARD")) {
                    // process inward
                    response = processInwardRequest(entity);
                }
            }
        } else {
            return ResponseDTO.builder().error(true).message("Invalid message id: " + id).build();
        }
        return response;
    }

    public ResponseDTO dispatchOutwardRequest(MsgLogEntity message) {
        String msgUsrRef = message.getMessageUserReference();
        String msgNetMir = message.getMessageNetMir();
        String businessMessageId = message.getBusinessMessageId();
        String contents = message.getMxMessage();
        String msgProcessSts;
        Long userInfoId = LoggedInUserDetails.getUserInfoDetails().getId();
        String errorMessage;
        try {
            MessageLogDTO messageLogDTO = MessageLogDTO.builder().build().setMessageSender("CIBLBDDHAXXX").setMessageReceiver("BBHOBDDHXRTG").setMessageType(message.getMessageType()).setMessageFormat("MX").setMessageNetMir(msgNetMir).setMessageUserReference(msgUsrRef).setBlock4(contents).setMsgPriority("").setBusinessMessageId(businessMessageId);

            //stp service calling here
            STPResponse stpResponse = stpService.invokeSTPService(messageLogDTO);
            if (Objects.nonNull(stpResponse)) {
                if (Objects.isNull(stpResponse.getType())) {
                    msgProcessSts = MessageProcessStatus.UNPROCESSED.toString();
                    errorMessage = "STP response code: " + stpResponse.getResponseCode();
                } else if (stpResponse.getType().equals("ACK")) {
                    msgProcessSts = MessageProcessStatus.PROCESSED.toString();
                    errorMessage = "STP response code: " + stpResponse.getResponseCode();
                } else {
                    msgProcessSts = MessageProcessStatus.UNPROCESSED.toString();
                    errorMessage = "STP response code: " + stpResponse.getResponseCode();
                }
            } else {
                msgProcessSts = MessageProcessStatus.UNPROCESSED.toString();
                errorMessage = "No Response found From STP";
            }

            message.setProcessStatus(msgProcessSts);
            message.setUpdatedBy(userInfoId);
            message.setUpdatedAt(new Date());
            message.setId(message.getId());
            msgLogRepository.save(message);
        } catch (RtgsMessageException mex) {
            log.error(mex.getMessage(), mex);
            errorMessage = mex.getMessage();
            message.setProcessStatus(MessageProcessStatus.UNPROCESSED.toString());
            message.setUpdatedBy(userInfoId);
            message.setId(message.getId());
            message.setUpdatedAt(new Date());
            message.setErrorMessage(mex.getMessage());
            msgLogRepository.save(message);
        }
        return ResponseDTO.builder().error(false).message(errorMessage).build();
    }

    private ResponseDTO processInwardRequest(MsgLogEntity message) {
        log.info("PROCESSING INWARD MESSAGE {} :{}", message.getMessageType(), message.getMxMessage());
        try {
            String block4 = message.getMxMessage();
            Map<String, Object> map = XMLParser.convertStringToPDUDocument(block4);

            Inward inward = InwardFactory.getService(message.getMessageType());
            inward.processInward(message.getId(), map.get("document"));
            return ResponseDTO.builder().error(false).message("PROCESSING INWARD MESSAGE : " + message.getMessageType() + " : " + message.getMessageUserReference()).build();

        } catch (Exception ex) {
            return ResponseDTO.builder().error(true).message(ex.getMessage()).build();
        }
    }
}
