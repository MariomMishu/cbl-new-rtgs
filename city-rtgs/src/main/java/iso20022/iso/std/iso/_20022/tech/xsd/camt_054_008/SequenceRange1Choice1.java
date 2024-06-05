
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import java.util.ArrayList;
import java.util.List;


/**
 * Specifies a choice of sequences.
 * 
 * <p>Java class for SequenceRange1Choice__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="SequenceRange1Choice__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <choice>
 *         <element name="FrSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="ToSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_"/>
 *         <element name="FrToSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}SequenceRange1__1" maxOccurs="unbounded"/>
 *         <element name="EQSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" maxOccurs="unbounded"/>
 *         <element name="NEQSeq" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" maxOccurs="unbounded"/>
 *       </choice>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SequenceRange1Choice__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "frSeq",
    "toSeq",
    "frToSeq",
    "eqSeq",
    "neqSeq"
})
public class SequenceRange1Choice1 {

    /**
     * Start sequence of the range.
     * 
     */
    @XmlElement(name = "FrSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String frSeq;
    /**
     * End sequence of the range.
     * 
     */
    @XmlElement(name = "ToSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected String toSeq;
    /**
     * Particular sequence range specified between a start sequence and an end sequence.
     * 
     */
    @XmlElement(name = "FrToSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected List<SequenceRange11> frToSeq;
    /**
     * Specified sequence to match.
     * 
     */
    @XmlElement(name = "EQSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected List<String> eqSeq;
    /**
     * Specified sequence to be excluded.
     * 
     */
    @XmlElement(name = "NEQSeq", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected List<String> neqSeq;

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

    /**
     * Particular sequence range specified between a start sequence and an end sequence.
     * 
     * Gets the value of the frToSeq property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the frToSeq property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getFrToSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SequenceRange11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the frToSeq property.
     */
    public List<SequenceRange11> getFrToSeq() {
        if (frToSeq == null) {
            frToSeq = new ArrayList<>();
        }
        return this.frToSeq;
    }

    /**
     * Specified sequence to match.
     * 
     * Gets the value of the eqSeq property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the eqSeq property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getEQSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * </p>
     * 
     * 
     * @return
     *     The value of the eqSeq property.
     */
    public List<String> getEQSeq() {
        if (eqSeq == null) {
            eqSeq = new ArrayList<>();
        }
        return this.eqSeq;
    }

    /**
     * Specified sequence to be excluded.
     * 
     * Gets the value of the neqSeq property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the neqSeq property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getNEQSeq().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * </p>
     * 
     * 
     * @return
     *     The value of the neqSeq property.
     */
    public List<String> getNEQSeq() {
        if (neqSeq == null) {
            neqSeq = new ArrayList<>();
        }
        return this.neqSeq;
    }

}
