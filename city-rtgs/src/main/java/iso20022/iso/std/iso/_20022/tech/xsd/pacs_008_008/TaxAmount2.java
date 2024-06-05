
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Set of elements used to provide information on the tax amount(s) of tax record.
 * 
 * <p>Java class for TaxAmount2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TaxAmount2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Rate" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PercentageRate" minOccurs="0"/>
 *         <element name="TaxblBaseAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="TtlAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="Dtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxRecordDetails2" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxAmount2", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "rate",
    "taxblBaseAmt",
    "ttlAmt",
    "dtls"
})
public class TaxAmount2 {

    /**
     * Rate used to calculate the tax.
     * 
     */
    @XmlElement(name = "Rate", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected BigDecimal rate;
    /**
     * Amount of money on which the tax is based.
     * 
     */
    @XmlElement(name = "TaxblBaseAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount taxblBaseAmt;
    /**
     * Total amount that is the result of the calculation of the tax for the record.
     * 
     */
    @XmlElement(name = "TtlAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount ttlAmt;
    /**
     * Set of elements used to provide details on the tax period and amount.
     * 
     */
    @XmlElement(name = "Dtls", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<TaxRecordDetails2> dtls;

    /**
     * Rate used to calculate the tax.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getRate()
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
    }

    /**
     * Amount of money on which the tax is based.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getTaxblBaseAmt() {
        return taxblBaseAmt;
    }

    /**
     * Sets the value of the taxblBaseAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getTaxblBaseAmt()
     */
    public void setTaxblBaseAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.taxblBaseAmt = value;
    }

    /**
     * Total amount that is the result of the calculation of the tax for the record.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getTtlAmt() {
        return ttlAmt;
    }

    /**
     * Sets the value of the ttlAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getTtlAmt()
     */
    public void setTtlAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.ttlAmt = value;
    }

    /**
     * Set of elements used to provide details on the tax period and amount.
     * 
     * Gets the value of the dtls property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dtls property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxRecordDetails2 }
     * </p>
     * 
     * 
     * @return
     *     The value of the dtls property.
     */
    public List<TaxRecordDetails2> getDtls() {
        if (dtls == null) {
            dtls = new ArrayList<>();
        }
        return this.dtls;
    }

}
