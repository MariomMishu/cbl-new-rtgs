
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Date and place of birth of a person.
 * 
 * <p>Java class for DateAndPlaceOfBirth1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="DateAndPlaceOfBirth1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="BirthDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ISODate"/>
 *         <element name="PrvcOfBirth" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="CityOfBirth" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended"/>
 *         <element name="CtryOfBirth" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CountryCode"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateAndPlaceOfBirth1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "birthDt",
    "prvcOfBirth",
    "cityOfBirth",
    "ctryOfBirth"
})
public class DateAndPlaceOfBirth11 {

    /**
     * Date on which a person is born.
     * 
     */
    @XmlElement(name = "BirthDt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar birthDt;
    /**
     * Province where a person was born.
     * 
     */
    @XmlElement(name = "PrvcOfBirth", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String prvcOfBirth;
    /**
     * City where a person was born.
     * 
     */
    @XmlElement(name = "CityOfBirth", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected String cityOfBirth;
    /**
     * Country where a person was born.
     * 
     */
    @XmlElement(name = "CtryOfBirth", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected String ctryOfBirth;

    /**
     * Date on which a person is born.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getBirthDt() {
        return birthDt;
    }

    /**
     * Sets the value of the birthDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getBirthDt()
     */
    public void setBirthDt(XMLGregorianCalendar value) {
        this.birthDt = value;
    }

    /**
     * Province where a person was born.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrvcOfBirth() {
        return prvcOfBirth;
    }

    /**
     * Sets the value of the prvcOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPrvcOfBirth()
     */
    public void setPrvcOfBirth(String value) {
        this.prvcOfBirth = value;
    }

    /**
     * City where a person was born.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityOfBirth() {
        return cityOfBirth;
    }

    /**
     * Sets the value of the cityOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCityOfBirth()
     */
    public void setCityOfBirth(String value) {
        this.cityOfBirth = value;
    }

    /**
     * Country where a person was born.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtryOfBirth() {
        return ctryOfBirth;
    }

    /**
     * Sets the value of the ctryOfBirth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCtryOfBirth()
     */
    public void setCtryOfBirth(String value) {
        this.ctryOfBirth = value;
    }

}
