//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_018_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessDayQuery1CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BusinessDayQuery1CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Crit" type="{urn:swift:xsd:camt.018.001.03}BusinessDayCriteria2Choice"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDayQuery1CMA", propOrder = {
        "crit"
})
public class BusinessDayQuery1CMA {

    @XmlElement(name = "Crit", required = true)
    protected BusinessDayCriteria2Choice crit;

    /**
     * Gets the value of the crit property.
     *
     * @return possible object is
     * {@link BusinessDayCriteria2Choice }
     */
    public BusinessDayCriteria2Choice getCrit() {
        return crit;
    }

    /**
     * Sets the value of the crit property.
     *
     * @param value allowed object is
     *              {@link BusinessDayCriteria2Choice }
     */
    public void setCrit(BusinessDayCriteria2Choice value) {
        this.crit = value;
    }

}
