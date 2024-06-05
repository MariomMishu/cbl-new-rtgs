
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the unique identification of an account as assigned by the account servicer.
 * 
 * <p>Java class for AccountIdentification4Choice__2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="AccountIdentification4Choice__2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}GenericAccountIdentification1__2"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountIdentification4Choice__2", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "othr"
})
public class AccountIdentification4Choice2 {

    /**
     * Unique identification of an account, as assigned by the account servicer, using an identification scheme.
     * 
     */
    @XmlElement(name = "Othr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected GenericAccountIdentification12 othr;

    /**
     * Unique identification of an account, as assigned by the account servicer, using an identification scheme.
     * 
     * @return
     *     possible object is
     *     {@link GenericAccountIdentification12 }
     *     
     */
    public GenericAccountIdentification12 getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericAccountIdentification12 }
     *     
     * @see #getOthr()
     */
    public void setOthr(GenericAccountIdentification12 value) {
        this.othr = value;
    }

}
