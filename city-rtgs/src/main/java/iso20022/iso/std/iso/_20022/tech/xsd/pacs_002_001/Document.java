//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.iso.std.iso._20022.tech.xsd.pacs_002_001;

import com.cbl.cityrtgs.models.dto.message.RtgsDocument;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Document complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="Document"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="FIToFIPmtStsRpt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.002.001.05}FIToFIPaymentStatusReportV05CMA"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Document", propOrder = {
        "fiToFIPmtStsRpt"
})
public class Document implements RtgsDocument {

    @XmlElement(name = "FIToFIPmtStsRpt", required = true)
    protected FIToFIPaymentStatusReportV05CMA fiToFIPmtStsRpt;

    /**
     * Gets the value of the fiToFIPmtStsRpt property.
     *
     * @return possible object is
     * {@link FIToFIPaymentStatusReportV05CMA }
     */
    public FIToFIPaymentStatusReportV05CMA getFIToFIPmtStsRpt() {
        return fiToFIPmtStsRpt;
    }

    /**
     * Sets the value of the fiToFIPmtStsRpt property.
     *
     * @param value allowed object is
     *              {@link FIToFIPaymentStatusReportV05CMA }
     */
    public void setFIToFIPmtStsRpt(FIToFIPaymentStatusReportV05CMA value) {
        this.fiToFIPmtStsRpt = value;
    }

}
