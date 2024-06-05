package com.cbl.cityrtgs.engine.utility;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

public class XMLUtility {

    public static Document stringToXMLDocument(String xml) {

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            return builder.parse(new InputSource(new StringReader(xml)));
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public static Map<String, String> getXMLvalues(Document document) {

        Map<String, String> map = new HashMap<>();

        Node body = document.getDocumentElement().getElementsByTagName("Body").item(0);
        Node AppHdr = body.getChildNodes().item(1);
        String mir = AppHdr.getChildNodes().item(5).getTextContent().trim();

        map.put("mir", mir);

        return map;
    }
}
