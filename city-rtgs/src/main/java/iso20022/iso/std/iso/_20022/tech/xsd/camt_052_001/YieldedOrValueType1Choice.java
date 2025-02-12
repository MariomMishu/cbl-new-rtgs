//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_052_001;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for YieldedOrValueType1Choice complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="YieldedOrValueType1Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Yldd" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}YesNoIndicator"/&gt;
 *         &lt;element name="ValTp" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}PriceValueType1Code"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "YieldedOrValueType1Choice", propOrder = {
        "yldd",
        "valTp"
})
public class YieldedOrValueType1Choice {

    @XmlElement(name = "Yldd")
    protected Boolean yldd;
    @XmlElement(name = "ValTp")
    @XmlSchemaType(name = "string")
    protected PriceValueType1Code valTp;

    /**
     * Gets the value of the yldd property.
     *
     * @return possible object is
     * {@link Boolean }
     */
    public Boolean isYldd() {
        return yldd;
    }

    /**
     * Sets the value of the yldd property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setYldd(Boolean value) {
        this.yldd = value;
    }

    /**
     * Gets the value of the valTp property.
     *
     * @return possible object is
     * {@link PriceValueType1Code }
     */
    public PriceValueType1Code getValTp() {
        return valTp;
    }

    /**
     * Sets the value of the valTp property.
     *
     * @param value allowed object is
     *              {@link PriceValueType1Code }
     */
    public void setValTp(PriceValueType1Code value) {
        this.valTp = value;
    }

}
