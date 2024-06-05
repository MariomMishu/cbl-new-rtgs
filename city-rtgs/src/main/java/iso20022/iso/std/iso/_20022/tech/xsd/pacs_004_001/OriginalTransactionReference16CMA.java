//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Java class for OriginalTransactionReference16CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="OriginalTransactionReference16CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="IntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="PmtTpInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PaymentTypeInformation25"/&gt;
 *         &lt;element name="PmtMtd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PaymentMethod4Code" minOccurs="0"/&gt;
 *         &lt;element name="RmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}RemittanceInformation7Strd" minOccurs="0"/&gt;
 *         &lt;element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PartyIdentificationCMA1"/&gt;
 *         &lt;element name="DbtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}CashAccount24CMA" minOccurs="0"/&gt;
 *         &lt;element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PACS004BranchAndFinancialInstitutionIdentification5"/&gt;
 *         &lt;element name="DbtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}CashAccount24CMA"/&gt;
 *         &lt;element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PACS004BranchAndFinancialInstitutionIdentification5"/&gt;
 *         &lt;element name="CdtrAgtAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}CashAccount24CMA" minOccurs="0"/&gt;
 *         &lt;element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PartyIdentificationCMA1"/&gt;
 *         &lt;element name="CdtrAcct" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}CashAccount24CMA" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OriginalTransactionReference16CMA", propOrder = {
        "intrBkSttlmAmt",
        "intrBkSttlmDt",
        "pmtTpInf",
        "pmtMtd",
        "rmtInf",
        "dbtr",
        "dbtrAcct",
        "dbtrAgt",
        "dbtrAgtAcct",
        "cdtrAgt",
        "cdtrAgtAcct",
        "cdtr",
        "cdtrAcct"
})
public class OriginalTransactionReference16CMA {

    @XmlElement(name = "IntrBkSttlmAmt")
    protected ActiveOrHistoricCurrencyAndAmount intrBkSttlmAmt;
    @XmlElement(name = "IntrBkSttlmDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar intrBkSttlmDt;
    @XmlElement(name = "PmtTpInf", required = true)
    protected PaymentTypeInformation25 pmtTpInf;
    @XmlElement(name = "PmtMtd")
    @XmlSchemaType(name = "string")
    protected PaymentMethod4Code pmtMtd;
    @XmlElement(name = "RmtInf")
    protected RemittanceInformation7Strd rmtInf;
    @XmlElement(name = "Dbtr", required = true)
    protected PartyIdentificationCMA1 dbtr;
    @XmlElement(name = "DbtrAcct")
    protected CashAccount24CMA dbtrAcct;
    @XmlElement(name = "DbtrAgt", required = true)
    protected PACS004BranchAndFinancialInstitutionIdentification5 dbtrAgt;
    @XmlElement(name = "DbtrAgtAcct", required = true)
    protected CashAccount24CMA dbtrAgtAcct;
    @XmlElement(name = "CdtrAgt", required = true)
    protected PACS004BranchAndFinancialInstitutionIdentification5 cdtrAgt;
    @XmlElement(name = "CdtrAgtAcct")
    protected CashAccount24CMA cdtrAgtAcct;
    @XmlElement(name = "Cdtr", required = true)
    protected PartyIdentificationCMA1 cdtr;
    @XmlElement(name = "CdtrAcct")
    protected CashAccount24CMA cdtrAcct;

    /**
     * Gets the value of the intrBkSttlmAmt property.
     *
     * @return possible object is
     * {@link ActiveOrHistoricCurrencyAndAmount }
     */
    public ActiveOrHistoricCurrencyAndAmount getIntrBkSttlmAmt() {
        return intrBkSttlmAmt;
    }

    /**
     * Sets the value of the intrBkSttlmAmt property.
     *
     * @param value allowed object is
     *              {@link ActiveOrHistoricCurrencyAndAmount }
     */
    public void setIntrBkSttlmAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.intrBkSttlmAmt = value;
    }

    /**
     * Gets the value of the intrBkSttlmDt property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getIntrBkSttlmDt() {
        return intrBkSttlmDt;
    }

    /**
     * Sets the value of the intrBkSttlmDt property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setIntrBkSttlmDt(XMLGregorianCalendar value) {
        this.intrBkSttlmDt = value;
    }

    /**
     * Gets the value of the pmtTpInf property.
     *
     * @return possible object is
     * {@link PaymentTypeInformation25 }
     */
    public PaymentTypeInformation25 getPmtTpInf() {
        return pmtTpInf;
    }

    /**
     * Sets the value of the pmtTpInf property.
     *
     * @param value allowed object is
     *              {@link PaymentTypeInformation25 }
     */
    public void setPmtTpInf(PaymentTypeInformation25 value) {
        this.pmtTpInf = value;
    }

