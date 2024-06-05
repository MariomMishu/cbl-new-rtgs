
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Provides further details of the type of payment.
 * 
 * <p>Java class for PaymentTypeInformation28__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PaymentTypeInformation28__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InstrPrty" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Priority2Code" minOccurs="0"/>
 *         <element name="SvcLvl" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ServiceLevel8Choice__1" maxOccurs="3" minOccurs="0"/>
 *         <element name="LclInstrm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}LocalInstrument2Choice__1" minOccurs="0"/>
 *         <element name="CtgyPurp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CategoryPurpose1Choice__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTypeInformation28__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "instrPrty",
    "svcLvl",
    "lclInstrm",
    "ctgyPurp"
})
public class PaymentTypeInformation281 {

    /**
     * Indicator of the urgency or order of importance that the instructing party would like the instructed party to apply to the processing of the instruction.
     * 
     */
    @XmlElement(name = "InstrPrty", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "string")
    protected Priority2Code instrPrty;
    /**
     * Agreement under which or rules under which the transaction should be processed.
     * 
     */
    @XmlElement(name = "SvcLvl", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<ServiceLevel8Choice1> svcLvl;
    /**
     * User community specific instrument.
     * 
     *                         Usage: This element is used to specify a local instrument, local clearing option and/or further qualify the service or service level.
     * 
     */
    @XmlElement(name = "LclInstrm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected LocalInstrument2Choice1 lclInstrm;
    /**
     * Specifies the high level purpose of the instruction based on a set of pre-defined categories.
     *                         Usage: This is used by the initiating party to provide information concerning the processing of the payment. It is likely to trigger special processing by any of the agents involved in the payment chain.
     * 
     */
    @XmlElement(name = "CtgyPurp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected CategoryPurpose1Choice1 ctgyPurp;

    /**
     * Indicator of the urgency or order of importance that the instructing party would like the instructed party to apply to the processing of the instruction.
     * 
     * @return
     *     possible object is
     *     {@link Priority2Code }
     *     
     */
    public Priority2Code getInstrPrty() {
        return instrPrty;
    }

    /**
     * Sets the value of the instrPrty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Priority2Code }
     *     
     * @see #getInstrPrty()
     */
    public void setInstrPrty(Priority2Code value) {
        this.instrPrty = value;
    }

    /**
     * Agreement under which or rules under which the transaction should be processed.
     * 
     * Gets the value of the svcLvl property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the svcLvl property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getSvcLvl().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ServiceLevel8Choice1 }
     * </p>
     * 
     * 
     * @return
     *     The value of the svcLvl property.
     */
    public List<ServiceLevel8Choice1> getSvcLvl() {
        if (svcLvl == null) {
            svcLvl = new ArrayList<>();
        }
        return this.svcLvl;
    }

    /**
     * User community specific instrument.
     * 
     *                         Usage: This element is used to specify a local instrument, local clearing option and/or further qualify the service or service level.
     * 
     * @return
     *     possible object is
     *     {@link LocalInstrument2Choice1 }
     *     
     */
    public LocalInstrument2Choice1 getLclInstrm() {
        return lclInstrm;
    }

    /**
     * Sets the value of the lclInstrm property.
     * 
     * @param value
     *     allowed object is
     *     {@link LocalInstrument2Choice1 }
     *     
     * @see #getLclInstrm()
     */
    public void setLclInstrm(LocalInstrument2Choice1 value) {
        this.lclInstrm = value;
    }

    /**
     * Specifies the high level purpose of the instruction based on a set of pre-defined categories.
     *                         Usage: This is used by the initiating party to provide information concerning the processing of the payment. It is likely to trigger special processing by any of the agents involved in the payment chain.
     * 
     * @return
     *     possible object is
     *     {@link CategoryPurpose1Choice1 }
     *     
     */
    public CategoryPurpose1Choice1 getCtgyPurp() {
        return ctgyPurp;
    }

    /**
     * Sets the value of the ctgyPurp property.
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryPurpose1Choice1 }
     *     
     * @see #getCtgyPurp()
     */
    public void setCtgyPurp(CategoryPurpose1Choice1 value) {
        this.ctgyPurp = value;
    }

}
