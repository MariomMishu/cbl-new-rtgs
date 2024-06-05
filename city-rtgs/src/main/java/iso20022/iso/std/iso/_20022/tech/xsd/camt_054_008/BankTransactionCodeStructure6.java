
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of elements used to identify the type or operations code of a transaction entry.
 * 
 * <p>Java class for BankTransactionCodeStructure6 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BankTransactionCodeStructure6">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ExternalBankTransactionFamily1Code"/>
 *         <element name="SubFmlyCd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ExternalBankTransactionSubFamily1Code"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankTransactionCodeStructure6", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "cd",
    "subFmlyCd"
})
public class BankTransactionCodeStructure6 {

    /**
     * Specifies the family within a domain.
     * 
     */
    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String cd;
    /**
     * Specifies the sub-product family within a specific family.
     * 
     */
    @XmlElement(name = "SubFmlyCd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String subFmlyCd;

    /**
     * Specifies the family within a domain.
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
     * Specifies the sub-product family within a specific family.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubFmlyCd() {
        return subFmlyCd;
    }

    /**
     * Sets the value of the subFmlyCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSubFmlyCd()
     */
    public void setSubFmlyCd(String value) {
        this.subFmlyCd = value;
    }

}
