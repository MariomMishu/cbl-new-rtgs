
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Provides further details of the account notification.
 * 
 * <p>Java class for AccountNotification17__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="AccountNotification17__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}RestrictedFINXMax35Text"/>
 *         <element name="RptgSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}SequenceRange1Choice__1" minOccurs="0"/>
 *         <element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_DateTime__3_" minOccurs="0"/>
 *         <element name="CpyDplctInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CopyDuplicate1Code__1" minOccurs="0"/>
 *         <element name="RptgSrc" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ReportingSource1Choice__1" minOccurs="0"/>
 *         <element name="Acct" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CashAccount39__1"/>
 *         <element name="Ntry" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ReportEntry10__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountNotification17__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "id",
    "rptgSeq",
    "creDtTm",
    "cpyDplctInd",
    "rptgSrc",
    "acct",
    "ntry"
})
public class AccountNotification171 {

    /**
     * Unique identification, as assigned by the account servicer, to unambiguously identify the account notification.
     * 
     */
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String id;
    /**
     * Specifies the range of identification sequence numbers, as provided in the request.
     * 
     */
    @XmlElement(name = "RptgSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected SequenceRange1Choice1 rptgSeq;
    /**
     * Date and time at which the notification was created.
     * 
     */
    @XmlElement(name = "CreDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    /**
     * Indicates whether the document is a copy, a duplicate, or a duplicate of a copy.
     * 
     */
    @XmlElement(name = "CpyDplctInd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    @XmlSchemaType(name = "string")
    protected CopyDuplicate1Code1 cpyDplctInd;
    /**
     * Specifies the application used to generate the reporting.
     * 
     */
    @XmlElement(name = "RptgSrc", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected ReportingSource1Choice1 rptgSrc;
    /**
     * Unambiguous identification of the account to which credit and debit entries are made.
     * 
     */
    @XmlElement(name = "Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected CashAccount391 acct;
    /**
     * Specifies an entry in the debit credit notification.

     *                         Usage: At least one reference must be provided to identify the entry and its underlying transaction(s).

     *                         

     *                         Usage Rule: In case of a Payments R-transaction the creditor / debtor referenced of the original payment initiation messages is also used for reporting of the R-transaction. The original debtor/creditor in the reporting of R-Transactions is not inverted. 

     *                         Following elements all defined in the TransactionDetails in RelatedParties or RelatedAgents are impacted by this usage rule:

     *                         Creditor, UltimateCreditor, CreditorAccount, CreditorAgent, Debtor, UltimateDebtor, DebtorAccount and DebtorAgent.
     * 
     */
    @XmlElement(name = "Ntry", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected ReportEntry101 ntry;

    /**
     * Unique identification, as assigned by the account servicer, to unambiguously identify the account notification.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getId()
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Specifies the range of identification sequence numbers, as provided in the request.
     * 
     * @return
     *     possible object is
     *     {@link SequenceRange1Choice1 }
     *     
     */
    public SequenceRange1Choice1 getRptgSeq() {
        return rptgSeq;
    }

    /**
     * Sets the value of the rptgSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SequenceRange1Choice1 }
     *     
     * @see #getRptgSeq()
     */
    public void setRptgSeq(SequenceRange1Choice1 value) {
        this.rptgSeq = value;
    }

    /**
     * Date and time at which the notification was created.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getCreDtTm()
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

    /**
     * Indicates whether the document is a copy, a duplicate, or a duplicate of a copy.
     * 
     * @return
     *     possible object is
     *     {@link CopyDuplicate1Code1 }
     *     
     */
    public CopyDuplicate1Code1 getCpyDplctInd() {
        return cpyDplctInd;
    }

    /**
     * Sets the value of the cpyDplctInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link CopyDuplicate1Code1 }
     *     
     * @see #getCpyDplctInd()
     */
    public void setCpyDplctInd(CopyDuplicate1Code1 value) {
        this.cpyDplctInd = value;
    }

    /**
     * Specifies the application used to generate the reporting.
     * 
     * @return
     *     possible object is
     *     {@link ReportingSource1Choice1 }
     *     
     */
    public ReportingSource1Choice1 getRptgSrc() {
        return rptgSrc;
    }

    /**
     * Sets the value of the rptgSrc property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportingSource1Choice1 }
     *     
     * @see #getRptgSrc()
     */
    public void setRptgSrc(ReportingSource1Choice1 value) {
        this.rptgSrc = value;
    }

    /**
     * Unambiguous identification of the account to which credit and debit entries are made.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount391 }
     *     
     */
    public CashAccount391 getAcct() {
        return acct;
    }

    /**
     * Sets the value of the acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount391 }
     *     
     * @see #getAcct()
     */
    public void setAcct(CashAccount391 value) {
        this.acct = value;
    }

    /**
     * Specifies an entry in the debit credit notification.

     *                         Usage: At least one reference must be provided to identify the entry and its underlying transaction(s).

     *                         

     *                         Usage Rule: In case of a Payments R-transaction the creditor / debtor referenced of the original payment initiation messages is also used for reporting of the R-transaction. The original debtor/creditor in the reporting of R-Transactions is not inverted. 

     *                         Following elements all defined in the TransactionDetails in RelatedParties or RelatedAgents are impacted by this usage rule:

     *                         Creditor, UltimateCreditor, CreditorAccount, CreditorAgent, Debtor, UltimateDebtor, DebtorAccount and DebtorAgent.
     * 
     * @return
     *     possible object is
     *     {@link ReportEntry101 }
     *     
     */
    public ReportEntry101 getNtry() {
        return ntry;
    }

    /**
     * Sets the value of the ntry property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReportEntry101 }
     *     
     * @see #getNtry()
     */
    public void setNtry(ReportEntry101 value) {
        this.ntry = value;
    }

}
