
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlType;


/**
 * Specifies a type of financial or commercial document.
 * 
 * <p>Java class for DocumentType3Code</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * <pre>{@code
 * <simpleType name="DocumentType3Code">
 *   <restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     <enumeration value="RADM"/>
 *     <enumeration value="RPIN"/>
 *     <enumeration value="FXDR"/>
 *     <enumeration value="DISP"/>
 *     <enumeration value="PUOR"/>
 *     <enumeration value="SCOR"/>
 *   </restriction>
 * </simpleType>
 * }</pre>
 * 
 */
@XmlType(name = "DocumentType3Code", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
@XmlEnum
public enum DocumentType3Code {


    /**
     * Document is a remittance advice sent separately from the current transaction.
     * 
     */
    RADM,

    /**
     * Document is a linked payment instruction to which the current payment instruction is related, for example, in a cover scenario.
     * 
     */
    RPIN,

    /**
     * Document is a pre-agreed or pre-arranged foreign exchange transaction to which the payment transaction refers.
     * 
     */
    FXDR,

    /**
     * Document is a dispatch advice.
     * 
     */
    DISP,

    /**
     * Document is a purchase order.
     * 
     */
    PUOR,

    /**
     * Document is a structured communication reference provided by the creditor to identify the referred transaction.
     * 
     */
    SCOR;

    public String value() {
        return name();
    }

    public static DocumentType3Code fromValue(String v) {
        return valueOf(v);
    }

}
