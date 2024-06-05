
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * Information supplied to enable the matching/reconciliation of an entry with the items that the payment is intended to settle, such as commercial invoices in an accounts' receivable system, in a structured form.
 * 
 * <p>Java class for StructuredRemittanceInformation16__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="StructuredRemittanceInformation16__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="RfrdDocInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}ReferredDocumentInformation7__1" maxOccurs="unbounded" minOccurs="0"/>
 *         <element name="RfrdDocAmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}RemittanceAmount2__1" minOccurs="0"/>
 *         <element name="CdtrRefInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CreditorReferenceInformation2__1" minOccurs="0"/>
 *         <element name="Invcr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PartyIdentification135__5" minOccurs="0"/>
 *         <element name="Invcee" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}PartyIdentification135__5" minOccurs="0"/>
 *         <element name="TaxRmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}TaxInformation7__1" minOccurs="0"/>
 *         <element name="GrnshmtRmt" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}Garnishment3__1" minOccurs="0"/>
 *         <element name="AddtlRmtInf" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax140Text_Extended" maxOccurs="3" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StructuredRemittanceInformation16__1", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "rfrdDocInf",
    "rfrdDocAmt",
    "cdtrRefInf",
    "invcr",
    "invcee",
    "taxRmt",
    "grnshmtRmt",
    "addtlRmtInf"
})
public class StructuredRemittanceInformation161 {

    /**
     * Provides the identification and the content of the referred document.
     * 
     */
    @XmlElement(name = "RfrdDocInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<ReferredDocumentInformation71> rfrdDocInf;
    /**
     * Provides details on the amounts of the referred document.
     * 
     */
    @XmlElement(name = "RfrdDocAmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected RemittanceAmount21 rfrdDocAmt;
    /**
     * Reference information provided by the creditor to allow the identification of the underlying documents.
     * 
     */
    @XmlElement(name = "CdtrRefInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected CreditorReferenceInformation21 cdtrRefInf;
    /**
     * Identification of the organisation issuing the invoice, when it is different from the creditor or ultimate creditor.
     * 
     */
    @XmlElement(name = "Invcr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected PartyIdentification1355 invcr;
    /**
     * Identification of the party to whom an invoice is issued, when it is different from the debtor or ultimate debtor.
     * 
     */
    @XmlElement(name = "Invcee", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected PartyIdentification1355 invcee;
    /**
     * Provides remittance information about a payment made for tax-related purposes.
     * 
     */
    @XmlElement(name = "TaxRmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected TaxInformation71 taxRmt;
    /**
     * Provides remittance information about a payment for garnishment-related purposes.
     * 
     */
    @XmlElement(name = "GrnshmtRmt", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected Garnishment31 grnshmtRmt;
    /**
     * Additional information, in free text form, to complement the structured remittance information.
     * 
     */
    @XmlElement(name = "AddtlRmtInf", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected List<String> addtlRmtInf;

    /**
     * Provides the identification and the content of the referred document.
     * 
     * Gets the value of the rfrdDocInf property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rfrdDocInf property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getRfrdDocInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ReferredDocumentInformation71 }
     * </p>
     * 
     * 
     * @return
     *     The value of the rfrdDocInf property.
     */
    public List<ReferredDocumentInformation71> getRfrdDocInf() {
        if (rfrdDocInf == null) {
            rfrdDocInf = new ArrayList<>();
        }
        return this.rfrdDocInf;
    }

    /**
     * Provides details on the amounts of the referred document.
     * 
     * @return
     *     possible object is
     *     {@link RemittanceAmount21 }
     *     
     */
    public RemittanceAmount21 getRfrdDocAmt() {
        return rfrdDocAmt;
    }

    /**
     * Sets the value of the rfrdDocAmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link RemittanceAmount21 }
     *     
     * @see #getRfrdDocAmt()
     */
    public void setRfrdDocAmt(RemittanceAmount21 value) {
        this.rfrdDocAmt = value;
    }

    /**
     * Reference information provided by the creditor to allow the identification of the underlying documents.
     * 
     * @return
     *     possible object is
     *     {@link CreditorReferenceInformation21 }
     *     
     */
    public CreditorReferenceInformation21 getCdtrRefInf() {
        return cdtrRefInf;
    }

    /**
     * Sets the value of the cdtrRefInf property.
     * 
     * @param value
     *     allowed object is
     *     {@link CreditorReferenceInformation21 }
     *     
     * @see #getCdtrRefInf()
     */
    public void setCdtrRefInf(CreditorReferenceInformation21 value) {
        this.cdtrRefInf = value;
    }

    /**
     * Identification of the organisation issuing the invoice, when it is different from the creditor or ultimate creditor.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1355 }
     *     
     */
    public PartyIdentification1355 getInvcr() {
        return invcr;
    }

    /**
     * Sets the value of the invcr property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1355 }
     *     
     * @see #getInvcr()
     */
    public void setInvcr(PartyIdentification1355 value) {
        this.invcr = value;
    }

    /**
     * Identification of the party to whom an invoice is issued, when it is different from the debtor or ultimate debtor.
     * 
     * @return
     *     possible object is
     *     {@link PartyIdentification1355 }
     *     
     */
    public PartyIdentification1355 getInvcee() {
        return invcee;
    }

    /**
     * Sets the value of the invcee property.
     * 
     * @param value
     *     allowed object is
     *     {@link PartyIdentification1355 }
     *     
     * @see #getInvcee()
     */
    public void setInvcee(PartyIdentification1355 value) {
        this.invcee = value;
    }

    /**
     * Provides remittance information about a payment made for tax-related purposes.
     * 
     * @return
     *     possible object is
     *     {@link TaxInformation71 }
     *     
     */
    public TaxInformation71 getTaxRmt() {
        return taxRmt;
    }

    /**
     * Sets the value of the taxRmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link TaxInformation71 }
     *     
     * @see #getTaxRmt()
     */
    public void setTaxRmt(TaxInformation71 value) {
        this.taxRmt = value;
    }

    /**
     * Provides remittance information about a payment for garnishment-related purposes.
     * 
     * @return
     *     possible object is
     *     {@link Garnishment31 }
     *     
     */
    public Garnishment31 getGrnshmtRmt() {
        return grnshmtRmt;
    }

    /**
     * Sets the value of the grnshmtRmt property.
     * 
     * @param value
     *     allowed object is
     *     {@link Garnishment31 }
     *     
     * @see #getGrnshmtRmt()
     */
    public void setGrnshmtRmt(Garnishment31 value) {
        this.grnshmtRmt = value;
    }

    /**
     * Additional information, in free text form, to complement the structured remittance information.
     * 
     * Gets the value of the addtlRmtInf property.
     * 
     * <p>This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the addtlRmtInf property.</p>
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * </p>
     * <pre>
     * getAddtlRmtInf().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link String }
     * </p>
     * 
     * 
     * @return
     *     The value of the addtlRmtInf property.
     */
    public List<String> getAddtlRmtInf() {
        if (addtlRmtInf == null) {
            addtlRmtInf = new ArrayList<>();
        }
        return this.addtlRmtInf;
    }

}
