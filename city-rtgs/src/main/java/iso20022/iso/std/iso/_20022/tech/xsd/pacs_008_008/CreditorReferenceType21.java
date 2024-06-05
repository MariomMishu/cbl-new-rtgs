
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the type of creditor reference.
 * 
 * <p>Java class for CreditorReferenceType2__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CreditorReferenceType2__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="CdOrPrtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CreditorReferenceType1Choice__1"/>
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
@XmlType(name = "CreditorReferenceType2__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "cdOrPrtry",
    "issr"
})
public class CreditorReferenceType21 {

    /**
     * Coded or proprietary format creditor reference type.
     * 
     */
    @XmlElement(name = "CdOrPrtry", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected CreditorReferenceType1Choice1 cdOrPrtry;
    /**
     * Entity that assigns the credit reference type.
     * 
     */
    @XmlElement(name = "Issr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String issr;

    /**
     * Coded or proprietary format creditor reference type.
     * 
     * @return
     *     possible object is
     *     {@link CreditorReferenceType1Choice1 }
     *     
     */
    public CreditorReferenceType1Choice1 getCdOrPrtry() {
        return cdOrPrtry;
    }

    /**
     * Sets the value of the cdOrPrtry property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorReferenceType1Choice1 }
     *     
     * @see #getCdOrPrtry()
     */
    public void setCdOrPrtry(CreditorReferenceType1Choice1 value) {
        this.cdOrPrtry = value;
    }

    /**
     * Entity that assigns the credit reference type.
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
