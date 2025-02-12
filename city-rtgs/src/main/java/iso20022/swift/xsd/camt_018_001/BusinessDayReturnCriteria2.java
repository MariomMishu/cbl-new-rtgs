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
 * <p>Java class for BusinessDayReturnCriteria2 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BusinessDayReturnCriteria2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="SysDtInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="SysStsInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="SysCcyInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="ClsrPrdInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="EvtInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="SsnPrdInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="EvtTpInd" type="{urn:swift:xsd:camt.018.001.03}RequestedIndicator" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BusinessDayReturnCriteria2", propOrder = {
        "sysDtInd",
        "sysStsInd",
        "sysCcyInd",
        "clsrPrdInd",
        "evtInd",
        "ssnPrdInd",
        "evtTpInd"
})
public class BusinessDayReturnCriteria2 {

    @XmlElement(name = "SysDtInd")
    protected Boolean sysDtInd;
    @XmlElement(name = "SysStsInd")
    protected Boolean sysStsInd;
    @XmlElement(name = "SysCcyInd")
    protected Boolean sysCcyInd;
    @XmlElement(name = "ClsrPrdInd")
    protected Boolean clsrPrdInd;
    @XmlElement(name = "EvtInd")
    protected Boolean evtInd;
    @XmlElement(name = "SsnPrdInd")
    protected Boolean ssnPrdInd;
    @XmlElement(name = "EvtTpInd")
    protected Boolean evtTpInd;

    /**
     * Gets the value of the sysDtInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSysDtInd() {
        return sysDtInd;
    }

    /**
     * Sets the value of the sysDtInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSysDtInd(Boolean value) {
        this.sysDtInd = value;
    }

    /**
     * Gets the value of the sysStsInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSysStsInd() {
        return sysStsInd;
    }

    /**
     * Sets the value of the sysStsInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSysStsInd(Boolean value) {
        this.sysStsInd = value;
    }

    /**
     * Gets the value of the sysCcyInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSysCcyInd() {
        return sysCcyInd;
    }

    /**
     * Sets the value of the sysCcyInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSysCcyInd(Boolean value) {
        this.sysCcyInd = value;
    }

    /**
     * Gets the value of the clsrPrdInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isClsrPrdInd() {
        return clsrPrdInd;
    }

    /**
     * Sets the value of the clsrPrdInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setClsrPrdInd(Boolean value) {
        this.clsrPrdInd = value;
    }

    /**
     * Gets the value of the evtInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isEvtInd() {
        return evtInd;
    }

    /**
     * Sets the value of the evtInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setEvtInd(Boolean value) {
        this.evtInd = value;
    }

    /**
     * Gets the value of the ssnPrdInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isSsnPrdInd() {
        return ssnPrdInd;
    }

    /**
     * Sets the value of the ssnPrdInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setSsnPrdInd(Boolean value) {
        this.ssnPrdInd = value;
    }

    /**
     * Gets the value of the evtTpInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isEvtTpInd() {
        return evtTpInd;
    }

    /**
     * Sets the value of the evtTpInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setEvtTpInd(Boolean value) {
        this.evtTpInd = value;
    }

}
