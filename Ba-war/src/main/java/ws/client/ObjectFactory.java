
package ws.client;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.client package. 
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

    private final static QName _ParseException_QNAME = new QName("http://ws/", "ParseException");
    private final static QName _RecupererClientResponse_QNAME = new QName("http://ws/", "recupererClientResponse");
    private final static QName _RecupererClient_QNAME = new QName("http://ws/", "recupererClient");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.client
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RecupererClientResponse }
     * 
     */
    public RecupererClientResponse createRecupererClientResponse() {
        return new RecupererClientResponse();
    }

    /**
     * Create an instance of {@link RecupererClient }
     * 
     */
    public RecupererClient createRecupererClient() {
        return new RecupererClient();
    }

    /**
     * Create an instance of {@link ParseException }
     * 
     */
    public ParseException createParseException() {
        return new ParseException();
    }

    /**
     * Create an instance of {@link DonnesClient }
     * 
     */
    public DonnesClient createDonnesClient() {
        return new DonnesClient();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ParseException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ParseException")
    public JAXBElement<ParseException> createParseException(ParseException value) {
        return new JAXBElement<ParseException>(_ParseException_QNAME, ParseException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecupererClientResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "recupererClientResponse")
    public JAXBElement<RecupererClientResponse> createRecupererClientResponse(RecupererClientResponse value) {
        return new JAXBElement<RecupererClientResponse>(_RecupererClientResponse_QNAME, RecupererClientResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecupererClient }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "recupererClient")
    public JAXBElement<RecupererClient> createRecupererClient(RecupererClient value) {
        return new JAXBElement<RecupererClient>(_RecupererClient_QNAME, RecupererClient.class, null, value);
    }

}
