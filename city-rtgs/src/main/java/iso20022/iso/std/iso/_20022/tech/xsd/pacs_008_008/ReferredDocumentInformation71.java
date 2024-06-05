
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.util.ArrayList;
import java.util.List;


/**
 * Set of elements used to identify the documents referred to in the remittance information.
 * 
 * <p>Java class for ReferredDocumentInformation7__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="ReferredDocumentInformation7__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ReferredDocumentType4__1" minOccurs="0"/>
 *         <element name="Nb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="RltdDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ISODate" minOccurs="0"/>
 *         <element name="LineDtls" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}DocumentLineInformation1__1" maxOccurs="unbounded" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ReferredDocumentInformation7__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "tp",
    "nb",
    "rltdDt",
    "lineDtls"
})
public class ReferredDocumentInformation71 {

    /**
     * Specifies the type of referred document.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected ReferredDocumentType41 tp;
    /**
     * Unique and unambiguous identification of the referred document.
     * 
     */
    @XmlElement(name = "Nb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String nb;
    /**
     * Date associated with the referred document.
     * 
     */
    @XmlElement(name = "RltdDt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rltdDt;
    /**
     * Set of elements used to provide the content of the referred document line.
     * 
     */
    @XmlElement(name = "LineDtls", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<DocumentLineInformation11> lineDtls;

    /**
     * Specifies the type of referred document.
     * 
     * @return
     *     possible object is
     *     {@link ReferredDocumentType41 }
     *     
     */
    public ReferredDocumentType41 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link ReferredDocumentType41 }
     *     
     * @see #getTp()
     */
    public void setTp(ReferredDocumentType41 value) {
        this.tp = value;
    }

    /**
     * Unique and unambiguous identification of the referred document.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNb() {
        return nb;
    }

    /**
     * Sets the value of the nb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getNb()
     */
    public void setNb(String value) {
        this.nb = value;
    }

    /**
     * Date associated with the referred document.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRltdDt() {
        return rltdDt;
    }

    /**
     * Sets the value of the rltdDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     * @see #getRltdDt()
     */
    public void setRltdDt(XMLGregorianCalendar value) {
        this.rltdDt = value;
    }

    /**
     * Set of elements used to provide the content of the referred document line.
     * 
     * Gets the value of the lineDtls property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the lineDtls property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getLineDtls().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentLineInformation11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the lineDtls property.
     */
    public List<DocumentLineInformation11> getLineDtls() {
        if (lineDtls == null) {
            lineDtls = new ArrayList<>();
        }
        return this.lineDtls;
    }

}
