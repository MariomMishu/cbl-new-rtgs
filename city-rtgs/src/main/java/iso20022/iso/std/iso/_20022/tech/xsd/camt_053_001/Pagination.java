//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.camt_053_001;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Pagination complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Pagination"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="PgNb" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}Max5NumericText"/&gt;
 *         &lt;element name="LastPgInd" type="{urn:iso:std:iso:20022:tech:xsd:camt.053.001.04}YesNoIndicator"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Pagination", propOrder = {
        "pgNb",
        "lastPgInd"
})
public class Pagination {

    @XmlElement(name = "PgNb", required = true)
    protected String pgNb;
    @XmlElement(name = "LastPgInd")
    protected boolean lastPgInd;

    /**
     * Gets the value of the pgNb property.
     *
     * @return possible object is
     * {@link String }
     */
    public String getPgNb() {
        return pgNb;
    }

    /**
     * Sets the value of the pgNb property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setPgNb(String value) {
        this.pgNb = value;
    }

    /**
     * Gets the value of the lastPgInd property.
     */
    public boolean isLastPgInd() {
        return lastPgInd;
    }

    /**
     * Sets the value of the lastPgInd property.
     */
    public void setLastPgInd(boolean value) {
        this.lastPgInd = value;
    }

}
