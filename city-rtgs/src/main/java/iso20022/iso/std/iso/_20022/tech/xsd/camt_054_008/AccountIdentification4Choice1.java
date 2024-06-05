
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the unique identification of an account as assigned by the account servicer.
 * 
 * <p>Java class for AccountIdentification4Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="AccountIdentification4Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="IBAN" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}IBAN2007Identifier"/>
 *         <element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}GenericAccountIdentification1__1"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountIdentification4Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "iban",
    "othr"
})
public class AccountIdentification4Choice1 {

    /**
     * International Bank Account Number (IBAN) - identifier used internationally by financial institutions to uniquely identify the account of a customer. Further specifications of the format and content of the IBAN can be found in the standard ISO 13616 "Banking and related financial services - International Bank Account Number (IBAN)" version 1997-10-01, or later revisions.
     * 
     */
    @XmlElement(name = "IBAN", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String iban;
    /**
     * Unique identification of an account, as assigned by the account servicer, using an identification scheme.
     * 
     */
    @XmlElement(name = "Othr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected GenericAccountIdentification11 othr;

    /**
     * International Bank Account Number (IBAN) - identifier used internationally by financial institutions to uniquely identify the account of a customer. Further specifications of the format and content of the IBAN can be found in the standard ISO 13616 "Banking and related financial services - International Bank Account Number (IBAN)" version 1997-10-01, or later revisions.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIBAN() {
        return iban;
    }

    /**
     * Sets the value of the iban property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIBAN()
     */
    public void setIBAN(String value) {
        this.iban = value;
    }

    /**
     * Unique identification of an account, as assigned by the account servicer, using an identification scheme.
     * 
     * @return
     *     possible object is
     *     {@link GenericAccountIdentification11 }
     *     
     */
    public GenericAccountIdentification11 getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GenericAccountIdentification11 }
     *     
     * @see #getOthr()
     */
    public void setOthr(GenericAccountIdentification11 value) {
        this.othr = value;
    }

}
