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


/**
 * <p>Java class for CAMT053CashAccount25 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CAMT053CashAccount25"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053AccountIdentification4Choice"/&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CashAccountType2Choice" minOccurs="0"/&gt;
 *         &lt;element name="Ccy" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ActiveOrHistoricCurrencyCode" minOccurs="0"/&gt;
 *         &lt;element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="Ownr" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053PartyIdentification43" minOccurs="0"/&gt;
 *         &lt;element name="Svcr" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CAMT053CashAccount25", propOrder = {
        "id",
        "tp",
        "ccy",
        "nm",
        "ownr",
        "svcr"
})
public class CAMT053CashAccount25 {

    @XmlElement(name = "Id", required = true)
    protected CAMT053AccountIdentification4Choice id;
    @XmlElement(name = "Tp")
    protected CashAccountType2Choice tp;
    @XmlElement(name = "Ccy")
    protected String ccy;
    @XmlElement(name = "Nm")
    protected String nm;
    @XmlElement(name = "Ownr")
    protected CAMT053PartyIdentification43 ownr;
    @XmlElement(name = "Svcr")
    protected CAMT053BranchAndFinancialInstitutionIdentification5 svcr;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link CAMT053AccountIdentification4Choice }
     */
    public CAMT053AccountIdentification4Choice getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link CAMT053AccountIdentification4Choice }
     */
    public void setId(CAMT053AccountIdentification4Choice value) {
        this.id = value;
    }

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link CashAccountType2Choice }
     */
    public CashAccountType2Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link CashAccountType2Choice }
     */
    public void setTp(CashAccountType2Choice value) {
        this.tp = value;
    }

    /**
     * Gets the value of the ccy property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCcy(String value) {
        this.ccy = value;
    }

    /**
     * Gets the value of the nm property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNm(String value) {
        this.nm = value;
    }

    /**
     * Gets the value of the ownr property.
     *
     * @return possible object is
     * {@link CAMT053PartyIdentification43 }
     */
    public CAMT053PartyIdentification43 getOwnr() {
        return ownr;
    }

    /**
     * Sets the value of the ownr property.
     *
     * @param value allowed object is
     *              {@link CAMT053PartyIdentification43 }
     */
    public void setOwnr(CAMT053PartyIdentification43 value) {
        this.ownr = value;
    }

    /**
     * Gets the value of the svcr property.
     *
     * @return possible object is
     * {@link CAMT053BranchAndFinancialInstitutionIdentification5 }
     */
    public CAMT053BranchAndFinancialInstitutionIdentification5 getSvcr() {
        return svcr;
    }

    /**
     * Sets the value of the svcr property.
     *
     * @param value allowed object is
     *              {@link CAMT053BranchAndFinancialInstitutionIdentification5 }
     */
    public void setSvcr(CAMT053BranchAndFinancialInstitutionIdentification5 value) {
        this.svcr = value;
    }

}
