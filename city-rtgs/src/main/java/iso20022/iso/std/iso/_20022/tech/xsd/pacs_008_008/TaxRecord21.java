
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Set of elements used to define the tax record.
 * 
 * <p>Java class for TaxRecord2__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TaxRecord2__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="Ctgy" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="CtgyDtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="DbtrSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="CertId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="FrmsCd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="Prd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxPeriod2" minOccurs="0"/>
 *         <element name="TaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxAmount2" minOccurs="0"/>
 *         <element name="AddtlInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax140Text_Extended" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxRecord2__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
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
public class TaxRecord21 {

    /**
     * High level code to identify the type of tax details.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String tp;
    /**
     * Specifies the tax code as published by the tax authority.
     * 
     */
    @XmlElement(name = "Ctgy", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String ctgy;
    /**
     * Provides further details of the category tax code.
     * 
     */
    @XmlElement(name = "CtgyDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String ctgyDtls;
    /**
     * Code provided by local authority to identify the status of the party that has drawn up the settlement document.
     * 
     */
    @XmlElement(name = "DbtrSts", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String dbtrSts;
    /**
     * Identification number of the tax report as assigned by the taxing authority.
     * 
     */
    @XmlElement(name = "CertId", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String certId;
    /**
     * Identifies, in a coded form, on which template the tax report is to be provided.
     * 
     */
    @XmlElement(name = "FrmsCd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String frmsCd;
    /**
     * Set of elements used to provide details on the period of time related to the tax payment.
     * 
     */
    @XmlElement(name = "Prd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected TaxPeriod2 prd;
    /**
     * Set of elements used to provide information on the amount of the tax record.
     * 
     */
    @XmlElement(name = "TaxAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected TaxAmount2 taxAmt;
    /**
     * Further details of the tax record.
     * 
     */
    @XmlElement(name = "AddtlInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String addtlInf;

    /**
     * High level code to identify the type of tax details.
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
     * @see #getTp()
     */
    public void setTp(String value) {
        this.tp = value;
    }

    /**
     * Specifies the tax code as published by the tax authority.
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
     * @see #getCtgy()
     */
    public void setCtgy(String value) {
        this.ctgy = value;
    }

    /**
     * Provides further details of the category tax code.
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
     * @see #getCtgyDtls()
     */
    public void setCtgyDtls(String value) {
        this.ctgyDtls = value;
    }

    /**
     * Code provided by local authority to identify the status of the party that has drawn up the settlement document.
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
     * @see #getDbtrSts()
     */
    public void setDbtrSts(String value) {
        this.dbtrSts = value;
    }

    /**
     * Identification number of the tax report as assigned by the taxing authority.
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
     * @see #getCertId()
     */
    public void setCertId(String value) {
        this.certId = value;
    }

    /**
     * Identifies, in a coded form, on which template the tax report is to be provided.
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
     * @see #getFrmsCd()
     */
    public void setFrmsCd(String value) {
        this.frmsCd = value;
    }

    /**
     * Set of elements used to provide details on the period of time related to the tax payment.
     * 
     * @return
     *     possible object is
     *     {@link TaxPeriod2 }
     *     
     */
    public TaxPeriod2 getPrd() {
        return prd;
    }

    /**
     * Sets the value of the prd property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxPeriod2 }
     *     
     * @see #getPrd()
     */
    public void setPrd(TaxPeriod2 value) {
        this.prd = value;
    }

    /**
     * Set of elements used to provide information on the amount of the tax record.
     * 
     * @return
     *     possible object is
     *     {@link TaxAmount2 }
     *     
     */
    public TaxAmount2 getTaxAmt() {
        return taxAmt;
    }

    /**
     * Sets the value of the taxAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxAmount2 }
     *     
     * @see #getTaxAmt()
     */
    public void setTaxAmt(TaxAmount2 value) {
        this.taxAmt = value;
    }

    /**
     * Further details of the tax record.
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
     * @see #getAddtlInf()
     */
    public void setAddtlInf(String value) {
        this.addtlInf = value;
    }

}
