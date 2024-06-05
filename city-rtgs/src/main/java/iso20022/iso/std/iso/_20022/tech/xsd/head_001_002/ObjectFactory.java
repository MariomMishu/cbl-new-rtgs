
package iso20022.iso.std.iso._20022.tech.xsd.head_001_002;

import javax.xml.namespace.QName;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the iso20022.iso.std.iso._20022.tech.xsd.head_001_002 package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _AppHdr_QNAME = new QName("urn:iso:std:iso:20022:tech:xsd:head.001.001.02", "AppHdr");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: iso20022.iso.std.iso._20022.tech.xsd.head_001_002
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link BusinessApplicationHeaderV02 }
     * 
     */
    public BusinessApplicationHeaderV02 createBusinessApplicationHeaderV02() {
        return new BusinessApplicationHeaderV02();
    }

    /**
     * Create an instance of {@link BranchAndFinancialInstitutionIdentification65 }
     * 
     */
    public BranchAndFinancialInstitutionIdentification65 createBranchAndFinancialInstitutionIdentification65() {
        return new BranchAndFinancialInstitutionIdentification65();
    }

    /**
     * Create an instance of {@link BusinessApplicationHeader51 }
     * 
     */
    public BusinessApplicationHeader51 createBusinessApplicationHeader51() {
        return new BusinessApplicationHeader51();
    }

    /**
     * Create an instance of {@link ClearingSystemIdentification2Choice1 }
     * 
     */
    public ClearingSystemIdentification2Choice1 createClearingSystemIdentification2Choice1() {
        return new ClearingSystemIdentification2Choice1();
    }

    /**
     * Create an instance of {@link ClearingSystemMemberIdentification22 }
     * 
     */
    public ClearingSystemMemberIdentification22 createClearingSystemMemberIdentification22() {
        return new ClearingSystemMemberIdentification22();
    }

    /**
     * Create an instance of {@link FinancialInstitutionIdentification184 }
     * 
     */
    public FinancialInstitutionIdentification184 createFinancialInstitutionIdentification184() {
        return new FinancialInstitutionIdentification184();
    }

    /**
     * Create an instance of {@link ImplementationSpecification1 }
     * 
     */
    public ImplementationSpecification1 createImplementationSpecification1() {
        return new ImplementationSpecification1();
    }

    /**
     * Create an instance of {@link Party44Choice1 }
     * 
     */
    public Party44Choice1 createParty44Choice1() {
        return new Party44Choice1();
    }

    /**
     * Create an instance of {@link SignatureEnvelope }
     * 
     */
    public SignatureEnvelope createSignatureEnvelope() {
        return new SignatureEnvelope();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link BusinessApplicationHeaderV02 }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link BusinessApplicationHeaderV02 }{@code >}
     */
    @XmlElementDecl(namespace = "urn:iso:std:iso:20022:tech:xsd:head.001.001.02", name = "AppHdr")
    public JAXBElement<BusinessApplicationHeaderV02> createAppHdr(BusinessApplicationHeaderV02 value) {
        return new JAXBElement<BusinessApplicationHeaderV02>(_AppHdr_QNAME, BusinessApplicationHeaderV02 .class, null, value);
    }

}
