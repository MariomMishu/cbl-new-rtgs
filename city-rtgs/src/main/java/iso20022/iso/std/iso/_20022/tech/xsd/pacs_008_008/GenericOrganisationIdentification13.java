
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information related to an identification of an organisation.
 * 
 * <p>Java class for GenericOrganisationIdentification1__3 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="GenericOrganisationIdentification1__3">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended"/>
 *         <element name="SchmeNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}OrganisationIdentificationSchemeName1Choice__3" minOccurs="0"/>
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
@XmlType(name = "GenericOrganisationIdentification1__3", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "id",
    "schmeNm",
    "issr"
})
public class GenericOrganisationIdentification13 {

    /**
     * Identification assigned by an institution.
     * 
     */
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected String id;
    /**
     * Name of the identification scheme.
     * 
     */
    @XmlElement(name = "SchmeNm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected OrganisationIdentificationSchemeName1Choice3 schmeNm;
    /**
     * Entity that assigns the identification.
     * 
     */
    @XmlElement(name = "Issr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String issr;

    /**
     * Identification assigned by an institution.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getId()
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Name of the identification scheme.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentificationSchemeName1Choice3 }
     *     
     */
    public OrganisationIdentificationSchemeName1Choice3 getSchmeNm() {
        return schmeNm;
    }

    /**
     * Sets the value of the schmeNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentificationSchemeName1Choice3 }
     *     
     * @see #getSchmeNm()
     */
    public void setSchmeNm(OrganisationIdentificationSchemeName1Choice3 value) {
        this.schmeNm = value;
    }

    /**
     * Entity that assigns the identification.
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