    /**
     * Gets the value of the pmtMtd property.
     *
     * @return possible object is
     * {@link PaymentMethod4Code }
     */
    public PaymentMethod4Code getPmtMtd() {
        return pmtMtd;
    }

    /**
     * Sets the value of the pmtMtd property.
     *
     * @param value allowed object is
     *              {@link PaymentMethod4Code }
     */
    public void setPmtMtd(PaymentMethod4Code value) {
        this.pmtMtd = value;
    }

    /**
     * Gets the value of the rmtInf property.
     *
     * @return possible object is
     * {@link RemittanceInformation7Strd }
     */
    public RemittanceInformation7Strd getRmtInf() {
        return rmtInf;
    }

    /**
     * Sets the value of the rmtInf property.
     *
     * @param value allowed object is
     *              {@link RemittanceInformation7Strd }
     */
    public void setRmtInf(RemittanceInformation7Strd value) {
        this.rmtInf = value;
    }

    /**
     * Gets the value of the dbtr property.
     *
     * @return possible object is
     * {@link PartyIdentificationCMA1 }
     */
    public PartyIdentificationCMA1 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     *
     * @param value allowed object is
     *              {@link PartyIdentificationCMA1 }
     */
    public void setDbtr(PartyIdentificationCMA1 value) {
        this.dbtr = value;
    }

    /**
     * Gets the value of the dbtrAcct property.
     *
     * @return possible object is
     * {@link CashAccount24CMA }
     */
    public CashAccount24CMA getDbtrAcct() {
        return dbtrAcct;
    }

    /**
     * Sets the value of the dbtrAcct property.
     *
     * @param value allowed object is
     *              {@link CashAccount24CMA }
     */
    public void setDbtrAcct(CashAccount24CMA value) {
        this.dbtrAcct = value;
    }

    /**
     * Gets the value of the dbtrAgt property.
     *
     * @return possible object is
     * {@link PACS004BranchAndFinancialInstitutionIdentification5 }
     */
    public PACS004BranchAndFinancialInstitutionIdentification5 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Sets the value of the dbtrAgt property.
     *
     * @param value allowed object is
     *              {@link PACS004BranchAndFinancialInstitutionIdentification5 }
     */
    public void setDbtrAgt(PACS004BranchAndFinancialInstitutionIdentification5 value) {
        this.dbtrAgt = value;
    }

    /**
     * Gets the value of the dbtrAgtAcct property.
     *
     * @return possible object is
     * {@link CashAccount24CMA }
     */
    public CashAccount24CMA getDbtrAgtAcct() {
        return dbtrAgtAcct;
    }

    /**
     * Sets the value of the dbtrAgtAcct property.
     *
     * @param value allowed object is
     *              {@link CashAccount24CMA }
     */
    public void setDbtrAgtAcct(CashAccount24CMA value) {
        this.dbtrAgtAcct = value;
    }

    /**
     * Gets the value of the cdtrAgt property.
     *
     * @return possible object is
     * {@link PACS004BranchAndFinancialInstitutionIdentification5 }
     */
    public PACS004BranchAndFinancialInstitutionIdentification5 getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Sets the value of the cdtrAgt property.
     *
     * @param value allowed object is
     *              {@link PACS004BranchAndFinancialInstitutionIdentification5 }
     */
    public void setCdtrAgt(PACS004BranchAndFinancialInstitutionIdentification5 value) {
        this.cdtrAgt = value;
    }

    /**
     * Gets the value of the cdtrAgtAcct property.
     *
     * @return possible object is
     * {@link CashAccount24CMA }
     */
    public CashAccount24CMA getCdtrAgtAcct() {
        return cdtrAgtAcct;
    }

    /**
     * Sets the value of the cdtrAgtAcct property.
     *
     * @param value allowed object is
     *              {@link CashAccount24CMA }
     */
    public void setCdtrAgtAcct(CashAccount24CMA value) {
        this.cdtrAgtAcct = value;
    }

    /**
     * Gets the value of the cdtr property.
     *
     * @return possible object is
     * {@link PartyIdentificationCMA1 }
     */
    public PartyIdentificationCMA1 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     *
     * @param value allowed object is
     *              {@link PartyIdentificationCMA1 }
     */
    public void setCdtr(PartyIdentificationCMA1 value) {
        this.cdtr = value;
    }

    /**
     * Gets the value of the cdtrAcct property.
     *
     * @return possible object is
     * {@link CashAccount24CMA }
     */
    public CashAccount24CMA getCdtrAcct() {
        return cdtrAcct;
    }

    /**
     * Sets the value of the cdtrAcct property.
     *
     * @param value allowed object is
     *              {@link CashAccount24CMA }
     */
    public void setCdtrAcct(CashAccount24CMA value) {
        this.cdtrAcct = value;
    }

}
