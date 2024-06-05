
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.*;


/**
 * Provides further details on an entry in the report.
 * 
 * <p>Java class for ReportEntry10__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="ReportEntry10__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="NtryRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyAndAmount"/>
 *         <element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CreditDebitCode"/>
 *         <element name="Sts" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}EntryStatus1Choice__1"/>
 *         <element name="BookgDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}DateAndDateTime2Choice__1" minOccurs="0"/>
 *         <element name="ValDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}DateAndDateTime2Choice__2" minOccurs="0"/>
 *         <element name="BkTxCd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BankTransactionCodeStructure4__1"/>
 *         <element name="AmtDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AmountAndCurrencyExchange3__1" minOccurs="0"/>
 *         <element name="NtryDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}EntryDetails9__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReportEntry10__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "ntryRef",
    "amt",
    "cdtDbtInd",
    "sts",
    "bookgDt",
    "valDt",
    "bkTxCd",
    "amtDtls",
    "ntryDtls"
})
public class ReportEntry101 {

    /**
     * Unique reference for the entry.
     * 
     */
    @XmlElement(name = "NtryRef", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String ntryRef;
    /**
     * Amount of money in the cash entry.
     * 
     */
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    /**
     * Indicates whether the entry is a credit or a debit entry.
     * 
     */
    @XmlElement(name = "CdtDbtInd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    /**
     * Status of an entry on the books of the account servicer.
     * 
     */
    @XmlElement(name = "Sts", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected EntryStatus1Choice1 sts;
    /**
     * Date and time when an entry is posted to an account on the account servicer's books.
     * 
     *                         Usage: Booking date is the expected booking date, unless the status is booked, in which case it is the actual booking date.
     * 
     */
    @XmlElement(name = "BookgDt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected DateAndDateTime2Choice1 bookgDt;
    /**
     * Date and time at which assets become available to the account owner in case of a credit entry, or cease to be available to the account owner in case of a debit entry.
     *                         Usage: If entry status is pending and value date is present, then the value date refers to an expected/requested value date.
     *                         For entries subject to availability/float and for which availability information is provided, the value date must not be used. In this case the availability component identifies the number of availability days.
     * 
     */
    @XmlElement(name = "ValDt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected DateAndDateTime2Choice2 valDt;
    /**
     * Set of elements used to fully identify the type of underlying transaction resulting in an entry.
     * 
     */
    @XmlElement(name = "BkTxCd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected BankTransactionCodeStructure41 bkTxCd;
    /**
     * Provides information on the original amount.
     * 
     *                         Usage: This component (on entry level) should be used when a total original batch or aggregate amount has to be provided. If required, the individual original amounts can be included in the same component on transaction details level.
     * 
     */
    @XmlElement(name = "AmtDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected AmountAndCurrencyExchange31 amtDtls;
    /**
     * Provides details on the entry.
     * 
     */
    @XmlElement(name = "NtryDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected EntryDetails91 ntryDtls;

    /**
     * Unique reference for the entry.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNtryRef() {
        return ntryRef;
    }

    /**
     * Sets the value of the ntryRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNtryRef()
     */
    public void setNtryRef(String value) {
        this.ntryRef = value;
    }

    /**
     * Amount of money in the cash entry.
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
     * Indicates whether the entry is a credit or a debit entry.
     * 
     * @return
     *     possible object is
     *     {@link CreditDebitCode }
     *     
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditDebitCode }
     *     
     * @see #getCdtDbtInd()
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Status of an entry on the books of the account servicer.
     * 
     * @return
     *     possible object is
     *     {@link EntryStatus1Choice1 }
     *     
     */
    public EntryStatus1Choice1 getSts() {
        return sts;
    }

    /**
     * Sets the value of the sts property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntryStatus1Choice1 }
     *     
     * @see #getSts()
     */
    public void setSts(EntryStatus1Choice1 value) {
        this.sts = value;
    }

    /**
     * Date and time when an entry is posted to an account on the account servicer's books.
     * 
     *                         Usage: Booking date is the expected booking date, unless the status is booked, in which case it is the actual booking date.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTime2Choice1 }
     *     
     */
    public DateAndDateTime2Choice1 getBookgDt() {
        return bookgDt;
    }

    /**
     * Sets the value of the bookgDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTime2Choice1 }
     *     
     * @see #getBookgDt()
     */
    public void setBookgDt(DateAndDateTime2Choice1 value) {
        this.bookgDt = value;
    }

    /**
     * Date and time at which assets become available to the account owner in case of a credit entry, or cease to be available to the account owner in case of a debit entry.
     *                         Usage: If entry status is pending and value date is present, then the value date refers to an expected/requested value date.
     *                         For entries subject to availability/float and for which availability information is provided, the value date must not be used. In this case the availability component identifies the number of availability days.
     * 
     * @return
     *     possible object is
     *     {@link DateAndDateTime2Choice2 }
     *     
     */
    public DateAndDateTime2Choice2 getValDt() {
        return valDt;
    }

    /**
     * Sets the value of the valDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link DateAndDateTime2Choice2 }
     *     
     * @see #getValDt()
     */
    public void setValDt(DateAndDateTime2Choice2 value) {
        this.valDt = value;
    }

    /**
     * Set of elements used to fully identify the type of underlying transaction resulting in an entry.
     * 
     * @return
     *     possible object is
     *     {@link BankTransactionCodeStructure41 }
     *     
     */
    public BankTransactionCodeStructure41 getBkTxCd() {
        return bkTxCd;
    }

    /**
     * Sets the value of the bkTxCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link BankTransactionCodeStructure41 }
     *     
     * @see #getBkTxCd()
     */
    public void setBkTxCd(BankTransactionCodeStructure41 value) {
        this.bkTxCd = value;
    }

    /**
     * Provides information on the original amount.
     * 
     *                         Usage: This component (on entry level) should be used when a total original batch or aggregate amount has to be provided. If required, the individual original amounts can be included in the same component on transaction details level.
     * 
     * @return
     *     possible object is
     *     {@link AmountAndCurrencyExchange31 }
     *     
     */
    public AmountAndCurrencyExchange31 getAmtDtls() {
        return amtDtls;
    }

    /**
     * Sets the value of the amtDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link AmountAndCurrencyExchange31 }
     *     
     * @see #getAmtDtls()
     */
    public void setAmtDtls(AmountAndCurrencyExchange31 value) {
        this.amtDtls = value;
    }

    /**
     * Provides details on the entry.
     * 
     * @return
     *     possible object is
     *     {@link EntryDetails91 }
     *     
     */
    public EntryDetails91 getNtryDtls() {
        return ntryDtls;
    }

    /**
     * Sets the value of the ntryDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntryDetails91 }
     *     
     * @see #getNtryDtls()
     */
    public void setNtryDtls(EntryDetails91 value) {
        this.ntryDtls = value;
    }

}
