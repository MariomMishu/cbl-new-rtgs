
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identification of a person, an organisation or a financial institution.
 * 
 * <p>Java class for Party40Choice__2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Party40Choice__2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Pty" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}PartyIdentification135__3"/>
 *         <element name="Agt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__2"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party40Choice__2", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "pty",
    "agt"
})
public class Party40Choice2 {

    /**
     * Identification of a person or an organisation.
     * 
     */
    @XmlElement(name = "Pty", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected PartyIdentification1353 pty;
    /**
     * Identification of a financial institution.
     * 
     */
    @XmlElement(name = "Agt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification62 agt;

    /**
     * Identification of a person or an organisation.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1353 }
     *     
     */
    public PartyIdentification1353 getPty() {
        return pty;
    }

    /**
     * Sets the value of the pty property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1353 }
     *     
     * @see #getPty()
     */
    public void setPty(PartyIdentification1353 value) {
        this.pty = value;
    }

    /**
     * Identification of a financial institution.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification62 getAgt() {
        return agt;
    }

    /**
     * Sets the value of the agt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     * @see #getAgt()
     */
    public void setAgt(BranchAndFinancialInstitutionIdentification62 value) {
        this.agt = value;
    }

}
