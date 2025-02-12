//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for BillerTransactionInformation complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="BillerTransactionInformation"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BllTxRef" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}RestrictedFINMax35Text"/&gt;
 *         &lt;element name="BllTxDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}ISODate"/&gt;
 *         &lt;element name="RcptNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}RestrictedFINMax35Text" minOccurs="0"/&gt;
 *         &lt;element name="BlrRsnCd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}RestrictedFINMax35Text" minOccurs="0"/&gt;
 *         &lt;element name="BlrRsnDscr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}RestrictedFINMax35Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BillerTransactionInformation", propOrder = {
        "bllTxRef",
        "bllTxDt",
        "rcptNb",
        "blrRsnCd",
        "blrRsnDscr"
})
public class BillerTransactionInformation {

    @XmlElement(name = "BllTxRef", required = true)
    protected String bllTxRef;
    @XmlElement(name = "BllTxDt", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar bllTxDt;
    @XmlElement(name = "RcptNb")
    protected String rcptNb;
    @XmlElement(name = "BlrRsnCd")
    protected String blrRsnCd;
    @XmlElement(name = "BlrRsnDscr")
    protected String blrRsnDscr;

    /**
     * Gets the value of the bllTxRef property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBllTxRef() {
        return bllTxRef;
    }

    /**
     * Sets the value of the bllTxRef property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBllTxRef(String value) {
        this.bllTxRef = value;
    }

    /**
     * Gets the value of the bllTxDt property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getBllTxDt() {
        return bllTxDt;
    }

    /**
     * Sets the value of the bllTxDt property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setBllTxDt(XMLGregorianCalendar value) {
        this.bllTxDt = value;
    }

    /**
     * Gets the value of the rcptNb property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getRcptNb() {
        return rcptNb;
    }

    /**
     * Sets the value of the rcptNb property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setRcptNb(String value) {
        this.rcptNb = value;
    }

    /**
     * Gets the value of the blrRsnCd property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBlrRsnCd() {
        return blrRsnCd;
    }

    /**
     * Sets the value of the blrRsnCd property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBlrRsnCd(String value) {
        this.blrRsnCd = value;
    }

    /**
     * Gets the value of the blrRsnDscr property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getBlrRsnDscr() {
        return blrRsnDscr;
    }

    /**
     * Sets the value of the blrRsnDscr property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setBlrRsnDscr(String value) {
        this.blrRsnDscr = value;
    }

}
