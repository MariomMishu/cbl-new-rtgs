//package com.cbl.cityrtgs.services.datapdu.pdu;
//
//import com.cbl.cityrtgs.services.datapdu.MessageDefinitionMapping;
//import com.cbl.cityrtgs.services.datapdu.PDU;
//import iso20022.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
//import iso20022.swift.xsd.camt_018_001.Document;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.dom4j.DocumentFactory;
//import org.dom4j.Element;
//import org.dom4j.QName;
//
//@Slf4j
//@NoArgsConstructor
//@Getter
//@Setter
//@ToString
//public class Camt01800103PDU implements PDU {
//
//    private String dataPDUxmlns = "urn:swift:saa:xsd:saa.2.0";
//    private String AppHdrXmlns = "urn:iso:std:iso:20022:tech:xsd:head.001.001.01";
//    private String documentXmlns;
//    private BusinessApplicationHeaderV01 AppHdrs;
//    private Document document;
//
//    public Camt01800103PDU(String messageDefinitionIdentifier, BusinessApplicationHeaderV01 AppHdrs, iso20022.swift.xsd.camt_018_001.Document document) {
//        this.documentXmlns = MessageDefinitionMapping.attributes.get(messageDefinitionIdentifier);
//        this.AppHdrs = AppHdrs;
//        this.document = document;
//    }
//
//    @Override
//    public String createPDU() {
//
//        String dataPDU = "";
//
//        try {
//
//            org.dom4j.Document doc = DocumentFactory.getInstance().createDocument();
//            Element root = doc.addElement("DataPDU", dataPDUxmlns).addAttribute(new QName("xmlns"), dataPDUxmlns);
//
//            Element Revision = root.addElement("Revision");
//            Revision.addText("2.0.5");
//
//            Element Body = root.addElement("Body");
//
//            Element AppHdr = Body.addElement("AppHdr", AppHdrXmlns).addAttribute(new QName("xmlns"), AppHdrXmlns);
//
//            Element Fr = AppHdr.addElement("Fr");
//            Element FIId = Fr.addElement("FIId");
//            Element FinInstnId = FIId.addElement("FinInstnId");
//            Element BICFI = FinInstnId.addElement("BICFI");
//            BICFI.addText(AppHdrs.getFr().getFIId().getFinInstnId().getBICFI());
//
//            Element To = AppHdr.addElement("To");
//            Element FIId1 = To.addElement("FIId");
//            Element FinInstnId1 = FIId1.addElement("FinInstnId");
//            Element BICFI1 = FinInstnId1.addElement("BICFI");
//            BICFI1.addText(AppHdrs.getTo().getFIId().getFinInstnId().getBICFI());
//
//            Element BizMsgIdr = FinInstnId1.addElement("BizMsgIdr");
//            BizMsgIdr.addText(AppHdrs.getBizMsgIdr());
//
//            Element MsgDefIdr = FinInstnId1.addElement("MsgDefIdr");
//            MsgDefIdr.addText(AppHdrs.getMsgDefIdr());
//
//            Element BizSvc = FinInstnId1.addElement("BizSvc");
//            BizSvc.addText(AppHdrs.getBizSvc());
//
//            Element CreDt = FinInstnId1.addElement("CreDt");
//            CreDt.addText(String.valueOf(AppHdrs.getCreDt()));
//            Element Document = Body.addElement("Document", documentXmlns).addAttribute(new QName("xmlns"), documentXmlns);
//
//            Element GetBizDayInf = Document.addElement("GetBizDayInf");
//            Element MsgHdr = GetBizDayInf.addElement("MsgHdr");
//
//            Element MsgId = MsgHdr.addElement("MsgId");
//            MsgId.addText(document.getGetBizDayInf().getMsgHdr().getMsgId());
//            Element CreDtTm = MsgHdr.addElement("CreDtTm");
//            CreDtTm.addText(document.getGetBizDayInf().getMsgHdr().getCreDtTm().toString());
//
//            Element BizDayInfQryDef = GetBizDayInf.addElement("BizDayInfQryDef");
//            Element Crit = BizDayInfQryDef.addElement("Crit");
//            Element NewCrit = Crit.addElement("NewCrit");
//            Element SchCrit = NewCrit.addElement("SchCrit");
//            Element EvtTp = SchCrit.addElement("EvtTp");
//            Element Prtry = EvtTp.addElement("Prtry");
//            Element Id = Prtry.addElement("Id");
//            Id.addText(document.getGetBizDayInf().getBizDayInfQryDef().getCrit().getNewCrit().getSchCrit().getEvtTp().getPrtry().getId());
//
//            dataPDU = doc.asXML();
//        } catch (Exception e) {
//            log.info("{}", e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//
//        return dataPDU;
//    }
//}
