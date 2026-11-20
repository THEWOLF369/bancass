
package ws.client;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour donnesClient complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="donnesClient">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DAT_NAISS" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDENTIFIANT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NCOMPTE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NOM_CLIENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="PRENOM_CLIENT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TEL" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="TYPE_DOC" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "donnesClient", propOrder = {
    "datnaiss",
    "identifiant",
    "ncompte",
    "nomclient",
    "prenomclient",
    "tel",
    "typedoc"
})
public class DonnesClient {

    @XmlElement(name = "DAT_NAISS")
    protected String datnaiss;
    @XmlElement(name = "IDENTIFIANT")
    protected String identifiant;
    @XmlElement(name = "NCOMPTE")
    protected String ncompte;
    @XmlElement(name = "NOM_CLIENT")
    protected String nomclient;
    @XmlElement(name = "PRENOM_CLIENT")
    protected String prenomclient;
    @XmlElement(name = "TEL")
    protected String tel;
    @XmlElement(name = "TYPE_DOC")
    protected String typedoc;

    /**
     * Obtient la valeur de la propri�t� datnaiss.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDATNAISS() {
        return datnaiss;
    }

    /**
     * D�finit la valeur de la propri�t� datnaiss.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDATNAISS(String value) {
        this.datnaiss = value;
    }

    /**
     * Obtient la valeur de la propri�t� identifiant.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDENTIFIANT() {
        return identifiant;
    }

    /**
     * D�finit la valeur de la propri�t� identifiant.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDENTIFIANT(String value) {
        this.identifiant = value;
    }

    /**
     * Obtient la valeur de la propri�t� ncompte.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNCOMPTE() {
        return ncompte;
    }

    /**
     * D�finit la valeur de la propri�t� ncompte.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNCOMPTE(String value) {
        this.ncompte = value;
    }

    /**
     * Obtient la valeur de la propri�t� nomclient.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNOMCLIENT() {
        return nomclient;
    }

    /**
     * D�finit la valeur de la propri�t� nomclient.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNOMCLIENT(String value) {
        this.nomclient = value;
    }

    /**
     * Obtient la valeur de la propri�t� prenomclient.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPRENOMCLIENT() {
        return prenomclient;
    }

    /**
     * D�finit la valeur de la propri�t� prenomclient.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPRENOMCLIENT(String value) {
        this.prenomclient = value;
    }

    /**
     * Obtient la valeur de la propri�t� tel.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTEL() {
        return tel;
    }

    /**
     * D�finit la valeur de la propri�t� tel.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTEL(String value) {
        this.tel = value;
    }

    /**
     * Obtient la valeur de la propri�t� typedoc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTYPEDOC() {
        return typedoc;
    }

    /**
     * D�finit la valeur de la propri�t� typedoc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTYPEDOC(String value) {
        this.typedoc = value;
    }

}
