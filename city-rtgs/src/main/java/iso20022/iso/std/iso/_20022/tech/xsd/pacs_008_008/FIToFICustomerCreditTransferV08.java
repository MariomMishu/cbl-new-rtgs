
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Scope

 *                 The FinancialInstitutionToFinancialInstitutionCustomerCreditTransfer message is sent by the debtor agent to the creditor agent, directly or through other agents and/or a payment clearing and settlement system. It is used to move funds from a debtor account to a creditor.

 *                 Usage

 *                 The FIToFICustomerCreditTransfer message is exchanged between agents and can contain one or more customer credit transfer instructions.

 *                 The FIToFICustomerCreditTransfer message does not allow for grouping: a CreditTransferTransactionInformation block must be present for each credit transfer transaction.

 *                 The FIToFICustomerCreditTransfer message can be used in different ways:

 *                 - If the instructing agent and the instructed agent wish to use their direct account relationship in the currency of the transfer then the message contains both the funds for the customer transfer(s) as well as the payment details;

 *                 - If the instructing agent and the instructed agent have no direct account relationship in the currency of the transfer, or do not wish to use their account relationship, then other (reimbursement) agents will be involved to cover for the customer transfer(s). The FIToFICustomerCreditTransfer contains only the payment details and the instructing agent must cover the customer transfer by sending a FinancialInstitutionCreditTransfer to a reimbursement agent. This payment method is called the Cover method;

 *                 - If more than two financial institutions are involved in the payment chain and if the FIToFICustomerCreditTransfer is sent from one financial institution to the next financial institution in the payment chain, then the payment method is called the Serial method.

 *                 The FIToFICustomerCreditTransfer message can be used in domestic and cross-border scenarios.
 * 
 * <p>Java class for FIToFICustomerCreditTransferV08 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="FIToFICustomerCreditTransferV08">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}GroupHeader93__1"/>
 *         <element name="CdtTrfTxInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CreditTransferTransaction39__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FIToFICustomerCreditTransferV08", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "grpHdr",
    "cdtTrfTxInf"
})
public class FIToFICustomerCreditTransferV08 {

    /**
     * Set of characteristics shared by all individual transactions included in the message.
     * 
     */
    @XmlElement(name = "GrpHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected GroupHeader931 grpHdr;
    /**
     * Set of elements providing information specific to the individual credit transfer(s).
     * 
     */
    @XmlElement(name = "CdtTrfTxInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected CreditTransferTransaction391 cdtTrfTxInf;

    /**
     * Set of characteristics shared by all individual transactions included in the message.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader931 }
     *     
     */
    public GroupHeader931 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader931 }
     *     
     * @see #getGrpHdr()
     */
    public void setGrpHdr(GroupHeader931 value) {
        this.grpHdr = value;
    }

    /**
     * Set of elements providing information specific to the individual credit transfer(s).
     * 
     * @return
     *     possible object is
     *     {@link CreditTransferTransaction391 }
     *     
     */
    public CreditTransferTransaction391 getCdtTrfTxInf() {
        return cdtTrfTxInf;
    }

    /**
     * Sets the value of the cdtTrfTxInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditTransferTransaction391 }
     *     
     * @see #getCdtTrfTxInf()
     */
    public void setCdtTrfTxInf(CreditTransferTransaction391 value) {
        this.cdtTrfTxInf = value;
    }

}
