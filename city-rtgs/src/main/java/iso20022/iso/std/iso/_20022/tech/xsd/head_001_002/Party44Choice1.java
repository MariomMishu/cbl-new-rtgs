
package iso20022.iso.std.iso._20022.tech.xsd.head_001_002;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Party44Choice__1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Party44Choice__1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="FIId" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}BranchAndFinancialInstitutionIdentification6__5"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party44Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", propOrder = {
    "fiId"
})
public class Party44Choice1 {

    @XmlElement(name = "FIId", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02")
    protected BranchAndFinancialInstitutionIdentification65 fiId;

    /**
     * Gets the value of the fiId property.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification65 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification65 getFIId() {
        return fiId;
    }

    /**
     * Sets the value of the fiId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification65 }
     *     
     */
    public void setFIId(BranchAndFinancialInstitutionIdentification65 value) {
        this.fiId = value;
    }

}
