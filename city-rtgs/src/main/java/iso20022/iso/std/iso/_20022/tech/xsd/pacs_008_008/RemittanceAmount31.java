
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Nature of the amount and currency on a document referred to in the remittance section, typically either the original amount due/payable or the amount actually remitted for the referenced document.
 * 
 * <p>Java class for RemittanceAmount3__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RemittanceAmount3__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DuePyblAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="DscntApldAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}DiscountAmountAndType1__1" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="CdtNoteAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *         <element name="TaxAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxAmountAndType1__1" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="AdjstmntAmtAndRsn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}DocumentAdjustment1__1" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="RmtdAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ActiveOrHistoricCurrencyAndAmount" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceAmount3__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "duePyblAmt",
    "dscntApldAmt",
    "cdtNoteAmt",
    "taxAmt",
    "adjstmntAmtAndRsn",
    "rmtdAmt"
})
public class RemittanceAmount31 {

    /**
     * Amount specified is the exact amount due and payable to the creditor.
     * 
     */
    @XmlElement(name = "DuePyblAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount duePyblAmt;
    /**
     * Amount of discount to be applied to the amount due and payable to the creditor.
     * 
     */
    @XmlElement(name = "DscntApldAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<DiscountAmountAndType11> dscntApldAmt;
    /**
     * Amount of a credit note.
     * 
     */
    @XmlElement(name = "CdtNoteAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount cdtNoteAmt;
    /**
     * Amount of the tax.
     * 
     */
    @XmlElement(name = "TaxAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<TaxAmountAndType11> taxAmt;
    /**
     * Specifies detailed information on the amount and reason of the adjustment.
     * 
     */
    @XmlElement(name = "AdjstmntAmtAndRsn", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<DocumentAdjustment11> adjstmntAmtAndRsn;
    /**
     * Amount of money remitted.
     * 
     */
    @XmlElement(name = "RmtdAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ActiveOrHistoricCurrencyAndAmount rmtdAmt;

    /**
     * Amount specified is the exact amount due and payable to the creditor.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getDuePyblAmt() {
        return duePyblAmt;
    }

    /**
     * Sets the value of the duePyblAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getDuePyblAmt()
     */
    public void setDuePyblAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.duePyblAmt = value;
    }

    /**
     * Amount of discount to be applied to the amount due and payable to the creditor.
     * 
     * Gets the value of the dscntApldAmt property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dscntApldAmt property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getDscntApldAmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DiscountAmountAndType11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the dscntApldAmt property.
     */
    public List<DiscountAmountAndType11> getDscntApldAmt() {
        if (dscntApldAmt == null) {
            dscntApldAmt = new ArrayList<>();
        }
        return this.dscntApldAmt;
    }

    /**
     * Amount of a credit note.
     * 
     * @return
     *     possible object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     */
    public ActiveOrHistoricCurrencyAndAmount getCdtNoteAmt() {
        return cdtNoteAmt;
    }

    /**
     * Sets the value of the cdtNoteAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link ActiveOrHistoricCurrencyAndAmount }
     *     
     * @see #getCdtNoteAmt()
     */
    public void setCdtNoteAmt(ActiveOrHistoricCurrencyAndAmount value) {
        this.cdtNoteAmt = value;
    }

    /**
     * Amount of the tax.
     * 
     * Gets the value of the taxAmt property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the taxAmt property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getTaxAmt().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TaxAmountAndType11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the taxAmt property.
     */
    public List<TaxAmountAndType11> getTaxAmt() {
        if (taxAmt == null) {
            taxAmt = new ArrayList<>();
        }
        return this.taxAmt;
    }

    /**
     * Specifies detailed information on the amount and reason of the adjustment.
     * 
     * Gets the value of the adjstmntAmtAndRsn property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adjstmntAmtAndRsn property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getAdjstmntAmtAndRsn().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentAdjustment11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the adjstmntAmtAndRsn property.
     */
    public List<DocumentAdjustment11> getAdjstmntAmtAndRsn() {
        if (adjstmntAmtAndRsn == null) {
            adjstmntAmtAndRsn = new ArrayList<>();
        }
        return this.adjstmntAmtAndRsn;
    }

    /**
     * Amount of money remitted.
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

}
