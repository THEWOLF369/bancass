
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Classe Java pour getDecisionCreditAgrResponse complex type.
 * 
 * <p>Le fragment de schma suivant indique le contenu attendu figurant dans cette classe.
 * 
 * <pre>
 * &lt;complexType name="getDecisionCreditAgrResponse">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="return" type="{http://services.agricole.saeb.soap.ws.bna.com/}decisionAgr" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "getDecisionCreditAgrResponse", propOrder = {
    "_return"
})
public class GetDecisionCreditAgrResponse {

    @XmlElement(name = "return")
    protected DecisionAgr _return;

    /**

     * 
     * @return
     *     possible object is
     *     {@link DecisionAgr }
     *     
     */
    public DecisionAgr getReturn() {
        return _return;
    }

    /**
    
     * 
     * @param value
     *     allowed object is
     *     {@link DecisionAgr }
     *     
     */
    public void setReturn(DecisionAgr value) {
        this._return = value;
    }

}
