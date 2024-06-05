//package com.cbl.cityrtgs.services.datapdu.pdu;
//
//import com.cbl.cityrtgs.services.datapdu.MessageDefinitionMapping;
//import com.cbl.cityrtgs.services.datapdu.PDU;
//import iso20022.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
//import iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document;
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
//public class Pacs00400104PDU implements PDU {
//
//    private String dataPDUxmlns = "urn:swift:saa:xsd:saa.2.0";
//    private String AppHdrXmlns = "urn:iso:std:iso:20022:tech:xsd:head.001.001.01";
//    private String documentXmlns;
//    private BusinessApplicationHeaderV01 AppHdrs;
//    private Document document;
//
//    public Pacs00400104PDU(String messageDefinitionIdentifier, BusinessApplicationHeaderV01 AppHdrs, Document document) {
//        this.documentXmlns = MessageDefinitionMapping.attributes.get(messageDefinitionIdentifier);
//        this.AppHdrs = AppHdrs;
//        this.document = document;
//    }
//
//    @Override
//    public String createPDU() {
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
//            Element PmtRtr = Document.addElement("PmtRtr");
//
//            Element GrpHdr = PmtRtr.addElement("GrpHdr");
//            Element MsgId = GrpHdr.addElement("MsgId");
//            MsgId.addText(document.getPmtRtr().getGrpHdr().getMsgId());
//
//            Element CreDtTm = GrpHdr.addElement("CreDtTm");
//            CreDtTm.addText(String.valueOf(document.getPmtRtr().getGrpHdr().getCreDtTm()));
//
//            Element NbOfTxs = GrpHdr.addElement("NbOfTxs");
//            NbOfTxs.addText(document.getPmtRtr().getGrpHdr().getNbOfTxs());
//
//            Element SttlmInf = GrpHdr.addElement("SttlmInf");
//            Element SttlmMtd = SttlmInf.addElement("SttlmMtd");
//            SttlmMtd.addText(document.getPmtRtr().getGrpHdr().getSttlmInf().getSttlmMtd().value());
//
//            Element OrgnlGrpInf = PmtRtr.addElement("OrgnlGrpInf");
//            Element OrgnlMsgId = OrgnlGrpInf.addElement("OrgnlMsgId");
//            OrgnlMsgId.addText(document.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgId());
//
//            Element OrgnlMsgNmId = OrgnlGrpInf.addElement("OrgnlMsgNmId");
//            OrgnlMsgNmId.addText(document.getPmtRtr().getOrgnlGrpInf().getOrgnlMsgNmId());
//
//            Element OrgnlCreDtTm = OrgnlGrpInf.addElement("OrgnlCreDtTm");
//            OrgnlCreDtTm.addText(String.valueOf(document.getPmtRtr().getOrgnlGrpInf().getOrgnlCreDtTm()));
//
//            Element RtrRsnInf = OrgnlGrpInf.addElement("RtrRsnInf");
//            Element Rsn = RtrRsnInf.addElement("Rsn");
//            Element Prtry = Rsn.addElement("Prtry");
//            Prtry.addText(document.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getRsn().getPrtry());
//
//            Element AddtlInf = RtrRsnInf.addElement("AddtlInf");
//            AddtlInf.addText(document.getPmtRtr().getOrgnlGrpInf().getRtrRsnInf().getAddtlInf().get(0));
//
//            Element TxInf = PmtRtr.addElement("TxInf");
//            Element RtrId = TxInf.addElement("RtrId");
//            RtrId.addText(document.getPmtRtr().getTxInf().getRtrId());
//
//            Element OrgnlInstrId = TxInf.addElement("OrgnlInstrId");
//            OrgnlInstrId.addText(document.getPmtRtr().getTxInf().getOrgnlInstrId());
//
//            Element OrgnlEndToEndId = TxInf.addElement("OrgnlEndToEndId");
//            OrgnlEndToEndId.addText(document.getPmtRtr().getTxInf().getOrgnlEndToEndId());
//
//            Element OrgnlTxId = TxInf.addElement("OrgnlTxId");
//            OrgnlTxId.addText(document.getPmtRtr().getTxInf().getOrgnlTxId());
//
//            Element RtrdIntrBkSttlmAmt = TxInf.addElement("RtrdIntrBkSttlmAmt").addAttribute(new QName("Ccy"), document.getPmtRtr().getTxInf().getRtrdIntrBkSttlmAmt().getCcy());
//            RtrdIntrBkSttlmAmt.addText(String.valueOf(document.getPmtRtr().getTxInf().getRtrdIntrBkSttlmAmt().getValue()));
//
//            Element IntrBkSttlmDt = TxInf.addElement("IntrBkSttlmDt");
//            IntrBkSttlmDt.addText(String.valueOf(document.getPmtRtr().getTxInf().getIntrBkSttlmDt()));
//
//            Element ChrgBr = TxInf.addElement("ChrgBr");
//            ChrgBr.addText(document.getPmtRtr().getTxInf().getChrgBr().value());
//
//            Element InstgAgt = TxInf.addElement("InstgAgt");
//            Element FinInstnId2 = InstgAgt.addElement("FinInstnId");
//            Element BICFI2 = FinInstnId2.addElement("BICFI");
//            BICFI2.addText(document.getPmtRtr().getTxInf().getInstgAgt().getFinInstnId().getBICFI());
//
//            Element InstdAgt = TxInf.addElement("InstdAgt");
//            Element FinInstnId3 = InstdAgt.addElement("FinInstnId");
//            Element BICFI3 = FinInstnId3.addElement("BICFI");
//            BICFI3.addText(document.getPmtRtr().getTxInf().getInstdAgt().getFinInstnId().getBICFI());
//
//            Element OrgnlTxRef = TxInf.addElement("OrgnlTxRef");
//            Element IntrBkSttlmAmt = OrgnlTxRef.addElement("IntrBkSttlmAmt").addAttribute(new QName("Ccy"), document.getPmtRtr().getTxInf().getOrgnlTxRef().getIntrBkSttlmAmt().getCcy());
//            IntrBkSttlmAmt.addText(String.valueOf(document.getPmtRtr().getTxInf().getOrgnlTxRef().getIntrBkSttlmAmt().getValue()));
//
//            Element IntrBkSttlmDt2 = OrgnlTxRef.addElement("IntrBkSttlmDt");
//            IntrBkSttlmDt2.addText(String.valueOf(document.getPmtRtr().getTxInf().getOrgnlTxRef().getIntrBkSttlmDt()));
//
//            Element PmtTpInf = OrgnlTxRef.addElement("PmtTpInf");
//            Element ClrChanl = PmtTpInf.addElement("ClrChanl");
//            ClrChanl.addText(String.valueOf(document.getPmtRtr().getTxInf().getOrgnlTxRef().getPmtTpInf().getClrChanl()));
//
//            Element SvcLvl = PmtTpInf.addElement("SvcLvl");
//            Element Prtry2 = SvcLvl.addElement("Prtry");
//            Prtry2.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getPmtTpInf().getSvcLvl().getPrtry());
//
//            Element LclInstrm = PmtTpInf.addElement("LclInstrm");
//            Element Prtry3 = LclInstrm.addElement("Prtry");
//            Prtry3.addText(String.valueOf(document.getPmtRtr().getTxInf().getOrgnlTxRef().getPmtTpInf().getLclInstrm().getPrtry()));
//
//            Element CtgyPurp = PmtTpInf.addElement("CtgyPurp");
//            Element Prtry4 = CtgyPurp.addElement("Prtry");
//            Prtry4.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getPmtTpInf().getCtgyPurp().getPrtry());
//
//            Element PmtMtd = OrgnlTxRef.addElement("PmtMtd");
//            PmtMtd.addText(String.valueOf(document.getPmtRtr().getTxInf().getOrgnlTxRef().getPmtMtd()));
//
//            Element Dbtr = OrgnlTxRef.addElement("Dbtr");
//            Element Nm = Dbtr.addElement("Nm");
//            Nm.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getDbtr().getNm());
//
//            Element DbtrAcct = OrgnlTxRef.addElement("DbtrAcct");
//            Element Id2 = DbtrAcct.addElement("Id");
//            Element Othr = Id2.addElement("Othr");
//            Element Id3 = Othr.addElement("Id");
//            Id3.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getDbtrAcct().getId().getOthr().getId());
//
//            Element DbtrAgt = OrgnlTxRef.addElement("DbtrAgt");
//            Element FinInstnId4 = DbtrAgt.addElement("FinInstnId");
//            Element BICFI4 = FinInstnId4.addElement("BICFI");
//            BICFI4.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getDbtrAgt().getFinInstnId().getBICFI());
//
//            Element BrnchId1 = DbtrAgt.addElement("BrnchId");
//            Element Id1 = BrnchId1.addElement("Id");
//            Id1.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getDbtrAgt().getBrnchId().getId());
//
//            Element DbtrAgtAcct = OrgnlTxRef.addElement("DbtrAgtAcct");
//            Element Id4 = DbtrAgtAcct.addElement("Id");
//            Element Othr2 = Id4.addElement("Othr");
//            Element Id5 = Othr2.addElement("Id");
//            Id5.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getDbtrAgtAcct().getId().getOthr().getId());
//
//            Element CdtrAgt = OrgnlTxRef.addElement("CdtrAgt");
//            Element FinInstnId5 = CdtrAgt.addElement("FinInstnId");
//            Element BICFI5 = FinInstnId5.addElement("BICFI");
//            BICFI5.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getCdtrAgt().getFinInstnId().getBICFI());
//
//            Element BrnchId2 = CdtrAgt.addElement("BrnchId");
//            Element Id6 = BrnchId2.addElement("Id");
//            Id6.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getCdtrAgt().getBrnchId().getId());
//
//            Element CdtrAgtAcct = OrgnlTxRef.addElement("CdtrAgtAcct");
//            Element Id7 = CdtrAgtAcct.addElement("Id");
//            Element Othr3 = Id7.addElement("Othr");
//            Element Id8 = Othr3.addElement("Id");
//            Id8.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getCdtrAgtAcct().getId().getOthr().getId());
//
//            Element Cdtr = OrgnlTxRef.addElement("Cdtr");
//            Element Nm2 = Cdtr.addElement("Nm");
//            Nm2.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getCdtr().getNm());
//
//            Element CdtrAcct = OrgnlTxRef.addElement("CdtrAcct");
//            Element Id9 = CdtrAcct.addElement("Id");
//            Element Othr1 = Id9.addElement("Othr");
//            Element Id10 = Othr1.addElement("Id");
//            Id10.addText(document.getPmtRtr().getTxInf().getOrgnlTxRef().getCdtrAcct().getId().getOthr().getId());
//            /*Document END*/
//
//            dataPDU = doc.asXML();
//
//        } catch (Exception e) {
//            log.info("{}", e.getMessage());
//        }
//
//        return dataPDU;
//    }
//}
