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
 * <p>Java class for BusinessDayCriteria1 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BusinessDayCriteria1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SchCrit" type="{urn:swift:xsd:camt.018.001.03}BusinessDaySearchCriteria1"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDayCriteria1", propOrder = {
        "schCrit"
})
public class BusinessDayCriteria1 {

    @XmlElement(name = "SchCrit", required = true)
    protected BusinessDaySearchCriteria1 schCrit;

    /**
     * Gets the value of the schCrit property.
     *
     * @return possible object is
     * {@link BusinessDaySearchCriteria1 }
     */
    public BusinessDaySearchCriteria1 getSchCrit() {
        return schCrit;
    }

    /**
     * Sets the value of the schCrit property.
     *
     * @param value allowed object is
     *              {@link BusinessDaySearchCriteria1 }
     */
    public void setSchCrit(BusinessDaySearchCriteria1 value) {
        this.schCrit = value;
    }

}
