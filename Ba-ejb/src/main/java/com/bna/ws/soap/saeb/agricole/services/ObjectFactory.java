
package com.bna.ws.soap.saeb.agricole.services;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.bna.ws.soap.saeb.agricole.services package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _GetDecisionCreditAgrResponse_QNAME = new QName("http://services.agricole.saeb.soap.ws.bna.com/", "getDecisionCreditAgrResponse");
    private final static QName _GetDecisionCreditAgr_QNAME = new QName("http://services.agricole.saeb.soap.ws.bna.com/", "getDecisionCreditAgr");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.bna.ws.soap.saeb.agricole.services
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link GetDecisionCreditAgrResponse }
     * 
     */
    public GetDecisionCreditAgrResponse createGetDecisionCreditAgrResponse() {
        return new GetDecisionCreditAgrResponse();
    }

    /**
     * Create an instance of {@link GetDecisionCreditAgr }
     * 
     */
    public GetDecisionCreditAgr createGetDecisionCreditAgr() {
        return new GetDecisionCreditAgr();
    }

    /**
     * Create an instance of {@link DetailDecAgri }
     * 
     */
    public DetailDecAgri createDetailDecAgri() {
        return new DetailDecAgri();
    }

    /**
     * Create an instance of {@link Adresse }
     * 
     */
    public Adresse createAdresse() {
        return new Adresse();
    }

    /**
     * Create an instance of {@link Agriculteur }
     * 
     */
    public Agriculteur createAgriculteur() {
        return new Agriculteur();
    }

    /**
     * Create an instance of {@link DecisionAgr }
     * 
     */
    public DecisionAgr createDecisionAgr() {
        return new DecisionAgr();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDecisionCreditAgrResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.agricole.saeb.soap.ws.bna.com/", name = "getDecisionCreditAgrResponse")
    public JAXBElement<GetDecisionCreditAgrResponse> createGetDecisionCreditAgrResponse(GetDecisionCreditAgrResponse value) {
        return new JAXBElement<GetDecisionCreditAgrResponse>(_GetDecisionCreditAgrResponse_QNAME, GetDecisionCreditAgrResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDecisionCreditAgr }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://services.agricole.saeb.soap.ws.bna.com/", name = "getDecisionCreditAgr")
    public JAXBElement<GetDecisionCreditAgr> createGetDecisionCreditAgr(GetDecisionCreditAgr value) {
        return new JAXBElement<GetDecisionCreditAgr>(_GetDecisionCreditAgr_QNAME, GetDecisionCreditAgr.class, null, value);
    }

}
