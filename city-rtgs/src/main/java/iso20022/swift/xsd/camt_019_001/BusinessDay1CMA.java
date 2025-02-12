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
 * <p>Java class for BusinessDay1CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BusinessDay1CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SysId" type="{urn:swift:xsd:camt.019.001.04}SystemIdentification2ChoiceCMA"/&gt;
 *         &lt;element name="BizDayOrErr" type="{urn:swift:xsd:camt.019.001.04}BusinessDayReportOrError2ChoiceCMA"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDay1CMA", propOrder = {
        "sysId",
        "bizDayOrErr"
})
public class BusinessDay1CMA {

    @XmlElement(name = "SysId", required = true)
    protected SystemIdentification2ChoiceCMA sysId;
    @XmlElement(name = "BizDayOrErr", required = true)
    protected BusinessDayReportOrError2ChoiceCMA bizDayOrErr;

    /**
     * Gets the value of the sysId property.
     *
     * @return possible object is
     * {@link SystemIdentification2ChoiceCMA }
     */
    public SystemIdentification2ChoiceCMA getSysId() {
        return sysId;
    }

    /**
     * Sets the value of the sysId property.
     *
     * @param value allowed object is
     *              {@link SystemIdentification2ChoiceCMA }
     */
    public void setSysId(SystemIdentification2ChoiceCMA value) {
        this.sysId = value;
    }

    /**
     * Gets the value of the bizDayOrErr property.
     *
     * @return possible object is
     * {@link BusinessDayReportOrError2ChoiceCMA }
     */
    public BusinessDayReportOrError2ChoiceCMA getBizDayOrErr() {
        return bizDayOrErr;
    }

    /**
     * Sets the value of the bizDayOrErr property.
     *
     * @param value allowed object is
     *              {@link BusinessDayReportOrError2ChoiceCMA }
     */
    public void setBizDayOrErr(BusinessDayReportOrError2ChoiceCMA value) {
        this.bizDayOrErr = value;
    }

}
