
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Scope
 *                 The BankToCustomerDebitCreditNotification message is sent by the account servicer to an account owner or to a party authorised by the account owner to receive the message. It can be used to inform the account owner, or authorised party, of single or multiple debit and/or credit entries reported to the account.
 *                 Usage
 *                 The BankToCustomerDebitCreditNotification message can contain reports for more than one account. It provides information for cash management and/or reconciliation.
 *                 The BankToCustomerDebitCreditNotification message can be used to:
 *                 - report pending and booked items;
 *                 - notify one or more debit entries;
 *                 - notify one or more credit entries;
 *                 - notify a combination of debit and credit entries.
 *                 It can include underlying details of transactions that have been included in the entry.
 *                 It is possible that the receiver of the message is not the account owner, but a party entitled by the account owner to receive the account information (also known as recipient).
 *                 It does not contain balance information.
 * 
 * <p>Java class for BankToCustomerDebitCreditNotificationV08 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="BankToCustomerDebitCreditNotificationV08">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}GroupHeader81__1"/>
 *         <element name="Ntfctn" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AccountNotification17__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BankToCustomerDebitCreditNotificationV08", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "grpHdr",
    "ntfctn"
})
public class BankToCustomerDebitCreditNotificationV08 {

    /**
     * Common information for the message.
     * 
     */
    @XmlElement(name = "GrpHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected GroupHeader811 grpHdr;
    /**
     * Notifies debit and credit entries for the account.
     * 
     */
    @XmlElement(name = "Ntfctn", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected AccountNotification171 ntfctn;

    /**
     * Common information for the message.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader811 }
     *     
     */
    public GroupHeader811 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader811 }
     *     
     * @see #getGrpHdr()
     */
    public void setGrpHdr(GroupHeader811 value) {
        this.grpHdr = value;
    }

    /**
     * Notifies debit and credit entries for the account.
     * 
     * @return
     *     possible object is
     *     {@link AccountNotification171 }
     *     
     */
    public AccountNotification171 getNtfctn() {
        return ntfctn;
    }

    /**
     * Sets the value of the ntfctn property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountNotification171 }
     *     
     * @see #getNtfctn()
     */
    public void setNtfctn(AccountNotification171 value) {
        this.ntfctn = value;
    }

}
