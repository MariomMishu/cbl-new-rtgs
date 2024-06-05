
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides the details to identify an account.
 * 
 * <p>Java class for CashAccount39__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CashAccount39__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}AccountIdentification4Choice__1"/>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}CashAccountType2Choice__1" minOccurs="0"/>
 *         <element name="Ccy" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ActiveOrHistoricCurrencyCode" minOccurs="0"/>
 *         <element name="Prxy" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}ProxyAccountIdentification1__1" minOccurs="0"/>
 *         <element name="Ownr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}PartyIdentification135__1" minOccurs="0"/>
 *         <element name="Svcr" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAccount39__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "id",
    "tp",
    "ccy",
    "prxy",
    "ownr",
    "svcr"
})
public class CashAccount391 {

    /**
     * Unique and unambiguous identification for the account between the account owner and the account servicer.
     * 
     */
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected AccountIdentification4Choice1 id;
    /**
     * Specifies the nature, or use of the account.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected CashAccountType2Choice1 tp;
    /**
     * Identification of the currency in which the account is held.
     * 
     */
    @XmlElement(name = "Ccy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String ccy;
    /**
     * Specifies an alternate assumed name for the identification of the account.
     * 
     */
    @XmlElement(name = "Prxy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected ProxyAccountIdentification11 prxy;
    /**
     * Party that legally owns the account.
     * 
     */
    @XmlElement(name = "Ownr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected PartyIdentification1351 ownr;
    /**
     * Party that manages the account on behalf of the account owner, that is manages the registration and booking of entries on the account, calculates balances on the account and provides information about the account.
     * 
     */
    @XmlElement(name = "Svcr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification61 svcr;

    /**
     * Unique and unambiguous identification for the account between the account owner and the account servicer.
     * 
     * @return
     *     possible object is
     *     {@link AccountIdentification4Choice1 }
     *     
     */
    public AccountIdentification4Choice1 getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link AccountIdentification4Choice1 }
     *     
     * @see #getId()
     */
    public void setId(AccountIdentification4Choice1 value) {
        this.id = value;
    }

    /**
     * Specifies the nature, or use of the account.
     * 
     * @return
     *     possible object is
     *     {@link CashAccountType2Choice1 }
     *     
     */
    public CashAccountType2Choice1 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link CashAccountType2Choice1 }
     *     
     * @see #getTp()
     */
    public void setTp(CashAccountType2Choice1 value) {
        this.tp = value;
    }

    /**
     * Identification of the currency in which the account is held.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCcy()
     */
    public void setCcy(String value) {
        this.ccy = value;
    }

    /**
     * Specifies an alternate assumed name for the identification of the account.
     * 
     * @return
     *     possible object is
     *     {@link ProxyAccountIdentification11 }
     *     
     */
    public ProxyAccountIdentification11 getPrxy() {
        return prxy;
    }

    /**
     * Sets the value of the prxy property.
     * 
     * @param value
     *     allowed object is
     *     {@link ProxyAccountIdentification11 }
     *     
     * @see #getPrxy()
     */
    public void setPrxy(ProxyAccountIdentification11 value) {
        this.prxy = value;
    }

    /**
     * Party that legally owns the account.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1351 }
     *     
     */
    public PartyIdentification1351 getOwnr() {
        return ownr;
    }

    /**
     * Sets the value of the ownr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1351 }
     *     
     * @see #getOwnr()
     */
    public void setOwnr(PartyIdentification1351 value) {
        this.ownr = value;
    }

    /**
     * Party that manages the account on behalf of the account owner, that is manages the registration and booking of entries on the account, calculates balances on the account and provides information about the account.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getSvcr() {
        return svcr;
    }

    /**
     * Sets the value of the svcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getSvcr()
     */
    public void setSvcr(BranchAndFinancialInstitutionIdentification61 value) {
        this.svcr = value;
    }

}
