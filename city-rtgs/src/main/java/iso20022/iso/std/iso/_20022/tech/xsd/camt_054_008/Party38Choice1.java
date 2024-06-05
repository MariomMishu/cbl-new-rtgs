
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Nature or use of the account.
 * 
 * <p>Java class for Party38Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Party38Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="OrgId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}OrganisationIdentification29__1"/>
 *         <element name="PrvtId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}PersonIdentification13__1"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Party38Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "orgId",
    "prvtId"
})
public class Party38Choice1 {

    /**
     * Unique and unambiguous way to identify an organisation.
     * 
     */
    @XmlElement(name = "OrgId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected OrganisationIdentification291 orgId;
    /**
     * Unique and unambiguous identification of a person, for example a passport.
     * 
     */
    @XmlElement(name = "PrvtId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected PersonIdentification131 prvtId;

    /**
     * Unique and unambiguous way to identify an organisation.
     * 
     * @return
     *     possible object is
     *     {@link OrganisationIdentification291 }
     *     
     */
    public OrganisationIdentification291 getOrgId() {
        return orgId;
    }

    /**
     * Sets the value of the orgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link OrganisationIdentification291 }
     *     
     * @see #getOrgId()
     */
    public void setOrgId(OrganisationIdentification291 value) {
        this.orgId = value;
    }

    /**
     * Unique and unambiguous identification of a person, for example a passport.
     * 
     * @return
     *     possible object is
     *     {@link PersonIdentification131 }
     *     
     */
    public PersonIdentification131 getPrvtId() {
        return prvtId;
    }

    /**
     * Sets the value of the prvtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PersonIdentification131 }
     *     
     * @see #getPrvtId()
     */
    public void setPrvtId(PersonIdentification131 value) {
        this.prvtId = value;
    }

}
