
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of elements used to identify the type or operations code of a transaction entry.
 * 
 * <p>Java class for BankTransactionCodeStructure5 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BankTransactionCodeStructure5">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ExternalBankTransactionDomain1Code"/>
 *         <element name="Fmly" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BankTransactionCodeStructure6"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankTransactionCodeStructure5", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "cd",
    "fmly"
})
public class BankTransactionCodeStructure5 {

    /**
     * Specifies the business area of the underlying transaction.
     * 
     */
    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String cd;
    /**
     * Specifies the family and the sub-family of the bank transaction code, within a specific domain, in a structured and hierarchical format.
     * 
     */
    @XmlElement(name = "Fmly", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected BankTransactionCodeStructure6 fmly;

    /**
     * Specifies the business area of the underlying transaction.
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
     * Specifies the family and the sub-family of the bank transaction code, within a specific domain, in a structured and hierarchical format.
     * 
     * @return
     *     possible object is
     *     {@link BankTransactionCodeStructure6 }
     *     
     */
    public BankTransactionCodeStructure6 getFmly() {
        return fmly;
    }

    /**
     * Sets the value of the fmly property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankTransactionCodeStructure6 }
     *     
     * @see #getFmly()
     */
    public void setFmly(BankTransactionCodeStructure6 value) {
        this.fmly = value;
    }

}
