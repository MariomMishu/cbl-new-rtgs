//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_001;

import javax.xml.bind.annotation.*;


/**
 * <p>Java class for PACS009LocalInstrument2Choice complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PACS009LocalInstrument2Choice"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;choice&gt;
 *         &lt;element name="Prtry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.04}PACS009LclInstrm"/&gt;
 *       &lt;/choice&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PACS009LocalInstrument2Choice", propOrder = {
        "prtry"
})
public class PACS009LocalInstrument2Choice {

    @XmlElement(name = "Prtry")
    @XmlSchemaType(name = "string")
    protected PACS009LclInstrm prtry;

    /**
     * Gets the value of the prtry property.
     *
     * @return possible object is
     * {@link PACS009LclInstrm }
     */
    public PACS009LclInstrm getPrtry() {
        return prtry;
    }

    /**
     * Sets the value of the prtry property.
     *
     * @param value allowed object is
     *              {@link PACS009LclInstrm }
     */
    public void setPrtry(PACS009LclInstrm value) {
        this.prtry = value;
    }

}
