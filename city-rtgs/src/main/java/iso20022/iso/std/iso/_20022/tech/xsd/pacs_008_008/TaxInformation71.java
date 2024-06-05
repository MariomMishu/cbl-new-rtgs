
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * Details about tax paid, or to be paid, to the government in accordance with the law, including pre-defined parameters such as thresholds and type of account.
 * 
 * <p>Java class for TaxInformation7__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TaxInformation7__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Cdtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxParty1__1" minOccurs="0"/>
 *         <element name="Dbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxParty2__1" minOccurs="0"/>
 *         <element name="UltmtDbtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxParty2__1" minOccurs="0"/>
 *         <element name="AdmstnZone" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="RefNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax140Text_Extended" minOccurs="0"/>
 *         <element name="Mtd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="TtlTaxblBaseAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="TtlTaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ISODate" minOccurs="0"/>
 *         <element name="SeqNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Number" minOccurs="0"/>
 *         <element name="Rcrd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxRecord2__1" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxInformation7__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "cdtr",
    "dbtr",
    "ultmtDbtr",
    "admstnZone",
    "refNb",
    "mtd",
    "ttlTaxblBaseAmt",
    "ttlTaxAmt",
    "dt",
    "seqNb",
    "rcrd"
})
public class TaxInformation71 {

    /**
     * Party on the credit side of the transaction to which the tax applies.
     * 
     */
    @XmlElement(name = "Cdtr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected TaxParty11 cdtr;
    /**
     * Identifies the party on the debit side of the transaction to which the tax applies.
     * 
     */
    @XmlElement(name = "Dbtr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected TaxParty21 dbtr;
    /**
     * Ultimate party that owes an amount of money to the (ultimate) creditor, in this case, to the taxing authority.
     * 
     */
    @XmlElement(name = "UltmtDbtr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected TaxParty21 ultmtDbtr;
    /**
     * Territorial part of a country to which the tax payment is related.
     * 
     */
    @XmlElement(name = "AdmstnZone", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String admstnZone;
    /**
     * Tax reference information that is specific to a taxing agency.
     * 
     */
    @XmlElement(name = "RefNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String refNb;
    /**
     * Method used to indicate the underlying business or how the tax is paid.
     * 
     */
    @XmlElement(name = "Mtd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String mtd;
    /**
     * Total amount of money on which the tax is based.
     * 
     */
    @XmlElement(name = "TtlTaxblBaseAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount ttlTaxblBaseAmt;
    /**
     * Total amount of money as result of the calculation of the tax.
     * 
     */
    @XmlElement(name = "TtlTaxAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount ttlTaxAmt;
    /**
     * Date by which tax is due.
     * 
     */
    @XmlElement(name = "Dt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;
    /**
     * Sequential number of the tax report.
     * 
     */
    @XmlElement(name = "SeqNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected BigDecimal seqNb;
    /**
     * Record of tax details.
     * 
     */
    @XmlElement(name = "Rcrd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<TaxRecord21> rcrd;

    /**
     * Party on the credit side of the transaction to which the tax applies.
     * 
     * @return
     *     possible object is
     *     {@link TaxParty11 }
     *     
     */
    public TaxParty11 getCdtr() {
        return cdtr;
    }

    /**
     * Sets the value of the cdtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxParty11 }
     *     
     * @see #getCdtr()
     */
    public void setCdtr(TaxParty11 value) {
        this.cdtr = value;
    }

    /**
     * Identifies the party on the debit side of the transaction to which the tax applies.
     * 
     * @return
     *     possible object is
     *     {@link TaxParty21 }
     *     
     */
    public TaxParty21 getDbtr() {
        return dbtr;
    }

    /**
     * Sets the value of the dbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxParty21 }
     *     
     * @see #getDbtr()
     */
    public void setDbtr(TaxParty21 value) {
        this.dbtr = value;
    }

    /**
     * Ultimate party that owes an amount of money to the (ultimate) creditor, in this case, to the taxing authority.
     * 
     * @return
     *     possible object is
     *     {@link TaxParty21 }
     *     
     */
    public TaxParty21 getUltmtDbtr() {
        return ultmtDbtr;
    }

    /**
     * Sets the value of the ultmtDbtr property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxParty21 }
     *     
     * @see #getUltmtDbtr()
     */
    public void setUltmtDbtr(TaxParty21 value) {
        this.ultmtDbtr = value;
    }

    /**
     * Territorial part of a country to which the tax payment is related.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdmstnZone() {
        return admstnZone;
    }

    /**
     * Sets the value of the admstnZone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getAdmstnZone()
     */
    public void setAdmstnZone(String value) {
        this.admstnZone = value;
    }

    /**
     * Tax reference information that is specific to a taxing agency.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRefNb() {
        return refNb;
    }

    /**
     * Sets the value of the refNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRefNb()
     */
    public void setRefNb(String value) {
        this.refNb = value;
    }

    /**
     * Method used to indicate the underlying business or how the tax is paid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMtd() {
        return mtd;
    }

    /**
     * Sets the value of the mtd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMtd()
     */
    public void setMtd(String value) {
        this.mtd = value;
    }

    /**
     * Total amount of money on which the tax is based.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getTtlTaxblBaseAmt() {
        return ttlTaxblBaseAmt;
    }

    /**
     * Sets the value of the ttlTaxblBaseAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getTtlTaxblBaseAmt()
     */
    public void setTtlTaxblBaseAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.ttlTaxblBaseAmt = value;
    }

    /**
     * Total amount of money as result of the calculation of the tax.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getTtlTaxAmt() {
        return ttlTaxAmt;
    }

    /**
     * Sets the value of the ttlTaxAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getTtlTaxAmt()
     */
    public void setTtlTaxAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.ttlTaxAmt = value;
    }

    /**
     * Date by which tax is due.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDt() {
        return dt;
    }

    /**
     * Sets the value of the dt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getDt()
     */
    public void setDt(XMLGregorianCalendar value) {
        this.dt = value;
    }

    /**
     * Sequential number of the tax report.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSeqNb() {
        return seqNb;
    }

    /**
     * Sets the value of the seqNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     * @see #getSeqNb()
     */
    public void setSeqNb(BigDecimal value) {
        this.seqNb = value;
    }

    /**
     * Record of tax details.
     * 
     * Gets the value of the rcrd property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rcrd property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getRcrd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxRecord21 }
     * </p>
     * 
     * 
     * @return
     *     The value of the rcrd property.
     */
    public List<TaxRecord21> getRcrd() {
        if (rcrd == null) {
            rcrd = new ArrayList<>();
        }
        return this.rcrd;
    }

}
