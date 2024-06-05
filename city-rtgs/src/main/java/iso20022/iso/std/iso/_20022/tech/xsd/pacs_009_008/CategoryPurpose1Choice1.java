
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the high level purpose of the instruction
 *                 based on a set of pre-defined categories.
 *                 Usage: This is used by the initiating party to provide information concerning the processing of the
 *                 payment. It is likely to trigger special processing by any of the agents involved in the payment chain.
 * 
 * <p>Java class for CategoryPurpose1Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CategoryPurpose1Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}ExternalCategoryPurpose1Code"/>
 *         <element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CategoryPurpose1Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "cd",
    "prtry"
})
public class CategoryPurpose1Choice1 {

    /**
     * Category purpose, as published in an external
     *                         category purpose code list.
     * 
     */
    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String cd;
    /**
     * Category purpose, in a proprietary form.
     * 
     */
    @XmlElement(name = "Prtry", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String prtry;

    /**
     * Category purpose, as published in an external
     *                         category purpose code list.
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
     * Category purpose, in a proprietary form.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPrtry()
     */
    public void setPrtry(String value) {
        this.prtry = value;
    }

}
