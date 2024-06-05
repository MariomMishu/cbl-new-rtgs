
package iso20022.iso.std.iso._20022.tech.xsd.camt_059_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for NotificationStatus3Code.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <pre>
 * &lt;simpleType name="NotificationStatus3Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="RCBD"/&gt;
 *     &lt;enumeration value="RCVD"/&gt;
 *     &lt;enumeration value="NRCD"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 * 
 */
@XmlType(name = "NotificationStatus3Code", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.059.001.08")
@XmlEnum
public enum NotificationStatus3Code {

    RCBD,
    RCVD,
    NRCD;

    public String value() {
        return name();
    }

    public static NotificationStatus3Code fromValue(String v) {
        return valueOf(v);
    }

}
