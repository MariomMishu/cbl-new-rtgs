
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * Provide further details specific to the individual
 *                 transaction(s) included in the message.
 * 
 * <p>Java class for CreditTransferTransaction36__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CreditTransferTransaction36__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="PmtId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}PaymentIdentification7__1"/>
 *         <element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}PaymentTypeInformation28__1" minOccurs="0"/>
 *         <element name="IntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}RestrictedFINActiveCurrencyAndAmount"/>
 *         <element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}ISODate"/>
 *         <element name="SttlmPrty" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}Priority3Code" minOccurs="0"/>
 *         <element name="SttlmTmIndctn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}SettlementDateTimeIndication1__1" minOccurs="0"/>
 *         <element name="SttlmTmReq" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}SettlementTimeRequest2__1" minOccurs="0"/>
 *         <element name="PrvsInstgAgt1" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="PrvsInstgAgt1Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="PrvsInstgAgt2" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__2" minOccurs="0"/>
 *         <element name="PrvsInstgAgt2Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="PrvsInstgAgt3" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__2" minOccurs="0"/>
 *         <element name="PrvsInstgAgt3Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="InstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__3"/>
 *         <element name="InstdAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__3"/>
 *         <element name="IntrmyAgt1" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="IntrmyAgt1Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="IntrmyAgt2" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__2" minOccurs="0"/>
 *         <element name="IntrmyAgt2Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="IntrmyAgt3" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__2" minOccurs="0"/>
 *         <element name="IntrmyAgt3Acct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__1"/>
 *         <element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="DbtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="CdtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}BranchAndFinancialInstitutionIdentification6__1"/>
 *         <element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="InstrForCdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}InstructionForCreditorAgent2__1" maxOccurs="2" minOccurs="0"/>
 *         <element name="InstrForNxtAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}InstructionForNextAgent1__1" maxOccurs="6" minOccurs="0"/>
 *         <element name="Purp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}Purpose2Choice__1" minOccurs="0"/>
 *         <element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}RemittanceInformation2__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CreditTransferTransaction36__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "pmtId",
    "pmtTpInf",
    "intrBkSttlmAmt",
    "intrBkSttlmDt",
    "sttlmPrty",
    "sttlmTmIndctn",
    "sttlmTmReq",
    "prvsInstgAgt1",
    "prvsInstgAgt1Acct",
    "prvsInstgAgt2",
    "prvsInstgAgt2Acct",
    "prvsInstgAgt3",
    "prvsInstgAgt3Acct",
    "instgAgt",
    "instdAgt",
    "intrmyAgt1",
    "intrmyAgt1Acct",
    "intrmyAgt2",
    "intrmyAgt2Acct",
    "intrmyAgt3",
    "intrmyAgt3Acct",
    "dbtr",
    "dbtrAcct",
    "dbtrAgt",
    "dbtrAgtAcct",
    "cdtrAgt",
    "cdtrAgtAcct",
    "cdtr",
    "cdtrAcct",
    "instrForCdtrAgt",
    "instrForNxtAgt",
    "purp",
    "rmtInf"
})
public class CreditTransferTransaction361 {

