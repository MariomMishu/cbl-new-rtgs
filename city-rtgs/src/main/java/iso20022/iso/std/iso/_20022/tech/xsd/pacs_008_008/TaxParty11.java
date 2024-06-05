
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Details about the entity involved in the tax paid or to be paid.
 * 
 * <p>Java class for TaxParty1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TaxParty1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="TaxId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="RegnId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="TaxTp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxParty1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "taxId",
    "regnId",
    "taxTp"
})
public class TaxParty11 {

    /**
     * Tax identification number of the creditor.
     * 
     */
    @XmlElement(name = "TaxId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String taxId;
    /**
     * Unique identification, as assigned by an organisation, to unambiguously identify a party.
     * 
     */
    @XmlElement(name = "RegnId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String regnId;
    /**
     * Type of tax payer.
     * 
     */
    @XmlElement(name = "TaxTp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String taxTp;

    /**
     * Tax identification number of the creditor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxId() {
        return taxId;
    }

    /**
     * Sets the value of the taxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTaxId()
     */
    public void setTaxId(String value) {
        this.taxId = value;
    }

    /**
     * Unique identification, as assigned by an organisation, to unambiguously identify a party.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegnId() {
        return regnId;
    }

    /**
     * Sets the value of the regnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRegnId()
     */
    public void setRegnId(String value) {
        this.regnId = value;
    }

    /**
     * Type of tax payer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTaxTp() {
        return taxTp;
    }

    /**
     * Sets the value of the taxTp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTaxTp()
     */
    public void setTaxTp(String value) {
        this.taxTp = value;
    }

}
