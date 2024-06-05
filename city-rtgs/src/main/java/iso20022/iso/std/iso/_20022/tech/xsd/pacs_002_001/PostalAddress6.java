//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for PostalAddress6 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="PostalAddress6"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="StrtNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Max70Text" minOccurs="0"/&gt;
 *         &lt;element name="TwnNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Max35Text" minOccurs="0"/&gt;
 *         &lt;element name="Ctry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}CountryCode" minOccurs="0"/&gt;
 *         &lt;element name="AdrLine" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}Max35Text" maxOccurs="3" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddress6", propOrder = {
        "strtNm",
        "twnNm",
        "ctry",
        "adrLine"
})
public class PostalAddress6 {

    @XmlElement(name = "StrtNm")
    protected String strtNm;
    @XmlElement(name = "TwnNm")
    protected String twnNm;
    @XmlElement(name = "Ctry")
    protected String ctry;
    @XmlElement(name = "AdrLine")
    protected List<String> adrLine;

    /**
     * Gets the value of the strtNm property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getStrtNm() {
        return strtNm;
    }

    /**
     * Sets the value of the strtNm property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setStrtNm(String value) {
        this.strtNm = value;
    }

    /**
     * Gets the value of the twnNm property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getTwnNm() {
        return twnNm;
    }

    /**
     * Sets the value of the twnNm property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setTwnNm(String value) {
        this.twnNm = value;
    }

    /**
     * Gets the value of the ctry property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * Sets the value of the ctry property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setCtry(String value) {
        this.ctry = value;
    }

    /**
     * Gets the value of the adrLine property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the adrLine property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAdrLine().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     */
    public List<String> getAdrLine() {
        if (adrLine == null) {
            adrLine = new ArrayList<String>();
        }
        return this.adrLine;
    }

}
