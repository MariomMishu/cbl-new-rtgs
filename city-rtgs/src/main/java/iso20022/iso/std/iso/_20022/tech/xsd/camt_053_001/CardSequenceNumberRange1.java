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
 * <p>Java class for CardSequenceNumberRange1 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CardSequenceNumberRange1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FrstTx" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="LastTx" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max35Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CardSequenceNumberRange1", propOrder = {
        "frstTx",
        "lastTx"
})
public class CardSequenceNumberRange1 {

    @XmlElement(name = "FrstTx")
    protected String frstTx;
    @XmlElement(name = "LastTx")
    protected String lastTx;

    /**
     * Gets the value of the frstTx property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getFrstTx() {
        return frstTx;
    }

    /**
     * Sets the value of the frstTx property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFrstTx(String value) {
        this.frstTx = value;
    }

    /**
     * Gets the value of the lastTx property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getLastTx() {
        return lastTx;
    }

    /**
     * Sets the value of the lastTx property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setLastTx(String value) {
        this.lastTx = value;
    }

}
