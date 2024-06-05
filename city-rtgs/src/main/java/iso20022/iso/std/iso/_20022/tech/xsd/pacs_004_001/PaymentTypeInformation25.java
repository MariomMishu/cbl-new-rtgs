//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PaymentTypeInformation25 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PaymentTypeInformation25"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="ClrChanl" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ClearingChannel2Code"/&gt;
 *         &lt;element name="SvcLvl" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}ServiceLevel8Choice" minOccurs="0"/&gt;
 *         &lt;element name="LclInstrm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}LocalInstrument2Choice" minOccurs="0"/&gt;
 *         &lt;element name="CtgyPurp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}CategoryPurpose1Choice"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PaymentTypeInformation25", propOrder = {
        "clrChanl",
        "svcLvl",
        "lclInstrm",
        "ctgyPurp"
})
public class PaymentTypeInformation25 {

    @XmlElement(name = "ClrChanl", required = true)
    @XmlSchemaType(name = "string")
    protected ClearingChannel2Code clrChanl;
    @XmlElement(name = "SvcLvl")
    protected ServiceLevel8Choice svcLvl;
    @XmlElement(name = "LclInstrm")
    protected LocalInstrument2Choice lclInstrm;
    @XmlElement(name = "CtgyPurp", required = true)
    protected CategoryPurpose1Choice ctgyPurp;

    /**
     * Gets the value of the clrChanl property.
     *
     * @return possible object is
     * {@link ClearingChannel2Code }
     */
    public ClearingChannel2Code getClrChanl() {
        return clrChanl;
    }

    /**
     * Sets the value of the clrChanl property.
     *
     * @param value allowed object is
     *              {@link ClearingChannel2Code }
     */
    public void setClrChanl(ClearingChannel2Code value) {
        this.clrChanl = value;
    }

    /**
     * Gets the value of the svcLvl property.
     *
     * @return possible object is
     * {@link ServiceLevel8Choice }
     */
    public ServiceLevel8Choice getSvcLvl() {
        return svcLvl;
    }

    /**
     * Sets the value of the svcLvl property.
     *
     * @param value allowed object is
     *              {@link ServiceLevel8Choice }
     */
    public void setSvcLvl(ServiceLevel8Choice value) {
        this.svcLvl = value;
    }

    /**
     * Gets the value of the lclInstrm property.
     *
     * @return possible object is
     * {@link LocalInstrument2Choice }
     */
    public LocalInstrument2Choice getLclInstrm() {
        return lclInstrm;
    }

    /**
     * Sets the value of the lclInstrm property.
     *
     * @param value allowed object is
     *              {@link LocalInstrument2Choice }
     */
    public void setLclInstrm(LocalInstrument2Choice value) {
        this.lclInstrm = value;
    }

    /**
     * Gets the value of the ctgyPurp property.
     *
     * @return possible object is
     * {@link CategoryPurpose1Choice }
     */
    public CategoryPurpose1Choice getCtgyPurp() {
        return ctgyPurp;
    }

    /**
     * Sets the value of the ctgyPurp property.
     *
     * @param value allowed object is
     *              {@link CategoryPurpose1Choice }
     */
    public void setCtgyPurp(CategoryPurpose1Choice value) {
        this.ctgyPurp = value;
    }

}
