//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_053_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for CAMT053TransactionParties3 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CAMT053TransactionParties3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="InitgPty" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053CashAccount24" minOccurs="0"/&gt;
 *         &lt;element name="UltmtCdtr" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="TradgPty" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053ProprietaryParty3" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CAMT053TransactionParties3", propOrder = {
        "initgPty",
        "dbtr",
        "dbtrAcct",
        "ultmtDbtr",
        "cdtr",
        "cdtrAcct",
        "ultmtCdtr",
        "tradgPty",
        "prtry"
})
public class CAMT053TransactionParties3 {

    @XmlElement(name = "InitgPty")
    protected CAMT053PartyIdentification43 initgPty;
    @XmlElement(name = "Dbtr")
    protected CAMT053PartyIdentification43 dbtr;
    @XmlElement(name = "DbtrAcct")
    protected CAMT053CashAccount24 dbtrAcct;
    @XmlElement(name = "UltmtDbtr")
    protected CAMT053PartyIdentification43 ultmtDbtr;
    @XmlElement(name = "Cdtr")
    protected CAMT053PartyIdentification43 cdtr;
    @XmlElement(name = "CdtrAcct")
    protected CAMT053CashAccount24 cdtrAcct;
    @XmlElement(name = "UltmtCdtr")
    protected CAMT053PartyIdentification43 ultmtCdtr;
    @XmlElement(name = "TradgPty")
    protected CAMT053PartyIdentification43 tradgPty;
    @XmlElement(name = "Prtry")
    protected List<CAMT053ProprietaryParty3> prtry;

    /**
     * Gets the value of the initgPty property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getInitgPty() {
        return initgPty;
    }

    /**
     * Sets the value of the initgPty property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setInitgPty(CAMT053PartyIdentification43 value) {
        this.initgPty = value;
    }

    /**
     * Gets the value of the dbtr property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setDbtr(CAMT053PartyIdentification43 value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the dbtrAcct property.
     *
     * @return possible object is
     * {@link CAMT053CashAccount24 }
     */
    public CAMT053CashAccount24 getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     *
     * @param value allowed object is
     *              {@link CAMT053CashAccount24 }
     */
    public void setDbtrAcct(CAMT053CashAccount24 value) {
        this.dbtrAcct = value;
    }

    /**
     * Gets the value of the ultmtDbtr property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setUltmtDbtr(CAMT053PartyIdentification43 value) {
        this.ultmtDbtr = value;
    }

    /**
     * Gets the value of the cdtr property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setCdtr(CAMT053PartyIdentification43 value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the cdtrAcct property.
     *
     * @return possible object is
     * {@link CAMT053CashAccount24 }
     */
    public CAMT053CashAccount24 getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     *
     * @param value allowed object is
     *              {@link CAMT053CashAccount24 }
     */
    public void setCdtrAcct(CAMT053CashAccount24 value) {
        this.cdtrAcct = value;
    }

    /**
     * Gets the value of the ultmtCdtr property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getUltmtCdtr() {
        return ultmtCdtr;
    }

    /**
     * Sets the value of the ultmtCdtr property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setUltmtCdtr(CAMT053PartyIdentification43 value) {
        this.ultmtCdtr = value;
    }

    /**
     * Gets the value of the tradgPty property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getTradgPty() {
        return tradgPty;
    }

    /**
     * Sets the value of the tradgPty property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setTradgPty(CAMT053PartyIdentification43 value) {
        this.tradgPty = value;
    }

    /**
     * Gets the value of the prtry property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the prtry property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPrtry().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CAMT053ProprietaryParty3 }
     */
    public List<CAMT053ProprietaryParty3> getPrtry() {
        if (prtry == null) {
            prtry = new ArrayList<CAMT053ProprietaryParty3>();
        }
        return this.prtry;
    }

}
