package com.cbl.cityrtgs.common.utility;

import iso20022.wrapper.DataPDU;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;

import javax.xml.bind.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.ByteArrayInputStream;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class XMLParser {
//    public static Map<String, Object> convertStringToPDUDocument(String block4) {
//
//        Map<String, Object> map = new HashMap<>();
//
//        try {
//            // JAXBContext context = JAXBContext.newInstance("iso20022.swift.xsd.camt_018_001:iso20022.swift.xsd.camt_019_001:iso20022.swift.xsd.camt_025_001:iso20022.iso.std.iso._20022.tech.xsd.camt_052_001:iso20022.iso.std.iso._20022.tech.xsd.camt_053_001:iso20022.iso.std.iso._20022.tech.xsd.camt_054_001:iso20022.iso.std.iso._20022.tech.xsd.head_001_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_008_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001:iso20022.wrapper");
//
//            JAXBContext context = JAXBContext.newInstance(iso20022.wrapper.DataPDU.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.head_001_002.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.ObjectFactory.class,
//                    iso20022.swift.xsd.camt_018_001.ObjectFactory.class,
//                    iso20022.swift.xsd.camt_019_001.ObjectFactory.class,
//                    iso20022.swift.xsd.camt_025_001.ObjectFactory.class);
//
//            XMLStreamReader xsr = XMLInputFactory.newFactory().createXMLStreamReader(new ByteArrayInputStream(block4.getBytes()));
//            Unmarshaller unmarshaller = context.createUnmarshaller();
//            DataPDU dataPDU = (DataPDU) unmarshaller.unmarshal(xsr);
//
//            Object header = dataPDU.getBody().getContent().get(0).getValue();
//            Object document = dataPDU.getBody().getContent().get(1).getValue();
//
//            map.put("header", header);
//            map.put("document", document);
//        } catch (Exception e) {
//            log.error("{}", e.getMessage());
//            throw new RuntimeException(e.getMessage());
//        }
//
//        return map;
//    }

    public static Map<String, Object> convertStringToPDUDocument(String block4) {
        Map<String, Object> map = new HashMap<>();

        try {
            JAXBContext context = createJAXBContext();

            XMLStreamReader xsr = XMLInputFactory.newFactory()
                    .createXMLStreamReader(new ByteArrayInputStream(block4.getBytes()));

            Unmarshaller unmarshaller = context.createUnmarshaller();
            DataPDU dataPDU = (DataPDU) unmarshaller.unmarshal(xsr);

            Object header = dataPDU.getBody().getContent().get(0).getValue();
            Object document = dataPDU.getBody().getContent().get(1).getValue();

            map.put("header", header);
            map.put("document", document);
        } catch (Exception e) {
            log.error("Error while converting string to PDU document: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to convert string to PDU document", e);
        }

        return map;
    }

    private static JAXBContext createJAXBContext() throws JAXBException {
        return JAXBContext.newInstance(
                iso20022.wrapper.DataPDU.class,
                iso20022.iso.std.iso._20022.tech.xsd.head_001_002.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.ObjectFactory.class,
                iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.ObjectFactory.class,
                iso20022.swift.xsd.camt_018_001.ObjectFactory.class,
                iso20022.swift.xsd.camt_019_001.ObjectFactory.class,
                iso20022.swift.xsd.camt_025_001.ObjectFactory.class
        );
    }

    public static String convertDataPduToString(DataPDU dataPDU) {
        String contents = "";
        try {
            JAXBContext context = createJAXBContext();

            // Create a new Document for marshalling
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDocument = documentBuilder.newDocument();

            // Marshal the dataPDU object to XML
            Marshaller marshaller = context.createMarshaller();
            marshaller.marshal(dataPDU, xmlDocument);

            // Modify the XML document and convert it to a string
            contents = DocumentModifier.modify(xmlDocument);

           // System.out.println(contents);
        } catch (Exception e) {
            log.error("Error while converting DataPDU to string: {}", e.getMessage(), e);
            contents = "Failed to convert DataPDU to string: " + e.getMessage();
        }
        return contents;
    }


//
//    public static String convertDataPduToString(DataPDU dataPDU) {
//        String contents = "";
//        try {
//            //   JAXBContext context = JAXBContext.newInstance("iso20022.wrapper:iso20022.iso.std.iso._20022.tech.xsd.head_001_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001:iso20022.iso.std.iso._20022.tech.xsd.pacs_008_001:iso20022.iso.std.iso._20022.tech.xsd.camt_052_001:iso20022.iso.std.iso._20022.tech.xsd.camt_053_001:iso20022.iso.std.iso._20022.tech.xsd.camt_054_001:iso20022.swift.xsd.camt_018_001:iso20022.swift.xsd.camt_019_001:iso20022.swift.xsd.camt_025_001");
//            JAXBContext context = JAXBContext.newInstance(iso20022.wrapper.DataPDU.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.head_001_002.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.ObjectFactory.class,
//                    iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.ObjectFactory.class,
//                    iso20022.swift.xsd.camt_018_001.ObjectFactory.class,
//                    iso20022.swift.xsd.camt_019_001.ObjectFactory.class,
//                    iso20022.swift.xsd.camt_025_001.ObjectFactory.class);
//
//            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
//            Document xmlDocument = documentBuilder.newDocument();
//            Marshaller marshaller = context.createMarshaller();
//            marshaller.marshal(dataPDU, xmlDocument);
//            contents = DocumentModifier.modify(xmlDocument);
//            System.out.println(contents);
//        } catch (Exception e) {
//            e.printStackTrace();
//            contents = e.getMessage();
//        }
//        return contents;
//    }

}
