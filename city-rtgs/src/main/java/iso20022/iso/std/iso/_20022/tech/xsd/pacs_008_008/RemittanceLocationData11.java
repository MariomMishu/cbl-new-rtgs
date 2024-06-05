
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;


/**
 * Provides additional details on the remittance advice.
 * 
 * <p>Java class for RemittanceLocationData1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="RemittanceLocationData1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Mtd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RemittanceLocationMethod2Code"/>
 *         <element name="ElctrncAdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax2048Text_Extended" minOccurs="0"/>
 *         <element name="PstlAdr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}NameAndAddress16__1" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RemittanceLocationData1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "mtd",
    "elctrncAdr",
    "pstlAdr"
})
public class RemittanceLocationData11 {

    /**
     * Method used to deliver the remittance advice information.
     * 
     */
    @XmlElement(name = "Mtd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    @XmlSchemaType(name = "string")
    protected RemittanceLocationMethod2Code mtd;
    /**
     * Electronic address to which an agent is to send the remittance information.
     * 
     */
    @XmlElement(name = "ElctrncAdr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String elctrncAdr;
    /**
     * Postal address to which an agent is to send the remittance information.
     * 
     */
    @XmlElement(name = "PstlAdr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected NameAndAddress161 pstlAdr;

    /**
     * Method used to deliver the remittance advice information.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceLocationMethod2Code }
     *     
     */
    public RemittanceLocationMethod2Code getMtd() {
        return mtd;
    }

    /**
     * Sets the value of the mtd property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceLocationMethod2Code }
     *     
     * @see #getMtd()
     */
    public void setMtd(RemittanceLocationMethod2Code value) {
        this.mtd = value;
    }

    /**
     * Electronic address to which an agent is to send the remittance information.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getElctrncAdr() {
        return elctrncAdr;
    }

    /**
     * Sets the value of the elctrncAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getElctrncAdr()
     */
    public void setElctrncAdr(String value) {
        this.elctrncAdr = value;
    }

    /**
     * Postal address to which an agent is to send the remittance information.
     * 
     * @return
     *     possible object is
     *     {@link NameAndAddress161 }
     *     
     */
    public NameAndAddress161 getPstlAdr() {
        return pstlAdr;
    }

    /**
     * Sets the value of the pstlAdr property.
     * 
     * @param value
     *     allowed object is
     *     {@link NameAndAddress161 }
     *     
     * @see #getPstlAdr()
     */
    public void setPstlAdr(NameAndAddress161 value) {
        this.pstlAdr = value;
    }

}
