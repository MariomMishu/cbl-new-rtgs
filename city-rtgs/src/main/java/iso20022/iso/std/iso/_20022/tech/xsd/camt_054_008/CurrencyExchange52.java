
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;


/**
 * Set of elements used to provide details of the currency exchange.
 * 
 * <p>Java class for CurrencyExchange5__2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CurrencyExchange5__2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="SrcCcy" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyCode"/>
 *         <element name="TrgtCcy" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyCode" minOccurs="0"/>
 *         <element name="UnitCcy" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyCode" minOccurs="0"/>
 *         <element name="XchgRate" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BaseOneRate"/>
 *         <element name="CtrctId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="QtnDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ISODateTime" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CurrencyExchange5__2", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "srcCcy",
    "trgtCcy",
    "unitCcy",
    "xchgRate",
    "ctrctId",
    "qtnDt"
})
public class CurrencyExchange52 {

    /**
     * Currency from which an amount is to be converted in a currency conversion.
     * 
     */
    @XmlElement(name = "SrcCcy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String srcCcy;
    /**
     * Currency into which an amount is to be converted in a currency conversion.
     * 
     */
    @XmlElement(name = "TrgtCcy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String trgtCcy;
    /**
     * Currency in which the rate of exchange is expressed in a currency exchange. In the example 1GBP = xxxCUR, the unit currency is GBP.
     * 
     */
    @XmlElement(name = "UnitCcy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String unitCcy;
    /**
     * Factor used to convert an amount from one currency into another. This reflects the price at which one currency was bought with another currency.
     * 
     *                         Usage: ExchangeRate expresses the ratio between UnitCurrency and QuotedCurrency (ExchangeRate = UnitCurrency/QuotedCurrency).
     * 
     */
    @XmlElement(name = "XchgRate", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected BigDecimal xchgRate;
    /**
     * Unique identification to unambiguously identify the foreign exchange contract.
     * 
     */
    @XmlElement(name = "CtrctId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String ctrctId;
    /**
     * Date and time at which an exchange rate is quoted.
     * 
     */
    @XmlElement(name = "QtnDt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar qtnDt;

    /**
     * Currency from which an amount is to be converted in a currency conversion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrcCcy() {
        return srcCcy;
    }

    /**
     * Sets the value of the srcCcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSrcCcy()
     */
    public void setSrcCcy(String value) {
        this.srcCcy = value;
    }

    /**
     * Currency into which an amount is to be converted in a currency conversion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTrgtCcy() {
        return trgtCcy;
    }

    /**
     * Sets the value of the trgtCcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTrgtCcy()
     */
    public void setTrgtCcy(String value) {
        this.trgtCcy = value;
    }

    /**
     * Currency in which the rate of exchange is expressed in a currency exchange. In the example 1GBP = xxxCUR, the unit currency is GBP.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUnitCcy() {
        return unitCcy;
    }

    /**
     * Sets the value of the unitCcy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getUnitCcy()
     */
    public void setUnitCcy(String value) {
        this.unitCcy = value;
    }

    /**
     * Factor used to convert an amount from one currency into another. This reflects the price at which one currency was bought with another currency.
     * 
     *                         Usage: ExchangeRate expresses the ratio between UnitCurrency and QuotedCurrency (ExchangeRate = UnitCurrency/QuotedCurrency).
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getXchgRate() {
        return xchgRate;
    }

    /**
     * Sets the value of the xchgRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getXchgRate()
     */
    public void setXchgRate(BigDecimal value) {
        this.xchgRate = value;
    }

    /**
     * Unique identification to unambiguously identify the foreign exchange contract.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtrctId() {
        return ctrctId;
    }

    /**
     * Sets the value of the ctrctId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCtrctId()
     */
    public void setCtrctId(String value) {
        this.ctrctId = value;
    }

    /**
     * Date and time at which an exchange rate is quoted.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getQtnDt() {
        return qtnDt;
    }

    /**
     * Sets the value of the qtnDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getQtnDt()
     */
    public void setQtnDt(XMLGregorianCalendar value) {
        this.qtnDt = value;
    }

}
