
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Provides document line information.
 * 
 * <p>Java class for DocumentLineInformation1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="DocumentLineInformation1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Id" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}DocumentLineIdentification1__1" maxOccurs="unbounded"/>
 *         <element name="Desc" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="Amt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RemittanceAmount3__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DocumentLineInformation1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "id",
    "desc",
    "amt"
})
public class DocumentLineInformation11 {

    /**
     * Provides identification of the document line.
     * 
     */
    @XmlElement(name = "Id", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected List<DocumentLineIdentification11> id;
    /**
     * Description associated with the document line.
     * 
     */
    @XmlElement(name = "Desc", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String desc;
    /**
     * Provides details on the amounts of the document line.
     * 
     */
    @XmlElement(name = "Amt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected RemittanceAmount31 amt;

    /**
     * Provides identification of the document line.
     * 
     * Gets the value of the id property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the id property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getId().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DocumentLineIdentification11 }
     * </p>
     * 
     * 
     * @return
     *     The value of the id property.
     */
    public List<DocumentLineIdentification11> getId() {
        if (id == null) {
            id = new ArrayList<>();
        }
        return this.id;
    }

    /**
     * Description associated with the document line.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDesc() {
        return desc;
    }

    /**
     * Sets the value of the desc property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDesc()
     */
    public void setDesc(String value) {
        this.desc = value;
    }

    /**
     * Provides details on the amounts of the document line.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceAmount31 }
     *     
     */
    public RemittanceAmount31 getAmt() {
        return amt;
    }

    /**
     * Sets the value of the amt property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceAmount31 }
     *     
     * @see #getAmt()
     */
    public void setAmt(RemittanceAmount31 value) {
        this.amt = value;
    }

}
