
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Classe Java pour agriculteur complex type.
 * 
 * <p>Le fragment de schma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="agriculteur">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="addresse" type="{http://services.agricole.saeb.soap.ws.bna.com/}adresse" minOccurs="0"/>
 *         &lt;element name="date_naissance" type="{http://www.w3.org/2001/XMLSchema}dateTime" minOccurs="0"/>
 *         &lt;element name="lib_naissance" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="mail" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="nom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numSeqPers" type="{http://www.w3.org/2001/XMLSchema}long" minOccurs="0"/>
 *         &lt;element name="num_piece" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="num_tel" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="prenom" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="sex" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="type_piece" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="raisonSocialle" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "agriculteur", propOrder = {
    "addresse",
    "dateNaissance",
    "libNaissance",
    "mail",
    "nom",
    "numSeqPers",
    "numPiece",
    "numTel",
    "prenom",
    "sex",
    "raisonsocialle",
    "typePiece"
})
public class Agriculteur {

    protected Adresse addresse;
    @XmlElement(name = "date_naissance")
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar dateNaissance;
    @XmlElement(name = "lib_naissance")
    protected String libNaissance;
    protected String mail;
    protected String nom;
    protected Long numSeqPers;
    @XmlElement(name = "num_piece")
    protected String numPiece;
    @XmlElement(name = "num_tel")
    protected String numTel;
    protected String prenom;
    protected String sex;
    @XmlElement(name = "type_piece")
    protected String typePiece;
    @XmlElement(name = "raisonSocialle")
    protected String raisonsocialle;

    /**
     * 
     * @return
     *     possible object is
     *     {@link Adresse }
     *     
     */
    public Adresse getAddresse() {
        return addresse;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Adresse }
     *     
     */
    public void setAddresse(Adresse value) {
        this.addresse = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDateNaissance() {
        return dateNaissance;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDateNaissance(XMLGregorianCalendar value) {
        this.dateNaissance = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLibNaissance() {
        return libNaissance;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLibNaissance(String value) {
        this.libNaissance = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMail() {
        return mail;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMail(String value) {
        this.mail = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNom() {
        return nom;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNom(String value) {
        this.nom = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getNumSeqPers() {
        return numSeqPers;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setNumSeqPers(Long value) {
        this.numSeqPers = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumPiece() {
        return numPiece;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumPiece(String value) {
        this.numPiece = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumTel() {
        return numTel;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumTel(String value) {
        this.numTel = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPrenom(String value) {
        this.prenom = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSex() {
        return sex;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSex(String value) {
        this.sex = value;
    }

    /**
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTypePiece() {
        return typePiece;
    }

    /**
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTypePiece(String value) {
        this.typePiece = value;
    }

	public String getRaisonsocialle() {
		return raisonsocialle;
	}

	public void setRaisonsocialle(String raisonsocialle) {
		this.raisonsocialle = raisonsocialle;
	}
    
    

}
