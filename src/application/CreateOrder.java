package application;

import javafx.scene.control.Label;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class CreateOrder {
    private String name;
    private String lastName;
    private String phoneName;
    private String adress;
    private String mobilName;
    public CreateOrder(String name, String lastName, String phoneName, String address, String mobileName) {
        this.name = name;
        this.lastName = lastName;
        this.phoneName = phoneName;
        this.adress = address;
        this.mobilName = mobileName;
    }

    public void createNewOrder(){
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        Document orederDocument = null;
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        orederDocument = documentBuilder.newDocument();
        Element root = orederDocument.createElement("order");

        Element nameTag = orederDocument.createElement("name");
        nameTag.appendChild(orederDocument.createTextNode(name));
        root.appendChild(nameTag);

        Element lastNameTag = orederDocument.createElement("lastName");
        lastNameTag.appendChild(orederDocument.createTextNode(lastName));
        root.appendChild(lastNameTag);

        Element phoneNameTag = orederDocument.createElement("phoneName");
        phoneNameTag.appendChild(orederDocument.createTextNode(phoneName));
        root.appendChild(phoneNameTag);

        Element addressTag = orederDocument.createElement("addressTag");
        addressTag.appendChild(orederDocument.createTextNode(adress));
        root.appendChild(addressTag);
        
        orederDocument.appendChild(root);
        
        saveXML(orederDocument);
    }

    private static void saveXML(Document orderDocument){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(orderDocument);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());

            StreamResult streamResult = new StreamResult(new File("Order_N"+timestamp.getTime()+".xml"));
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
}
