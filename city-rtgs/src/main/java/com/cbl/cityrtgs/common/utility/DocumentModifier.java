package com.cbl.cityrtgs.common.utility;

import org.w3c.dom.*;

import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.util.ArrayList;

public class DocumentModifier {
    public static String modify(Document doc) throws Exception {
        Element appHeader = null;
        Element document = null;
        Element dataPdu = doc.getDocumentElement();
        NodeList bodyChilds = doc.getElementsByTagNameNS(doc.getDocumentElement().getNamespaceURI(), "Body").item(0).getChildNodes();



        for (int i = 0; i < bodyChilds.getLength(); ++i) {
            Node node = bodyChilds.item(i);
            if (node instanceof Element && ((Element) node).getNodeName().contains("Document")) {
                document = (Element) node;
            } else if (node instanceof Element && ((Element) node).getNodeName().contains("AppHdr")) {
                appHeader = (Element) node;
            }
        }

        String dataPduUri = dataPdu.getNamespaceURI();
        String headerUri = appHeader.getNamespaceURI();
        String docUri = document.getNamespaceURI();
        String dataPduPrefix = dataPdu.getPrefix();
        String headerPrefix = appHeader.getPrefix();
        String docPrefix = document.getPrefix();
        removeNs(dataPdu);
        removeNs(appHeader);
        removeNs(document);
       // header.removeAttributeNS("xmlns", "");
        // header.setAttribute("", "");
        dataPdu.setAttribute("xmlns", dataPduUri);
        appHeader.setAttribute("xmlns", headerUri);
        document.setAttribute("xmlns", docUri);
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty("omit-xml-declaration", "yes");
        transformer.setOutputProperty("indent", "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
        DOMSource source = new DOMSource(doc);
        StringWriter writer = new StringWriter();
        transformer.transform(source, new StreamResult(writer));
        String msg = writer.getBuffer().toString();

        msg = msg.replaceAll( "Message xmlns=\"\"", "Message");
        msg = msg.replaceAll(dataPduPrefix + ":", "");
        msg = msg.replaceAll(headerPrefix + ":", "");
        msg = msg.replaceAll(docPrefix + ":", "");
        msg = msg.replaceAll(" xmlns:" + dataPduPrefix + "=\"" + dataPduUri + "\"", "");
        msg = msg.replaceAll(" xmlns:" + headerPrefix + "=\"" + headerUri + "\"", "");
        msg = msg.replaceAll(" xmlns:" + docPrefix + "=\"" + docUri + "\"", "");
        return msg;
    }

    public static void removeNs(Element element) {
        ArrayList<Node> nodeList = new ArrayList();
        NamedNodeMap namedNodeMap = element.getAttributes();
        int len = namedNodeMap.getLength();

        int i;
        for (i = 0; i < len; ++i) {
            Node node = namedNodeMap.item(i);
            nodeList.add(node);
        }

        for (i = 0; i < nodeList.size(); ++i) {
            element.removeAttribute(((Node) nodeList.get(i)).getNodeName());
        }
    }
}
