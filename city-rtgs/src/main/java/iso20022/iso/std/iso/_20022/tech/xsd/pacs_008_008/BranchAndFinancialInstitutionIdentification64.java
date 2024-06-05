
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Unique and unambiguous identification of a financial institution or a branch of a financial institution.
 * 
 * <p>Java class for BranchAndFinancialInstitutionIdentification6__4 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BranchAndFinancialInstitutionIdentification6__4">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="FinInstnId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}FinancialInstitutionIdentification18__1"/>
 *         <element name="BrnchId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}BranchData3__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BranchAndFinancialInstitutionIdentification6__4", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "finInstnId",
    "brnchId"
})
public class BranchAndFinancialInstitutionIdentification64 {

    /**
     * Unique and unambiguous identification of a financial institution, as assigned under an internationally recognised or proprietary identification scheme.
     * 
     */
    @XmlElement(name = "FinInstnId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected FinancialInstitutionIdentification181 finInstnId;
    /**
     * Identifies a specific branch of a financial institution.
     * 
     *                         Usage: This component should be used in case the identification information in the financial institution component does not provide identification up to branch level.
     * 
     */
    @XmlElement(name = "BrnchId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected BranchData31 brnchId;

    /**
     * Unique and unambiguous identification of a financial institution, as assigned under an internationally recognised or proprietary identification scheme.
     * 
     * @return
     *     possible object is
     *     {@link FinancialInstitutionIdentification181 }
     *     
     */
    public FinancialInstitutionIdentification181 getFinInstnId() {
        return finInstnId;
    }

    /**
     * Sets the value of the finInstnId property.
     * 
     * @param value
     *     allowed object is
     *     {@link FinancialInstitutionIdentification181 }
     *     
     * @see #getFinInstnId()
     */
    public void setFinInstnId(FinancialInstitutionIdentification181 value) {
        this.finInstnId = value;
    }

    /**
     * Identifies a specific branch of a financial institution.
     * 
     *                         Usage: This component should be used in case the identification information in the financial institution component does not provide identification up to branch level.
     * 
     * @return
     *     possible object is
     *     {@link BranchData31 }
     *     
     */
    public BranchData31 getBrnchId() {
        return brnchId;
    }

    /**
     * Sets the value of the brnchId property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchData31 }
     *     
     * @see #getBrnchId()
     */
    public void setBrnchId(BranchData31 value) {
        this.brnchId = value;
    }

}
