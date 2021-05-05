package application;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class XMLReader {
	private String filename = "data.xml";
	private String[][][] phones;

	public XMLReader(String filename) {
		this.filename = filename;
	}
	
	public void readXML() {
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

	      try {

	          // optional, but recommended
	          // process XML securely, avoid attacks like XML External Entities (XXE)
	          dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);

	          // parse XML file
	          DocumentBuilder db = dbf.newDocumentBuilder();

	          Document doc = db.parse(new File(filename));

	          // optional, but recommended
	          // http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
	          doc.getDocumentElement().normalize();

	          // get <staff>
	          NodeList list = doc.getElementsByTagName("phone");
	          
	          int maxNumberOfAttributes = 0;
	          
	          for (int temp = 0; temp < list.getLength(); temp++) {
	              Node node = list.item(temp);
	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                  Element element = (Element) node;
	                  if (element.getChildNodes().getLength()/2 > maxNumberOfAttributes) {
	                	  maxNumberOfAttributes = element.getChildNodes().getLength()/2;
	                  }
	              }
	          }
	          
	          
	          phones = new String[list.getLength()][maxNumberOfAttributes][2];
	          
	          for (int temp = 0; temp < list.getLength(); temp++) {
	              Node node = list.item(temp);
	              if (node.getNodeType() == Node.ELEMENT_NODE) {
	                  Element element = (Element) node;
	                  for (int i = 0; i < element.getChildNodes().getLength(); i++) {
						if(i%2 == 0) {
							phones[temp][i/2][0] = element.getChildNodes().item(i).getChildNodes().item(0).getTextContent();
						}else {
							phones[temp][i/2][1] = element.getChildNodes().item(i).getChildNodes().item(0).getTextContent();
						}
	                  }	                
	              }
	          }
	      } catch (ParserConfigurationException | SAXException | IOException e) {
	          e.printStackTrace();
	      }
	}
	
	
	public String[][][] getPhones() {
		return phones;
	}
}
