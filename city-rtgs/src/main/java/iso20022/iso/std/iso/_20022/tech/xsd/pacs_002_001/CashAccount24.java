//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CashAccount24 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CashAccount24"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}AccountIdentification4Choice"/&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}CashAccountType2Choice" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAccount24", propOrder = {
        "id",
        "tp"
})
public class CashAccount24 {

    @XmlElement(name = "Id", required = true)
    protected AccountIdentification4Choice id;
    @XmlElement(name = "Tp")
    protected CashAccountType2Choice tp;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link AccountIdentification4Choice }
     */
    public AccountIdentification4Choice getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link AccountIdentification4Choice }
     */
    public void setId(AccountIdentification4Choice value) {
        this.id = value;
    }

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link CashAccountType2Choice }
     */
    public CashAccountType2Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link CashAccountType2Choice }
     */
    public void setTp(CashAccountType2Choice value) {
        this.tp = value;
    }

}
