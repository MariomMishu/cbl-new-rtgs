//package com.cbl.cityrtgs.services.datapdu;
//
//import lombok.extern.slf4j.Slf4j;
//import javax.xml.XMLConstants;
//import javax.xml.transform.OutputKeys;
//import javax.xml.transform.Source;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.stream.StreamResult;
//import javax.xml.transform.stream.StreamSource;
//import java.io.StringReader;
//import java.io.StringWriter;
//
//@Slf4j
//public class DataPduUtility {
//    public static String prettyFormat(String input) {
//        try {
//            Source xmlInput = new StreamSource(new StringReader(input));
//            StringWriter stringWriter = new StringWriter();
//            StreamResult xmlOutput = new StreamResult(stringWriter);
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            transformerFactory.setAttribute("indent-number", 5);
//            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_DTD, "");
//            transformerFactory.setAttribute(XMLConstants.ACCESS_EXTERNAL_STYLESHEET, "");
//            Transformer transformer = transformerFactory.newTransformer();
//            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
//            transformer.transform(xmlInput, xmlOutput);
//            return xmlOutput.getWriter().toString();
//        } catch (Exception e) {
//         log.error(e.getMessage());
//        }
//        return null;
//    }
//
//}
