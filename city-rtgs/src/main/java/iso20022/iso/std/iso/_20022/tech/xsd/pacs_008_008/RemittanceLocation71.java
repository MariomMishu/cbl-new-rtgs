
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Provides information on the remittance advice.
 * 
 * <p>Java class for RemittanceLocation7__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RemittanceLocation7__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="RmtId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="RmtLctnDtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RemittanceLocationData1__1" maxOccurs="2" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceLocation7__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "rmtId",
    "rmtLctnDtls"
})
public class RemittanceLocation71 {

    /**
     * Unique identification, as assigned by the initiating party, to unambiguously identify the remittance information sent separately from the payment instruction, such as a remittance advice.
     * 
     */
    @XmlElement(name = "RmtId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String rmtId;
    /**
     * Set of elements used to provide information on the location and/or delivery of the remittance information.
     * 
     */
    @XmlElement(name = "RmtLctnDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<RemittanceLocationData11> rmtLctnDtls;

    /**
     * Unique identification, as assigned by the initiating party, to unambiguously identify the remittance information sent separately from the payment instruction, such as a remittance advice.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRmtId() {
        return rmtId;
    }

    /**
     * Sets the value of the rmtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRmtId()
     */
    public void setRmtId(String value) {
        this.rmtId = value;
    }

    /**
     * Set of elements used to provide information on the location and/or delivery of the remittance information.
     * 
     * Gets the value of the rmtLctnDtls property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rmtLctnDtls property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getRmtLctnDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RemittanceLocationData11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the rmtLctnDtls property.
     */
    public List<RemittanceLocationData11> getRmtLctnDtls() {
        if (rmtLctnDtls == null) {
            rmtLctnDtls = new ArrayList<>();
        }
        return this.rmtLctnDtls;
    }

}
