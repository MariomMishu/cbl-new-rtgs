
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Further information related to the processing of the payment instruction that may need to be acted upon by an other agent. The instruction may relate to a level of service, or may be an instruction that has to be executed by the creditor's agent, or may be information required by the other agent.
 * 
 * <p>Java class for InstructionForNextAgent1__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="InstructionForNextAgent1__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InstrInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text__3_" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "InstructionForNextAgent1__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "instrInf"
})
public class InstructionForNextAgent11 {

    /**
     * Further information complementing the coded instruction or instruction to the next agent that is bilaterally agreed or specific to a user community.
     * 
     */
    @XmlElement(name = "InstrInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String instrInf;

    /**
     * Further information complementing the coded instruction or instruction to the next agent that is bilaterally agreed or specific to a user community.
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
