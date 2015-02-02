package solap;

// imports
import java.io.CharArrayReader;
import java.io.File;
import java.io.IOException;
import java.io.Reader;

import javax.jws.WebMethod;
import javax.jws.WebService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

import solap.utils.XMLUtils;

//main class
@WebService
public class SOLAPServer {

    @WebMethod
    public String solapQuery(String xmlRequest) {
 
        String xmlResponse = "empty";

        try {
            // create a factory
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            
            System.out.println("PEDIDO: " + xmlRequest);
            
            //TODO: alterar o schema para prever o elemento clustering
           // if (isValidRequest(xmlRequest)) {
                // parse the input
                Reader reader = new CharArrayReader(xmlRequest.toCharArray());
                Document doc = builder.parse(new org.xml.sax.InputSource(reader));
                
                //parse request
                SOLAPRequest request = new SOLAPRequest(doc);
                //generate new response object
                SOLAPResponse response = new SOLAPResponse(request.getParamsObject());
                //get a xml response for the client
                
                xmlResponse = response.getXMLResponse();
                
            
          /*  }
          
            else
                xmlResponse = "ERROR: Invalid XML Request";*/
        }
        catch (Exception e) {
            System.err.println(e);
        }

        return xmlResponse;
    }

    //validates a xml request
    private boolean isValidRequest (String xmlRequest) throws SAXException,
                                                              IOException {
        //set validating settings
        SchemaFactory xsdfactory = 
        SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        //schema file
        File schemaLocation = new File("request.xsd");
        Schema schema = null;
        
        try {
            schema = xsdfactory.newSchema(schemaLocation);
        } catch (SAXException e) {
            System.out.println(e);
        }
        Validator validator = schema.newValidator();
        
        Reader reader = new CharArrayReader(xmlRequest.toCharArray());
        Source source = new StreamSource(reader);
        
        // validate the request
        try {
            validator.validate(source);
            //System.out.println("Request is valid.");
            return true;
        }
        catch (SAXException ex) {
            System.out.println("Request is not valid because ");
            System.out.println(ex.getMessage());
            return false;
        } catch (IOException e) {
            System.out.println(e);
        }
        
        return false;
    }

}
