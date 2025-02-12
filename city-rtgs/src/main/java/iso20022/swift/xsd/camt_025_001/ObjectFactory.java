//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_025_001;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the swift.xsd.camt_025_001 package.
 * <p>An ObjectFactory allows you to programatically
 * construct new instances of the Java representation
 * for XML content. The Java representation of XML
 * content can consist of schema derived interfaces
 * and classes representing the binding of schema
 * type definitions, element declarations and model
 * groups.  Factory methods for each of these are
 * provided in this class.
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Document_QNAME = new QName("urn:swift:xsd:camt.025.001.03", "Document");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: swift.xsd.camt_025_001
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Document }
     */
    public Document createDocument() {
        return new Document();
    }

    /**
     * Create an instance of {@link GenericIdentification1 }
     */
    public GenericIdentification1 createGenericIdentification1() {
        return new GenericIdentification1();
    }

    /**
     * Create an instance of {@link MessageHeader2 }
     */
    public MessageHeader2 createMessageHeader2() {
        return new MessageHeader2();
    }

    /**
     * Create an instance of {@link CAMT025MessageHeader2CMA }
     */
    public CAMT025MessageHeader2CMA createCAMT025MessageHeader2CMA() {
        return new CAMT025MessageHeader2CMA();
    }

    /**
     * Create an instance of {@link OriginalMessageAndIssuer1 }
     */
    public OriginalMessageAndIssuer1 createOriginalMessageAndIssuer1() {
        return new OriginalMessageAndIssuer1();
    }

    /**
     * Create an instance of {@link Receipt1 }
     */
    public Receipt1 createReceipt1() {
        return new Receipt1();
    }

    /**
     * Create an instance of {@link ReceiptV03 }
     */
    public ReceiptV03 createReceiptV03() {
        return new ReceiptV03();
    }

    /**
     * Create an instance of {@link RequestHandling }
     */
    public RequestHandling createRequestHandling() {
        return new RequestHandling();
    }

    /**
     * Create an instance of {@link RequestType2Choice }
     */
    public RequestType2Choice createRequestType2Choice() {
        return new RequestType2Choice();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Document }{@code >}
     *
     * @param value Java instance representing xml element's value.
     * @return the new instance of {@link JAXBElement }{@code <}{@link Document }{@code >}
     */
    @XmlElementDecl(namespace = "urn:swift:xsd:camt.025.001.03", name = "Document")
    public JAXBElement<Document> createDocument(Document value) {
        return new JAXBElement<Document>(_Document_QNAME, Document.class, null, value);
    }

}
