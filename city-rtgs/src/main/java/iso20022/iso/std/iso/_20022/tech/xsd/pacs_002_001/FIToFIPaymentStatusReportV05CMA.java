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
 * <p>Java class for FIToFIPaymentStatusReportV05CMA complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="FIToFIPaymentStatusReportV05CMA"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="GrpHdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}GroupHeader53CMA"/&gt;
 *         &lt;element name="OrgnlGrpInfAndSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}OriginalGroupHeaderCMA1"/&gt;
 *         &lt;element name="TxInfAndSts" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}PaymentTransaction33CMA" maxOccurs="unbounded" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FIToFIPaymentStatusReportV05CMA", propOrder = {
        "grpHdr",
        "orgnlGrpInfAndSts",
        "txInfAndSts"
})
public class FIToFIPaymentStatusReportV05CMA {

    @XmlElement(name = "GrpHdr", required = true)
    protected GroupHeader53CMA grpHdr;
    @XmlElement(name = "OrgnlGrpInfAndSts", required = true)
    protected OriginalGroupHeaderCMA1 orgnlGrpInfAndSts;
    @XmlElement(name = "TxInfAndSts")
    protected List<PaymentTransaction33CMA> txInfAndSts;

    /**
     * Gets the value of the grpHdr property.
     *
     * @return possible object is
     * {@link GroupHeader53CMA }
     */
    public GroupHeader53CMA getGrpHdr() {
        return grpHdr;
    }

    /**
     * Sets the value of the grpHdr property.
     *
     * @param value allowed object is
     *              {@link GroupHeader53CMA }
     */
    public void setGrpHdr(GroupHeader53CMA value) {
        this.grpHdr = value;
    }

    /**
     * Gets the value of the orgnlGrpInfAndSts property.
     *
     * @return possible object is
     * {@link OriginalGroupHeaderCMA1 }
     */
    public OriginalGroupHeaderCMA1 getOrgnlGrpInfAndSts() {
        return orgnlGrpInfAndSts;
    }

    /**
     * Sets the value of the orgnlGrpInfAndSts property.
     *
     * @param value allowed object is
     *              {@link OriginalGroupHeaderCMA1 }
     */
    public void setOrgnlGrpInfAndSts(OriginalGroupHeaderCMA1 value) {
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
     * {@link PaymentTransaction33CMA }
     */
    public List<PaymentTransaction33CMA> getTxInfAndSts() {
        if (txInfAndSts == null) {
            txInfAndSts = new ArrayList<PaymentTransaction33CMA>();
        }
        return this.txInfAndSts;
    }

}
