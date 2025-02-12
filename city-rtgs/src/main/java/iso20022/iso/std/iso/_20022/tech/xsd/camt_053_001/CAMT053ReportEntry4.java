//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_053_001;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for CAMT053ReportEntry4 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CAMT053ReportEntry4"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="NtryRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max35Text"/&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CreditDebitCode"/&gt;
 *         &lt;element name="Sts" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}EntryStatus2Code"/&gt;
 *         &lt;element name="BookgDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="ValDt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053DateAndDateTimeChoice" minOccurs="0"/&gt;
 *         &lt;element name="BkTxCd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT054BankTransactionCodeStructure4"/&gt;
 *         &lt;element name="NtryDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053EntryDetails3" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CAMT053ReportEntry4", propOrder = {
        "ntryRef",
        "amt",
        "cdtDbtInd",
        "sts",
        "bookgDt",
        "valDt",
        "bkTxCd",
        "ntryDtls"
})
public class CAMT053ReportEntry4 {

    @XmlElement(name = "NtryRef", required = true)
    protected String ntryRef;
    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "CdtDbtInd", required = true)
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "Sts", required = true)
    @XmlSchemaType(name = "string")
    protected EntryStatus2Code sts;
    @XmlElement(name = "BookgDt")
    protected CAMT053DateAndDateTimeChoice bookgDt;
    @XmlElement(name = "ValDt")
    protected CAMT053DateAndDateTimeChoice valDt;
    @XmlElement(name = "BkTxCd", required = true)
    protected CAMT054BankTransactionCodeStructure4 bkTxCd;
    @XmlElement(name = "NtryDtls")
    protected CAMT053EntryDetails3 ntryDtls;

    /**
     * Gets the value of the ntryRef property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNtryRef() {
        return ntryRef;
    }

    /**
     * Sets the value of the ntryRef property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNtryRef(String value) {
        this.ntryRef = value;
    }

    /**
     * Gets the value of the amt property.
     *
     * @return possible object is
     * {@link ActiveOrHistoricCurrencyAndAmount }
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     *
     * @param value allowed object is
     *              {@link ActiveOrHistoricCurrencyAndAmount }
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
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
     * Gets the value of the sts property.
     *
     * @return possible object is
     * {@link EntryStatus2Code }
     */
    public EntryStatus2Code getSts() {
        return sts;
    }

    /**
     * Sets the value of the sts property.
     *
     * @param value allowed object is
     *              {@link EntryStatus2Code }
     */
    public void setSts(EntryStatus2Code value) {
        this.sts = value;
    }

    /**
     * Gets the value of the bookgDt property.
     *
     * @return possible object is
     * {@link CAMT053DateAndDateTimeChoice }
     */
    public CAMT053DateAndDateTimeChoice getBookgDt() {
        return bookgDt;
    }

    /**
     * Sets the value of the bookgDt property.
     *
     * @param value allowed object is
     *              {@link CAMT053DateAndDateTimeChoice }
     */
    public void setBookgDt(CAMT053DateAndDateTimeChoice value) {
        this.bookgDt = value;
    }

    /**
     * Gets the value of the valDt property.
     *
     * @return possible object is
     * {@link CAMT053DateAndDateTimeChoice }
     */
    public CAMT053DateAndDateTimeChoice getValDt() {
        return valDt;
    }

    /**
     * Sets the value of the valDt property.
     *
     * @param value allowed object is
     *              {@link CAMT053DateAndDateTimeChoice }
     */
    public void setValDt(CAMT053DateAndDateTimeChoice value) {
        this.valDt = value;
    }

    /**
     * Gets the value of the bkTxCd property.
     *
     * @return possible object is
     * {@link CAMT054BankTransactionCodeStructure4 }
     */
    public CAMT054BankTransactionCodeStructure4 getBkTxCd() {
        return bkTxCd;
    }

    /**
     * Sets the value of the bkTxCd property.
     *
     * @param value allowed object is
     *              {@link CAMT054BankTransactionCodeStructure4 }
     */
    public void setBkTxCd(CAMT054BankTransactionCodeStructure4 value) {
        this.bkTxCd = value;
    }

    /**
     * Gets the value of the ntryDtls property.
     *
     * @return possible object is
     * {@link CAMT053EntryDetails3 }
     */
    public CAMT053EntryDetails3 getNtryDtls() {
        return ntryDtls;
    }

    /**
     * Sets the value of the ntryDtls property.
     *
     * @param value allowed object is
     *              {@link CAMT053EntryDetails3 }
     */
    public void setNtryDtls(CAMT053EntryDetails3 value) {
        this.ntryDtls = value;
    }

}
