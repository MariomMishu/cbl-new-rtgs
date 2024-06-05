
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * Set of elements used to provide information on the original amount.
 * 
 * <p>Java class for AmountAndCurrencyExchange3__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="AmountAndCurrencyExchange3__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InstdAmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AmountAndCurrencyExchangeDetails3__1" minOccurs="0"/>
 *         <element name="TxAmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AmountAndCurrencyExchangeDetails3__1" minOccurs="0"/>
 *         <element name="PrtryAmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AmountAndCurrencyExchangeDetails4__1" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AmountAndCurrencyExchange3__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "instdAmt",
    "txAmt",
    "prtryAmt"
})
public class AmountAndCurrencyExchange31 {

    /**
     * Identifies the amount of money to be moved between the debtor and creditor, before deduction of charges, expressed in the currency as ordered by the initiating party and provides currency exchange information in case the instructed amount and/or currency is/are different from the entry amount and/or currency.
     * 
     */
    @XmlElement(name = "InstdAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected AmountAndCurrencyExchangeDetails31 instdAmt;
    /**
     * Amount of the underlying transaction.
     * 
     */
    @XmlElement(name = "TxAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected AmountAndCurrencyExchangeDetails31 txAmt;
    /**
     * Set of elements used to provide information on the original amount and currency exchange.
     * 
     */
    @XmlElement(name = "PrtryAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected List<AmountAndCurrencyExchangeDetails41> prtryAmt;

    /**
     * Identifies the amount of money to be moved between the debtor and creditor, before deduction of charges, expressed in the currency as ordered by the initiating party and provides currency exchange information in case the instructed amount and/or currency is/are different from the entry amount and/or currency.
     * 
     * @return
     *     possible object is
     *     {@link AmountAndCurrencyExchangeDetails31 }
     *     
     */
    public AmountAndCurrencyExchangeDetails31 getInstdAmt() {
        return instdAmt;
    }

    /**
     * Sets the value of the instdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountAndCurrencyExchangeDetails31 }
     *     
     * @see #getInstdAmt()
     */
    public void setInstdAmt(AmountAndCurrencyExchangeDetails31 value) {
        this.instdAmt = value;
    }

    /**
     * Amount of the underlying transaction.
     * 
     * @return
     *     possible object is
     *     {@link AmountAndCurrencyExchangeDetails31 }
     *     
     */
    public AmountAndCurrencyExchangeDetails31 getTxAmt() {
        return txAmt;
    }

    /**
     * Sets the value of the txAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountAndCurrencyExchangeDetails31 }
     *     
     * @see #getTxAmt()
     */
    public void setTxAmt(AmountAndCurrencyExchangeDetails31 value) {
        this.txAmt = value;
    }

    /**
     * Set of elements used to provide information on the original amount and currency exchange.
     * 
     * Gets the value of the prtryAmt property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prtryAmt property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getPrtryAmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AmountAndCurrencyExchangeDetails41 }
     * </p>
     * 
     * 
     * @return
     *     The value of the prtryAmt property.
     */
    public List<AmountAndCurrencyExchangeDetails41> getPrtryAmt() {
        if (prtryAmt == null) {
            prtryAmt = new ArrayList<>();
        }
        return this.prtryAmt;
    }

}
