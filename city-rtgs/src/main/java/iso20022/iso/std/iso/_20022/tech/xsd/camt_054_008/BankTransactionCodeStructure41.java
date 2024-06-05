
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of elements used to identify the type or operations code of a transaction entry.
 * 
 * <p>Java class for BankTransactionCodeStructure4__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BankTransactionCodeStructure4__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Domn" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BankTransactionCodeStructure5" minOccurs="0"/>
 *         <element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ProprietaryBankTransactionCodeStructure1__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankTransactionCodeStructure4__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "domn",
    "prtry"
})
public class BankTransactionCodeStructure41 {

    /**
     * Set of elements used to provide the domain, the family and the sub-family of the bank transaction code, in a structured and hierarchical format.
     * 
     *                         Usage: If a specific family or sub-family code cannot be provided, the generic family code defined for the domain or the generic sub-family code defined for the family should be provided.
     * 
     */
    @XmlElement(name = "Domn", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BankTransactionCodeStructure5 domn;
    /**
     * Bank transaction code in a proprietary form, as defined by the issuer.
     * 
     */
    @XmlElement(name = "Prtry", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected ProprietaryBankTransactionCodeStructure11 prtry;

    /**
     * Set of elements used to provide the domain, the family and the sub-family of the bank transaction code, in a structured and hierarchical format.
     * 
     *                         Usage: If a specific family or sub-family code cannot be provided, the generic family code defined for the domain or the generic sub-family code defined for the family should be provided.
     * 
     * @return
     *     possible object is
     *     {@link BankTransactionCodeStructure5 }
     *     
     */
    public BankTransactionCodeStructure5 getDomn() {
        return domn;
    }

    /**
     * Sets the value of the domn property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankTransactionCodeStructure5 }
     *     
     * @see #getDomn()
     */
    public void setDomn(BankTransactionCodeStructure5 value) {
        this.domn = value;
    }

    /**
     * Bank transaction code in a proprietary form, as defined by the issuer.
     * 
     * @return
     *     possible object is
     *     {@link ProprietaryBankTransactionCodeStructure11 }
     *     
     */
    public ProprietaryBankTransactionCodeStructure11 getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProprietaryBankTransactionCodeStructure11 }
     *     
     * @see #getPrtry()
     */
    public void setPrtry(ProprietaryBankTransactionCodeStructure11 value) {
        this.prtry = value;
    }

}
