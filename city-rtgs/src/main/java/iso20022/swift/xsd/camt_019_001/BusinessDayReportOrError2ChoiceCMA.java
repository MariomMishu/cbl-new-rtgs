//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_019_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for BusinessDayReportOrError2ChoiceCMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BusinessDayReportOrError2ChoiceCMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;choice&gt;
 *           &lt;element name="BizDayInf" type="{urn:swift:xsd:camt.019.001.04}BusinessDay2CMA"/&gt;
 *         &lt;/choice&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDayReportOrError2ChoiceCMA", propOrder = {
        "bizDayInf"
})
public class BusinessDayReportOrError2ChoiceCMA {

    @XmlElement(name = "BizDayInf")
    protected BusinessDay2CMA bizDayInf;

    /**
     * Gets the value of the bizDayInf property.
     *
     * @return possible object is
     * {@link BusinessDay2CMA }
     */
    public BusinessDay2CMA getBizDayInf() {
        return bizDayInf;
    }

    /**
     * Sets the value of the bizDayInf property.
     *
     * @param value allowed object is
     *              {@link BusinessDay2CMA }
     */
    public void setBizDayInf(BusinessDay2CMA value) {
        this.bizDayInf = value;
    }

}
