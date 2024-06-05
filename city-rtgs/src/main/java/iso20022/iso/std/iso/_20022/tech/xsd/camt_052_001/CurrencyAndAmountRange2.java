//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_052_001;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CurrencyAndAmountRange2 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CurrencyAndAmountRange2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}ImpliedCurrencyAmountRangeChoice"/&gt;
 *         &lt;element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}CreditDebitCode" minOccurs="0"/&gt;
 *         &lt;element name="Ccy" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}ActiveOrHistoricCurrencyCode"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyAndAmountRange2", propOrder = {
        "amt",
        "cdtDbtInd",
        "ccy"
})
public class CurrencyAndAmountRange2 {

    @XmlElement(name = "Amt", required = true)
    protected ImpliedCurrencyAmountRangeChoice amt;
    @XmlElement(name = "CdtDbtInd")
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "Ccy", required = true)
    protected String ccy;

    /**
     * Gets the value of the amt property.
     *
     * @return possible object is
     * {@link ImpliedCurrencyAmountRangeChoice }
     */
    public ImpliedCurrencyAmountRangeChoice getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     *
     * @param value allowed object is
     *              {@link ImpliedCurrencyAmountRangeChoice }
     */
    public void setAmt(ImpliedCurrencyAmountRangeChoice value) {
        this.amt = value;
    }

    /**
     * Gets the value of the cdtDbtInd property.
     *
     * @return possible object is
     * {@link CreditDebitCode }
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     *
     * @param value allowed object is
     *              {@link CreditDebitCode }
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Gets the value of the ccy property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCcy(String value) {
        this.ccy = value;
    }

}
