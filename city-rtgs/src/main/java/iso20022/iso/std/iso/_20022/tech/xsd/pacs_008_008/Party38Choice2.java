
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Nature or use of the account.
 * 
 * <p>Java class for Party38Choice__2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Party38Choice__2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="OrgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}OrganisationIdentification29__2"/>
 *         <element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PersonIdentification13__2"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party38Choice__2", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "orgId",
    "prvtId"
})
public class Party38Choice2 {

    /**
     * Unique and unambiguous way to identify an organisation.
     * 
     */
    @XmlElement(name = "OrgId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected OrganisationIdentification292 orgId;
    /**
     * Unique and unambiguous identification of a person, for example a passport.
     * 
     */
    @XmlElement(name = "PrvtId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected PersonIdentification132 prvtId;

    /**
     * Unique and unambiguous way to identify an organisation.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentification292 }
     *     
     */
    public OrganisationIdentification292 getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentification292 }
     *     
     * @see #getOrgId()
     */
    public void setOrgId(OrganisationIdentification292 value) {
        this.orgId = value;
    }

    /**
     * Unique and unambiguous identification of a person, for example a passport.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentification132 }
     *     
     */
    public PersonIdentification132 getPrvtId() {
        return prvtId;
    }

    /**
     * Sets the value of the prvtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentification132 }
     *     
     * @see #getPrvtId()
     */
    public void setPrvtId(PersonIdentification132 value) {
        this.prvtId = value;
    }

}
