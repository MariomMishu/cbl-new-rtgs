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
 * <p>Java class for DateTimePeriodDetails complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="DateTimePeriodDetails"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FrDtTm" type="{urn:swift:xsd:camt.019.001.04}ISODateTime"/&gt;
 *         &lt;element name="ToDtTm" type="{urn:swift:xsd:camt.019.001.04}ISODateTime"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateTimePeriodDetails", propOrder = {
        "frDtTm",
        "toDtTm"
})
public class DateTimePeriodDetails {

    @XmlElement(name = "FrDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar frDtTm;
    @XmlElement(name = "ToDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar toDtTm;

    /**
     * Gets the value of the frDtTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getFrDtTm() {
        return frDtTm;
    }

    /**
     * Sets the value of the frDtTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setFrDtTm(XMLGregorianCalendar value) {
        this.frDtTm = value;
    }

    /**
     * Gets the value of the toDtTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getToDtTm() {
        return toDtTm;
    }

    /**
     * Sets the value of the toDtTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setToDtTm(XMLGregorianCalendar value) {
        this.toDtTm = value;
    }

}
