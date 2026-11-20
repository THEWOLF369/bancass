
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour detailDecAgri complex type.
 * 
 * <p>Le fragment de schma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="detailDecAgri">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="codFngDetagr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="code_produit_speculation" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="date_contrat" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="date_derniere_ech" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="date_premiere_ech" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="duree" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="error" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="montant" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="numDetailAgr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="speculation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="terme" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type_credit" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "detailDecAgri", propOrder = {
    "codFngDetagr",
    "codeProduitSpeculation",
    "dateContrat",
    "dateDerniereEch",
    "datePremiereEch",
    "duree",
    "error",
    "montant",
    "numDetailAgr",
    "speculation",
    "terme",
    "typeCredit"
})
public class DetailDecAgri {

    protected String codFngDetagr;
    @XmlElement(name = "code_produit_speculation")
    protected Long codeProduitSpeculation;
    @XmlElement(name = "date_contrat")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateContrat;
    @XmlElement(name = "date_derniere_ech")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateDerniereEch;
    @XmlElement(name = "date_premiere_ech")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar datePremiereEch;
    protected Long duree;
    protected String error;
    protected Long montant;
    protected String numDetailAgr;
    protected String speculation;
    protected String terme;
    @XmlElement(name = "type_credit")
    protected int typeCredit;

    /**
 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodFngDetagr() {
        return codFngDetagr;
    }

    /**

     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodFngDetagr(String value) {
        this.codFngDetagr = value;
    }

    /**
  
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getCodeProduitSpeculation() {
        return codeProduitSpeculation;
    }

    /**

     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setCodeProduitSpeculation(Long value) {
        this.codeProduitSpeculation = value;
    }

    /**
 
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateContrat() {
        return dateContrat;
    }

    /**
    
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateContrat(XMLGregorianCalendar value) {
        this.dateContrat = value;
    }

    /**
    
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateDerniereEch() {
        return dateDerniereEch;
    }

    /**
 
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateDerniereEch(XMLGregorianCalendar value) {
        this.dateDerniereEch = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDatePremiereEch() {
        return datePremiereEch;
    }

    /**
   
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDatePremiereEch(XMLGregorianCalendar value) {
        this.datePremiereEch = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getDuree() {
        return duree;
    }

    /**
    
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setDuree(Long value) {
        this.duree = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getError() {
        return error;
    }

    /**

     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setError(String value) {
        this.error = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getMontant() {
        return montant;
    }

    /**

     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setMontant(Long value) {
        this.montant = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDetailAgr() {
        return numDetailAgr;
    }

    /**
  
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDetailAgr(String value) {
        this.numDetailAgr = value;
    }

    /**
    
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeculation() {
        return speculation;
    }

    /**

     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeculation(String value) {
        this.speculation = value;
    }

    /**

     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTerme() {
        return terme;
    }

    /**
 
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTerme(String value) {
        this.terme = value;
    }

    /**
  
     * 
     */
    public int getTypeCredit() {
        return typeCredit;
    }

    /**

     * 
     */
    public void setTypeCredit(int value) {
        this.typeCredit = value;
    }

}
