
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identification of a person, an organisation or a financial institution.
 * 
 * <p>Java class for Party40Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Party40Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Pty" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}PartyIdentification135__2"/>
 *         <element name="Agt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__1"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party40Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "pty",
    "agt"
})
public class Party40Choice1 {

    /**
     * Identification of a person or an organisation.
     * 
     */
    @XmlElement(name = "Pty", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected PartyIdentification1352 pty;
    /**
     * Identification of a financial institution.
     * 
     */
    @XmlElement(name = "Agt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification61 agt;

    /**
     * Identification of a person or an organisation.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1352 }
     *     
     */
    public PartyIdentification1352 getPty() {
        return pty;
    }

    /**
     * Sets the value of the pty property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1352 }
     *     
     * @see #getPty()
     */
    public void setPty(PartyIdentification1352 value) {
        this.pty = value;
    }

    /**
     * Identification of a financial institution.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getAgt() {
        return agt;
    }

    /**
     * Sets the value of the agt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getAgt()
     */
    public void setAgt(BranchAndFinancialInstitutionIdentification61 value) {
        this.agt = value;
    }

}
