
package iso20022.iso.std.iso._20022.tech.xsd.head_001_002;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for FinancialInstitutionIdentification18__4 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="FinancialInstitutionIdentification18__4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BICFI" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}BICFIDec2014Identifier"/&gt;
 *         &lt;element name="ClrSysMmbId" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}ClearingSystemMemberIdentification2__2" minOccurs="0"/&gt;
 *         &lt;element name="LEI" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}LEIIdentifier" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialInstitutionIdentification18__4", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", propOrder = {
    "bicfi",
    "clrSysMmbId",
    "lei"
})
public class FinancialInstitutionIdentification184 {

    @XmlElement(name = "BICFI", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", required = true)
    protected String bicfi;
    @XmlElement(name = "ClrSysMmbId", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02")
    protected ClearingSystemMemberIdentification22 clrSysMmbId;
    @XmlElement(name = "LEI", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02")
    protected String lei;

    /**
     * Gets the value of the bicfi property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBICFI() {
        return bicfi;
    }

    /**
     * Sets the value of the bicfi property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBICFI(String value) {
        this.bicfi = value;
    }

    /**
     * Gets the value of the clrSysMmbId property.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemMemberIdentification22 }
     *     
     */
    public ClearingSystemMemberIdentification22 getClrSysMmbId() {
        return clrSysMmbId;
    }

    /**
     * Sets the value of the clrSysMmbId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemMemberIdentification22 }
     *     
     */
    public void setClrSysMmbId(ClearingSystemMemberIdentification22 value) {
        this.clrSysMmbId = value;
    }

    /**
     * Gets the value of the lei property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLEI() {
        return lei;
    }

    /**
     * Sets the value of the lei property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLEI(String value) {
        this.lei = value;
    }

}
