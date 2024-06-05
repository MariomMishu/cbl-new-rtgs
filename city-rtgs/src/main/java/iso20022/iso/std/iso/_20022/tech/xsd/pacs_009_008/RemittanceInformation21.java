
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information supplied to enable the matching of an entry
 *                 with the items that the transfer is intended to settle.
 * 
 * <p>Java class for RemittanceInformation2__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RemittanceInformation2__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Ustrd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax140Text_Extended" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceInformation2__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "ustrd"
})
public class RemittanceInformation21 {

    /**
     * Information supplied to enable the matching of
     *                         an entry with the items that the transfer is intended to settle, for example, commercial
     *                         invoices in an accounts' receivable system in an unstructured form.
     * 
     */
    @XmlElement(name = "Ustrd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String ustrd;

    /**
     * Information supplied to enable the matching of
     *                         an entry with the items that the transfer is intended to settle, for example, commercial
     *                         invoices in an accounts' receivable system in an unstructured form.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUstrd() {
        return ustrd;
    }

    /**
     * Sets the value of the ustrd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getUstrd()
     */
    public void setUstrd(String value) {
        this.ustrd = value;
    }

}
