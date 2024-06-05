
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Scope

 *                 The FinancialInstitutionCreditTransfer message is sent by a debtor financial institution to a creditor
 *                 financial institution, directly or through other agents and/or a payment clearing and settlement system.

 *                 It is used to move funds from a debtor account to a creditor, where both debtor and creditor are
 *                 financial institutions.

 *                 Usage

 *                 The FinancialInstitutionCreditTransfer message is exchanged between agents and can contain one or more
 *                 credit transfer instructions where debtor and creditor are both financial institutions.

 *                 The FinancialInstitutionCreditTransfer message does not allow for grouping: a
 *                 CreditTransferTransactionInformation block must be present for each credit transfer transaction.

 *                 The FinancialInstitutionCreditTransfer message can be used in domestic and cross-border scenarios.
 * 
 * <p>Java class for FinancialInstitutionCreditTransferV08 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="FinancialInstitutionCreditTransferV08">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}GroupHeader93__1"/>
 *         <element name="CdtTrfTxInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CreditTransferTransaction36__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FinancialInstitutionCreditTransferV08", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "grpHdr",
    "cdtTrfTxInf"
})
public class FinancialInstitutionCreditTransferV08 {

    /**
     * Set of characteristics shared by all individual
     *                         transactions included in the message.
     * 
     */
    @XmlElement(name = "GrpHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected GroupHeader931 grpHdr;
    /**
     * Set of elements providing information specific
     *                         to the individual credit transfer(s).
     * 
     */
    @XmlElement(name = "CdtTrfTxInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected CreditTransferTransaction361 cdtTrfTxInf;

    /**
     * Set of characteristics shared by all individual
     *                         transactions included in the message.
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
     * Set of elements providing information specific
     *                         to the individual credit transfer(s).
     * 
     * @return
     *     possible object is
     *     {@link CreditTransferTransaction361 }
     *     
     */
    public CreditTransferTransaction361 getCdtTrfTxInf() {
        return cdtTrfTxInf;
    }

    /**
     * Sets the value of the cdtTrfTxInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditTransferTransaction361 }
     *     
     * @see #getCdtTrfTxInf()
     */
    public void setCdtTrfTxInf(CreditTransferTransaction361 value) {
        this.cdtTrfTxInf = value;
    }

}
