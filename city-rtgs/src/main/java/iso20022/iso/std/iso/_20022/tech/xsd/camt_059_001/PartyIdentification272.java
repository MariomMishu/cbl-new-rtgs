
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PartyIdentification272 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PartyIdentification272"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max140Text" minOccurs="0"/&gt;
 *         &lt;element name="PstlAdr" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}PostalAddress27" minOccurs="0"/&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Party52Choice" minOccurs="0"/&gt;
 *         &lt;element name="CtryOfRes" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}CountryCode" minOccurs="0"/&gt;
 *         &lt;element name="CtctDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Contact13" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PartyIdentification272", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", propOrder = {
    "nm",
    "pstlAdr",
    "id",
    "ctryOfRes",
    "ctctDtls"
})
public class PartyIdentification272 {

    @XmlElement(name = "Nm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String nm;
    @XmlElement(name = "PstlAdr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected PostalAddress27 pstlAdr;
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected Party52Choice id;
    @XmlElement(name = "CtryOfRes", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String ctryOfRes;
    @XmlElement(name = "CtctDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected Contact13 ctctDtls;

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the pstlAdr property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddress27 }
     *     
     */
    public PostalAddress27 getPstlAdr() {
        return pstlAdr;
    }

    /**
     * Sets the value of the pstlAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddress27 }
     *     
     */
    public void setPstlAdr(PostalAddress27 value) {
        this.pstlAdr = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Party52Choice }
     *     
     */
    public Party52Choice getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party52Choice }
     *     
     */
    public void setId(Party52Choice value) {
        this.id = value;
    }

    /**
     * Gets the value of the ctryOfRes property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtryOfRes() {
        return ctryOfRes;
    }

    /**
     * Sets the value of the ctryOfRes property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtryOfRes(String value) {
        this.ctryOfRes = value;
    }

    /**
     * Gets the value of the ctctDtls property.
     * 
     * @return
     *     possible object is
     *     {@link Contact13 }
     *     
     */
    public Contact13 getCtctDtls() {
        return ctctDtls;
    }

    /**
     * Sets the value of the ctctDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link Contact13 }
     *     
     */
    public void setCtctDtls(Contact13 value) {
        this.ctctDtls = value;
    }

}
