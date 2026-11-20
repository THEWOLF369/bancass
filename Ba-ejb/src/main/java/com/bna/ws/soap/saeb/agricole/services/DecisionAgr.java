
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour decisionAgr complex type.
 * 
 * <p>Le fragment de schma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="decisionAgr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="agriculteur" type="{http://services.agricole.saeb.soap.ws.bna.com/}agriculteur" minOccurs="0"/>
 *         &lt;element name="code_category" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="date_annulation" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="date_decision" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="date_saisie_decision" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="details_decision" type="{http://services.agricole.saeb.soap.ws.bna.com/}detailDecAgri" minOccurs="0"/>
 *         &lt;element name="montant" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="num_compte" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num_decision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num_matricule_user" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num_perm_agricole" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type_annulation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "decisionAgr", propOrder = {
    "agriculteur",
    "codeCategory",
    "dateAnnulation",
    "dateDecision",
    "dateSaisieDecision",
    "detailsDecision",
    "montant",
    "numCompte",
    "numDecision",
    "numMatriculeUser",
    "numPermAgricole",
    "typeAnnulation"
})
public class DecisionAgr {

    protected Agriculteur agriculteur;
    @XmlElement(name = "code_category")
    protected String codeCategory;
    @XmlElement(name = "date_annulation")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateAnnulation;
    @XmlElement(name = "date_decision")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateDecision;
    @XmlElement(name = "date_saisie_decision")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateSaisieDecision;
    @XmlElement(name = "details_decision")
    protected DetailDecAgri detailsDecision;
    protected Long montant;
    @XmlElement(name = "num_compte")
    protected String numCompte;
    @XmlElement(name = "num_decision")
    protected String numDecision;
    @XmlElement(name = "num_matricule_user")
    protected String numMatriculeUser;
    @XmlElement(name = "num_perm_agricole")
    protected String numPermAgricole;
    @XmlElement(name = "type_annulation")
    protected String typeAnnulation;

    /**
     * 
     * @return
     *     possible object is
     *     {@link Agriculteur }
     *     
     */
    public Agriculteur getAgriculteur() {
        return agriculteur;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Agriculteur }
     *     
     */
    public void setAgriculteur(Agriculteur value) {
        this.agriculteur = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCodeCategory() {
        return codeCategory;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCodeCategory(String value) {
        this.codeCategory = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateAnnulation() {
        return dateAnnulation;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateAnnulation(XMLGregorianCalendar value) {
        this.dateAnnulation = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateDecision() {
        return dateDecision;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateDecision(XMLGregorianCalendar value) {
        this.dateDecision = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateSaisieDecision() {
        return dateSaisieDecision;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateSaisieDecision(XMLGregorianCalendar value) {
        this.dateSaisieDecision = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link DetailDecAgri }
     *     
     */
    public DetailDecAgri getDetailsDecision() {
        return detailsDecision;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link DetailDecAgri }
     *     
     */
    public void setDetailsDecision(DetailDecAgri value) {
        this.detailsDecision = value;
    }

    /**
     * 
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
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumCompte() {
        return numCompte;
    }

    /**
     *
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumCompte(String value) {
        this.numCompte = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDecision() {
        return numDecision;
    }

    /**
     *
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDecision(String value) {
        this.numDecision = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumMatriculeUser() {
        return numMatriculeUser;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumMatriculeUser(String value) {
        this.numMatriculeUser = value;
    }

    /**
     * 
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumPermAgricole() {
        return numPermAgricole;
    }

    /**
     * 
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumPermAgricole(String value) {
        this.numPermAgricole = value;
    }

    /**
     *
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypeAnnulation() {
        return typeAnnulation;
    }

    /**
     *
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypeAnnulation(String value) {
        this.typeAnnulation = value;
    }

}
