
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies the method used to settle the credit transfer instruction.
 * 
 * <p>Java class for SettlementMethod1Code__1</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="SettlementMethod1Code__1">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="CLRG"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "SettlementMethod1Code__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
@XmlEnum
public enum SettlementMethod1Code1 {


    /**
     * Settlement is done through a payment clearing system.
     * 
     */
    CLRG;

    public String value() {
        return name();
    }

    public static SettlementMethod1Code1 fromValue(String v) {
        return valueOf(v);
    }

}
