
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.*;


/**
 * Further information related to the processing of the payment instruction that may need to be acted upon by the creditor's agent. The instruction may relate to a level of service, or may be an instruction that has to be executed by the creditor's agent, or may be information required by the creditor's agent.
 * 
 * <p>Java class for InstructionForCreditorAgent1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="InstructionForCreditorAgent1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Cd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Instruction3Code" minOccurs="0"/>
 *         <element name="InstrInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax140Text" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstructionForCreditorAgent1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "cd",
    "instrInf"
})
public class InstructionForCreditorAgent11 {

    /**
     * Coded information related to the processing of the payment instruction, provided by the initiating party, and intended for the creditor's agent.
     * 
     */
    @XmlElement(name = "Cd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    @XmlSchemaType(name = "string")
    protected Instruction3Code cd;
    /**
     * Further information complementing the coded instruction or instruction to the creditor's agent that is bilaterally agreed or specific to a user community.
     * 
     */
    @XmlElement(name = "InstrInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String instrInf;

    /**
     * Coded information related to the processing of the payment instruction, provided by the initiating party, and intended for the creditor's agent.
     * 
     * @return
     *     possible object is
     *     {@link Instruction3Code }
     *     
     */
    public Instruction3Code getCd() {
        return cd;
    }

    /**
     * Sets the value of the cd property.
     * 
     * @param value
     *     allowed object is
     *     {@link Instruction3Code }
     *     
     * @see #getCd()
     */
    public void setCd(Instruction3Code value) {
        this.cd = value;
    }

    /**
     * Further information complementing the coded instruction or instruction to the creditor's agent that is bilaterally agreed or specific to a user community.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstrInf() {
        return instrInf;
    }

    /**
     * Sets the value of the instrInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getInstrInf()
     */
    public void setInstrInf(String value) {
        this.instrInf = value;
    }

}
