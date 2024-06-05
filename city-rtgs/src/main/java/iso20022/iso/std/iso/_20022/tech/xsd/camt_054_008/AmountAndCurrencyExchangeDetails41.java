
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of elements used to provide information on the original amount and currency exchange.
 * 
 * <p>Java class for AmountAndCurrencyExchangeDetails4__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="AmountAndCurrencyExchangeDetails4__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyAndAmount"/>
 *         <element name="CcyXchg" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CurrencyExchange5__2" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountAndCurrencyExchangeDetails4__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "tp",
    "amt",
    "ccyXchg"
})
public class AmountAndCurrencyExchangeDetails41 {

    /**
     * Specifies the type of amount.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String tp;
    /**
     * Amount of money to be exchanged against another amount of money in the counter currency.
     * 
     */
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    /**
     * Set of elements used to provide details on the currency exchange.
     * 
     */
    @XmlElement(name = "CcyXchg", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected CurrencyExchange52 ccyXchg;

    /**
     * Specifies the type of amount.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTp()
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Amount of money to be exchanged against another amount of money in the counter currency.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getAmt()
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Set of elements used to provide details on the currency exchange.
     * 
     * @return
     *     possible object is
     *     {@link CurrencyExchange52 }
     *     
     */
    public CurrencyExchange52 getCcyXchg() {
        return ccyXchg;
    }

    /**
     * Sets the value of the ccyXchg property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyExchange52 }
     *     
     * @see #getCcyXchg()
     */
    public void setCcyXchg(CurrencyExchange52 value) {
        this.ccyXchg = value;
    }

}
