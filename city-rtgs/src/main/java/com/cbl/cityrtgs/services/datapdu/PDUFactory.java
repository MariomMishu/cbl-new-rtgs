//package com.cbl.cityrtgs.services.datapdu;
//
//import com.cbl.cityrtgs.services.datapdu.pdu.Camt01800103PDU;
//import com.cbl.cityrtgs.services.datapdu.pdu.Pacs00400104PDU;
//import com.cbl.cityrtgs.services.datapdu.pdu.Pacs00800104PDU;
//import com.cbl.cityrtgs.services.datapdu.pdu.Pacs00900104PDU;
//import iso20022.iso.std.iso._20022.tech.xsd.head_001_001.BusinessApplicationHeaderV01;
//
//public class PDUFactory {
//
//    public static PDU getInstance(String messageDefinitionIdentifier, BusinessApplicationHeaderV01 AppHdr, DocumentTypeWrapper document) {
//
//        PDU pdu;
//
//        switch (messageDefinitionIdentifier) {
//
//            case "camt.018.001.03":
//                pdu = new Camt01800103PDU(messageDefinitionIdentifier, AppHdr, (iso20022.swift.xsd.camt_018_001.Document) document);
//                break;
//
//            case "pacs.009.001.04":
//                pdu = new Pacs00900104PDU(messageDefinitionIdentifier, AppHdr, (iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document) document);
//                break;
//
//            case "pacs.008.001.04":
//                pdu = new Pacs00800104PDU(messageDefinitionIdentifier, AppHdr, (iso20022.iso.std.iso._20022.tech.xsd.pacs_008_001.Document) document);
//                break;
//
//            case "pacs.004.001.04":
//                pdu = new Pacs00400104PDU(messageDefinitionIdentifier, AppHdr, (iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document) document);
//                break;
//            default:
//                pdu = null;
//        }
//
//        return pdu;
//    }
//}
