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
 * <p>Java class for SystemEvent2CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SystemEvent2CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:swift:xsd:camt.019.001.04}SystemEventType2ChoiceCMA"/&gt;
 *         &lt;element name="SchdldTm" type="{urn:swift:xsd:camt.019.001.04}ISODateTime"/&gt;
 *         &lt;element name="StartTm" type="{urn:swift:xsd:camt.019.001.04}ISODateTime" minOccurs="0"/&gt;
 *         &lt;element name="EndTm" type="{urn:swift:xsd:camt.019.001.04}ISODateTime" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SystemEvent2CMA", propOrder = {
        "tp",
        "schdldTm",
        "startTm",
        "endTm"
})
public class SystemEvent2CMA {

    @XmlElement(name = "Tp", required = true)
    protected SystemEventType2ChoiceCMA tp;
    @XmlElement(name = "SchdldTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar schdldTm;
    @XmlElement(name = "StartTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar startTm;
    @XmlElement(name = "EndTm")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar endTm;

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link SystemEventType2ChoiceCMA }
     */
    public SystemEventType2ChoiceCMA getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link SystemEventType2ChoiceCMA }
     */
    public void setTp(SystemEventType2ChoiceCMA value) {
        this.tp = value;
    }

    /**
     * Gets the value of the schdldTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getSchdldTm() {
        return schdldTm;
    }

    /**
     * Sets the value of the schdldTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setSchdldTm(XMLGregorianCalendar value) {
        this.schdldTm = value;
    }

    /**
     * Gets the value of the startTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getStartTm() {
        return startTm;
    }

    /**
     * Sets the value of the startTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setStartTm(XMLGregorianCalendar value) {
        this.startTm = value;
    }

    /**
     * Gets the value of the endTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getEndTm() {
        return endTm;
    }

    /**
     * Sets the value of the endTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setEndTm(XMLGregorianCalendar value) {
        this.endTm = value;
    }

}
