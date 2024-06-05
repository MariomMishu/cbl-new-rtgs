//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for GroupHeader54 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="GroupHeader54"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}Max35Text"/&gt;
 *         &lt;element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ISODateTime"/&gt;
 *         &lt;element name="Authstn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}Authorisation1Choice" maxOccurs="2" minOccurs="0"/&gt;
 *         &lt;element name="BtchBookg" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}BatchBookingIndicator" minOccurs="0"/&gt;
 *         &lt;element name="NbOfTxs" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}Max15NumericText"/&gt;
 *         &lt;element name="CtrlSum" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}DecimalNumber" minOccurs="0"/&gt;
 *         &lt;element name="GrpRtr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}TrueFalseIndicator" minOccurs="0"/&gt;
 *         &lt;element name="TtlRtrdIntrBkSttlmAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ActiveCurrencyAndAmount" minOccurs="0"/&gt;
 *         &lt;element name="IntrBkSttlmDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ISODate" minOccurs="0"/&gt;
 *         &lt;element name="SttlmInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}SettlementInstruction1"/&gt;
 *         &lt;element name="InstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PACS004INSTDAAGTBranchAndFinInstIdentif5" minOccurs="0"/&gt;
 *         &lt;element name="InstdAgt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}PACS004INSTDAAGTBranchAndFinInstIdentif5" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader54", propOrder = {
        "msgId",
        "creDtTm",
        "authstn",
        "btchBookg",
        "nbOfTxs",
        "ctrlSum",
        "grpRtr",
        "ttlRtrdIntrBkSttlmAmt",
        "intrBkSttlmDt",
        "sttlmInf",
        "instgAgt",
        "instdAgt"
})
public class GroupHeader54 {

    @XmlElement(name = "MsgId", required = true)
    protected String msgId;
    @XmlElement(name = "CreDtTm", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;
    @XmlElement(name = "Authstn")
    protected List<Authorisation1Choice> authstn;
    @XmlElement(name = "BtchBookg")
    protected Boolean btchBookg;
    @XmlElement(name = "NbOfTxs", required = true)
    protected String nbOfTxs;
    @XmlElement(name = "CtrlSum")
    protected BigDecimal ctrlSum;
    @XmlElement(name = "GrpRtr")
    protected Boolean grpRtr;
    @XmlElement(name = "TtlRtrdIntrBkSttlmAmt")
    protected ActiveCurrencyAndAmount ttlRtrdIntrBkSttlmAmt;
    @XmlElement(name = "IntrBkSttlmDt")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar intrBkSttlmDt;
    @XmlElement(name = "SttlmInf", required = true)
    protected SettlementInstruction1 sttlmInf;
    @XmlElement(name = "InstgAgt")
    protected PACS004INSTDAAGTBranchAndFinInstIdentif5 instgAgt;
    @XmlElement(name = "InstdAgt")
    protected PACS004INSTDAAGTBranchAndFinInstIdentif5 instdAgt;

    /**
     * Gets the value of the msgId property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Gets the value of the creDtTm property.
     *
     * @return possible object is
     * {@link XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     *
     * @param value allowed object is
     *              {@link XMLGregorianCalendar }
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

    /**
     * Gets the value of the authstn property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the authstn property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAuthstn().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Authorisation1Choice }
     */
    public List<Authorisation1Choice> getAuthstn() {
        if (authstn == null) {
            authstn = new ArrayList<Authorisation1Choice>();
        }
        return this.authstn;
    }

    /**
     * Gets the value of the btchBookg property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isBtchBookg() {
        return btchBookg;
    }

    /**
     * Sets the value of the btchBookg property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setBtchBookg(Boolean value) {
        this.btchBookg = value;
    }

    /**
     * Gets the value of the nbOfTxs property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getNbOfTxs() {
        return nbOfTxs;
    }

    /**
     * Sets the value of the nbOfTxs property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setNbOfTxs(String value) {
        this.nbOfTxs = value;
    }

    /**
     * Gets the value of the ctrlSum property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getCtrlSum() {
        return ctrlSum;
    }

    /**
     * Sets the value of the ctrlSum property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setCtrlSum(BigDecimal value) {
        this.ctrlSum = value;
    }

    /**
     * Gets the value of the grpRtr property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isGrpRtr() {
        return grpRtr;
    }

    /**
     * Sets the value of the grpRtr property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setGrpRtr(Boolean value) {
        this.grpRtr = value;
    }

    /**
     * Gets the value of the ttlRtrdIntrBkSttlmAmt property.
     *
     * @return possible object is
     * {@link ActiveCurrencyAndAmount }
     */
    public ActiveCurrencyAndAmount getTtlRtrdIntrBkSttlmAmt() {
        return ttlRtrdIntrBkSttlmAmt;
    }

    /**
     * Sets the value of the ttlRtrdIntrBkSttlmAmt property.
     *
     * @param value allowed object is
     *              {@link ActiveCurrencyAndAmount }
     */
    public void setTtlRtrdIntrBkSttlmAmt(ActiveCurrencyAndAmount value) {
        this.ttlRtrdIntrBkSttlmAmt = value;
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
     * Gets the value of the sttlmInf property.
     *
     * @return possible object is
     * {@link SettlementInstruction1 }
     */
    public SettlementInstruction1 getSttlmInf() {
        return sttlmInf;
    }

    /**
     * Sets the value of the sttlmInf property.
     *
     * @param value allowed object is
     *              {@link SettlementInstruction1 }
     */
    public void setSttlmInf(SettlementInstruction1 value) {
        this.sttlmInf = value;
    }

    /**
     * Gets the value of the instgAgt property.
     *
     * @return possible object is
     * {@link PACS004INSTDAAGTBranchAndFinInstIdentif5 }
     */
    public PACS004INSTDAAGTBranchAndFinInstIdentif5 getInstgAgt() {
        return instgAgt;
    }

    /**
     * Sets the value of the instgAgt property.
     *
     * @param value allowed object is
     *              {@link PACS004INSTDAAGTBranchAndFinInstIdentif5 }
     */
    public void setInstgAgt(PACS004INSTDAAGTBranchAndFinInstIdentif5 value) {
        this.instgAgt = value;
    }

    /**
     * Gets the value of the instdAgt property.
     *
     * @return possible object is
     * {@link PACS004INSTDAAGTBranchAndFinInstIdentif5 }
     */
    public PACS004INSTDAAGTBranchAndFinInstIdentif5 getInstdAgt() {
        return instdAgt;
    }

    /**
     * Sets the value of the instdAgt property.
     *
     * @param value allowed object is
     *              {@link PACS004INSTDAAGTBranchAndFinInstIdentif5 }
     */
    public void setInstdAgt(PACS004INSTDAAGTBranchAndFinInstIdentif5 value) {
        this.instdAgt = value;
    }

}
