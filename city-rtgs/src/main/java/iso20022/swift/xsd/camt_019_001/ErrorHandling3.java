//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_019_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ErrorHandling3 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="ErrorHandling3"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Err" type="{urn:swift:xsd:camt.019.001.04}ErrorHandling1Choice"/&gt;
 *         &lt;element name="Desc" type="{urn:swift:xsd:camt.019.001.04}Max140Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ErrorHandling3", propOrder = {
        "err",
        "desc"
})
public class ErrorHandling3 {

    @XmlElement(name = "Err", required = true)
    protected ErrorHandling1Choice err;
    @XmlElement(name = "Desc")
    protected String desc;

    /**
     * Gets the value of the err property.
     *
     * @return possible object is
     * {@link ErrorHandling1Choice }
     */
    public ErrorHandling1Choice getErr() {
        return err;
    }

    /**
     * Sets the value of the err property.
     *
     * @param value allowed object is
     *              {@link ErrorHandling1Choice }
     */
    public void setErr(ErrorHandling1Choice value) {
        this.err = value;
    }

    /**
     * Gets the value of the desc property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setDesc(String value) {
        this.desc = value;
    }

}
