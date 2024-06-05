
package iso20022.iso.std.iso._20022.tech.xsd.pacs_009_008;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies further instructions concerning the processing
 *                 of a payment instruction, provided by the sending clearing agent to the next agent(s).
 * 
 * <p>Java class for Instruction5Code</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="Instruction5Code">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="PHOB"/>
 *     <enumeration value="TELB"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "Instruction5Code", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.009.001.08")
@XmlEnum
public enum Instruction5Code {


    /**
     * Please advise/contact (ultimate)
     *                         creditor/claimant by phone.
     * 
     */
    PHOB,

    /**
     * Please advise/contact (ultimate)
     *                         creditor/claimant by the most efficient means of telecommunication.
     * 
     */
    TELB;

    public String value() {
        return name();
    }

    public static Instruction5Code fromValue(String v) {
        return valueOf(v);
    }

}
