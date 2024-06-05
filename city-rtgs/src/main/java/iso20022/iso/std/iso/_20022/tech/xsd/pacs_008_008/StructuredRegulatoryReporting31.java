
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * Information needed due to regulatory and statutory requirements.
 * 
 * <p>Java class for StructuredRegulatoryReporting3__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="StructuredRegulatoryReporting3__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ISODate" minOccurs="0"/>
 *         <element name="Ctry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CountryCode" minOccurs="0"/>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax10Text" minOccurs="0"/>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RestrictedFINActiveCurrencyAndAmount" minOccurs="0"/>
 *         <element name="Inf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructuredRegulatoryReporting3__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "tp",
    "dt",
    "ctry",
    "cd",
    "amt",
    "inf"
})
public class StructuredRegulatoryReporting31 {

    /**
     * Specifies the type of the information supplied in the regulatory reporting details.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String tp;
    /**
     * Date related to the specified type of regulatory reporting details.
     * 
     */
    @XmlElement(name = "Dt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;
    /**
     * Country related to the specified type of regulatory reporting details.
     * 
     */
    @XmlElement(name = "Ctry", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String ctry;
    /**
     * Specifies the nature, purpose, and reason for the transaction to be reported for regulatory and statutory requirements in a coded form.
     * 
     */
    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String cd;
    /**
     * Amount of money to be reported for regulatory and statutory requirements.
     * 
     */
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected RestrictedFINActiveCurrencyAndAmount amt;
    /**
     * Additional details that cater for specific domestic regulatory requirements.
     * 
     */
    @XmlElement(name = "Inf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<String> inf;

    /**
     * Specifies the type of the information supplied in the regulatory reporting details.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTp()
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Date related to the specified type of regulatory reporting details.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getDt()
     */
    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

    /**
     * Country related to the specified type of regulatory reporting details.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * Sets the value of the ctry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCtry()
     */
    public void setCtry(String value) {
        this.ctry = value;
    }

    /**
     * Specifies the nature, purpose, and reason for the transaction to be reported for regulatory and statutory requirements in a coded form.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCd()
     */
    public void setCd(String value) {
        this.cd = value;
    }

    /**
     * Amount of money to be reported for regulatory and statutory requirements.
     * 
     * @return
     *     possible object is
     *     {@link RestrictedFINActiveCurrencyAndAmount }
     *     
     */
    public RestrictedFINActiveCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictedFINActiveCurrencyAndAmount }
     *     
     * @see #getAmt()
     */
    public void setAmt(RestrictedFINActiveCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Additional details that cater for specific domestic regulatory requirements.
     * 
     * Gets the value of the inf property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the inf property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * </p>
     * 
     * 
     * @return
     *     The value of the inf property.
     */
    public List<String> getInf() {
        if (inf == null) {
            inf = new ArrayList<>();
        }
        return this.inf;
    }

}
