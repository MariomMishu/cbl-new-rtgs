
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies if this document is a copy, a duplicate, or a duplicate of a copy.
 * 
 * <p>Java class for CopyDuplicate1Code__1</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="CopyDuplicate1Code__1">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="DUPL"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "CopyDuplicate1Code__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
@XmlEnum
public enum CopyDuplicate1Code1 {


    /**
     * Message is for information/confirmation purposes. It is a duplicate of a message previously sent.
     * 
     */
    DUPL;

    public String value() {
        return name();
    }

    public static CopyDuplicate1Code1 fromValue(String v) {
        return valueOf(v);
    }

}
