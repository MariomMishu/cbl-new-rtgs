
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides the details to identify an account.
 * 
 * <p>Java class for CashAccount38__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CashAccount38__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AccountIdentification4Choice__2"/>
 *         <element name="Prxy" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ProxyAccountIdentification1__2" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAccount38__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "id",
    "prxy"
})
public class CashAccount381 {

    /**
     * Unique and unambiguous identification for the account between the account owner and the account servicer.
     * 
     */
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected AccountIdentification4Choice2 id;
    /**
     * Specifies an alternate assumed name for the identification of the account.
     * 
     */
    @XmlElement(name = "Prxy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected ProxyAccountIdentification12 prxy;

    /**
     * Unique and unambiguous identification for the account between the account owner and the account servicer.
     * 
     * @return
     *     possible object is
     *     {@link AccountIdentification4Choice2 }
     *     
     */
    public AccountIdentification4Choice2 getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountIdentification4Choice2 }
     *     
     * @see #getId()
     */
    public void setId(AccountIdentification4Choice2 value) {
        this.id = value;
    }

    /**
     * Specifies an alternate assumed name for the identification of the account.
     * 
     * @return
     *     possible object is
     *     {@link ProxyAccountIdentification12 }
     *     
     */
    public ProxyAccountIdentification12 getPrxy() {
        return prxy;
    }

    /**
     * Sets the value of the prxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProxyAccountIdentification12 }
     *     
     * @see #getPrxy()
     */
    public void setPrxy(ProxyAccountIdentification12 value) {
        this.prxy = value;
    }

}
