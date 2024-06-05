
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Provides remittance information about a payment for garnishment-related purposes.
 * 
 * <p>Java class for Garnishment3__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="Garnishment3__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}GarnishmentType1__1"/>
 *         <element name="Grnshee" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PartyIdentification135__5" minOccurs="0"/>
 *         <element name="GrnshmtAdmstr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PartyIdentification135__5" minOccurs="0"/>
 *         <element name="RefNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax140Text_Extended" minOccurs="0"/>
 *         <element name="Dt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ISODate" minOccurs="0"/>
 *         <element name="RmtdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="FmlyMdclInsrncInd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TrueFalseIndicator" minOccurs="0"/>
 *         <element name="MplyeeTermntnInd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TrueFalseIndicator" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Garnishment3__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "tp",
    "grnshee",
    "grnshmtAdmstr",
    "refNb",
    "dt",
    "rmtdAmt",
    "fmlyMdclInsrncInd",
    "mplyeeTermntnInd"
})
public class Garnishment31 {

    /**
     * Specifies the type of garnishment.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected GarnishmentType11 tp;
    /**
     * Ultimate party that owes an amount of money to the (ultimate) creditor, in this case, to the garnisher.
     * 
     */
    @XmlElement(name = "Grnshee", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected PartyIdentification1355 grnshee;
    /**
     * Party on the credit side of the transaction who administers the garnishment on behalf of the ultimate beneficiary.
     * 
     */
    @XmlElement(name = "GrnshmtAdmstr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected PartyIdentification1355 grnshmtAdmstr;
    /**
     * Reference information that is specific to the agency receiving the garnishment.
     * 
     */
    @XmlElement(name = "RefNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String refNb;
    /**
     * Date of payment which garnishment was taken from.
     * 
     */
    @XmlElement(name = "Dt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dt;
    /**
     * Amount of money remitted for the referred document.
     * 
     */
    @XmlElement(name = "RmtdAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount rmtdAmt;
    /**
     * Indicates if the person to whom the garnishment applies (that is, the ultimate debtor) has family medical insurance coverage available.
     * 
     */
    @XmlElement(name = "FmlyMdclInsrncInd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected Boolean fmlyMdclInsrncInd;
    /**
     * Indicates if the employment of the person to whom the garnishment applies (that is, the ultimate debtor) has been terminated.
     * 
     */
    @XmlElement(name = "MplyeeTermntnInd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected Boolean mplyeeTermntnInd;

    /**
     * Specifies the type of garnishment.
     * 
     * @return
     *     possible object is
     *     {@link GarnishmentType11 }
     *     
     */
    public GarnishmentType11 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link GarnishmentType11 }
     *     
     * @see #getTp()
     */
    public void setTp(GarnishmentType11 value) {
        this.tp = value;
    }

    /**
     * Ultimate party that owes an amount of money to the (ultimate) creditor, in this case, to the garnisher.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1355 }
     *     
     */
    public PartyIdentification1355 getGrnshee() {
        return grnshee;
    }

    /**
     * Sets the value of the grnshee property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1355 }
     *     
     * @see #getGrnshee()
     */
    public void setGrnshee(PartyIdentification1355 value) {
        this.grnshee = value;
    }

    /**
     * Party on the credit side of the transaction who administers the garnishment on behalf of the ultimate beneficiary.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1355 }
     *     
     */
    public PartyIdentification1355 getGrnshmtAdmstr() {
        return grnshmtAdmstr;
    }

    /**
     * Sets the value of the grnshmtAdmstr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1355 }
     *     
     * @see #getGrnshmtAdmstr()
     */
    public void setGrnshmtAdmstr(PartyIdentification1355 value) {
        this.grnshmtAdmstr = value;
    }

    /**
     * Reference information that is specific to the agency receiving the garnishment.
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
     * Date of payment which garnishment was taken from.
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
     * Amount of money remitted for the referred document.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getRmtdAmt() {
        return rmtdAmt;
    }

    /**
     * Sets the value of the rmtdAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getRmtdAmt()
     */
    public void setRmtdAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.rmtdAmt = value;
    }

    /**
     * Indicates if the person to whom the garnishment applies (that is, the ultimate debtor) has family medical insurance coverage available.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFmlyMdclInsrncInd() {
        return fmlyMdclInsrncInd;
    }

    /**
     * Sets the value of the fmlyMdclInsrncInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isFmlyMdclInsrncInd()
     */
    public void setFmlyMdclInsrncInd(Boolean value) {
        this.fmlyMdclInsrncInd = value;
    }

    /**
     * Indicates if the employment of the person to whom the garnishment applies (that is, the ultimate debtor) has been terminated.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isMplyeeTermntnInd() {
        return mplyeeTermntnInd;
    }

    /**
     * Sets the value of the mplyeeTermntnInd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     * @see #isMplyeeTermntnInd()
     */
    public void setMplyeeTermntnInd(Boolean value) {
        this.mplyeeTermntnInd = value;
    }

}
