//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.03.21 at 01:21:52 AM BDT 
//


package iso20022.swift.xsd.camt_019_001;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for RequestType2Code.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="RequestType2Code"&gt;
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *     &lt;enumeration value="RT11"/&gt;
 *     &lt;enumeration value="RT12"/&gt;
 *     &lt;enumeration value="RT13"/&gt;
 *     &lt;enumeration value="RT14"/&gt;
 *     &lt;enumeration value="RT15"/&gt;
 *   &lt;/restriction&gt;
 * &lt;/simpleType&gt;
 * </pre>
 */
@XmlType(name = "RequestType2Code")
@XmlEnum
public enum RequestType2Code {

    @XmlEnumValue("RT11")
    RT_11("RT11"),
    @XmlEnumValue("RT12")
    RT_12("RT12"),
    @XmlEnumValue("RT13")
    RT_13("RT13"),
    @XmlEnumValue("RT14")
    RT_14("RT14"),
    @XmlEnumValue("RT15")
    RT_15("RT15");
    private final String value;

    RequestType2Code(String v) {
        value = v;
    }

    public static RequestType2Code fromValue(String v) {
        for (RequestType2Code c : RequestType2Code.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

    public String value() {
        return value;
    }

}
