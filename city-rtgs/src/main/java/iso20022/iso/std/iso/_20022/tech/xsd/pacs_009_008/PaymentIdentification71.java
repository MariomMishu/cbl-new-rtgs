
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides further means of referencing a payment
 *                 transaction.
 * 
 * <p>Java class for PaymentIdentification7__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PaymentIdentification7__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InstrId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="EndToEndId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="TxId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *         <element name="UETR" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}UUIDv4Identifier"/>
 *         <element name="ClrSysRef" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentIdentification7__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "instrId",
    "endToEndId",
    "txId",
    "uetr",
    "clrSysRef"
})
public class PaymentIdentification71 {

    /**
     * Unique identification, as assigned by an
     *                         instructing party for an instructed party, to unambiguously identify the instruction.
     * 
     *                         Usage: The instruction identification is a point to point reference that can be used between the
     *                         instructing party and the instructed party to refer to the individual instruction. It can be
     *                         included in several messages related to the instruction.
     * 
     */
    @XmlElement(name = "InstrId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected String instrId;
    /**
     * Unique identification, as assigned by the
     *                         initiating party, to unambiguously identify the transaction. This identification is passed on,
     *                         unchanged, throughout the entire end-to-end chain.
     * 
     *                         Usage: The end-to-end identification can be used for reconciliation or to link tasks relating to
     *                         the transaction. It can be included in several messages related to the transaction.
     * 
     *                         Usage: In case there are technical limitations to pass on multiple references, the end-to-end
     *                         identification must be passed on throughout the entire end-to-end chain.
     * 
     */
    @XmlElement(name = "EndToEndId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected String endToEndId;
    /**
     * Unique identification, as assigned by the first
     *                         instructing agent, to unambiguously identify the transaction that is passed on, unchanged,
     *                         throughout the entire interbank chain.
     *                         Usage: The transaction identification can be used for reconciliation, tracking or to link tasks
     *                         relating to the transaction on the interbank level.
     *                         Usage: The instructing agent has to make sure that the transaction identification is unique for
     *                         a pre-agreed period.
     * 
     */
    @XmlElement(name = "TxId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String txId;
    /**
     * Universally unique identifier to provide an
     *                         end-to-end reference of a payment transaction.
     * 
     */
    @XmlElement(name = "UETR", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected String uetr;
    /**
     * Unique reference, as assigned by a clearing
     *                         system, to unambiguously identify the instruction.
     * 
     */
    @XmlElement(name = "ClrSysRef", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String clrSysRef;

    /**
     * Unique identification, as assigned by an
     *                         instructing party for an instructed party, to unambiguously identify the instruction.
     * 
     *                         Usage: The instruction identification is a point to point reference that can be used between the
     *                         instructing party and the instructed party to refer to the individual instruction. It can be
     *                         included in several messages related to the instruction.
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
     * Unique identification, as assigned by the
     *                         initiating party, to unambiguously identify the transaction. This identification is passed on,
     *                         unchanged, throughout the entire end-to-end chain.
     * 
     *                         Usage: The end-to-end identification can be used for reconciliation or to link tasks relating to
     *                         the transaction. It can be included in several messages related to the transaction.
     * 
     *                         Usage: In case there are technical limitations to pass on multiple references, the end-to-end
     *                         identification must be passed on throughout the entire end-to-end chain.
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
     * Unique identification, as assigned by the first
     *                         instructing agent, to unambiguously identify the transaction that is passed on, unchanged,
     *                         throughout the entire interbank chain.
     *                         Usage: The transaction identification can be used for reconciliation, tracking or to link tasks
     *                         relating to the transaction on the interbank level.
     *                         Usage: The instructing agent has to make sure that the transaction identification is unique for
     *                         a pre-agreed period.
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
     * Universally unique identifier to provide an
     *                         end-to-end reference of a payment transaction.
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
     * Unique reference, as assigned by a clearing
     *                         system, to unambiguously identify the instruction.
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

}
