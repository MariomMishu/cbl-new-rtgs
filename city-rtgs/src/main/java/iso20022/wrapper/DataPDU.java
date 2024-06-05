//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.wrapper;

import iso20022.iso.std.iso._20022.tech.xsd.head_001_002.BusinessApplicationHeaderV02;
import iso20022.iso.std.iso._20022.tech.xsd.header.Header;
import lombok.ToString;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Revision" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Body"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}AppHdr"/&gt;
 *                   &lt;choice&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:swift:xsd:camt.018.001.03}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:swift:xsd:camt.019.001.04}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:swift:xsd:camt.025.001.03}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Document" minOccurs="0"/&gt;
 *                     &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}Document" minOccurs="0"/&gt;
 *                   &lt;/choice&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "revision",
        "header",
        "body"
})
@XmlRootElement(name = "DataPDU")
@ToString
public class DataPDU {
    @XmlElement(name = "Revision", required = true)
    protected String revision;
    @XmlElement(name = "Header", required = true)
    protected Header header;
    @XmlElement(name = "Body", required = true)
    protected Body body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public DataPDU() {
    }

    /**
     * Gets the value of the revision property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRevision() {
        return revision;
    }

    /**
     * Sets the value of the revision property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRevision(String value) {
        this.revision = value;
    }

    /**
     * Gets the value of the body property.
     *
     * @return possible object is
     * {@link Body }
     */
    public Body getBody() {
        return body;
    }

    /**
     * Sets the value of the body property.
     *
     * @param value allowed object is
     *              {@link Body }
     */
    public void setBody(Body value) {
        this.body = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     *
     * <p>The following schema fragment specifies the expected content contained within this class.
     *
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:head.001.001.01}AppHdr"/&gt;
     *         &lt;choice&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:swift:xsd:camt.018.001.03}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:swift:xsd:camt.019.001.04}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:swift:xsd:camt.025.001.03}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Document" minOccurs="0"/&gt;
     *           &lt;element ref="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.04}Document" minOccurs="0"/&gt;
     *         &lt;/choice&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
            "content"
    })
    //  @ToString
    public static class Body {

        @XmlElementRefs({
                @XmlElementRef(name = "AppHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:swift:xsd:camt.018.001.03", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:swift:xsd:camt.019.001.04", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:swift:xsd:camt.025.001.03", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.052.001.04", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.053.001.04", type = JAXBElement.class, required = false),
                @XmlElementRef(name = "Document", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", type = JAXBElement.class, required = false)
        })
        protected List<JAXBElement<?>> content;

        public Body() {
        }

        /**
         * Gets the rest of the content model.
         *
         * <p>
         * You are getting this "catch-all" property because of the following reason:
         * The field name "Document" is used by two different parts of a schema. See:
         * line 42 of file:/home/bs578/Java%20projects/city-rtgs/src/main/resources/xsd/wrapper.xsd
         * line 41 of file:/home/bs578/Java%20projects/city-rtgs/src/main/resources/xsd/wrapper.xsd
         * <p>
         * To get rid of this property, apply a property customization to one
         * of both of the following declarations to change their names:
         * Gets the value of the content property.
         *
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the content property.
         *
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getContent().add(newItem);
         * </pre>
         *
         *
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link JAXBElement }{@code <}{@link BusinessApplicationHeaderV02 }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.swift.xsd.camt_018_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.swift.xsd.camt_019_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.swift.xsd.camt_025_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.camt_052_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.camt_053_001.Document }{@code >}
         * {@link JAXBElement }{@code <}{@link iso20022.iso.std.iso._20022.tech.xsd.camt_054_008.Document }{@code >}
         */
        public List<JAXBElement<?>> getContent() {
            if (content == null) {
                content = new ArrayList<>();
            }
            return this.content;
        }

//        public void setContent(List<JAXBElement<?>> content) {
//            this.content = content;
//        }
    }

}
