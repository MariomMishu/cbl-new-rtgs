
package iso20022.iso.std.iso._20022.tech.xsd.head_001_002;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ClearingSystemMemberIdentification2__2 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ClearingSystemMemberIdentification2__2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClrSysId" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}ClearingSystemIdentification2Choice__1"/&gt;
 *         &lt;element name="MmbId" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}HVPSPlus_IA_RestrictedFINXMax28Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClearingSystemMemberIdentification2__2", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", propOrder = {
    "clrSysId",
    "mmbId"
})
public class ClearingSystemMemberIdentification22 {

    @XmlElement(name = "ClrSysId", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", required = true)
    protected ClearingSystemIdentification2Choice1 clrSysId;
    @XmlElement(name = "MmbId", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", required = true)
    protected String mmbId;

    /**
     * Gets the value of the clrSysId property.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemIdentification2Choice1 }
     *     
     */
    public ClearingSystemIdentification2Choice1 getClrSysId() {
        return clrSysId;
    }

    /**
     * Sets the value of the clrSysId property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemIdentification2Choice1 }
     *     
     */
    public void setClrSysId(ClearingSystemIdentification2Choice1 value) {
        this.clrSysId = value;
    }

    /**
     * Gets the value of the mmbId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMmbId() {
        return mmbId;
    }

    /**
     * Sets the value of the mmbId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMmbId(String value) {
        this.mmbId = value;
    }

}
