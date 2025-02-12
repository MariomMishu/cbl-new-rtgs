
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RemittanceInformation22 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RemittanceInformation22"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="Ustrd" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}Max140Text" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="Strd" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}StructuredRemittanceInformation18" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceInformation22", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", propOrder = {
    "ustrd",
    "strd"
})
public class RemittanceInformation22 {

    @XmlElement(name = "Ustrd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected List<String> ustrd;
    @XmlElement(name = "Strd", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected List<StructuredRemittanceInformation18> strd;

    /**
     * Gets the value of the ustrd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the javax XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the ustrd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUstrd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * 
     * 
     */
    public List<String> getUstrd() {
        if (ustrd == null) {
            ustrd = new ArrayList<String>();
        }
        return this.ustrd;
    }

    /**
     * Gets the value of the strd property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the javax XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the strd property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getStrd().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link StructuredRemittanceInformation18 }
     * 
     * 
     */
    public List<StructuredRemittanceInformation18> getStrd() {
        if (strd == null) {
            strd = new ArrayList<StructuredRemittanceInformation18>();
        }
        return this.strd;
    }

}
