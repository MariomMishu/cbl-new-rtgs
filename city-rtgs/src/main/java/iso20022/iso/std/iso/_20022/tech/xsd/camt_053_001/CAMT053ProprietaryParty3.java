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
 * <p>Java class for CAMT053ProprietaryParty3 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CAMT053ProprietaryParty3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max35Text"/&gt;
 *         &lt;element name="Pty" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CAMT053ProprietaryParty3", propOrder = {
        "tp",
        "pty"
})
public class CAMT053ProprietaryParty3 {

    @XmlElement(name = "Tp", required = true)
    protected String tp;
    @XmlElement(name = "Pty", required = true)
    protected CAMT053PartyIdentification43 pty;

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Gets the value of the pty property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getPty() {
        return pty;
    }

    /**
     * Sets the value of the pty property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setPty(CAMT053PartyIdentification43 value) {
        this.pty = value;
    }

}
