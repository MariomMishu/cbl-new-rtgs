
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of elements used to provide information on the original amount and currency exchange.
 * 
 * <p>Java class for AmountAndCurrencyExchangeDetails3__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="AmountAndCurrencyExchangeDetails3__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyAndAmount"/>
 *         <element name="CcyXchg" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CurrencyExchange5__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountAndCurrencyExchangeDetails3__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "amt",
    "ccyXchg"
})
public class AmountAndCurrencyExchangeDetails31 {

    /**
     * Amount of money to be moved between the debtor and creditor, before deduction of charges, expressed in the currency as ordered by the initiating party.
     * 
     */
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    /**
     * Set of elements used to provide details on the currency exchange.
     * 
     */
    @XmlElement(name = "CcyXchg", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected CurrencyExchange51 ccyXchg;

    /**
     * Amount of money to be moved between the debtor and creditor, before deduction of charges, expressed in the currency as ordered by the initiating party.
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
     *     {@link CurrencyExchange51 }
     *     
     */
    public CurrencyExchange51 getCcyXchg() {
        return ccyXchg;
    }

    /**
     * Sets the value of the ccyXchg property.
     * 
     * @param value
     *     allowed object is
     *     {@link CurrencyExchange51 }
     *     
     * @see #getCcyXchg()
     */
    public void setCcyXchg(CurrencyExchange51 value) {
        this.ccyXchg = value;
    }

}