    /**
     * Set of elements used to reference a payment
     *                         instruction.
     * 
     */
    @XmlElement(name = "PmtId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected PaymentIdentification71 pmtId;
    /**
     * Set of elements used to further specify the type
     *                         of transaction.
     * 
     */
    @XmlElement(name = "PmtTpInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected PaymentTypeInformation281 pmtTpInf;
    /**
     * Amount of money moved between the instructing
     *                         agent and the instructed agent.
     * 
     */
    @XmlElement(name = "IntrBkSttlmAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected RestrictedFINActiveCurrencyAndAmount intrBkSttlmAmt;
    /**
     * Date on which the amount of money ceases to be
     *                         available to the agent that owes it and when the amount of money becomes available to the agent
     *                         to which it is due.
     * 
     */
    @XmlElement(name = "IntrBkSttlmDt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar intrBkSttlmDt;
    /**
     * Indicator of the urgency or order of importance
     *                         that the instructing party would like the instructed party to apply to the processing of the
     *                         settlement instruction.
     * 
     */
    @XmlElement(name = "SttlmPrty", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    @XmlSchemaType(name = "string")
    protected Priority3Code sttlmPrty;
    /**
     * Provides information on the occurred settlement
     *                         time(s) of the payment transaction.
     * 
     */
    @XmlElement(name = "SttlmTmIndctn", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected SettlementDateTimeIndication11 sttlmTmIndctn;
    /**
     * Provides information on the requested settlement
     *                         time(s) of the payment instruction.
     * 
     */
    @XmlElement(name = "SttlmTmReq", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected SettlementTimeRequest21 sttlmTmReq;
    /**
     * Agent immediately prior to the instructing
     *                         agent.
     * 
     */
    @XmlElement(name = "PrvsInstgAgt1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification61 prvsInstgAgt1;
    /**
     * Unambiguous identification of the account of the
     *                         previous instructing agent at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "PrvsInstgAgt1Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 prvsInstgAgt1Acct;
    /**
     * Agent immediately prior to the instructing
     *                         agent.
     * 
     */
    @XmlElement(name = "PrvsInstgAgt2", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification62 prvsInstgAgt2;
    /**
     * Unambiguous identification of the account of the
     *                         previous instructing agent at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "PrvsInstgAgt2Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 prvsInstgAgt2Acct;
    /**
     * Agent immediately prior to the instructing
     *                         agent.
     * 
     */
    @XmlElement(name = "PrvsInstgAgt3", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification62 prvsInstgAgt3;
    /**
     * Unambiguous identification of the account of the
     *                         previous instructing agent at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "PrvsInstgAgt3Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 prvsInstgAgt3Acct;
    /**
     * Agent that instructs the next party in the chain
     *                         to carry out the (set of) instruction(s).
     * 
     */
    @XmlElement(name = "InstgAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected BranchAndFinancialInstitutionIdentification63 instgAgt;
    /**
     * Agent that is instructed by the previous party
     *                         in the chain to carry out the (set of) instruction(s).
     * 
     */
    @XmlElement(name = "InstdAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected BranchAndFinancialInstitutionIdentification63 instdAgt;
    /**
     * Agent between the debtor's agent and the
     *                         creditor's agent.
     * 
     *                         Usage: If more than one intermediary agent is present, then IntermediaryAgent1 identifies the
     *                         agent between the DebtorAgent and the IntermediaryAgent2.
     * 
     */
    @XmlElement(name = "IntrmyAgt1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification61 intrmyAgt1;
    /**
     * Unambiguous identification of the account of the
     *                         intermediary agent 1 at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "IntrmyAgt1Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 intrmyAgt1Acct;
    /**
     * Agent between the debtor's agent and the
     *                         creditor's agent.
     * 
     *                         Usage: If more than two intermediary agents are present, then IntermediaryAgent2 identifies the
     *                         agent between the IntermediaryAgent1 and the IntermediaryAgent3.
     * 
     */
    @XmlElement(name = "IntrmyAgt2", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification62 intrmyAgt2;
    /**
     * Unambiguous identification of the account of the
     *                         intermediary agent 2 at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "IntrmyAgt2Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 intrmyAgt2Acct;
    /**
     * Agent between the debtor's agent and the
     *                         creditor's agent.Usage: If IntermediaryAgent3 is present, then it identifies the agent between
     *                         the IntermediaryAgent 2 and the CreditorAgent.
     * 
     */
    @XmlElement(name = "IntrmyAgt3", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification62 intrmyAgt3;
    /**
     * Unambiguous identification of the account of the
     *                         intermediary agent 3 at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "IntrmyAgt3Acct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 intrmyAgt3Acct;
    /**
     * Financial institution that owes an amount of
     *                         money to the (ultimate) financial institutional creditor.
     * 
     */
    @XmlElement(name = "Dbtr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected BranchAndFinancialInstitutionIdentification61 dbtr;
    /**
     * Unambiguous identification of the account of the
     *                         debtor to which a debit entry will be made as a result of the transaction.
     * 
     */
    @XmlElement(name = "DbtrAcct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 dbtrAcct;
    /**
     * Financial institution servicing an account for
     *                         the debtor.
     * 
     */
    @XmlElement(name = "DbtrAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification61 dbtrAgt;
    /**
     * Unambiguous identification of the account of the
     *                         debtor agent at its servicing agent in the payment chain.
     * 
     */
    @XmlElement(name = "DbtrAgtAcct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 dbtrAgtAcct;
    /**
     * Financial institution servicing an account for
     *                         the creditor.
     * 
     */
    @XmlElement(name = "CdtrAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected BranchAndFinancialInstitutionIdentification61 cdtrAgt;
    /**
     * Unambiguous identification of the account of the
     *                         creditor agent at its servicing agent to which a credit entry will be made as a result of the
     *                         payment transaction.
     * 
     */
    @XmlElement(name = "CdtrAgtAcct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 cdtrAgtAcct;
    /**
     * Financial institution that receives an amount of
     *                         money from the financial institutional debtor.
     * 
     */
    @XmlElement(name = "Cdtr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected BranchAndFinancialInstitutionIdentification61 cdtr;
    /**
     * Unambiguous identification of the account of the
     *                         creditor to which a credit entry will be posted as a result of the payment transaction.
     * 
     */
    @XmlElement(name = "CdtrAcct", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccount381 cdtrAcct;
    /**
     * Further information related to the processing of
     *                         the payment instruction, provided by the initiating party, and intended for the creditor agent.
     * 
     */
    @XmlElement(name = "InstrForCdtrAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected List<InstructionForCreditorAgent21> instrForCdtrAgt;
    /**
     * Further information related to the processing of
     *                         the payment instruction that may need to be acted upon by the next agent.
     * 
     *                         Usage: The next agent may not be the creditor agent.
     *                         The instruction can relate to a level of service, can be an instruction that has to be executed
     *                         by the agent, or can be information required by the next agent.
     * 
     */
    @XmlElement(name = "InstrForNxtAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected List<InstructionForNextAgent11> instrForNxtAgt;
    /**
     * Underlying reason for the payment transaction.
     *                         Usage: Purpose is used by the end-customers, that is initiating party, (ultimate) debtor,
     *                         (ultimate) creditor to provide information concerning the nature of the payment. Purpose is a
     *                         content element, which is not used for processing by any of the agents involved in the payment
     *                         chain.
     * 
     */
    @XmlElement(name = "Purp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected Purpose2Choice1 purp;
    /**
     * Information supplied to enable the matching of
     *                         an entry with the items that the transfer is intended to settle, such as commercial invoices in
     *                         an accounts' receivable system.
     * 
     */
    @XmlElement(name = "RmtInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected RemittanceInformation21 rmtInf;

    /**
     * Set of elements used to reference a payment
     *                         instruction.
     * 
     * @return
     *     possible object is
     *     {@link PaymentIdentification71 }
     *     
     */
    public PaymentIdentification71 getPmtId() {
        return pmtId;
    }

    /**
     * Sets the value of the pmtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentIdentification71 }
     *     
     * @see #getPmtId()
     */
    public void setPmtId(PaymentIdentification71 value) {
        this.pmtId = value;
    }

    /**
     * Set of elements used to further specify the type
     *                         of transaction.
     * 
     * @return
     *     possible object is
     *     {@link PaymentTypeInformation281 }
     *     
     */
    public PaymentTypeInformation281 getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * Sets the value of the pmtTpInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PaymentTypeInformation281 }
     *     
     * @see #getPmtTpInf()
     */
    public void setPmtTpInf(PaymentTypeInformation281 value) {
        this.pmtTpInf = value;
    }

    /**
     * Amount of money moved between the instructing
     *                         agent and the instructed agent.
     * 
     * @return
     *     possible object is
     *     {@link RestrictedFINActiveCurrencyAndAmount }
     *     
     */
    public RestrictedFINActiveCurrencyAndAmount getIntrBkSttlmAmt() {
        return intrBkSttlmAmt;
    }

    /**
     * Sets the value of the intrBkSttlmAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link RestrictedFINActiveCurrencyAndAmount }
     *     
     * @see #getIntrBkSttlmAmt()
     */
    public void setIntrBkSttlmAmt(RestrictedFINActiveCurrencyAndAmount value) {
        this.intrBkSttlmAmt = value;
    }

    /**
     * Date on which the amount of money ceases to be
     *                         available to the agent that owes it and when the amount of money becomes available to the agent
     *                         to which it is due.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getIntrBkSttlmDt() {
        return intrBkSttlmDt;
    }

    /**
     * Sets the value of the intrBkSttlmDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getIntrBkSttlmDt()
     */
    public void setIntrBkSttlmDt(XMLGregorianCalendar value) {
        this.intrBkSttlmDt = value;
    }

    /**
     * Indicator of the urgency or order of importance
     *                         that the instructing party would like the instructed party to apply to the processing of the
     *                         settlement instruction.
     * 
     * @return
     *     possible object is
     *     {@link Priority3Code }
     *     
     */
    public Priority3Code getSttlmPrty() {
        return sttlmPrty;
    }

    /**
     * Sets the value of the sttlmPrty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority3Code }
     *     
     * @see #getSttlmPrty()
     */
    public void setSttlmPrty(Priority3Code value) {
        this.sttlmPrty = value;
    }

    /**
     * Provides information on the occurred settlement
     *                         time(s) of the payment transaction.
     * 
     * @return
     *     possible object is
     *     {@link SettlementDateTimeIndication11 }
     *     
     */
    public SettlementDateTimeIndication11 getSttlmTmIndctn() {
        return sttlmTmIndctn;
    }

    /**
     * Sets the value of the sttlmTmIndctn property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementDateTimeIndication11 }
     *     
     * @see #getSttlmTmIndctn()
     */
    public void setSttlmTmIndctn(SettlementDateTimeIndication11 value) {
        this.sttlmTmIndctn = value;
    }

    /**
     * Provides information on the requested settlement
     *                         time(s) of the payment instruction.
     * 
     * @return
     *     possible object is
     *     {@link SettlementTimeRequest21 }
     *     
     */
    public SettlementTimeRequest21 getSttlmTmReq() {
        return sttlmTmReq;
    }

    /**
     * Sets the value of the sttlmTmReq property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementTimeRequest21 }
     *     
     * @see #getSttlmTmReq()
     */
    public void setSttlmTmReq(SettlementTimeRequest21 value) {
        this.sttlmTmReq = value;
    }

    /**
     * Agent immediately prior to the instructing
     *                         agent.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getPrvsInstgAgt1() {
        return prvsInstgAgt1;
    }

    /**
     * Sets the value of the prvsInstgAgt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getPrvsInstgAgt1()
     */
    public void setPrvsInstgAgt1(BranchAndFinancialInstitutionIdentification61 value) {
        this.prvsInstgAgt1 = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         previous instructing agent at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getPrvsInstgAgt1Acct() {
        return prvsInstgAgt1Acct;
    }

    /**
     * Sets the value of the prvsInstgAgt1Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getPrvsInstgAgt1Acct()
     */
    public void setPrvsInstgAgt1Acct(CashAccount381 value) {
        this.prvsInstgAgt1Acct = value;
    }

    /**
     * Agent immediately prior to the instructing
     *                         agent.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification62 getPrvsInstgAgt2() {
        return prvsInstgAgt2;
    }

    /**
     * Sets the value of the prvsInstgAgt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     * @see #getPrvsInstgAgt2()
     */
    public void setPrvsInstgAgt2(BranchAndFinancialInstitutionIdentification62 value) {
        this.prvsInstgAgt2 = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         previous instructing agent at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getPrvsInstgAgt2Acct() {
        return prvsInstgAgt2Acct;
    }

    /**
     * Sets the value of the prvsInstgAgt2Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getPrvsInstgAgt2Acct()
     */
    public void setPrvsInstgAgt2Acct(CashAccount381 value) {
        this.prvsInstgAgt2Acct = value;
    }

    /**
     * Agent immediately prior to the instructing
     *                         agent.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification62 getPrvsInstgAgt3() {
        return prvsInstgAgt3;
    }

    /**
     * Sets the value of the prvsInstgAgt3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     * @see #getPrvsInstgAgt3()
     */
    public void setPrvsInstgAgt3(BranchAndFinancialInstitutionIdentification62 value) {
        this.prvsInstgAgt3 = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         previous instructing agent at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getPrvsInstgAgt3Acct() {
        return prvsInstgAgt3Acct;
    }

    /**
     * Sets the value of the prvsInstgAgt3Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getPrvsInstgAgt3Acct()
     */
    public void setPrvsInstgAgt3Acct(CashAccount381 value) {
        this.prvsInstgAgt3Acct = value;
    }

    /**
     * Agent that instructs the next party in the chain
     *                         to carry out the (set of) instruction(s).
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification63 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification63 getInstgAgt() {
        return instgAgt;
    }

    /**
     * Sets the value of the instgAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification63 }
     *     
     * @see #getInstgAgt()
     */
    public void setInstgAgt(BranchAndFinancialInstitutionIdentification63 value) {
        this.instgAgt = value;
    }

    /**
     * Agent that is instructed by the previous party
     *                         in the chain to carry out the (set of) instruction(s).
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification63 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification63 getInstdAgt() {
        return instdAgt;
    }

    /**
     * Sets the value of the instdAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification63 }
     *     
     * @see #getInstdAgt()
     */
    public void setInstdAgt(BranchAndFinancialInstitutionIdentification63 value) {
        this.instdAgt = value;
    }

    /**
     * Agent between the debtor's agent and the
     *                         creditor's agent.
     * 
     *                         Usage: If more than one intermediary agent is present, then IntermediaryAgent1 identifies the
     *                         agent between the DebtorAgent and the IntermediaryAgent2.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getIntrmyAgt1() {
        return intrmyAgt1;
    }

    /**
     * Sets the value of the intrmyAgt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getIntrmyAgt1()
     */
    public void setIntrmyAgt1(BranchAndFinancialInstitutionIdentification61 value) {
        this.intrmyAgt1 = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         intermediary agent 1 at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getIntrmyAgt1Acct() {
        return intrmyAgt1Acct;
    }

    /**
     * Sets the value of the intrmyAgt1Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getIntrmyAgt1Acct()
     */
    public void setIntrmyAgt1Acct(CashAccount381 value) {
        this.intrmyAgt1Acct = value;
    }

    /**
     * Agent between the debtor's agent and the
     *                         creditor's agent.
     * 
     *                         Usage: If more than two intermediary agents are present, then IntermediaryAgent2 identifies the
     *                         agent between the IntermediaryAgent1 and the IntermediaryAgent3.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification62 getIntrmyAgt2() {
        return intrmyAgt2;
    }

    /**
     * Sets the value of the intrmyAgt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     * @see #getIntrmyAgt2()
     */
    public void setIntrmyAgt2(BranchAndFinancialInstitutionIdentification62 value) {
        this.intrmyAgt2 = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         intermediary agent 2 at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getIntrmyAgt2Acct() {
        return intrmyAgt2Acct;
    }

    /**
     * Sets the value of the intrmyAgt2Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getIntrmyAgt2Acct()
     */
    public void setIntrmyAgt2Acct(CashAccount381 value) {
        this.intrmyAgt2Acct = value;
    }

    /**
     * Agent between the debtor's agent and the
     *                         creditor's agent.Usage: If IntermediaryAgent3 is present, then it identifies the agent between
     *                         the IntermediaryAgent 2 and the CreditorAgent.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification62 getIntrmyAgt3() {
        return intrmyAgt3;
    }

    /**
     * Sets the value of the intrmyAgt3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification62 }
     *     
     * @see #getIntrmyAgt3()
     */
    public void setIntrmyAgt3(BranchAndFinancialInstitutionIdentification62 value) {
        this.intrmyAgt3 = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         intermediary agent 3 at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getIntrmyAgt3Acct() {
        return intrmyAgt3Acct;
    }

    /**
     * Sets the value of the intrmyAgt3Acct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getIntrmyAgt3Acct()
     */
    public void setIntrmyAgt3Acct(CashAccount381 value) {
        this.intrmyAgt3Acct = value;
    }

    /**
     * Financial institution that owes an amount of
     *                         money to the (ultimate) financial institutional creditor.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getDbtr()
     */
    public void setDbtr(BranchAndFinancialInstitutionIdentification61 value) {
        this.dbtr = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         debtor to which a debit entry will be made as a result of the transaction.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getDbtrAcct()
     */
    public void setDbtrAcct(CashAccount381 value) {
        this.dbtrAcct = value;
    }

    /**
     * Financial institution servicing an account for
     *                         the debtor.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Sets the value of the dbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getDbtrAgt()
     */
    public void setDbtrAgt(BranchAndFinancialInstitutionIdentification61 value) {
        this.dbtrAgt = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         debtor agent at its servicing agent in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getDbtrAgtAcct() {
        return dbtrAgtAcct;
    }

    /**
     * Sets the value of the dbtrAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getDbtrAgtAcct()
     */
    public void setDbtrAgtAcct(CashAccount381 value) {
        this.dbtrAgtAcct = value;
    }

    /**
     * Financial institution servicing an account for
     *                         the creditor.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Sets the value of the cdtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getCdtrAgt()
     */
    public void setCdtrAgt(BranchAndFinancialInstitutionIdentification61 value) {
        this.cdtrAgt = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         creditor agent at its servicing agent to which a credit entry will be made as a result of the
     *                         payment transaction.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getCdtrAgtAcct() {
        return cdtrAgtAcct;
    }

    /**
     * Sets the value of the cdtrAgtAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getCdtrAgtAcct()
     */
    public void setCdtrAgtAcct(CashAccount381 value) {
        this.cdtrAgtAcct = value;
    }

    /**
     * Financial institution that receives an amount of
     *                         money from the financial institutional debtor.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getCdtr()
     */
    public void setCdtr(BranchAndFinancialInstitutionIdentification61 value) {
        this.cdtr = value;
    }

    /**
     * Unambiguous identification of the account of the
     *                         creditor to which a credit entry will be posted as a result of the payment transaction.
     * 
     * @return
     *     possible object is
     *     {@link CashAccount381 }
     *     
     */
    public CashAccount381 getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccount381 }
     *     
     * @see #getCdtrAcct()
     */
    public void setCdtrAcct(CashAccount381 value) {
        this.cdtrAcct = value;
    }

    /**
     * Further information related to the processing of
     *                         the payment instruction, provided by the initiating party, and intended for the creditor agent.
     * 
     * Gets the value of the instrForCdtrAgt property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrForCdtrAgt property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getInstrForCdtrAgt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionForCreditorAgent21 }
     * </p>
     * 
     * 
     * @return
     *     The value of the instrForCdtrAgt property.
     */
    public List<InstructionForCreditorAgent21> getInstrForCdtrAgt() {
        if (instrForCdtrAgt == null) {
            instrForCdtrAgt = new ArrayList<>();
        }
        return this.instrForCdtrAgt;
    }

    /**
     * Further information related to the processing of
     *                         the payment instruction that may need to be acted upon by the next agent.
     * 
     *                         Usage: The next agent may not be the creditor agent.
     *                         The instruction can relate to a level of service, can be an instruction that has to be executed
     *                         by the agent, or can be information required by the next agent.
     * 
     * Gets the value of the instrForNxtAgt property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the instrForNxtAgt property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getInstrForNxtAgt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InstructionForNextAgent11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the instrForNxtAgt property.
     */
    public List<InstructionForNextAgent11> getInstrForNxtAgt() {
        if (instrForNxtAgt == null) {
            instrForNxtAgt = new ArrayList<>();
        }
        return this.instrForNxtAgt;
    }

    /**
     * Underlying reason for the payment transaction.
     *                         Usage: Purpose is used by the end-customers, that is initiating party, (ultimate) debtor,
     *                         (ultimate) creditor to provide information concerning the nature of the payment. Purpose is a
     *                         content element, which is not used for processing by any of the agents involved in the payment
     *                         chain.
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
     * Information supplied to enable the matching of
     *                         an entry with the items that the transfer is intended to settle, such as commercial invoices in
     *                         an accounts' receivable system.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceInformation21 }
     *     
     */
    public RemittanceInformation21 getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceInformation21 }
     *     
     * @see #getRmtInf()
     */
    public void setRmtInf(RemittanceInformation21 value) {
        this.rmtInf = value;
    }

}
