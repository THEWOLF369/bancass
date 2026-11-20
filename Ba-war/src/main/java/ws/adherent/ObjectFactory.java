
package ws.adherent;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws.adherent package. 
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
    private final static QName _RecupererAdherent_QNAME = new QName("http://ws/", "recupererAdherent");
    private final static QName _RecupererAdherentResponse_QNAME = new QName("http://ws/", "recupererAdherentResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws.adherent
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RecupererAdherentResponse }
     * 
     */
    public RecupererAdherentResponse createRecupererAdherentResponse() {
        return new RecupererAdherentResponse();
    }

    /**
     * Create an instance of {@link RecupererAdherent }
     * 
     */
    public RecupererAdherent createRecupererAdherent() {
        return new RecupererAdherent();
    }

    /**
     * Create an instance of {@link ParseException }
     * 
     */
    public ParseException createParseException() {
        return new ParseException();
    }

    /**
     * Create an instance of {@link DonneesAdherent }
     * 
     */
    public DonneesAdherent createDonneesAdherent() {
        return new DonneesAdherent();
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
     * Create an instance of {@link JAXBElement }{@code <}{@link RecupererAdherent }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "recupererAdherent")
    public JAXBElement<RecupererAdherent> createRecupererAdherent(RecupererAdherent value) {
        return new JAXBElement<RecupererAdherent>(_RecupererAdherent_QNAME, RecupererAdherent.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RecupererAdherentResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://ws/", name = "recupererAdherentResponse")
    public JAXBElement<RecupererAdherentResponse> createRecupererAdherentResponse(RecupererAdherentResponse value) {
        return new JAXBElement<RecupererAdherentResponse>(_RecupererAdherentResponse_QNAME, RecupererAdherentResponse.class, null, value);
    }

}
