
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the details of the contact person.
 * 
 * <p>Java class for Contact4__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Contact4__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax140Text_Extended" minOccurs="0"/>
 *         <element name="PhneNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PhoneNumber" minOccurs="0"/>
 *         <element name="MobNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PhoneNumber" minOccurs="0"/>
 *         <element name="FaxNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PhoneNumber" minOccurs="0"/>
 *         <element name="EmailAdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax2048Text_Extended" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Contact4__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "nm",
    "phneNb",
    "mobNb",
    "faxNb",
    "emailAdr"
})
public class Contact41 {

    /**
     * Name by which a party is known and which is usually used to identify that party.
     * 
     */
    @XmlElement(name = "Nm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String nm;
    /**
     * Collection of information that identifies a phone number, as defined by telecom services.
     * 
     */
    @XmlElement(name = "PhneNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String phneNb;
    /**
     * Collection of information that identifies a mobile phone number, as defined by telecom services.
     * 
     */
    @XmlElement(name = "MobNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String mobNb;
    /**
     * Collection of information that identifies a FAX number, as defined by telecom services.
     * 
     */
    @XmlElement(name = "FaxNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String faxNb;
    /**
     * Address for electronic mail (e-mail).
     * 
     */
    @XmlElement(name = "EmailAdr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String emailAdr;

    /**
     * Name by which a party is known and which is usually used to identify that party.
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
     * @see #getNm()
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Collection of information that identifies a phone number, as defined by telecom services.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhneNb() {
        return phneNb;
    }

    /**
     * Sets the value of the phneNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPhneNb()
     */
    public void setPhneNb(String value) {
        this.phneNb = value;
    }

    /**
     * Collection of information that identifies a mobile phone number, as defined by telecom services.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMobNb() {
        return mobNb;
    }

    /**
     * Sets the value of the mobNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMobNb()
     */
    public void setMobNb(String value) {
        this.mobNb = value;
    }

    /**
     * Collection of information that identifies a FAX number, as defined by telecom services.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFaxNb() {
        return faxNb;
    }

    /**
     * Sets the value of the faxNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFaxNb()
     */
    public void setFaxNb(String value) {
        this.faxNb = value;
    }

    /**
     * Address for electronic mail (e-mail).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEmailAdr() {
        return emailAdr;
    }

    /**
     * Sets the value of the emailAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getEmailAdr()
     */
    public void setEmailAdr(String value) {
        this.emailAdr = value;
    }

}
