//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_019_001;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for ClosureReason2Choice complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ClosureReason2Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="Cd" type="{urn:swift:xsd:camt.019.001.04}SystemClosureReason1Code"/&gt;
 *           &lt;element name="Prtry" type="{urn:swift:xsd:camt.019.001.04}Max35Text"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ClosureReason2Choice", propOrder = {
        "cd",
        "prtry"
})
public class ClosureReason2Choice {

    @XmlElement(name = "Cd")
    @XmlSchemaType(name = "string")
    protected SystemClosureReason1Code cd;
    @XmlElement(name = "Prtry")
    protected String prtry;

    /**
     * Gets the value of the cd property.
     *
     * @return possible object is
     * {@link SystemClosureReason1Code }
     */
    public SystemClosureReason1Code getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     *
     * @param value allowed object is
     *              {@link SystemClosureReason1Code }
     */
    public void setCd(SystemClosureReason1Code value) {
        this.cd = value;
    }

    /**
     * Gets the value of the prtry property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPrtry(String value) {
        this.prtry = value;
    }

}
