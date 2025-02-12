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
 * <p>Java class for FIToFIPaymentStatusReportV05 complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FIToFIPaymentStatusReportV05"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}GroupHeader53"/&gt;
 *         &lt;element name="OrgnlGrpInfAndSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}OriginalGroupHeader1"/&gt;
 *         &lt;element name="TxInfAndSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}PaymentTransaction43" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="SplmtryData" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}SupplementaryData1CMA1" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FIToFIPaymentStatusReportV05", propOrder = {
        "grpHdr",
        "orgnlGrpInfAndSts",
        "txInfAndSts",
        "splmtryData"
})
public class FIToFIPaymentStatusReportV05 {

    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader53 grpHdr;
    @XmlElement(name = "OrgnlGrpInfAndSts", required = true)
    protected OriginalGroupHeader1 orgnlGrpInfAndSts;
    @XmlElement(name = "TxInfAndSts")
    protected List<PaymentTransaction43> txInfAndSts;
    @XmlElement(name = "SplmtryData")
    protected SupplementaryData1CMA1 splmtryData;

    /**
     * Gets the value of the grpHdr property.
     *
     * @return possible object is
     * {@link GroupHeader53 }
     */
    public GroupHeader53 getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     *
     * @param value allowed object is
     *              {@link GroupHeader53 }
     */
    public void setGrpHdr(GroupHeader53 value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the orgnlGrpInfAndSts property.
     *
     * @return possible object is
     * {@link OriginalGroupHeader1 }
     */
    public OriginalGroupHeader1 getOrgnlGrpInfAndSts() {
        return orgnlGrpInfAndSts;
    }

    /**
     * Sets the value of the orgnlGrpInfAndSts property.
     *
     * @param value allowed object is
     *              {@link OriginalGroupHeader1 }
     */
    public void setOrgnlGrpInfAndSts(OriginalGroupHeader1 value) {
        this.orgnlGrpInfAndSts = value;
    }

    /**
     * Gets the value of the txInfAndSts property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the txInfAndSts property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTxInfAndSts().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PaymentTransaction43 }
     */
    public List<PaymentTransaction43> getTxInfAndSts() {
        if (txInfAndSts == null) {
            txInfAndSts = new ArrayList<PaymentTransaction43>();
        }
        return this.txInfAndSts;
    }

    /**
     * Gets the value of the splmtryData property.
     *
     * @return possible object is
     * {@link SupplementaryData1CMA1 }
     */
    public SupplementaryData1CMA1 getSplmtryData() {
        return splmtryData;
    }

    /**
     * Sets the value of the splmtryData property.
     *
     * @param value allowed object is
     *              {@link SupplementaryData1CMA1 }
     */
    public void setSplmtryData(SupplementaryData1CMA1 value) {
        this.splmtryData = value;
    }

}
