
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for OriginalItemAndStatus8 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OriginalItemAndStatus8"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="OrgnlItmId" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text"/&gt;
 *         &lt;element name="OrgnlEndToEndId" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlUETR" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}UUIDv4Identifier" minOccurs="0"/&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="XpctdValDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="ItmSts" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}NotificationStatus3Code"/&gt;
 *         &lt;element name="AddtlStsInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max105Text" minOccurs="0"/&gt;
 *         &lt;element name="OrgnlItmRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}OriginalItemReference7" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalItemAndStatus8", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", propOrder = {
    "orgnlItmId",
    "orgnlEndToEndId",
    "orgnlUETR",
    "amt",
    "xpctdValDt",
    "itmSts",
    "addtlStsInf",
    "orgnlItmRef"
})
public class OriginalItemAndStatus8 {

    @XmlElement(name = "OrgnlItmId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", required = true)
    protected String orgnlItmId;
    @XmlElement(name = "OrgnlEndToEndId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String orgnlEndToEndId;
    @XmlElement(name = "OrgnlUETR", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String orgnlUETR;
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "XpctdValDt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar xpctdValDt;
    @XmlElement(name = "ItmSts", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", required = true)
    @XmlSchemaType(name = "string")
    protected NotificationStatus3Code itmSts;
    @XmlElement(name = "AddtlStsInf", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String addtlStsInf;
    @XmlElement(name = "OrgnlItmRef", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected OriginalItemReference7 orgnlItmRef;

    /**
     * Gets the value of the orgnlItmId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlItmId() {
        return orgnlItmId;
    }

    /**
     * Sets the value of the orgnlItmId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlItmId(String value) {
        this.orgnlItmId = value;
    }

    /**
     * Gets the value of the orgnlEndToEndId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlEndToEndId() {
        return orgnlEndToEndId;
    }

    /**
     * Sets the value of the orgnlEndToEndId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlEndToEndId(String value) {
        this.orgnlEndToEndId = value;
    }

    /**
     * Gets the value of the orgnlUETR property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrgnlUETR() {
        return orgnlUETR;
    }

    /**
     * Sets the value of the orgnlUETR property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrgnlUETR(String value) {
        this.orgnlUETR = value;
    }

    /**
     * Gets the value of the amt property.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Gets the value of the xpctdValDt property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getXpctdValDt() {
        return xpctdValDt;
    }

    /**
     * Sets the value of the xpctdValDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setXpctdValDt(XMLGregorianCalendar value) {
        this.xpctdValDt = value;
    }

    /**
     * Gets the value of the itmSts property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationStatus3Code }
     *     
     */
    public NotificationStatus3Code getItmSts() {
        return itmSts;
    }

    /**
     * Sets the value of the itmSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationStatus3Code }
     *     
     */
    public void setItmSts(NotificationStatus3Code value) {
        this.itmSts = value;
    }

    /**
     * Gets the value of the addtlStsInf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddtlStsInf() {
        return addtlStsInf;
    }

    /**
     * Sets the value of the addtlStsInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddtlStsInf(String value) {
        this.addtlStsInf = value;
    }

    /**
     * Gets the value of the orgnlItmRef property.
     * 
     * @return
     *     possible object is
     *     {@link OriginalItemReference7 }
     *     
     */
    public OriginalItemReference7 getOrgnlItmRef() {
        return orgnlItmRef;
    }

    /**
     * Sets the value of the orgnlItmRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginalItemReference7 }
     *     
     */
    public void setOrgnlItmRef(OriginalItemReference7 value) {
        this.orgnlItmRef = value;
    }

}
