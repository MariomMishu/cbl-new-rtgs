
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxRecord3 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxRecord3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Ctgy" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CtgyDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="DbtrSts" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="CertId" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="FrmsCd" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Prd" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}TaxPeriod3" minOccurs="0"/&gt;
 *         &lt;element name="TaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}TaxAmount3" minOccurs="0"/&gt;
 *         &lt;element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max140Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxRecord3", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", propOrder = {
    "tp",
    "ctgy",
    "ctgyDtls",
    "dbtrSts",
    "certId",
    "frmsCd",
    "prd",
    "taxAmt",
    "addtlInf"
})
public class TaxRecord3 {

    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String tp;
    @XmlElement(name = "Ctgy", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String ctgy;
    @XmlElement(name = "CtgyDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String ctgyDtls;
    @XmlElement(name = "DbtrSts", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String dbtrSts;
    @XmlElement(name = "CertId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String certId;
    @XmlElement(name = "FrmsCd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String frmsCd;
    @XmlElement(name = "Prd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected TaxPeriod3 prd;
    @XmlElement(name = "TaxAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected TaxAmount3 taxAmt;
    @XmlElement(name = "AddtlInf", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String addtlInf;

    /**
     * Gets the value of the tp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Gets the value of the ctgy property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtgy() {
        return ctgy;
    }

    /**
     * Sets the value of the ctgy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtgy(String value) {
        this.ctgy = value;
    }

    /**
     * Gets the value of the ctgyDtls property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtgyDtls() {
        return ctgyDtls;
    }

    /**
     * Sets the value of the ctgyDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCtgyDtls(String value) {
        this.ctgyDtls = value;
    }

    /**
     * Gets the value of the dbtrSts property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDbtrSts() {
        return dbtrSts;
    }

    /**
     * Sets the value of the dbtrSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDbtrSts(String value) {
        this.dbtrSts = value;
    }

    /**
     * Gets the value of the certId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCertId() {
        return certId;
    }

    /**
     * Sets the value of the certId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCertId(String value) {
        this.certId = value;
    }

    /**
     * Gets the value of the frmsCd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrmsCd() {
        return frmsCd;
    }

    /**
     * Sets the value of the frmsCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFrmsCd(String value) {
        this.frmsCd = value;
    }

    /**
     * Gets the value of the prd property.
     * 
     * @return
     *     possible object is
     *     {@link TaxPeriod3 }
     *     
     */
    public TaxPeriod3 getPrd() {
        return prd;
    }

    /**
     * Sets the value of the prd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPeriod3 }
     *     
     */
    public void setPrd(TaxPeriod3 value) {
        this.prd = value;
    }

    /**
     * Gets the value of the taxAmt property.
     * 
     * @return
     *     possible object is
     *     {@link TaxAmount3 }
     *     
     */
    public TaxAmount3 getTaxAmt() {
        return taxAmt;
    }

    /**
     * Sets the value of the taxAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAmount3 }
     *     
     */
    public void setTaxAmt(TaxAmount3 value) {
        this.taxAmt = value;
    }

    /**
     * Gets the value of the addtlInf property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddtlInf() {
        return addtlInf;
    }

    /**
     * Sets the value of the addtlInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddtlInf(String value) {
        this.addtlInf = value;
    }

}
