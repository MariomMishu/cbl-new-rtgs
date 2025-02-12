//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_019_001;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BusinessDay2CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BusinessDay2CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SysDt" type="{urn:swift:xsd:camt.019.001.04}ISODate"/&gt;
 *         &lt;element name="SysInfPerCcy" type="{urn:swift:xsd:camt.019.001.04}SystemAvailabilityAndEvents1CMA"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDay2CMA", propOrder = {
        "sysDt",
        "sysInfPerCcy"
})
public class BusinessDay2CMA {

    @XmlElement(name = "SysDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar sysDt;
    @XmlElement(name = "SysInfPerCcy", required = true)
    protected SystemAvailabilityAndEvents1CMA sysInfPerCcy;

    /**
     * Gets the value of the sysDt property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getSysDt() {
        return sysDt;
    }

    /**
     * Sets the value of the sysDt property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setSysDt(XMLGregorianCalendar value) {
        this.sysDt = value;
    }

    /**
     * Gets the value of the sysInfPerCcy property.
     *
     * @return possible object is
     * {@link SystemAvailabilityAndEvents1CMA }
     */
    public SystemAvailabilityAndEvents1CMA getSysInfPerCcy() {
        return sysInfPerCcy;
    }

    /**
     * Sets the value of the sysInfPerCcy property.
     *
     * @param value allowed object is
     *              {@link SystemAvailabilityAndEvents1CMA }
     */
    public void setSysInfPerCcy(SystemAvailabilityAndEvents1CMA value) {
        this.sysInfPerCcy = value;
    }

}
