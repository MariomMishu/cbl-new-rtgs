
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificationToReceiveStatusReportV08 complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="NotificationToReceiveStatusReportV08"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}GroupHeader121"/&gt;
 *         &lt;element name="OrgnlNtfctnAndSts" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}OriginalNotification15"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:camt.059.001.08}SupplementaryData1" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "NotificationToReceiveStatusReportV08", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", propOrder = {
    "grpHdr",
    "orgnlNtfctnAndSts",
    "splmtryData"
})
public class NotificationToReceiveStatusReportV08 {

    @XmlElement(name = "GrpHdr", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", required = true)
    protected GroupHeader121 grpHdr;
    @XmlElement(name = "OrgnlNtfctnAndSts", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08", required = true)
    protected OriginalNotification15 orgnlNtfctnAndSts;
    @XmlElement(name = "SplmtryData", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
    protected List<SupplementaryData1> splmtryData;

    /**
     * Gets the value of the grpHdr property.
     * 
     * @return
     *     possible object is
     *     {@link GroupHeader121 }
     *     
     */
    public GroupHeader121 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link GroupHeader121 }
     *     
     */
    public void setGrpHdr(GroupHeader121 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the orgnlNtfctnAndSts property.
     * 
     * @return
     *     possible object is
     *     {@link OriginalNotification15 }
     *     
     */
    public OriginalNotification15 getOrgnlNtfctnAndSts() {
        return orgnlNtfctnAndSts;
    }

    /**
     * Sets the value of the orgnlNtfctnAndSts property.
     * 
     * @param value
     *     allowed object is
     *     {@link OriginalNotification15 }
     *     
     */
    public void setOrgnlNtfctnAndSts(OriginalNotification15 value) {
        this.orgnlNtfctnAndSts = value;
    }

    /**
     * Gets the value of the splmtryData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the javax XML Binding object.
     * This is why there is not a <CODE>set</CODE> method for the splmtryData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSplmtryData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link SupplementaryData1 }
     * 
     * 
     */
    public List<SupplementaryData1> getSplmtryData() {
        if (splmtryData == null) {
            splmtryData = new ArrayList<SupplementaryData1>();
        }
        return this.splmtryData;
    }

}
