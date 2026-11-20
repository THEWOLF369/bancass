
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour adresse complex type.
 * 
 * <p>Le furant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="adresse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="cite" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code_postale" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="gouvernerat" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="immeuble" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="pays" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="rue" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ville" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adresse", propOrder = {
    "cite",
    "codePostale",
    "gouvernerat",
    "immeuble",
    "pays",
    "rue",
    "ville"
})
public class Adresse {

    protected String cite;
    @XmlElement(name = "code_postale")
    protected String codePostale;
    protected int gouvernerat;
    protected String immeuble;
    protected String pays;
    protected String rue;
    protected String ville;

    /**

     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCite() {
        return cite;
    }

    /**

     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCite(String value) {
        this.cite = value;
    }

    /**
  
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodePostale() {
        return codePostale;
    }

    /**
    
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodePostale(String value) {
        this.codePostale = value;
    }

    /**

     * 
     */
    public int getGouvernerat() {
        return gouvernerat;
    }

    /**
 
     * 
     */
    public void setGouvernerat(int value) {
        this.gouvernerat = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImmeuble() {
        return immeuble;
    }

    /**
    
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImmeuble(String value) {
        this.immeuble = value;
    }

    /**
     
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPays() {
        return pays;
    }

    /**
    
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPays(String value) {
        this.pays = value;
    }

    /**
     
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRue() {
        return rue;
    }

    /**
    
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRue(String value) {
        this.rue = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVille() {
        return ville;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVille(String value) {
        this.ville = value;
    }

}
