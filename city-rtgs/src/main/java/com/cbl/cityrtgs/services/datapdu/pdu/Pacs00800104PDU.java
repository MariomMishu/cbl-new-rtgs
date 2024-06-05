//package com.cbl.cityrtgs.services.datapdu.pdu;
//
//import com.cbl.cityrtgs.services.datapdu.MessageDefinitionMapping;
//import com.cbl.cityrtgs.services.datapdu.PDU;
//import iso20022.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
//import iso20022.iso.std.iso._20022.tech.xsd.pacs_008_001.Document;
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
//public class Pacs00800104PDU implements PDU {
//
//    private String dataPDUxmlns = "urn:swift:saa:xsd:saa.2.0";
//    private String AppHdrXmlns = "urn:iso:std:iso:20022:tech:xsd:head.001.001.01";
//    private String documentXmlns;
//    private BusinessApplicationHeaderV01 AppHdrs;
//    private Document document;
//
//    public Pacs00800104PDU(String messageDefinitionIdentifier, BusinessApplicationHeaderV01 AppHdrs, Document document) {
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
//            Element FIToFICstmrCdtTrf = Document.addElement("FIToFICstmrCdtTrf");
//            Element GrpHdr = FIToFICstmrCdtTrf.addElement("GrpHdr");
//            Element MsgId = GrpHdr.addElement("MsgId");
//            MsgId.addText(document.getFIToFICstmrCdtTrf().getGrpHdr().getMsgId());
//
//            Element CreDtTm = GrpHdr.addElement("CreDtTm");
//            CreDtTm.addText(String.valueOf(document.getFIToFICstmrCdtTrf().getGrpHdr().getCreDtTm()));
//
//            Element NbOfTxs = GrpHdr.addElement("NbOfTxs");
//            NbOfTxs.addText(document.getFIToFICstmrCdtTrf().getGrpHdr().getNbOfTxs());
//
//            Element SttlmInf = GrpHdr.addElement("SttlmInf");
//            Element SttlmMtd = SttlmInf.addElement("SttlmMtd");
//            SttlmMtd.addText(document.getFIToFICstmrCdtTrf().getGrpHdr().getSttlmInf().getSttlmMtd().value());
//
//            Element CdtTrfTxInf = FIToFICstmrCdtTrf.addElement("CdtTrfTxInf");
//            Element PmtId = CdtTrfTxInf.addElement("PmtId");
//            Element InstrId = PmtId.addElement("InstrId");
//            InstrId.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getInstrId());
//
//            Element EndToEndId = PmtId.addElement("EndToEndId");
//            EndToEndId.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getEndToEndId());
//            Element TxId = PmtId.addElement("TxId");
//            TxId.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtId().getTxId());
//
//            Element PmtTpInf = CdtTrfTxInf.addElement("PmtTpInf");
//            Element ClrChanl = PmtTpInf.addElement("ClrChanl");
//            ClrChanl.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getClrChanl().value());
//
//            Element SvcLvl = PmtTpInf.addElement("SvcLvl");
//            Element Prtry = SvcLvl.addElement("Prtry");
//            Prtry.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getSvcLvl().getPrtry());
//
//            Element LclInstrm = PmtTpInf.addElement("LclInstrm");
//            Element Prtry1 = LclInstrm.addElement("Prtry");
//            Prtry1.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getLclInstrm().getPrtry().value());
//
//            Element CtgyPurp = PmtTpInf.addElement("CtgyPurp");
//            Element Prtry2 = CtgyPurp.addElement("Prtry");
//            Prtry2.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getCtgyPurp().getPrtry());
//
//            Element IntrBkSttlmAmt = CdtTrfTxInf.addElement("IntrBkSttlmAmt").addAttribute(new QName("Ccy"), document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy());
//            IntrBkSttlmAmt.addText(String.valueOf(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getValue()));
//
//            Element IntrBkSttlmDt = CdtTrfTxInf.addElement("IntrBkSttlmDt");
//            IntrBkSttlmDt.addText(String.valueOf(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmDt()));
//
//            Element ChrgBr = CdtTrfTxInf.addElement("ChrgBr");
//            ChrgBr.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getChrgBr().value());
//
//            Element InstgAgt = CdtTrfTxInf.addElement("InstgAgt");
//            Element FinInstnId2 = InstgAgt.addElement("FinInstnId");
//            Element BICFI2 = FinInstnId2.addElement("BICFI");
//            BICFI2.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstgAgt().getFinInstnId().getBICFI());
//
//            Element InstdAgt = CdtTrfTxInf.addElement("InstdAgt");
//            Element FinInstnId3 = InstdAgt.addElement("FinInstnId");
//            Element BICFI3 = FinInstnId3.addElement("BICFI");
//            BICFI3.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstdAgt().getFinInstnId().getBICFI());
//
//            Element Dbtr = CdtTrfTxInf.addElement("Dbtr");
//            Element Nm = Dbtr.addElement("Nm");
//            Nm.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtr().getNm());
//            Element DbtrAcct = CdtTrfTxInf.addElement("DbtrAcct");
//            Element Id2 = DbtrAcct.addElement("Id");
//            Element Othr = Id2.addElement("Othr");
//            Element Id3 = Othr.addElement("Id");
//            Id3.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAcct().getId().getOthr().getId());
//
//            Element DbtrAgt = CdtTrfTxInf.addElement("DbtrAgt");
//            Element FinInstnId4 = DbtrAgt.addElement("FinInstnId");
//            Element BICFI4 = FinInstnId4.addElement("BICFI");
//            BICFI4.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt().getFinInstnId().getBICFI());
//
//            Element BrnchId1 = DbtrAgt.addElement("BrnchId");
//            Element Id1 = BrnchId1.addElement("Id");
//            Id1.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgt().getBrnchId().getId());
//
//            Element DbtrAgtAcct = CdtTrfTxInf.addElement("DbtrAgtAcct");
//            Element Id4 = DbtrAgtAcct.addElement("Id");
//            Element Othr2 = Id4.addElement("Othr");
//            Element Id5 = Othr2.addElement("Id");
//            Id5.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getDbtrAgtAcct().getId().getOthr().getId());
//
//            Element CdtrAgt = CdtTrfTxInf.addElement("CdtrAgt");
//            Element FinInstnId5 = CdtrAgt.addElement("FinInstnId");
//            Element BICFI5 = FinInstnId5.addElement("BICFI");
//            BICFI5.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgt().getFinInstnId().getBICFI());
//
//            Element BrnchId2 = CdtrAgt.addElement("BrnchId");
//            Element Id6 = BrnchId2.addElement("Id");
//            Id6.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgt().getBrnchId().getId());
//
//            Element CdtrAgtAcct = CdtTrfTxInf.addElement("CdtrAgtAcct");
//            Element Id7 = CdtrAgtAcct.addElement("Id");
//            Element Othr3 = Id7.addElement("Othr");
//            Element Id8 = Othr3.addElement("Id");
//            Id8.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAgtAcct().getId().getOthr().getId());
//
//            Element Cdtr = CdtTrfTxInf.addElement("Cdtr");
//            Element Nm2 = Cdtr.addElement("Nm");
//            Nm2.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtr().getNm());
//            Element CdtrAcct = CdtTrfTxInf.addElement("CdtrAcct");
//            Element Id9 = CdtrAcct.addElement("Id");
//            Element Othr1 = Id9.addElement("Othr");
//            Element Id10 = Othr1.addElement("Id");
//            Id10.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getCdtrAcct().getId().getOthr().getId());
////            if(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getIntrBkSttlmAmt().getCcy().equals("BDT")) {
////                Element InstrForNxtAgt = CdtTrfTxInf.addElement("InstrForNxtAgt");
////                Element InstrInf = InstrForNxtAgt.addElement("InstrInf");
////                InstrInf.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstrForNxtAgt().get(0).getInstrInf());
////            }else{
//                for (int i = 0; i < document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstrForNxtAgt().size(); ++i) {
//                    Element InstrForNxtAgt = CdtTrfTxInf.addElement("InstrForNxtAgt");
//                    Element InstrInf = InstrForNxtAgt.addElement("InstrInf");
//                    InstrInf.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getInstrForNxtAgt().get(i).getInstrInf());
//                }
//           // }
//
//            if(!document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getPmtTpInf().getCtgyPurp().getPrtry().equals(TransactionTypeCodeEnum.ORDINARY_TRANSFER.getCode())) {
//                Element RmtInf = CdtTrfTxInf.addElement("RmtInf");
//                for (int i = 0; i < document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getRmtInf().getUstrd().size(); ++i) {
//                    Element Ustrd = RmtInf.addElement("Ustrd");
//                    Ustrd.addText(document.getFIToFICstmrCdtTrf().getCdtTrfTxInf().get(0).getRmtInf().getUstrd().get(i));
//                }
//            }
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
