
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for TaxAuthorisation1 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="TaxAuthorisation1"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Titl" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Nm" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max140Text" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TaxAuthorisation1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", propOrder = {
    "titl",
    "nm"
})
public class TaxAuthorisation1 {

    @XmlElement(name = "Titl", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String titl;
    @XmlElement(name = "Nm", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected String nm;

    /**
     * Gets the value of the titl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTitl() {
        return titl;
    }

    /**
     * Sets the value of the titl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTitl(String value) {
        this.titl = value;
    }

    /**
     * Gets the value of the nm property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNm() {
        return nm;
    }

    /**
     * Sets the value of the nm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNm(String value) {
        this.nm = value;
    }

}
