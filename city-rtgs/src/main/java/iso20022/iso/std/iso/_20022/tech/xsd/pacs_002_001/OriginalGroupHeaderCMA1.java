//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OriginalGroupHeaderCMA1 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OriginalGroupHeaderCMA1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrgnlMsgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Max35Text"/&gt;
 *         &lt;element name="OrgnlMsgNmId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Max35Text"/&gt;
 *         &lt;element name="OrgnlCreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}ISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="GrpSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}TransactionGroupStatus3Code"/&gt;
 *         &lt;element name="StsRsnInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}StatusReasonInformation9CMA1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalGroupHeaderCMA1", propOrder = {
        "orgnlMsgId",
        "orgnlMsgNmId",
        "orgnlCreDtTm",
        "grpSts",
        "stsRsnInf"
})
public class OriginalGroupHeaderCMA1 {

    @XmlElement(name = "OrgnlMsgId", required = true)
    protected String orgnlMsgId;
    @XmlElement(name = "OrgnlMsgNmId", required = true)
    protected String orgnlMsgNmId;
    @XmlElement(name = "OrgnlCreDtTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar orgnlCreDtTm;
    @XmlElement(name = "GrpSts", required = true)
    @XmlSchemaType(name = "string")
    protected TransactionGroupStatus3Code grpSts;
    @XmlElement(name = "StsRsnInf")
    protected StatusReasonInformation9CMA1 stsRsnInf;

    /**
     * Gets the value of the orgnlMsgId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOrgnlMsgId() {
        return orgnlMsgId;
    }

    /**
     * Sets the value of the orgnlMsgId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrgnlMsgId(String value) {
        this.orgnlMsgId = value;
    }

    /**
     * Gets the value of the orgnlMsgNmId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getOrgnlMsgNmId() {
        return orgnlMsgNmId;
    }

    /**
     * Sets the value of the orgnlMsgNmId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setOrgnlMsgNmId(String value) {
        this.orgnlMsgNmId = value;
    }

    /**
     * Gets the value of the orgnlCreDtTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getOrgnlCreDtTm() {
        return orgnlCreDtTm;
    }

    /**
     * Sets the value of the orgnlCreDtTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setOrgnlCreDtTm(XMLGregorianCalendar value) {
        this.orgnlCreDtTm = value;
    }

    /**
     * Gets the value of the grpSts property.
     *
     * @return possible object is
     * {@link TransactionGroupStatus3Code }
     */
    public TransactionGroupStatus3Code getGrpSts() {
        return grpSts;
    }

    /**
     * Sets the value of the grpSts property.
     *
     * @param value allowed object is
     *              {@link TransactionGroupStatus3Code }
     */
    public void setGrpSts(TransactionGroupStatus3Code value) {
        this.grpSts = value;
    }

    /**
     * Gets the value of the stsRsnInf property.
     *
     * @return possible object is
     * {@link StatusReasonInformation9CMA1 }
     */
    public StatusReasonInformation9CMA1 getStsRsnInf() {
        return stsRsnInf;
    }

    /**
     * Sets the value of the stsRsnInf property.
     *
     * @param value allowed object is
     *              {@link StatusReasonInformation9CMA1 }
     */
    public void setStsRsnInf(StatusReasonInformation9CMA1 value) {
        this.stsRsnInf = value;
    }

}
