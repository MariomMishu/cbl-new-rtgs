
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * Identifies the underlying transaction.
 * 
 * <p>Java class for TransactionReferences6__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TransactionReferences6__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="AcctSvcrRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="PmtInfId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="InstrId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="EndToEndId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="UETR" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}UUIDv4Identifier" minOccurs="0"/>
 *         <element name="TxId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="MndtId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="ChqNb" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="ClrSysRef" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="AcctOwnrTxId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="AcctSvcrTxId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="MktInfrstrctrTxId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="PrcgId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ProprietaryReference1__1" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionReferences6__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "msgId",
    "acctSvcrRef",
    "pmtInfId",
    "instrId",
    "endToEndId",
    "uetr",
    "txId",
    "mndtId",
    "chqNb",
    "clrSysRef",
    "acctOwnrTxId",
    "acctSvcrTxId",
    "mktInfrstrctrTxId",
    "prcgId",
    "prtry"
})
public class TransactionReferences61 {

    /**
     * Point to point reference, as assigned by the instructing party of the underlying message.
     * 
     */
    @XmlElement(name = "MsgId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String msgId;
    /**
     * Unique reference, as assigned by the account servicing institution, to unambiguously identify the instruction.
     * 
     */
    @XmlElement(name = "AcctSvcrRef", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String acctSvcrRef;
    /**
     * Unique identification, as assigned by a sending party, to unambiguously identify the payment information group within the message.
     * 
     */
    @XmlElement(name = "PmtInfId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String pmtInfId;
    /**
     * Unique identification, as assigned by an instructing party for an instructed party, to unambiguously identify the instruction.
     * 
     *                         Usage: The instruction identification is a point to point reference that can be used between the instructing party and the instructed party to refer to the individual instruction. It can be included in several messages related to the instruction.
     * 
     */
    @XmlElement(name = "InstrId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String instrId;
    /**
     * Unique identification, as assigned by the initiating party, to unambiguously identify the transaction. This identification is passed on, unchanged, throughout the entire end-to-end chain.
     * 
     *                         Usage: The end-to-end identification can be used for reconciliation or to link tasks relating to the transaction. It can be included in several messages related to the transaction.
     * 
     *                         Usage: In case there are technical limitations to pass on multiple references, the end-to-end identification must be passed on throughout the entire end-to-end chain.
     * 
     */
    @XmlElement(name = "EndToEndId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String endToEndId;
    /**
     * Universally unique identifier to provide an end-to-end reference of a payment transaction.
     * 
     */
    @XmlElement(name = "UETR", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String uetr;
    /**
     * Unique identification, as assigned by the first instructing agent, to unambiguously identify the transaction that is passed on, unchanged, throughout the entire interbank chain.
     *                         Usage: The transaction identification can be used for reconciliation, tracking or to link tasks relating to the transaction on the interbank level.
     *                         Usage: The instructing agent has to make sure that the transaction identification is unique for a pre-agreed period.
     * 
     */
    @XmlElement(name = "TxId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String txId;
    /**
     * Unique identification, as assigned by the creditor, to unambiguously identify the mandate.
     * 
     */
    @XmlElement(name = "MndtId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String mndtId;
    /**
     * Unique and unambiguous identifier for a cheque as assigned by the agent.
     * 
     */
    @XmlElement(name = "ChqNb", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String chqNb;
    /**
     * Unique reference, as assigned by a clearing system, to unambiguously identify the instruction.
     *                         Usage: In case there are technical limitations to pass on multiple references, the end-to-end identification must be passed on throughout the entire end-to-end chain.
     * 
     */
    @XmlElement(name = "ClrSysRef", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String clrSysRef;
    /**
     * Unambiguous identification of the securities transaction as known by the securities account owner (or the instructing party managing the account).
     * 
     */
    @XmlElement(name = "AcctOwnrTxId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String acctOwnrTxId;
    /**
     * Unambiguous identification of the securities transaction as known by the securities account servicer.
     * 
     */
    @XmlElement(name = "AcctSvcrTxId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String acctSvcrTxId;
    /**
     * Identification of a securities transaction assigned by a market infrastructure other than a central securities depository, for example, Target2-Securities.
     * 
     */
    @XmlElement(name = "MktInfrstrctrTxId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String mktInfrstrctrTxId;
    /**
     * Identification of the securities transaction assigned by the processor of the instruction other than the securities account owner, the securities account servicer and the market infrastructure.
     * 
     */
    @XmlElement(name = "PrcgId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String prcgId;
    /**
     * Proprietary reference related to the underlying transaction.
     * 
     */
    @XmlElement(name = "Prtry", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected List<ProprietaryReference11> prtry;

    /**
     * Point to point reference, as assigned by the instructing party of the underlying message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMsgId()
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Unique reference, as assigned by the account servicing institution, to unambiguously identify the instruction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctSvcrRef() {
        return acctSvcrRef;
    }

    /**
     * Sets the value of the acctSvcrRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAcctSvcrRef()
     */
    public void setAcctSvcrRef(String value) {
        this.acctSvcrRef = value;
    }

    /**
     * Unique identification, as assigned by a sending party, to unambiguously identify the payment information group within the message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPmtInfId() {
        return pmtInfId;
    }

    /**
     * Sets the value of the pmtInfId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPmtInfId()
     */
    public void setPmtInfId(String value) {
        this.pmtInfId = value;
    }

    /**
     * Unique identification, as assigned by an instructing party for an instructed party, to unambiguously identify the instruction.
     * 
     *                         Usage: The instruction identification is a point to point reference that can be used between the instructing party and the instructed party to refer to the individual instruction. It can be included in several messages related to the instruction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstrId() {
        return instrId;
    }

    /**
     * Sets the value of the instrId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getInstrId()
     */
    public void setInstrId(String value) {
        this.instrId = value;
    }

    /**
     * Unique identification, as assigned by the initiating party, to unambiguously identify the transaction. This identification is passed on, unchanged, throughout the entire end-to-end chain.
     * 
     *                         Usage: The end-to-end identification can be used for reconciliation or to link tasks relating to the transaction. It can be included in several messages related to the transaction.
     * 
     *                         Usage: In case there are technical limitations to pass on multiple references, the end-to-end identification must be passed on throughout the entire end-to-end chain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndToEndId() {
        return endToEndId;
    }

    /**
     * Sets the value of the endToEndId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getEndToEndId()
     */
    public void setEndToEndId(String value) {
        this.endToEndId = value;
    }

    /**
     * Universally unique identifier to provide an end-to-end reference of a payment transaction.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUETR() {
        return uetr;
    }

    /**
     * Sets the value of the uetr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getUETR()
     */
    public void setUETR(String value) {
        this.uetr = value;
    }

    /**
     * Unique identification, as assigned by the first instructing agent, to unambiguously identify the transaction that is passed on, unchanged, throughout the entire interbank chain.
     *                         Usage: The transaction identification can be used for reconciliation, tracking or to link tasks relating to the transaction on the interbank level.
     *                         Usage: The instructing agent has to make sure that the transaction identification is unique for a pre-agreed period.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxId() {
        return txId;
    }

    /**
     * Sets the value of the txId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTxId()
     */
    public void setTxId(String value) {
        this.txId = value;
    }

    /**
     * Unique identification, as assigned by the creditor, to unambiguously identify the mandate.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMndtId() {
        return mndtId;
    }

    /**
     * Sets the value of the mndtId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMndtId()
     */
    public void setMndtId(String value) {
        this.mndtId = value;
    }

    /**
     * Unique and unambiguous identifier for a cheque as assigned by the agent.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChqNb() {
        return chqNb;
    }

    /**
     * Sets the value of the chqNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getChqNb()
     */
    public void setChqNb(String value) {
        this.chqNb = value;
    }

    /**
     * Unique reference, as assigned by a clearing system, to unambiguously identify the instruction.
     *                         Usage: In case there are technical limitations to pass on multiple references, the end-to-end identification must be passed on throughout the entire end-to-end chain.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClrSysRef() {
        return clrSysRef;
    }

    /**
     * Sets the value of the clrSysRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getClrSysRef()
     */
    public void setClrSysRef(String value) {
        this.clrSysRef = value;
    }

    /**
     * Unambiguous identification of the securities transaction as known by the securities account owner (or the instructing party managing the account).
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctOwnrTxId() {
        return acctOwnrTxId;
    }

    /**
     * Sets the value of the acctOwnrTxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAcctOwnrTxId()
     */
    public void setAcctOwnrTxId(String value) {
        this.acctOwnrTxId = value;
    }

    /**
     * Unambiguous identification of the securities transaction as known by the securities account servicer.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAcctSvcrTxId() {
        return acctSvcrTxId;
    }

    /**
     * Sets the value of the acctSvcrTxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAcctSvcrTxId()
     */
    public void setAcctSvcrTxId(String value) {
        this.acctSvcrTxId = value;
    }

    /**
     * Identification of a securities transaction assigned by a market infrastructure other than a central securities depository, for example, Target2-Securities.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMktInfrstrctrTxId() {
        return mktInfrstrctrTxId;
    }

    /**
     * Sets the value of the mktInfrstrctrTxId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMktInfrstrctrTxId()
     */
    public void setMktInfrstrctrTxId(String value) {
        this.mktInfrstrctrTxId = value;
    }

    /**
     * Identification of the securities transaction assigned by the processor of the instruction other than the securities account owner, the securities account servicer and the market infrastructure.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrcgId() {
        return prcgId;
    }

    /**
     * Sets the value of the prcgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPrcgId()
     */
    public void setPrcgId(String value) {
        this.prcgId = value;
    }

    /**
     * Proprietary reference related to the underlying transaction.
     * 
     * Gets the value of the prtry property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prtry property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getPrtry().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ProprietaryReference11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the prtry property.
     */
    public List<ProprietaryReference11> getPrtry() {
        if (prtry == null) {
            prtry = new ArrayList<>();
        }
        return this.prtry;
    }

}
