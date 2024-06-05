//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.06.06 at 11:07:57 AM BDT 
//


package com.cbl.cityrtgs.engine.dto.stpa;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the com.cbl.cityrtgs.engine.dto.stpa package.
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

    private final static QName _Send_QNAME = new QName("http://integration.gwclient.smallsystems.cma.se/", "send");
    private final static QName _SendResponse_QNAME = new QName("http://integration.gwclient.smallsystems.cma.se/", "sendResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cbl.cityrtgs.engine.dto.stpa
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendT }
     */
    public SendT createSendT() {
        return new SendT();
    }

    /**
     * Create an instance of {@link SendResponse }
     */
    public SendResponse createSendResponse() {
        return new SendResponse();
    }

    /**
     * Create an instance of {@link ParamsMtMsg }
     */
    public ParamsMtMsg createParamsMtMsg() {
        return new ParamsMtMsg();
    }

    /**
     * Create an instance of {@link ResultT }
     */
    public ResultT createResultT() {
        return new ResultT();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendT }{@code >}
     *
     * @param value Java instance representing xml element's value.
     * @return the new instance of {@link JAXBElement }{@code <}{@link SendT }{@code >}
     */
    @XmlElementDecl(namespace = "http://integration.gwclient.smallsystems.cma.se/", name = "send")
    public JAXBElement<SendT> createSend(SendT value) {
        return new JAXBElement<SendT>(_Send_QNAME, SendT.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SendResponse }{@code >}
     *
     * @param value Java instance representing xml element's value.
     * @return the new instance of {@link JAXBElement }{@code <}{@link SendResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://integration.gwclient.smallsystems.cma.se/", name = "sendResponse")
    public JAXBElement<SendResponse> createSendResponse(SendResponse value) {
        return new JAXBElement<SendResponse>(_SendResponse_QNAME, SendResponse.class, null, value);
    }

}
