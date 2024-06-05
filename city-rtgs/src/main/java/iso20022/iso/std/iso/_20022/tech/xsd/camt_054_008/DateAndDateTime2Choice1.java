
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.*;

import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Choice between a date or a date and time format.
 * 
 * <p>Java class for DateAndDateTime2Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="DateAndDateTime2Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="DtTm" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_DateTime__3_"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DateAndDateTime2Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "dtTm"
})
public class DateAndDateTime2Choice1 {

    /**
     * Specified date and time.
     * 
     */
    @XmlElement(name = "DtTm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dtTm;

    /**
     * Specified date and time.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDtTm() {
        return dtTm;
    }

    /**
     * Sets the value of the dtTm property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getDtTm()
     */
    public void setDtTm(XMLGregorianCalendar value) {
        this.dtTm = value;
    }

}
