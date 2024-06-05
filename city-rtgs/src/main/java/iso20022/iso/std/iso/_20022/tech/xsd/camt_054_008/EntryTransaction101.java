
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.*;


/**
 * Identifies the underlying transaction.
 * 
 * <p>Java class for EntryTransaction10__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="EntryTransaction10__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Refs" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}TransactionReferences6__1" minOccurs="0"/>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CreditDebitCode" minOccurs="0"/>
 *         <element name="BkTxCd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BankTransactionCodeStructure4__1" minOccurs="0"/>
 *         <element name="RltdPties" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}TransactionParties6__1" minOccurs="0"/>
 *         <element name="RltdAgts" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}TransactionAgents5__1" minOccurs="0"/>
 *         <element name="LclInstrm" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}LocalInstrument2Choice__1" minOccurs="0"/>
 *         <element name="Purp" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}Purpose2Choice__1" minOccurs="0"/>
 *         <element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}RemittanceInformation16__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntryTransaction10__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "refs",
    "amt",
    "cdtDbtInd",
    "bkTxCd",
    "rltdPties",
    "rltdAgts",
    "lclInstrm",
    "purp",
    "rmtInf"
})
public class EntryTransaction101 {

    /**
     * Provides the identification of the underlying transaction.
     * 
     */
    @XmlElement(name = "Refs", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected TransactionReferences61 refs;
    /**
     * Amount of money in the cash transaction.
     * 
     */
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected ActiveOrHistoricCurrencyAndAmount amt;
    /**
     * Indicates whether the transaction is a credit or a debit transaction.
     * 
     */
    @XmlElement(name = "CdtDbtInd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    /**
     * Set of elements used to fully identify the type of underlying transaction resulting in an entry.
     * 
     */
    @XmlElement(name = "BkTxCd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BankTransactionCodeStructure41 bkTxCd;
    /**
     * Set of elements used to identify the parties related to the underlying transaction.
     * 
     */
    @XmlElement(name = "RltdPties", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected TransactionParties61 rltdPties;
    /**
     * Set of elements used to identify the agents related to the underlying transaction.
     * 
     */
    @XmlElement(name = "RltdAgts", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected TransactionAgents51 rltdAgts;
    /**
     * User community specific instrument.
     *                         Usage: This element is used to specify a local instrument, local clearing option and/or further qualify the service or service level.
     * 
     */
    @XmlElement(name = "LclInstrm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected LocalInstrument2Choice1 lclInstrm;
    /**
     * Underlying reason for the payment transaction.
     *                         Usage: Purpose is used by the end-customers, that is initiating party, (ultimate) debtor, (ultimate) creditor to provide information concerning the nature of the payment. Purpose is a content element, which is not used for processing by any of the agents involved in the payment chain.
     * 
     */
    @XmlElement(name = "Purp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected Purpose2Choice1 purp;
    /**
     * Structured information that enables the matching, that is reconciliation, of a payment with the items that the payment is intended to settle, such as commercial invoices in an account receivable system.
     * 
     */
    @XmlElement(name = "RmtInf", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected RemittanceInformation161 rmtInf;

    /**
     * Provides the identification of the underlying transaction.
     * 
     * @return
     *     possible object is
     *     {@link TransactionReferences61 }
     *     
     */
    public TransactionReferences61 getRefs() {
        return refs;
    }

    /**
     * Sets the value of the refs property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionReferences61 }
     *     
     * @see #getRefs()
     */
    public void setRefs(TransactionReferences61 value) {
        this.refs = value;
    }

    /**
     * Amount of money in the cash transaction.
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
     * Indicates whether the transaction is a credit or a debit transaction.
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
     * Set of elements used to identify the parties related to the underlying transaction.
     * 
     * @return
     *     possible object is
     *     {@link TransactionParties61 }
     *     
     */
    public TransactionParties61 getRltdPties() {
        return rltdPties;
    }

    /**
     * Sets the value of the rltdPties property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionParties61 }
     *     
     * @see #getRltdPties()
     */
    public void setRltdPties(TransactionParties61 value) {
        this.rltdPties = value;
    }

    /**
     * Set of elements used to identify the agents related to the underlying transaction.
     * 
     * @return
     *     possible object is
     *     {@link TransactionAgents51 }
     *     
     */
    public TransactionAgents51 getRltdAgts() {
        return rltdAgts;
    }

    /**
     * Sets the value of the rltdAgts property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransactionAgents51 }
     *     
     * @see #getRltdAgts()
     */
    public void setRltdAgts(TransactionAgents51 value) {
        this.rltdAgts = value;
    }

    /**
     * User community specific instrument.
     *                         Usage: This element is used to specify a local instrument, local clearing option and/or further qualify the service or service level.
     * 
     * @return
     *     possible object is
     *     {@link LocalInstrument2Choice1 }
     *     
     */
    public LocalInstrument2Choice1 getLclInstrm() {
        return lclInstrm;
    }

    /**
     * Sets the value of the lclInstrm property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalInstrument2Choice1 }
     *     
     * @see #getLclInstrm()
     */
    public void setLclInstrm(LocalInstrument2Choice1 value) {
        this.lclInstrm = value;
    }

    /**
     * Underlying reason for the payment transaction.
     *                         Usage: Purpose is used by the end-customers, that is initiating party, (ultimate) debtor, (ultimate) creditor to provide information concerning the nature of the payment. Purpose is a content element, which is not used for processing by any of the agents involved in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link Purpose2Choice1 }
     *     
     */
    public Purpose2Choice1 getPurp() {
        return purp;
    }

    /**
     * Sets the value of the purp property.
     * 
     * @param value
     *     allowed object is
     *     {@link Purpose2Choice1 }
     *     
     * @see #getPurp()
     */
    public void setPurp(Purpose2Choice1 value) {
        this.purp = value;
    }

    /**
     * Structured information that enables the matching, that is reconciliation, of a payment with the items that the payment is intended to settle, such as commercial invoices in an account receivable system.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceInformation161 }
     *     
     */
    public RemittanceInformation161 getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceInformation161 }
     *     
     * @see #getRmtInf()
     */
    public void setRmtInf(RemittanceInformation161 value) {
        this.rmtInf = value;
    }

}
