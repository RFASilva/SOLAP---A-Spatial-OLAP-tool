
package types;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the types package. 
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

    private final static QName _SolapQuery_QNAME = new QName("http://solap/", "solapQuery");
    private final static QName _SolapQueryResponse_QNAME = new QName("http://solap/", "solapQueryResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: types
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SolapQueryResponse }
     * 
     */
    public SolapQueryResponse createSolapQueryResponse() {
        return new SolapQueryResponse();
    }

    /**
     * Create an instance of {@link SolapQuery }
     * 
     */
    public SolapQuery createSolapQuery() {
        return new SolapQuery();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolapQuery }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solap/", name = "solapQuery")
    public JAXBElement<SolapQuery> createSolapQuery(SolapQuery value) {
        return new JAXBElement<SolapQuery>(_SolapQuery_QNAME, SolapQuery.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link SolapQueryResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://solap/", name = "solapQueryResponse")
    public JAXBElement<SolapQueryResponse> createSolapQueryResponse(SolapQueryResponse value) {
        return new JAXBElement<SolapQueryResponse>(_SolapQueryResponse_QNAME, SolapQueryResponse.class, null, value);
    }

}
