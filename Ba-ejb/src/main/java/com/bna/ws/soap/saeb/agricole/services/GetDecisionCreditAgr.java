
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getDecisionCreditAgr complex type.
 * 
 * <p>Le fragment de schma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getDecisionCreditAgr">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="numDecision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="numDetailDecision" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDecisionCreditAgr", propOrder = {
    "numDecision",
    "numDetailDecision"
})
public class GetDecisionCreditAgr {

    protected String numDecision;
    protected String numDetailDecision;

    /**

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
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumDetailDecision() {
        return numDetailDecision;
    }

    /**
     
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumDetailDecision(String value) {
        this.numDetailDecision = value;
    }

}
