
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the type of the document line identification.
 * 
 * <p>Java class for DocumentLineType1Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="DocumentLineType1Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ExternalDocumentLineType1Code"/>
 *         <element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentLineType1Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "cd",
    "prtry"
})
public class DocumentLineType1Choice1 {

    /**
     * Line identification type in a coded form.
     * 
     */
    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String cd;
    /**
     * Proprietary identification of the type of the remittance document.
     * 
     */
    @XmlElement(name = "Prtry", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String prtry;

    /**
     * Line identification type in a coded form.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCd()
     */
    public void setCd(String value) {
        this.cd = value;
    }

    /**
     * Proprietary identification of the type of the remittance document.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPrtry()
     */
    public void setPrtry(String value) {
        this.prtry = value;
    }

}
