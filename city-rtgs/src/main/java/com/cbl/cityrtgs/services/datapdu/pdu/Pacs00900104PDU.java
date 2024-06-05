//package com.cbl.cityrtgs.services.datapdu.pdu;
//
//import com.cbl.cityrtgs.services.datapdu.MessageDefinitionMapping;
//import com.cbl.cityrtgs.services.datapdu.PDU;
//import iso20022.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
//import iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document;
//import lombok.NoArgsConstructor;
//import lombok.ToString;
//import lombok.extern.slf4j.Slf4j;
//import org.dom4j.DocumentFactory;
//import org.dom4j.Element;
//import org.dom4j.QName;
//
//@Slf4j
//@NoArgsConstructor
//@ToString
//public class Pacs00900104PDU implements PDU {
//
//    private String dataPDUxmlns = "urn:swift:saa:xsd:saa.2.0";
//    private String AppHdrXmlns = "urn:iso:std:iso:20022:tech:xsd:head.001.001.01";
//    private String documentXmlns;
//    private BusinessApplicationHeaderV01 AppHdrs;
//    private Document document;
//
//    public Pacs00900104PDU(String messageDefinitionIdentifier, BusinessApplicationHeaderV01 AppHdrs, iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document document) {
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
//
//            Element root = doc.addElement("DataPDU", dataPDUxmlns).addAttribute(new QName("xmlns"), dataPDUxmlns);
//
//            Element Revision = root.addElement("Revision");
//            Revision.addText("2.0.5");
//
//            Element Body = root.addElement("Body");
//
//            /*App Header Start*/
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
//            Element BizMsgIdr = AppHdr.addElement("BizMsgIdr");
//            BizMsgIdr.addText(AppHdrs.getBizMsgIdr());
//
//            Element MsgDefIdr = AppHdr.addElement("MsgDefIdr");
//            MsgDefIdr.addText(AppHdrs.getMsgDefIdr());
//
//            Element BizSvc = AppHdr.addElement("BizSvc");
//            BizSvc.addText(AppHdrs.getBizSvc());
//
//            Element CreDt = AppHdr.addElement("CreDt");
//            CreDt.addText(String.valueOf(AppHdrs.getCreDt()));
//            /*App Header End*/
//
//
//            /*Document Start*/
//            Element Document = Body.addElement("Document", documentXmlns).addAttribute(new QName("xmlns"), documentXmlns);
//
//            Element FICdtTrf = Document.addElement("FICdtTrf");
//            Element GrpHdr = FICdtTrf.addElement("GrpHdr");
//            Element MsgId = GrpHdr.addElement("MsgId");
//            MsgId.addText(document.getFICdtTrf().getGrpHdr().getMsgId());
//
//            Element CreDtTm = GrpHdr.addElement("CreDtTm");
//            CreDtTm.addText(String.valueOf(document.getFICdtTrf().getGrpHdr().getCreDtTm()));
//
//            Element NbOfTxs = GrpHdr.addElement("NbOfTxs");
//            NbOfTxs.addText(document.getFICdtTrf().getGrpHdr().getNbOfTxs());
//
//            Element SttlmInf = GrpHdr.addElement("SttlmInf");
//            Element SttlmMtd = SttlmInf.addElement("SttlmMtd");
//            SttlmMtd.addText(document.getFICdtTrf().getGrpHdr().getSttlmInf().getSttlmMtd().value());
//
//            Element CdtTrfTxInf = FICdtTrf.addElement("CdtTrfTxInf");
//            Element PmtId = CdtTrfTxInf.addElement("PmtId");
//
//            Element InstrId = PmtId.addElement("InstrId");
//            InstrId.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtId().getInstrId());
//            Element EndToEndId = PmtId.addElement("EndToEndId");
//            EndToEndId.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtId().getEndToEndId());
//            Element TxId = PmtId.addElement("TxId");
//            TxId.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtId().getTxId());
//
//            Element PmtTpInf = CdtTrfTxInf.addElement("PmtTpInf");
//            Element ClrChanl = PmtTpInf.addElement("ClrChanl");
//            ClrChanl.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtTpInf().getClrChanl().value());
//
//            Element SvcLvl = PmtTpInf.addElement("SvcLvl");
//            Element Prtry = SvcLvl.addElement("Prtry");
//            Prtry.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtTpInf().getSvcLvl().getPrtry());
//
//            Element LclInstrm = PmtTpInf.addElement("LclInstrm");
//            Element Prtry1 = LclInstrm.addElement("Prtry");
//            Prtry1.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtTpInf().getLclInstrm().getPrtry().value());
//
//            Element CtgyPurp = PmtTpInf.addElement("CtgyPurp");
//            Element Prtry2 = CtgyPurp.addElement("Prtry");
//            Prtry2.addText(document.getFICdtTrf().getCdtTrfTxInf().getPmtTpInf().getCtgyPurp().getPrtry());
//
//            Element IntrBkSttlmAmt = CdtTrfTxInf.addElement("IntrBkSttlmAmt").addAttribute(new QName("Ccy"), document.getFICdtTrf().getCdtTrfTxInf().getIntrBkSttlmAmt().getCcy());
//            IntrBkSttlmAmt.addText(String.valueOf(document.getFICdtTrf().getCdtTrfTxInf().getIntrBkSttlmAmt().getValue()));
//
//            Element IntrBkSttlmDt = CdtTrfTxInf.addElement("IntrBkSttlmDt");
//            IntrBkSttlmDt.addText(String.valueOf(document.getFICdtTrf().getCdtTrfTxInf().getIntrBkSttlmDt()));
//
//            Element InstgAgt = CdtTrfTxInf.addElement("InstgAgt");
//            Element FinInstnId2 = InstgAgt.addElement("FinInstnId");
//            Element BICFI2 = FinInstnId2.addElement("BICFI");
//            BICFI2.addText(document.getFICdtTrf().getCdtTrfTxInf().getInstgAgt().getFinInstnId().getBICFI());
//
//            Element InstdAgt = CdtTrfTxInf.addElement("InstdAgt");
//            Element FinInstnId3 = InstdAgt.addElement("FinInstnId");
//            Element BICFI3 = FinInstnId3.addElement("BICFI");
//            BICFI3.addText(document.getFICdtTrf().getCdtTrfTxInf().getInstdAgt().getFinInstnId().getBICFI());
//
//            Element Dbtr = CdtTrfTxInf.addElement("Dbtr");
//            Element FinInstnId4 = Dbtr.addElement("FinInstnId");
//            Element BICFI4 = FinInstnId4.addElement("BICFI");
//            BICFI4.addText(document.getFICdtTrf().getCdtTrfTxInf().getDbtr().getFinInstnId().getBICFI());
//
//            Element BrnchId = Dbtr.addElement("BrnchId");
//            Element Id = BrnchId.addElement("Id");
//            Id.addText(document.getFICdtTrf().getCdtTrfTxInf().getDbtr().getBrnchId().getId());
//
//            Element DbtrAcct = CdtTrfTxInf.addElement("DbtrAcct");
//            Element Id2 = DbtrAcct.addElement("Id");
//            Element Othr = Id2.addElement("Othr");
//            Element Id3 = Othr.addElement("Id");
//            Id3.addText(document.getFICdtTrf().getCdtTrfTxInf().getDbtrAcct().getId().getOthr().getId());
//
//            Element Cdtr = CdtTrfTxInf.addElement("Cdtr");
//            Element FinInstnId5 = Cdtr.addElement("FinInstnId");
//            Element BICFI5 = FinInstnId5.addElement("BICFI");
//            BICFI5.addText(document.getFICdtTrf().getCdtTrfTxInf().getCdtr().getFinInstnId().getBICFI());
//
//            Element BrnchId1 = Cdtr.addElement("BrnchId");
//            Element Id1 = BrnchId1.addElement("Id");
//            Id1.addText(document.getFICdtTrf().getCdtTrfTxInf().getCdtr().getBrnchId().getId());
//
//            Element CdtrAcct = CdtTrfTxInf.addElement("CdtrAcct");
//            Element Id4 = CdtrAcct.addElement("Id");
//            Element Othr1 = Id4.addElement("Othr");
//            Element Id5 = Othr1.addElement("Id");
//            Id5.addText(document.getFICdtTrf().getCdtTrfTxInf().getCdtrAcct().getId().getOthr().getId());
//
//            for (int i = 0; i < document.getFICdtTrf().getCdtTrfTxInf().getInstrForNxtAgt().size(); ++i) {
//                Element InstrForNxtAgt = CdtTrfTxInf.addElement("InstrForNxtAgt");
//                Element InstrInf = InstrForNxtAgt.addElement("InstrInf");
//                InstrInf.addText(document.getFICdtTrf().getCdtTrfTxInf().getInstrForNxtAgt().get(i).getInstrInf());
//            }
//            /*Document END*/
//
//            dataPDU = doc.asXML();
//
//        } catch (Exception e) {
//            log.info("{}", e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//
//        return dataPDU;
//    }
//}
