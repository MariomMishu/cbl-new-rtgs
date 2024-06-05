
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Provides further details on the message.
 * 
 * <p>Java class for GroupHeader81__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="GroupHeader81__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="MsgId" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="CreDtTm" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_DateTime__3_"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GroupHeader81__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "msgId",
    "creDtTm"
})
public class GroupHeader811 {

    /**
     * Point to point reference, as assigned by the account servicing institution, and sent to the account owner or the party authorised to receive the message, to unambiguously identify the message.
     *                         Usage: The account servicing institution has to make sure that MessageIdentification is unique per account owner for a pre-agreed period.
     * 
     */
    @XmlElement(name = "MsgId", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String msgId;
    /**
     * Date and time at which the message was created.
     * 
     */
    @XmlElement(name = "CreDtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creDtTm;

    /**
     * Point to point reference, as assigned by the account servicing institution, and sent to the account owner or the party authorised to receive the message, to unambiguously identify the message.
     *                         Usage: The account servicing institution has to make sure that MessageIdentification is unique per account owner for a pre-agreed period.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * Sets the value of the msgId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getMsgId()
     */
    public void setMsgId(String value) {
        this.msgId = value;
    }

    /**
     * Date and time at which the message was created.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCreDtTm() {
        return creDtTm;
    }

    /**
     * Sets the value of the creDtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getCreDtTm()
     */
    public void setCreDtTm(XMLGregorianCalendar value) {
        this.creDtTm = value;
    }

}
