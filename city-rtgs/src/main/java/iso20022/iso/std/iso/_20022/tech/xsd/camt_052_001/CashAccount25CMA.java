//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_052_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashAccount25CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CashAccount25CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}CAMT052AccountIdentification4ChoiceCMA"/&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}CAMT052CashAccountType2Choice" minOccurs="0"/&gt;
 *         &lt;element name="Ownr" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}CAMT052PartyIdentification43" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAccount25CMA", propOrder = {
        "id",
        "tp",
        "ownr"
})
public class CashAccount25CMA {

    @XmlElement(name = "Id", required = true)
    protected CAMT052AccountIdentification4ChoiceCMA id;
    @XmlElement(name = "Tp")
    protected CAMT052CashAccountType2Choice tp;
    @XmlElement(name = "Ownr")
    protected CAMT052PartyIdentification43 ownr;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link CAMT052AccountIdentification4ChoiceCMA }
     */
    public CAMT052AccountIdentification4ChoiceCMA getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link CAMT052AccountIdentification4ChoiceCMA }
     */
    public void setId(CAMT052AccountIdentification4ChoiceCMA value) {
        this.id = value;
    }

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link CAMT052CashAccountType2Choice }
     */
    public CAMT052CashAccountType2Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link CAMT052CashAccountType2Choice }
     */
    public void setTp(CAMT052CashAccountType2Choice value) {
        this.tp = value;
    }

    /**
     * Gets the value of the ownr property.
     *
     * @return possible object is
     * {@link CAMT052PartyIdentification43 }
     */
    public CAMT052PartyIdentification43 getOwnr() {
        return ownr;
    }

    /**
     * Sets the value of the ownr property.
     *
     * @param value allowed object is
     *              {@link CAMT052PartyIdentification43 }
     */
    public void setOwnr(CAMT052PartyIdentification43 value) {
        this.ownr = value;
    }

}
