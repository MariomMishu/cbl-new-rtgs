
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.*;


/**
 * Provides further details on the settlement of the
 *                 instruction.
 * 
 * <p>Java class for SettlementInstruction7__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="SettlementInstruction7__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="SttlmMtd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}SettlementMethod1Code__1"/>
 *         <element name="ClrSys" type="{urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08}ClearingSystemIdentification3Choice__1"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SettlementInstruction7__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", propOrder = {
    "sttlmMtd",
    "clrSys"
})
public class SettlementInstruction71 {

    /**
     * Method used to settle the (batch of) payment
     *                         instructions.
     * 
     */
    @XmlElement(name = "SttlmMtd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    @XmlSchemaType(name = "string")
    protected SettlementMethod1Code1 sttlmMtd;
    /**
     * Specification of a pre-agreed offering between
     *                         clearing agents or the channel through which the payment instruction is processed.
     * 
     */
    @XmlElement(name = "ClrSys", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08", required = true)
    protected ClearingSystemIdentification3Choice1 clrSys;

    /**
     * Method used to settle the (batch of) payment
     *                         instructions.
     * 
     * @return
     *     possible object is
     *     {@link SettlementMethod1Code1 }
     *     
     */
    public SettlementMethod1Code1 getSttlmMtd() {
        return sttlmMtd;
    }

    /**
     * Sets the value of the sttlmMtd property.
     * 
     * @param value
     *     allowed object is
     *     {@link SettlementMethod1Code1 }
     *     
     * @see #getSttlmMtd()
     */
    public void setSttlmMtd(SettlementMethod1Code1 value) {
        this.sttlmMtd = value;
    }

    /**
     * Specification of a pre-agreed offering between
     *                         clearing agents or the channel through which the payment instruction is processed.
     * 
     * @return
     *     possible object is
     *     {@link ClearingSystemIdentification3Choice1 }
     *     
     */
    public ClearingSystemIdentification3Choice1 getClrSys() {
        return clrSys;
    }

    /**
     * Sets the value of the clrSys property.
     * 
     * @param value
     *     allowed object is
     *     {@link ClearingSystemIdentification3Choice1 }
     *     
     * @see #getClrSys()
     */
    public void setClrSys(ClearingSystemIdentification3Choice1 value) {
        this.clrSys = value;
    }

}
