
package iso20022.iso.std.iso._20022.tech.xsd.head_001_002;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ImplementationSpecification1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ImplementationSpecification1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Regy" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}Max350Text"/&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:head.001.001.02}Max2048Text"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImplementationSpecification1", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", propOrder = {
    "regy",
    "id"
})
public class ImplementationSpecification1 {

    @XmlElement(name = "Regy", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", required = true)
    protected String regy;
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", required = true)
    protected String id;

    /**
     * Gets the value of the regy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegy() {
        return regy;
    }

    /**
     * Sets the value of the regy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegy(String value) {
        this.regy = value;
    }

    /**
     * Gets the value of the id property.
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
     */
    public void setId(String value) {
        this.id = value;
    }

}
