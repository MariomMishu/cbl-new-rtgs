//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_004_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AccountIdentification4ChoiceCMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="AccountIdentification4ChoiceCMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Othr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.004.001.04}GenericAccountIdentificationCMA1"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountIdentification4ChoiceCMA", propOrder = {
        "othr"
})
public class AccountIdentification4ChoiceCMA {

    @XmlElement(name = "Othr")
    protected GenericAccountIdentificationCMA1 othr;

    /**
     * Gets the value of the othr property.
     *
     * @return possible object is
     * {@link GenericAccountIdentificationCMA1 }
     */
    public GenericAccountIdentificationCMA1 getOthr() {
        return othr;
    }

    /**
     * Sets the value of the othr property.
     *
     * @param value allowed object is
     *              {@link GenericAccountIdentificationCMA1 }
     */
    public void setOthr(GenericAccountIdentificationCMA1 value) {
        this.othr = value;
    }

}
