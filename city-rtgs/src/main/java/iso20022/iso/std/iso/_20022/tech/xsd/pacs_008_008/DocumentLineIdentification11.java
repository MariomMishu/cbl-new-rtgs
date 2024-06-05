
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Identifies the documents referred to in the remittance information.
 * 
 * <p>Java class for DocumentLineIdentification1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="DocumentLineIdentification1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Tp" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}DocumentLineType1__1" minOccurs="0"/>
 *         <element name="Nb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="RltdDt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ISODate" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentLineIdentification1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "tp",
    "nb",
    "rltdDt"
})
public class DocumentLineIdentification11 {

    /**
     * Specifies the type of referred document line identification.
     * 
     */
    @XmlElement(name = "Tp", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected DocumentLineType11 tp;
    /**
     * Identification of the type specified for the referred document line.
     * 
     */
    @XmlElement(name = "Nb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String nb;
    /**
     * Date associated with the referred document line.
     * 
     */
    @XmlElement(name = "RltdDt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar rltdDt;

    /**
     * Specifies the type of referred document line identification.
     * 
     * @return
     *     possible object is
     *     {@link DocumentLineType11 }
     *     
     */
    public DocumentLineType11 getTp() {
        return tp;
    }

    /**
     * Sets the value of the tp property.
     * 
     * @param value
     *     allowed object is
     *     {@link DocumentLineType11 }
     *     
     * @see #getTp()
     */
    public void setTp(DocumentLineType11 value) {
        this.tp = value;
    }

    /**
     * Identification of the type specified for the referred document line.
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
     * Date associated with the referred document line.
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

}
