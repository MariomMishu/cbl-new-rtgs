
package iso20022.iso.std.iso._20022.tech.xsd.camt_054_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Provides further details on the agents specific to the individual transaction.
 * 
 * <p>Java class for TransactionAgents5__1 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="TransactionAgents5__1">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="InstgAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="InstdAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="DbtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="CdtrAgt" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__3" minOccurs="0"/>
 *         <element name="IntrmyAgt1" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__1" minOccurs="0"/>
 *         <element name="IntrmyAgt2" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__4" minOccurs="0"/>
 *         <element name="IntrmyAgt3" type="{urn:iso:std:iso:20022:tech:xsd:camt.054.001.08}BranchAndFinancialInstitutionIdentification6__5" minOccurs="0"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TransactionAgents5__1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08", propOrder = {
    "instgAgt",
    "instdAgt",
    "dbtrAgt",
    "cdtrAgt",
    "intrmyAgt1",
    "intrmyAgt2",
    "intrmyAgt3"
})
public class TransactionAgents51 {

    /**
     * Agent that instructs the next party in the chain to carry out the (set of) instruction(s).
     * 
     */
    @XmlElement(name = "InstgAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification61 instgAgt;
    /**
     * Agent that is instructed by the previous party in the chain to carry out the (set of) instruction(s).
     * 
     */
    @XmlElement(name = "InstdAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification61 instdAgt;
    /**
     * Financial institution servicing an account for the debtor.
     * 
     */
    @XmlElement(name = "DbtrAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification61 dbtrAgt;
    /**
     * Financial institution servicing an account for the creditor.
     * 
     */
    @XmlElement(name = "CdtrAgt", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification63 cdtrAgt;
    /**
     * Agent between the debtor's agent and the creditor's agent.
     * 
     *                         Usage: If more than one intermediary agent is present, then IntermediaryAgent1 identifies the agent between the DebtorAgent and the IntermediaryAgent2.
     * 
     */
    @XmlElement(name = "IntrmyAgt1", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification61 intrmyAgt1;
    /**
     * Agent between the debtor's agent and the creditor's agent.
     * 
     *                         Usage: If more than two intermediary agents are present, then IntermediaryAgent2 identifies the agent between the IntermediaryAgent1 and the IntermediaryAgent3.
     * 
     */
    @XmlElement(name = "IntrmyAgt2", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification64 intrmyAgt2;
    /**
     * Agent between the debtor's agent and the creditor's agent.
     * 
     *                         Usage: If IntermediaryAgent3 is present, then it identifies the agent between the IntermediaryAgent 2 and the CreditorAgent.
     * 
     */
    @XmlElement(name = "IntrmyAgt3", namespace = "urn:iso:std:iso:20022:tech:xsd:camt.054.001.08")
    protected BranchAndFinancialInstitutionIdentification65 intrmyAgt3;

    /**
     * Agent that instructs the next party in the chain to carry out the (set of) instruction(s).
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getInstgAgt() {
        return instgAgt;
    }

    /**
     * Sets the value of the instgAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getInstgAgt()
     */
    public void setInstgAgt(BranchAndFinancialInstitutionIdentification61 value) {
        this.instgAgt = value;
    }

    /**
     * Agent that is instructed by the previous party in the chain to carry out the (set of) instruction(s).
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getInstdAgt() {
        return instdAgt;
    }

    /**
     * Sets the value of the instdAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getInstdAgt()
     */
    public void setInstdAgt(BranchAndFinancialInstitutionIdentification61 value) {
        this.instdAgt = value;
    }

    /**
     * Financial institution servicing an account for the debtor.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getDbtrAgt() {
        return dbtrAgt;
    }

    /**
     * Sets the value of the dbtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getDbtrAgt()
     */
    public void setDbtrAgt(BranchAndFinancialInstitutionIdentification61 value) {
        this.dbtrAgt = value;
    }

    /**
     * Financial institution servicing an account for the creditor.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification63 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification63 getCdtrAgt() {
        return cdtrAgt;
    }

    /**
     * Sets the value of the cdtrAgt property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification63 }
     *     
     * @see #getCdtrAgt()
     */
    public void setCdtrAgt(BranchAndFinancialInstitutionIdentification63 value) {
        this.cdtrAgt = value;
    }

    /**
     * Agent between the debtor's agent and the creditor's agent.
     * 
     *                         Usage: If more than one intermediary agent is present, then IntermediaryAgent1 identifies the agent between the DebtorAgent and the IntermediaryAgent2.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification61 getIntrmyAgt1() {
        return intrmyAgt1;
    }

    /**
     * Sets the value of the intrmyAgt1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification61 }
     *     
     * @see #getIntrmyAgt1()
     */
    public void setIntrmyAgt1(BranchAndFinancialInstitutionIdentification61 value) {
        this.intrmyAgt1 = value;
    }

    /**
     * Agent between the debtor's agent and the creditor's agent.
     * 
     *                         Usage: If more than two intermediary agents are present, then IntermediaryAgent2 identifies the agent between the IntermediaryAgent1 and the IntermediaryAgent3.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification64 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification64 getIntrmyAgt2() {
        return intrmyAgt2;
    }

    /**
     * Sets the value of the intrmyAgt2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification64 }
     *     
     * @see #getIntrmyAgt2()
     */
    public void setIntrmyAgt2(BranchAndFinancialInstitutionIdentification64 value) {
        this.intrmyAgt2 = value;
    }

    /**
     * Agent between the debtor's agent and the creditor's agent.
     * 
     *                         Usage: If IntermediaryAgent3 is present, then it identifies the agent between the IntermediaryAgent 2 and the CreditorAgent.
     * 
     * @return
     *     possible object is
     *     {@link BranchAndFinancialInstitutionIdentification65 }
     *     
     */
    public BranchAndFinancialInstitutionIdentification65 getIntrmyAgt3() {
        return intrmyAgt3;
    }

    /**
     * Sets the value of the intrmyAgt3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link BranchAndFinancialInstitutionIdentification65 }
     *     
     * @see #getIntrmyAgt3()
     */
    public void setIntrmyAgt3(BranchAndFinancialInstitutionIdentification65 value) {
        this.intrmyAgt3 = value;
    }

}
