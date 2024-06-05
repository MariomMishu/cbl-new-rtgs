
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Information on the occurred settlement time(s) of the
 *                 payment transaction.
 * 
 * <p>Java class for SettlementDateTimeIndication1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="SettlementDateTimeIndication1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="DbtDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_DateTime__3_" minOccurs="0"/>
 *         <element name="CdtDtTm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}HVPSPlus_DateTime__3_" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementDateTimeIndication1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "dbtDtTm",
    "cdtDtTm"
})
public class SettlementDateTimeIndication11 {

    /**
     * Date and time at which a payment has been
     *                         debited at the transaction administrator. In the case of TARGET, the date and time at which the
     *                         payment has been debited at the central bank, expressed in Central European Time (CET).
     * 
     */
    @XmlElement(name = "DbtDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dbtDtTm;
    /**
     * Date and time at which a payment has been
     *                         credited at the transaction administrator. In the case of TARGET, the date and time at which the
     *                         payment has been credited at the receiving central bank, expressed in Central European Time
     *                         (CET).
     * 
     */
    @XmlElement(name = "CdtDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar cdtDtTm;

    /**
     * Date and time at which a payment has been
     *                         debited at the transaction administrator. In the case of TARGET, the date and time at which the
     *                         payment has been debited at the central bank, expressed in Central European Time (CET).
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDbtDtTm() {
        return dbtDtTm;
    }

    /**
     * Sets the value of the dbtDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getDbtDtTm()
     */
    public void setDbtDtTm(XMLGregorianCalendar value) {
        this.dbtDtTm = value;
    }

    /**
     * Date and time at which a payment has been
     *                         credited at the transaction administrator. In the case of TARGET, the date and time at which the
     *                         payment has been credited at the receiving central bank, expressed in Central European Time
     *                         (CET).
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCdtDtTm() {
        return cdtDtTm;
    }

    /**
     * Sets the value of the cdtDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getCdtDtTm()
     */
    public void setCdtDtTm(XMLGregorianCalendar value) {
        this.cdtDtTm = value;
    }

}
