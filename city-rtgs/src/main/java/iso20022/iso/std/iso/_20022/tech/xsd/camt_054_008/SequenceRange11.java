
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies a range of sequences from a start sequence to an end sequence.
 * 
 * <p>Java class for SequenceRange1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="SequenceRange1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="FrSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="ToSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SequenceRange1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "frSeq",
    "toSeq"
})
public class SequenceRange11 {

    /**
     * Start sequence of the range.
     * 
     */
    @XmlElement(name = "FrSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String frSeq;
    /**
     * End sequence of the range.
     * 
     */
    @XmlElement(name = "ToSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", required = true)
    protected String toSeq;

    /**
     * Start sequence of the range.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFrSeq() {
        return frSeq;
    }

    /**
     * Sets the value of the frSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFrSeq()
     */
    public void setFrSeq(String value) {
        this.frSeq = value;
    }

    /**
     * End sequence of the range.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getToSeq() {
        return toSeq;
    }

    /**
     * Sets the value of the toSeq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getToSeq()
     */
    public void setToSeq(String value) {
        this.toSeq = value;
    }

}
