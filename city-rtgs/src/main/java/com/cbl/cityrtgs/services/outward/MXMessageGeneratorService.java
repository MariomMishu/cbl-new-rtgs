package com.cbl.cityrtgs.services.outward;

import com.cbl.cityrtgs.common.enums.SequenceType;
import com.cbl.cityrtgs.common.utility.DateUtility;
import com.cbl.cityrtgs.models.dto.message.MessageDefinitionIdentifier;
import com.cbl.cityrtgs.models.dto.message.RtgsDocument;
import com.cbl.cityrtgs.models.dto.transaction.ReferenceGenerateResponse;
import com.cbl.cityrtgs.services.transaction.ReferenceNoGenerateService;
import iso20022.iso.std.iso._20022.tech.xsd.head_001_002.*;
import iso20022.iso.std.iso._20022.tech.xsd.header.Header;
import iso20022.wrapper.DataPDU;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBElement;

@Slf4j
@RequiredArgsConstructor
@Service
public class MXMessageGeneratorService {
    private final ReferenceNoGenerateService referenceNoGenerateService;
    String bizMsgIdr = "";

    public DataPDU generate(RtgsDocument document) {
        ReferenceGenerateResponse getReferenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTD.name());
        bizMsgIdr = getReferenceNo.getBusinessMessageId();
        DataPDU dataPDU = new DataPDU();
        dataPDU.setRevision("2.0.11");
        dataPDU.setHeader(createHeader(bizMsgIdr,
                "CIBLBDDHAXXX", "CITY Bank PLC",
                "RTGSBDDHAXXX", "Bangladesh Bank", document));
        JAXBElement<?> appHeader = this.createHeaderElement(document);
        JAXBElement<?> message = this.createMessageElement(document);
        if (appHeader != null) {
            if (dataPDU.getBody() == null) {
                dataPDU.setBody(new DataPDU.Body());
            }
            dataPDU.getBody().getContent().add(appHeader);
        }

        dataPDU.getBody().getContent().add(message);
        return dataPDU;
    }

    private JAXBElement<?> createHeaderElement(RtgsDocument document) {
        if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document) {
            return (new ObjectFactory()).createAppHdr(this.createHead001(MessageDefinitionIdentifier.PACS009));
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) {
            return (new ObjectFactory()).createAppHdr(this.createHead001(MessageDefinitionIdentifier.PACS004));
        } else if (document instanceof iso20022.swift.xsd.camt_018_001.Document) {
            return (new ObjectFactory()).createAppHdr(this.createHead001(MessageDefinitionIdentifier.CAMT018));
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) {
            return (new ObjectFactory()).createAppHdr(this.createHead001(MessageDefinitionIdentifier.PACS008));
        } else {
            return null;
        }
    }

    private JAXBElement<?> createMessageElement(RtgsDocument document) {
        if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document) {
            return (new iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.ObjectFactory()).createDocument((iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document) document);
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) {
            return (new iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.ObjectFactory()).createDocument((iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) document);
        } else if (document instanceof iso20022.swift.xsd.camt_018_001.Document) {
            return (new iso20022.swift.xsd.camt_018_001.ObjectFactory()).createDocument((iso20022.swift.xsd.camt_018_001.Document) document);
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) {
            return (new iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.ObjectFactory()).createDocument((iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) document);
        } else {
            return null;
        }
    }


    private BusinessApplicationHeaderV02 createHead001(MessageDefinitionIdentifier msgIdentifier) {
        // ReferenceGenerateResponse referenceNo = referenceNoGenerateService.getReferenceNo(SequenceType.OUTD.name());

        BusinessApplicationHeaderV02 businessApplicationHeader = new BusinessApplicationHeaderV02();
        Party44Choice1 from = new Party44Choice1();
        from.setFIId(new BranchAndFinancialInstitutionIdentification65());
        from.getFIId().setFinInstnId(new FinancialInstitutionIdentification184());
        from.getFIId().getFinInstnId().setBICFI("CIBLBDDH");
        Party44Choice1 to = new Party44Choice1();
        to.setFIId(new BranchAndFinancialInstitutionIdentification65());
        to.getFIId().setFinInstnId(new FinancialInstitutionIdentification184());
        to.getFIId().getFinInstnId().setBICFI("BBHOBDDH");
        businessApplicationHeader.setFr(from);
        businessApplicationHeader.setTo(to);
        businessApplicationHeader.setMsgDefIdr(msgIdentifier.toString());
        businessApplicationHeader.setBizSvc("bd.rtgs.02");
        businessApplicationHeader.setCreDt(DateUtility.creDt());
        businessApplicationHeader.setBizMsgIdr(bizMsgIdr);
        return businessApplicationHeader;
    }

    private Header createHeader(String refno,
                                String senderBic,
                                String senderName,
                                String receiverBic,
                                String receiverName,
                                RtgsDocument document) {
        String msgIdentifier = "";
        if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.Document) {
            msgIdentifier = MessageDefinitionIdentifier.PACS009.value();
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) {
            msgIdentifier = MessageDefinitionIdentifier.PACS004.value();
        } else if (document instanceof iso20022.swift.xsd.camt_018_001.Document) {
            msgIdentifier = MessageDefinitionIdentifier.CAMT018.value();
        } else if (document instanceof iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document) {
            msgIdentifier = MessageDefinitionIdentifier.PACS008.value();
        }
        Header header = new Header();
        Header.Message message = new Header.Message();
        message.setSenderReference(refno);
        message.setMessageIdentifier(msgIdentifier);
        message.setFormat("AnyXML");
        Header.Message.Sender sender = new Header.Message.Sender();
        sender.setBIC12(senderBic);
        Header.Message.Sender.FullName fullName = new Header.Message.Sender.FullName();
        fullName.setX1(senderName);
        sender.setFullName(fullName);
        message.setSender(sender);
        Header.Message.Receiver receiver = new Header.Message.Receiver();
        receiver.setBIC12(receiverBic);
        Header.Message.Receiver.FullName fullName1 = new Header.Message.Receiver.FullName();
        fullName1.setX1(receiverName);
        receiver.setFullName(fullName1);
        message.setReceiver(receiver);
        Header.Message.InterfaceInfo interfaceInfo = new Header.Message.InterfaceInfo();
        interfaceInfo.setUserReference(refno);
        message.setInterfaceInfo(interfaceInfo);
        Header.Message.NetworkInfo networkInfo = new Header.Message.NetworkInfo();
        networkInfo.setPriority("Normal");
        networkInfo.setNetwork("Application");
        message.setNetworkInfo(networkInfo);
        header.setMessage(message);

        return header;
    }


}
