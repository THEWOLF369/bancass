
package ws.adherent;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour donneesAdherent complex type.
 * 
 * <p>Le fragment de sch�ma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="donneesAdherent">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CAPITAL" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="DUREE" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *         &lt;element name="FRANCHISE" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="IDENTIFIANT" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="NADHESION" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="TAUX_INTER" type="{http://www.w3.org/2001/XMLSchema}double"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "donneesAdherent", propOrder = {
    "capital",
    "duree",
    "franchise",
    "identifiant",
    "nadhesion",
    "tauxinter"
})
public class DonneesAdherent {

    @XmlElement(name = "CAPITAL")
    protected double capital;
    @XmlElement(name = "DUREE")
    protected double duree;
    @XmlElement(name = "FRANCHISE")
    protected String franchise;
    @XmlElement(name = "IDENTIFIANT")
    protected String identifiant;
    @XmlElement(name = "NADHESION")
    protected int nadhesion;
    @XmlElement(name = "TAUX_INTER")
    protected double tauxinter;

    /**
     * Obtient la valeur de la propri�t� capital.
     * 
     */
    public double getCAPITAL() {
        return capital;
    }

    /**
     * D�finit la valeur de la propri�t� capital.
     * 
     */
    public void setCAPITAL(double value) {
        this.capital = value;
    }

    /**
     * Obtient la valeur de la propri�t� duree.
     * 
     */
    public double getDUREE() {
        return duree;
    }

    /**
     * D�finit la valeur de la propri�t� duree.
     * 
     */
    public void setDUREE(double value) {
        this.duree = value;
    }

    /**
     * Obtient la valeur de la propri�t� franchise.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFRANCHISE() {
        return franchise;
    }

    /**
     * D�finit la valeur de la propri�t� franchise.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFRANCHISE(String value) {
        this.franchise = value;
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
     * Obtient la valeur de la propri�t� nadhesion.
     * 
     */
    public int getNADHESION() {
        return nadhesion;
    }

    /**
     * D�finit la valeur de la propri�t� nadhesion.
     * 
     */
    public void setNADHESION(int value) {
        this.nadhesion = value;
    }

    /**
     * Obtient la valeur de la propri�t� tauxinter.
     * 
     */
    public double getTAUXINTER() {
        return tauxinter;
    }

    /**
     * D�finit la valeur de la propri�t� tauxinter.
     * 
     */
    public void setTAUXINTER(double value) {
        this.tauxinter = value;
    }

}
