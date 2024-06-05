
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Reference information provided by the creditor to allow the identification of the underlying documents.
 * 
 * <p>Java class for CreditorReferenceInformation2__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CreditorReferenceInformation2__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CreditorReferenceType2__1" minOccurs="0"/>
 *         <element name="Ref" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditorReferenceInformation2__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "tp",
    "ref"
})
public class CreditorReferenceInformation21 {

    /**
     * Specifies the type of creditor reference.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected CreditorReferenceType21 tp;
    /**
     * Unique reference, as assigned by the creditor, to unambiguously refer to the payment transaction.
     * 
     *                         Usage: If available, the initiating party should provide this reference in the structured remittance information, to enable reconciliation by the creditor upon receipt of the amount of money.
     * 
     *                         If the business context requires the use of a creditor reference or a payment remit identification, and only one identifier can be passed through the end-to-end chain, the creditor's reference or payment remittance identification should be quoted in the end-to-end transaction identification.
     * 
     */
    @XmlElement(name = "Ref", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String ref;

    /**
     * Specifies the type of creditor reference.
     * 
     * @return
     *     possible object is
     *     {@link CreditorReferenceType21 }
     *     
     */
    public CreditorReferenceType21 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorReferenceType21 }
     *     
     * @see #getTp()
     */
    public void setTp(CreditorReferenceType21 value) {
        this.tp = value;
    }

    /**
     * Unique reference, as assigned by the creditor, to unambiguously refer to the payment transaction.
     * 
     *                         Usage: If available, the initiating party should provide this reference in the structured remittance information, to enable reconciliation by the creditor upon receipt of the amount of money.
     * 
     *                         If the business context requires the use of a creditor reference or a payment remit identification, and only one identifier can be passed through the end-to-end chain, the creditor's reference or payment remittance identification should be quoted in the end-to-end transaction identification.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRef() {
        return ref;
    }

    /**
     * Sets the value of the ref property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRef()
     */
    public void setRef(String value) {
        this.ref = value;
    }

}
