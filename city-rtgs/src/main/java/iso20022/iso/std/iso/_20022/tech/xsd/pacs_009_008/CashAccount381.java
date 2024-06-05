
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides the details to identify an account.
 * 
 * <p>Java class for CashAccount38__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="CashAccount38__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}AccountIdentification4Choice__1"/>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}CashAccountType2Choice__1" minOccurs="0"/>
 *         <element name="Ccy" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}ActiveOrHistoricCurrencyCode" minOccurs="0"/>
 *         <element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_RestrictedFINXMax70Text" minOccurs="0"/>
 *         <element name="Prxy" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}ProxyAccountIdentification1__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CashAccount38__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "id",
    "tp",
    "ccy",
    "nm",
    "prxy"
})
public class CashAccount381 {

    /**
     * Unique and unambiguous identification for the
     *                         account between the account owner and the account servicer.
     * 
     */
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected AccountIdentification4Choice1 id;
    /**
     * Specifies the nature, or use of the account.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected CashAccountType2Choice1 tp;
    /**
     * Identification of the currency in which the
     *                         account is held.
     * 
     *                         Usage: Currency should only be used in case one and the same account number covers several
     *                         currencies
     *                         and the initiating party needs to identify which currency needs to be used for settlement on the
     *                         account.
     * 
     */
    @XmlElement(name = "Ccy", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String ccy;
    /**
     * Name of the account, as assigned by the account
     *                         servicing institution, in agreement with the account owner in order to provide an additional
     *                         means of identification of the account.
     * 
     *                         Usage: The account name is different from the account owner name. The account name is used in
     *                         certain user communities to provide a means of identifying the account, in addition to the
     *                         account owner's identity and the account number.
     * 
     */
    @XmlElement(name = "Nm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected String nm;
    /**
     * Specifies an alternate assumed name for the
     *                         identification of the account.
     * 
     */
    @XmlElement(name = "Prxy", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    protected ProxyAccountIdentification11 prxy;

    /**
     * Unique and unambiguous identification for the
     *                         account between the account owner and the account servicer.
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
     * Identification of the currency in which the
     *                         account is held.
     * 
     *                         Usage: Currency should only be used in case one and the same account number covers several
     *                         currencies
     *                         and the initiating party needs to identify which currency needs to be used for settlement on the
     *                         account.
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
     * Name of the account, as assigned by the account
     *                         servicing institution, in agreement with the account owner in order to provide an additional
     *                         means of identification of the account.
     * 
     *                         Usage: The account name is different from the account owner name. The account name is used in
     *                         certain user communities to provide a means of identifying the account, in addition to the
     *                         account owner's identity and the account number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNm()
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Specifies an alternate assumed name for the
     *                         identification of the account.
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

}
