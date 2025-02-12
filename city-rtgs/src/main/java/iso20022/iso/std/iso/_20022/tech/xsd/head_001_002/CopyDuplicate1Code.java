
package iso20022.iso.std.iso._20022.tech.xsd.head_001_002;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CopyDuplicate1Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="CopyDuplicate1Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="CODU"/&gt;
 *     &lt;enumeration value="COPY"/&gt;
 *     &lt;enumeration value="DUPL"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "CopyDuplicate1Code", namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02")
@XmlEnum
public enum CopyDuplicate1Code {

    CODU,
    COPY,
    DUPL;

    public String value() {
        return name();
    }

    public static CopyDuplicate1Code fromValue(String v) {
        return valueOf(v);
    }

}
