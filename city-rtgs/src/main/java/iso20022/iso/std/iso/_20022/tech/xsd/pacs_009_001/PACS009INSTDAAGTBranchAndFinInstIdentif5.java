//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PACS009INSTDAAGTBranchAndFinInstIdentif5 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PACS009INSTDAAGTBranchAndFinInstIdentif5"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FinInstnId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.04}FinancialInstitutionIdentification8"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PACS009INSTDAAGTBranchAndFinInstIdentif5", propOrder = {
        "finInstnId"
})
public class PACS009INSTDAAGTBranchAndFinInstIdentif5 {

    @XmlElement(name = "FinInstnId", required = true)
    protected FinancialInstitutionIdentification8 finInstnId;

    /**
     * Gets the value of the finInstnId property.
     *
     * @return possible object is
     * {@link FinancialInstitutionIdentification8 }
     */
    public FinancialInstitutionIdentification8 getFinInstnId() {
        return finInstnId;
    }

    /**
     * Sets the value of the finInstnId property.
     *
     * @param value allowed object is
     *              {@link FinancialInstitutionIdentification8 }
     */
    public void setFinInstnId(FinancialInstitutionIdentification8 value) {
        this.finInstnId = value;
    }

}
