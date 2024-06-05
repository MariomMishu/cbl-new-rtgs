
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Unique and unambiguous way to identify an organisation.
 * 
 * <p>Java class for OrganisationIdentification29__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="OrganisationIdentification29__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="AnyBIC" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}AnyBICDec2014Identifier" minOccurs="0"/>
 *         <element name="LEI" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}LEIIdentifier" minOccurs="0"/>
 *         <element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}GenericOrganisationIdentification1__1" maxOccurs="2" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganisationIdentification29__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "anyBIC",
    "lei",
    "othr"
})
public class OrganisationIdentification291 {

    /**
     * Business identification code of the organisation.
     * 
     */
    @XmlElement(name = "AnyBIC", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String anyBIC;
    /**
     * Legal entity identification as an alternate identification for a party.
     * 
     */
    @XmlElement(name = "LEI", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String lei;
    /**
     * Unique identification of an organisation, as assigned by an institution, using an identification scheme.
     * 
     */
    @XmlElement(name = "Othr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<GenericOrganisationIdentification11> othr;

    /**
     * Business identification code of the organisation.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAnyBIC() {
        return anyBIC;
    }

    /**
     * Sets the value of the anyBIC property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAnyBIC()
     */
    public void setAnyBIC(String value) {
        this.anyBIC = value;
    }

    /**
     * Legal entity identification as an alternate identification for a party.
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
     * @see #getLEI()
     */
    public void setLEI(String value) {
        this.lei = value;
    }

    /**
     * Unique identification of an organisation, as assigned by an institution, using an identification scheme.
     * 
     * Gets the value of the othr property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the othr property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getOthr().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link GenericOrganisationIdentification11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the othr property.
     */
    public List<GenericOrganisationIdentification11> getOthr() {
        if (othr == null) {
            othr = new ArrayList<>();
        }
        return this.othr;
    }

}
