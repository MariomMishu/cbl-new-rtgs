
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Set of characteristics shared by all individual
 *                 transactions included in the message.
 * 
 * <p>Java class for GroupHeader93__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="GroupHeader93__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_DateTime__3_"/>
 *         <element name="NbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}Max15NumericText_fixed"/>
 *         <element name="SttlmInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}SettlementInstruction7__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader93__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "msgId",
    "creDtTm",
    "nbOfTxs",
    "sttlmInf"
})
public class GroupHeader931 {

    /**
     * Point to point reference, as assigned by the
     *                         instructing party, and sent to the next party in the chain to unambiguously identify the
     *                         message.
     *                         Usage: The instructing party has to make sure that MessageIdentification is unique per
     *                         instructed party for a pre-agreed period.
     * 
     */
    @XmlElement(name = "MsgId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected String msgId;
    /**
     * Date and time at which the message was
     *                         created.
     * 
     */
    @XmlElement(name = "CreDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    /**
     * Number of individual transactions contained in
     *                         the message.
     * 
     */
    @XmlElement(name = "NbOfTxs", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected String nbOfTxs;
    /**
     * Specifies the details on how the settlement of
     *                         the transaction(s) between the instructing agent and the instructed agent is completed.
     * 
     */
    @XmlElement(name = "SttlmInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected SettlementInstruction71 sttlmInf;

    /**
     * Point to point reference, as assigned by the
     *                         instructing party, and sent to the next party in the chain to unambiguously identify the
     *                         message.
     *                         Usage: The instructing party has to make sure that MessageIdentification is unique per
     *                         instructed party for a pre-agreed period.
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
     * Date and time at which the message was
     *                         created.
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
     * Number of individual transactions contained in
     *                         the message.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNbOfTxs() {
        return nbOfTxs;
    }

    /**
     * Sets the value of the nbOfTxs property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNbOfTxs()
     */
    public void setNbOfTxs(String value) {
        this.nbOfTxs = value;
    }

    /**
     * Specifies the details on how the settlement of
     *                         the transaction(s) between the instructing agent and the instructed agent is completed.
     * 
     * @return
     *     possible object is
     *     {@link SettlementInstruction71 }
     *     
     */
    public SettlementInstruction71 getSttlmInf() {
        return sttlmInf;
    }

    /**
     * Sets the value of the sttlmInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementInstruction71 }
     *     
     * @see #getSttlmInf()
     */
    public void setSttlmInf(SettlementInstruction71 value) {
        this.sttlmInf = value;
    }

}
