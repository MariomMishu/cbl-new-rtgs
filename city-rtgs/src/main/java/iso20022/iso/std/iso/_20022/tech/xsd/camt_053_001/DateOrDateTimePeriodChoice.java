//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_053_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for DateOrDateTimePeriodChoice complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DateOrDateTimePeriodChoice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}DatePeriodDetails"/&gt;
 *         &lt;element name="DtTm" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}DateTimePeriodDetails"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateOrDateTimePeriodChoice", propOrder = {
        "dt",
        "dtTm"
})
public class DateOrDateTimePeriodChoice {

    @XmlElement(name = "Dt")
    protected DatePeriodDetails dt;
    @XmlElement(name = "DtTm")
    protected DateTimePeriodDetails dtTm;

    /**
     * Gets the value of the dt property.
     *
     * @return possible object is
     * {@link DatePeriodDetails }
     */
    public DatePeriodDetails getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     *
     * @param value allowed object is
     *              {@link DatePeriodDetails }
     */
    public void setDt(DatePeriodDetails value) {
        this.dt = value;
    }

    /**
     * Gets the value of the dtTm property.
     *
     * @return possible object is
     * {@link DateTimePeriodDetails }
     */
    public DateTimePeriodDetails getDtTm() {
        return dtTm;
    }

    /**
     * Sets the value of the dtTm property.
     *
     * @param value allowed object is
     *              {@link DateTimePeriodDetails }
     */
    public void setDtTm(DateTimePeriodDetails value) {
        this.dtTm = value;
    }

}
