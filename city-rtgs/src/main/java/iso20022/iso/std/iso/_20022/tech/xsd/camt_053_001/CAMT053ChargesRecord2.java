//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_053_001;

import javax.xml.bind.annotation.*;
import java.math.BigDecimal;


/**
 * <p>Java class for CAMT053ChargesRecord2 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="CAMT053ChargesRecord2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ActiveOrHistoricCurrencyAndAmount"/&gt;
 *         &lt;element name="CdtDbtInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CreditDebitCode" minOccurs="0"/&gt;
 *         &lt;element name="ChrgInclInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ChargeIncludedIndicator" minOccurs="0"/&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ChargeType3Choice" minOccurs="0"/&gt;
 *         &lt;element name="Rate" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}PercentageRate" minOccurs="0"/&gt;
 *         &lt;element name="Br" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}ChargeBearerType1Code" minOccurs="0"/&gt;
 *         &lt;element name="Agt" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}CAMT053BranchAndFinancialInstitutionIdentification5" minOccurs="0"/&gt;
 *         &lt;element name="Tax" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}TaxCharges2" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CAMT053ChargesRecord2", propOrder = {
        "amt",
        "cdtDbtInd",
        "chrgInclInd",
        "tp",
        "rate",
        "br",
        "agt",
        "tax"
})
public class CAMT053ChargesRecord2 {

    @XmlElement(name = "Amt", required = true)
    protected ActiveOrHistoricCurrencyAndAmount amt;
    @XmlElement(name = "CdtDbtInd")
    @XmlSchemaType(name = "string")
    protected CreditDebitCode cdtDbtInd;
    @XmlElement(name = "ChrgInclInd")
    protected Boolean chrgInclInd;
    @XmlElement(name = "Tp")
    protected ChargeType3Choice tp;
    @XmlElement(name = "Rate")
    protected BigDecimal rate;
    @XmlElement(name = "Br")
    @XmlSchemaType(name = "string")
    protected ChargeBearerType1Code br;
    @XmlElement(name = "Agt")
    protected CAMT053BranchAndFinancialInstitutionIdentification5 agt;
    @XmlElement(name = "Tax")
    protected TaxCharges2 tax;

    /**
     * Gets the value of the amt property.
     *
     * @return possible object is
     * {@link ActiveOrHistoricCurrencyAndAmount }
     */
    public ActiveOrHistoricCurrencyAndAmount getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     *
     * @param value allowed object is
     *              {@link ActiveOrHistoricCurrencyAndAmount }
     */
    public void setAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.amt = value;
    }

    /**
     * Gets the value of the cdtDbtInd property.
     *
     * @return possible object is
     * {@link CreditDebitCode }
     */
    public CreditDebitCode getCdtDbtInd() {
        return cdtDbtInd;
    }

    /**
     * Sets the value of the cdtDbtInd property.
     *
     * @param value allowed object is
     *              {@link CreditDebitCode }
     */
    public void setCdtDbtInd(CreditDebitCode value) {
        this.cdtDbtInd = value;
    }

    /**
     * Gets the value of the chrgInclInd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isChrgInclInd() {
        return chrgInclInd;
    }

    /**
     * Sets the value of the chrgInclInd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setChrgInclInd(Boolean value) {
        this.chrgInclInd = value;
    }

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link ChargeType3Choice }
     */
    public ChargeType3Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link ChargeType3Choice }
     */
    public void setTp(ChargeType3Choice value) {
        this.tp = value;
    }

    /**
     * Gets the value of the rate property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getRate() {
        return rate;
    }

    /**
     * Sets the value of the rate property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setRate(BigDecimal value) {
        this.rate = value;
    }

    /**
     * Gets the value of the br property.
     *
     * @return possible object is
     * {@link ChargeBearerType1Code }
     */
    public ChargeBearerType1Code getBr() {
        return br;
    }

    /**
     * Sets the value of the br property.
     *
     * @param value allowed object is
     *              {@link ChargeBearerType1Code }
     */
    public void setBr(ChargeBearerType1Code value) {
        this.br = value;
    }

    /**
     * Gets the value of the agt property.
     *
     * @return possible object is
     * {@link CAMT053BranchAndFinancialInstitutionIdentification5 }
     */
    public CAMT053BranchAndFinancialInstitutionIdentification5 getAgt() {
        return agt;
    }

    /**
     * Sets the value of the agt property.
     *
     * @param value allowed object is
     *              {@link CAMT053BranchAndFinancialInstitutionIdentification5 }
     */
    public void setAgt(CAMT053BranchAndFinancialInstitutionIdentification5 value) {
        this.agt = value;
    }

    /**
     * Gets the value of the tax property.
     *
     * @return possible object is
     * {@link TaxCharges2 }
     */
    public TaxCharges2 getTax() {
        return tax;
    }

    /**
     * Sets the value of the tax property.
     *
     * @param value allowed object is
     *              {@link TaxCharges2 }
     */
    public void setTax(TaxCharges2 value) {
        this.tax = value;
    }

}
