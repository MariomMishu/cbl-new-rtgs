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
 * <p>Java class for GenericIdentification20 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="GenericIdentification20"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}Exact4AlphaNumericText"/&gt;
 *         &lt;element name="Issr" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}Max35Text"/&gt;
 *         &lt;element name="SchmeNm" type="{urn:iso:std:iso:20022:tech:xsd:camt.052.001.04}Max35Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GenericIdentification20", propOrder = {
        "id",
        "issr",
        "schmeNm"
})
public class GenericIdentification20 {

    @XmlElement(name = "Id", required = true)
    protected String id;
    @XmlElement(name = "Issr", required = true)
    protected String issr;
    @XmlElement(name = "SchmeNm")
    protected String schmeNm;

    /**
     * Gets the value of the id property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setId(String value) {
        this.id = value;
    }

    /**
     * Gets the value of the issr property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getIssr() {
        return issr;
    }

    /**
     * Sets the value of the issr property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setIssr(String value) {
        this.issr = value;
    }

    /**
     * Gets the value of the schmeNm property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getSchmeNm() {
        return schmeNm;
    }

    /**
     * Sets the value of the schmeNm property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setSchmeNm(String value) {
        this.schmeNm = value;
    }

}
