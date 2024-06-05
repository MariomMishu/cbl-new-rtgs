
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Information needed due to regulatory and/or statutory requirements.
 * 
 * <p>Java class for RegulatoryReporting3__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RegulatoryReporting3__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DbtCdtRptgInd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RegulatoryReportingType1Code" minOccurs="0"/>
 *         <element name="Authrty" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RegulatoryAuthority2__1" minOccurs="0"/>
 *         <element name="Dtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}StructuredRegulatoryReporting3__1" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RegulatoryReporting3__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "dbtCdtRptgInd",
    "authrty",
    "dtls"
})
public class RegulatoryReporting31 {

    /**
     * Identifies whether the regulatory reporting information applies to the debit side, to the credit side or to both debit and credit sides of the transaction.
     * 
     */
    @XmlElement(name = "DbtCdtRptgInd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "string")
    protected RegulatoryReportingType1Code dbtCdtRptgInd;
    /**
     * Entity requiring the regulatory reporting information.
     * 
     */
    @XmlElement(name = "Authrty", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected RegulatoryAuthority21 authrty;
    /**
     * Set of elements used to provide details on the regulatory reporting information.
     * 
     */
    @XmlElement(name = "Dtls", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<StructuredRegulatoryReporting31> dtls;

    /**
     * Identifies whether the regulatory reporting information applies to the debit side, to the credit side or to both debit and credit sides of the transaction.
     * 
     * @return
     *     possible object is
     *     {@link RegulatoryReportingType1Code }
     *     
     */
    public RegulatoryReportingType1Code getDbtCdtRptgInd() {
        return dbtCdtRptgInd;
    }

    /**
     * Sets the value of the dbtCdtRptgInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegulatoryReportingType1Code }
     *     
     * @see #getDbtCdtRptgInd()
     */
    public void setDbtCdtRptgInd(RegulatoryReportingType1Code value) {
        this.dbtCdtRptgInd = value;
    }

    /**
     * Entity requiring the regulatory reporting information.
     * 
     * @return
     *     possible object is
     *     {@link RegulatoryAuthority21 }
     *     
     */
    public RegulatoryAuthority21 getAuthrty() {
        return authrty;
    }

    /**
     * Sets the value of the authrty property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegulatoryAuthority21 }
     *     
     * @see #getAuthrty()
     */
    public void setAuthrty(RegulatoryAuthority21 value) {
        this.authrty = value;
    }

    /**
     * Set of elements used to provide details on the regulatory reporting information.
     * 
     * Gets the value of the dtls property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dtls property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructuredRegulatoryReporting31 }
     * </p>
     * 
     * 
     * @return
     *     The value of the dtls property.
     */
    public List<StructuredRegulatoryReporting31> getDtls() {
        if (dtls == null) {
            dtls = new ArrayList<>();
        }
        return this.dtls;
    }

}
