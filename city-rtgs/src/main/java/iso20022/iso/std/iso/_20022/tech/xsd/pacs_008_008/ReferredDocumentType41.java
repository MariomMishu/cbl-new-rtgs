
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the type of the document referred in the remittance information.
 * 
 * <p>Java class for ReferredDocumentType4__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="ReferredDocumentType4__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CdOrPrtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ReferredDocumentType3Choice__1"/>
 *         <element name="Issr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferredDocumentType4__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "cdOrPrtry",
    "issr"
})
public class ReferredDocumentType41 {

    /**
     * Provides the type details of the referred document.
     * 
     */
    @XmlElement(name = "CdOrPrtry", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected ReferredDocumentType3Choice1 cdOrPrtry;
    /**
     * Identification of the issuer of the reference document type.
     * 
     */
    @XmlElement(name = "Issr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String issr;

    /**
     * Provides the type details of the referred document.
     * 
     * @return
     *     possible object is
     *     {@link ReferredDocumentType3Choice1 }
     *     
     */
    public ReferredDocumentType3Choice1 getCdOrPrtry() {
        return cdOrPrtry;
    }

    /**
     * Sets the value of the cdOrPrtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferredDocumentType3Choice1 }
     *     
     * @see #getCdOrPrtry()
     */
    public void setCdOrPrtry(ReferredDocumentType3Choice1 value) {
        this.cdOrPrtry = value;
    }

    /**
     * Identification of the issuer of the reference document type.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIssr() {
        return issr;
    }

    /**
     * Sets the value of the issr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getIssr()
     */
    public void setIssr(String value) {
        this.issr = value;
    }

}
