
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.*;

import java.math.BigDecimal;


/**
 * A number of monetary units specified in an active
 *                 currency where the unit of currency is explicit and compliant with ISO 4217.
 * 
 * <p>Java class for RestrictedFINActiveCurrencyAndAmount complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RestrictedFINActiveCurrencyAndAmount">
 *   <simpleContent>
 *     <extension base="<urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08>RestrictedFINActiveCurrencyAndAmount_SimpleType">
 *       <attribute name="Ccy" use="required" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}ActiveCurrencyCode" />
 *     </extension>
 *   </simpleContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RestrictedFINActiveCurrencyAndAmount", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "value"
})
public class RestrictedFINActiveCurrencyAndAmount {

    @XmlValue
    protected BigDecimal value;
    /**
     * Medium of exchange of value.
     * 
     */
    @XmlAttribute(name = "Ccy", required = true)
    protected String ccy;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Medium of exchange of value.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCcy() {
        return ccy;
    }

    /**
     * Sets the value of the ccy property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCcy()
     */
    public void setCcy(String value) {
        this.ccy = value;
    }

}
