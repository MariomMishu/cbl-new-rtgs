
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides further details on the parties specific to the individual transaction.
 * 
 * <p>Java class for TransactionParties6__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TransactionParties6__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}Party40Choice__1" minOccurs="0"/>
 *         <element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CashAccount38__1" minOccurs="0"/>
 *         <element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}Party40Choice__2" minOccurs="0"/>
 *         <element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CashAccount38__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionParties6__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "dbtr",
    "dbtrAcct",
    "cdtr",
    "cdtrAcct"
})
public class TransactionParties61 {

    /**
     * Party that owes an amount of money to the (ultimate) creditor.
     * 
     */
    @XmlElement(name = "Dbtr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected Party40Choice1 dbtr;
    /**
     * Unambiguous identification of the account of the debtor.
     * 
     */
    @XmlElement(name = "DbtrAcct", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected CashAccount381 dbtrAcct;
    /**
     * Party to which an amount of money is due.
     * 
     */
    @XmlElement(name = "Cdtr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected Party40Choice2 cdtr;
    /**
     * Unambiguous identification of the account of the creditor to which a credit entry has been posted as a result of the payment transaction.
     * 
     */
    @XmlElement(name = "CdtrAcct", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected CashAccount381 cdtrAcct;

    /**
     * Party that owes an amount of money to the (ultimate) creditor.
     * 
     * @return
     *     possible object is
     *     {@link Party40Choice1 }
     *     
     */
    public Party40Choice1 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party40Choice1 }
     *     
     * @see #getDbtr()
     */
    public void setDbtr(Party40Choice1 value) {
        this.dbtr = value;
    }

    /**
     * Unambiguous identification of the account of the debtor.
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
     * Party to which an amount of money is due.
     * 
     * @return
     *     possible object is
     *     {@link Party40Choice2 }
     *     
     */
    public Party40Choice2 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link Party40Choice2 }
     *     
     * @see #getCdtr()
     */
    public void setCdtr(Party40Choice2 value) {
        this.cdtr = value;
    }

    /**
     * Unambiguous identification of the account of the creditor to which a credit entry has been posted as a result of the payment transaction.
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

}
