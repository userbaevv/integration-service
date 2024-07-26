//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.3.0 
// See <a href="https://javaee.github.io/jaxb-v2/">https://javaee.github.io/jaxb-v2/</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2023.12.07 at 06:20:21 PM ALMT 
//


package kz.dara.gbdulNewService.generated;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Основной тип, описывающий учредителя-юрлицо
 * 
 * <p>Java class for OrganizationShortType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrganizationShortType"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="BIN" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Country" type="{http://gbdulinfobybin_v2.egp.gbdul.tamur.kz}DirectoryType" minOccurs="0"/&gt;
 *         &lt;element name="RegistrationNumber" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="RegistrationLastDate" type="{http://www.w3.org/2001/XMLSchema}date"/&gt;
 *         &lt;element name="OrganizationNameRu" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="OrganizationNameKz" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="Activity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="Value" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *         &lt;element name="Percent" type="{http://www.w3.org/2001/XMLSchema}double" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrganizationShortType", namespace = "http://gbdulinfobybin_v2.egp.gbdul.tamur.kz", propOrder = {
    "bin",
    "country",
    "registrationNumber",
    "registrationLastDate",
    "organizationNameRu",
    "organizationNameKz",
    "activity",
    "value",
    "percent"
})
public class OrganizationShortType {

    @XmlElement(name = "BIN")
    protected String bin;
    @XmlElement(name = "Country")
    protected DirectoryType country;
    @XmlElement(name = "RegistrationNumber")
    protected String registrationNumber;
    @XmlElement(name = "RegistrationLastDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar registrationLastDate;
    @XmlElement(name = "OrganizationNameRu", required = true)
    protected String organizationNameRu;
    @XmlElement(name = "OrganizationNameKz", required = true)
    protected String organizationNameKz;
    @XmlElement(name = "Activity")
    protected String activity;
    @XmlElement(name = "Value")
    protected Double value;
    @XmlElement(name = "Percent")
    protected Double percent;

    /**
     * Gets the value of the bin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBIN() {
        return bin;
    }

    /**
     * Sets the value of the bin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBIN(String value) {
        this.bin = value;
    }

    /**
     * Gets the value of the country property.
     * 
     * @return
     *     possible object is
     *     {@link DirectoryType }
     *     
     */
    public DirectoryType getCountry() {
        return country;
    }

    /**
     * Sets the value of the country property.
     * 
     * @param value
     *     allowed object is
     *     {@link DirectoryType }
     *     
     */
    public void setCountry(DirectoryType value) {
        this.country = value;
    }

    /**
     * Gets the value of the registrationNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRegistrationNumber() {
        return registrationNumber;
    }

    /**
     * Sets the value of the registrationNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRegistrationNumber(String value) {
        this.registrationNumber = value;
    }

    /**
     * Gets the value of the registrationLastDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getRegistrationLastDate() {
        return registrationLastDate;
    }

    /**
     * Sets the value of the registrationLastDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setRegistrationLastDate(XMLGregorianCalendar value) {
        this.registrationLastDate = value;
    }

    /**
     * Gets the value of the organizationNameRu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationNameRu() {
        return organizationNameRu;
    }

    /**
     * Sets the value of the organizationNameRu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationNameRu(String value) {
        this.organizationNameRu = value;
    }

    /**
     * Gets the value of the organizationNameKz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrganizationNameKz() {
        return organizationNameKz;
    }

    /**
     * Sets the value of the organizationNameKz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrganizationNameKz(String value) {
        this.organizationNameKz = value;
    }

    /**
     * Gets the value of the activity property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getActivity() {
        return activity;
    }

    /**
     * Sets the value of the activity property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setActivity(String value) {
        this.activity = value;
    }

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setValue(Double value) {
        this.value = value;
    }

    /**
     * Gets the value of the percent property.
     * 
     * @return
     *     possible object is
     *     {@link Double }
     *     
     */
    public Double getPercent() {
        return percent;
    }

    /**
     * Sets the value of the percent property.
     * 
     * @param value
     *     allowed object is
     *     {@link Double }
     *     
     */
    public void setPercent(Double value) {
        this.percent = value;
    }

}