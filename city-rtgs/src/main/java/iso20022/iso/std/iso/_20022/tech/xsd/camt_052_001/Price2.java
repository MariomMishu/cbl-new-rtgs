//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_052_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Price2 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Price2"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}YieldedOrValueType1Choice"/&gt;
 *         &lt;element name="Val" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}PriceRateOrAmountChoice"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Price2", propOrder = {
        "tp",
        "val"
})
public class Price2 {

    @XmlElement(name = "Tp", required = true)
    protected YieldedOrValueType1Choice tp;
    @XmlElement(name = "Val", required = true)
    protected PriceRateOrAmountChoice val;

    /**
     * Gets the value of the tp property.
     *
     * @return possible object is
     * {@link YieldedOrValueType1Choice }
     */
    public YieldedOrValueType1Choice getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     *
     * @param value allowed object is
     *              {@link YieldedOrValueType1Choice }
     */
    public void setTp(YieldedOrValueType1Choice value) {
        this.tp = value;
    }

    /**
     * Gets the value of the val property.
     *
     * @return possible object is
     * {@link PriceRateOrAmountChoice }
     */
    public PriceRateOrAmountChoice getVal() {
        return val;
    }

    /**
     * Sets the value of the val property.
     *
     * @param value allowed object is
     *              {@link PriceRateOrAmountChoice }
     */
    public void setVal(PriceRateOrAmountChoice value) {
        this.val = value;
    }

}
