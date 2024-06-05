
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Identifies the underlying transaction(s) and/or batched entries.
 * 
 * <p>Java class for EntryDetails9__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="EntryDetails9__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="TxDtls" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}EntryTransaction10__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "EntryDetails9__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "txDtls"
})
public class EntryDetails91 {

    /**
     * Provides information on the underlying transaction(s).
     * 
     */
    @XmlElement(name = "TxDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected EntryTransaction101 txDtls;

    /**
     * Provides information on the underlying transaction(s).
     * 
     * @return
     *     possible object is
     *     {@link EntryTransaction101 }
     *     
     */
    public EntryTransaction101 getTxDtls() {
        return txDtls;
    }

    /**
     * Sets the value of the txDtls property.
     * 
     * @param value
     *     allowed object is
     *     {@link EntryTransaction101 }
     *     
     * @see #getTxDtls()
     */
    public void setTxDtls(EntryTransaction101 value) {
        this.txDtls = value;
    }

}
