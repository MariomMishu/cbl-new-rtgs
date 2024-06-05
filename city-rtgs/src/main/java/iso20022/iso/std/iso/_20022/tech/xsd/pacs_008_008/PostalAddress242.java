
package iso20022.iso.std.iso._20022.tech.xsd.pacs_008_008;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Information that locates and identifies a specific address, as defined by postal services.
 * 
 * <p>Java class for PostalAddress24__2 complex type</p>.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.</p>
 * 
 * <pre>{@code
 * <complexType name="PostalAddress24__2">
 *   <complexContent>
 *     <restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       <sequence>
 *         <element name="Dept" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax70Text_Extended" minOccurs="0"/>
 *         <element name="SubDept" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax70Text_Extended" minOccurs="0"/>
 *         <element name="StrtNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax70Text_Extended" minOccurs="0"/>
 *         <element name="BldgNb" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax16Text_Extended" minOccurs="0"/>
 *         <element name="BldgNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="Flr" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax70Text_Extended" minOccurs="0"/>
 *         <element name="PstBx" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax16Text_Extended" minOccurs="0"/>
 *         <element name="Room" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax70Text_Extended" minOccurs="0"/>
 *         <element name="PstCd" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax16Text_Extended" minOccurs="0"/>
 *         <element name="TwnNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended"/>
 *         <element name="TwnLctnNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="DstrctNm" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="CtrySubDvsn" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}HVPSPlus_RestrictedFINXMax35Text_Extended" minOccurs="0"/>
 *         <element name="Ctry" type="{urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08}CountryCode"/>
 *       </sequence>
 *     </restriction>
 *   </complexContent>
 * </complexType>
 * }</pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostalAddress24__2", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", propOrder = {
    "dept",
    "subDept",
    "strtNm",
    "bldgNb",
    "bldgNm",
    "flr",
    "pstBx",
    "room",
    "pstCd",
    "twnNm",
    "twnLctnNm",
    "dstrctNm",
    "ctrySubDvsn",
    "ctry"
})
public class PostalAddress242 {

    /**
     * Identification of a division of a large organisation or building.
     * 
     */
    @XmlElement(name = "Dept", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String dept;
    /**
     * Identification of a sub-division of a large organisation or building.
     * 
     */
    @XmlElement(name = "SubDept", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String subDept;
    /**
     * Name of a street or thoroughfare.
     * 
     */
    @XmlElement(name = "StrtNm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String strtNm;
    /**
     * Number that identifies the position of a building on a street.
     * 
     */
    @XmlElement(name = "BldgNb", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String bldgNb;
    /**
     * Name of the building or house.
     * 
     */
    @XmlElement(name = "BldgNm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String bldgNm;
    /**
     * Floor or storey within a building.
     * 
     */
    @XmlElement(name = "Flr", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String flr;
    /**
     * Numbered box in a post office, assigned to a person or organisation, where letters are kept until called for.
     * 
     */
    @XmlElement(name = "PstBx", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String pstBx;
    /**
     * Building room number.
     * 
     */
    @XmlElement(name = "Room", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String room;
    /**
     * Identifier consisting of a group of letters and/or numbers that is added to a postal address to assist the sorting of mail.
     * 
     */
    @XmlElement(name = "PstCd", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String pstCd;
    /**
     * Name of a built-up area, with defined boundaries, and a local government.
     * 
     */
    @XmlElement(name = "TwnNm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected String twnNm;
    /**
     * Specific location name within the town.
     * 
     */
    @XmlElement(name = "TwnLctnNm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String twnLctnNm;
    /**
     * Identifies a subdivision within a country sub-division.
     * 
     */
    @XmlElement(name = "DstrctNm", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String dstrctNm;
    /**
     * Identifies a subdivision of a country such as state, region, county.
     * 
     */
    @XmlElement(name = "CtrySubDvsn", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08")
    protected String ctrySubDvsn;
    /**
     * Nation with its own government.
     * 
     */
    @XmlElement(name = "Ctry", namespace = "urn:iso:std:iso:20022:tech:xsd:pacs.008.001.08", required = true)
    protected String ctry;

    /**
     * Identification of a division of a large organisation or building.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDept() {
        return dept;
    }

    /**
     * Sets the value of the dept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDept()
     */
    public void setDept(String value) {
        this.dept = value;
    }

    /**
     * Identification of a sub-division of a large organisation or building.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubDept() {
        return subDept;
    }

    /**
     * Sets the value of the subDept property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getSubDept()
     */
    public void setSubDept(String value) {
        this.subDept = value;
    }

    /**
     * Name of a street or thoroughfare.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStrtNm() {
        return strtNm;
    }

    /**
     * Sets the value of the strtNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getStrtNm()
     */
    public void setStrtNm(String value) {
        this.strtNm = value;
    }

    /**
     * Number that identifies the position of a building on a street.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBldgNb() {
        return bldgNb;
    }

    /**
     * Sets the value of the bldgNb property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBldgNb()
     */
    public void setBldgNb(String value) {
        this.bldgNb = value;
    }

    /**
     * Name of the building or house.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBldgNm() {
        return bldgNm;
    }

    /**
     * Sets the value of the bldgNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getBldgNm()
     */
    public void setBldgNm(String value) {
        this.bldgNm = value;
    }

    /**
     * Floor or storey within a building.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlr() {
        return flr;
    }

    /**
     * Sets the value of the flr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getFlr()
     */
    public void setFlr(String value) {
        this.flr = value;
    }

    /**
     * Numbered box in a post office, assigned to a person or organisation, where letters are kept until called for.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPstBx() {
        return pstBx;
    }

    /**
     * Sets the value of the pstBx property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPstBx()
     */
    public void setPstBx(String value) {
        this.pstBx = value;
    }

    /**
     * Building room number.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoom() {
        return room;
    }

    /**
     * Sets the value of the room property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getRoom()
     */
    public void setRoom(String value) {
        this.room = value;
    }

    /**
     * Identifier consisting of a group of letters and/or numbers that is added to a postal address to assist the sorting of mail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPstCd() {
        return pstCd;
    }

    /**
     * Sets the value of the pstCd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getPstCd()
     */
    public void setPstCd(String value) {
        this.pstCd = value;
    }

    /**
     * Name of a built-up area, with defined boundaries, and a local government.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwnNm() {
        return twnNm;
    }

    /**
     * Sets the value of the twnNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTwnNm()
     */
    public void setTwnNm(String value) {
        this.twnNm = value;
    }

    /**
     * Specific location name within the town.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTwnLctnNm() {
        return twnLctnNm;
    }

    /**
     * Sets the value of the twnLctnNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getTwnLctnNm()
     */
    public void setTwnLctnNm(String value) {
        this.twnLctnNm = value;
    }

    /**
     * Identifies a subdivision within a country sub-division.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDstrctNm() {
        return dstrctNm;
    }

    /**
     * Sets the value of the dstrctNm property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getDstrctNm()
     */
    public void setDstrctNm(String value) {
        this.dstrctNm = value;
    }

    /**
     * Identifies a subdivision of a country such as state, region, county.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtrySubDvsn() {
        return ctrySubDvsn;
    }

    /**
     * Sets the value of the ctrySubDvsn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCtrySubDvsn()
     */
    public void setCtrySubDvsn(String value) {
        this.ctrySubDvsn = value;
    }

    /**
     * Nation with its own government.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCtry() {
        return ctry;
    }

    /**
     * Sets the value of the ctry property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     * @see #getCtry()
     */
    public void setCtry(String value) {
        this.ctry = value;
    }

}
