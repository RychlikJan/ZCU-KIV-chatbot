package application;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;



public class GetData {

    public static ArrayList<String> params = null;
    public static Document finalDocument = null;

    public GetData() {
        getAllData();
    }

    public static void getAllData() {
        long startTime = System.nanoTime();
      //  Document doc = Jsoup.parse(new File(fileName), "utf-8");
        initDocument();
        System.out.println("Data started");
        try {
            getUrl();
        } catch (IOException | SAXException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        saveXML();
        long endTime   = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println("[INFO]-Get data runs: " + (totalTime/ 1_000_000_000.0)/60 + "min");
    }
    private static void saveXML(){
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource domSource = new DOMSource(finalDocument);
            StreamResult streamResult = new StreamResult(new File("data.xml"));
            transformer.transform(domSource, streamResult);
            System.out.println("Done creating XML File");

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }
    private static void initDocument(){
        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        finalDocument = documentBuilder.newDocument();
        Element root = finalDocument.createElement("phones");
        finalDocument.appendChild(root);

    }

    private static void reworkXML(String xmlFile, String url, String picUrl, String price) throws Exception {
        Document doc = convertStringToXMLDocument(xmlFile);
        removeAll(doc, Node.ELEMENT_NODE, "p");
        removeAll(doc, Node.ELEMENT_NODE, "h3");
        removeAll(doc, Node.ELEMENT_NODE, "span");
        removeAll(doc, Node.ELEMENT_NODE, "button");
        //removeAll(doc, Node.ELEMENT_NODE, "a");
        params = new ArrayList<>();
        params.add("Nazev");
        params.add(getName(url));
        params.add("url");
        params.add(url);
        params.add("imgUrl");
        params.add(picUrl);
        params.add("cena");
        params.add(price);
        parseDocument(doc.getDocumentElement());
        String out = "section" + ", ";
        for (String s2:params ) {
            out = out + s2 +",";
        }
        System.out.println(out);
        saveToXML();
    }

    private static String getName(String url) {
        String name = "";
        String[] urlArray = url.split("/");
        name = urlArray[urlArray.length-2];

        String[] namesPart = name.split("-");
        name = "";
        for (String s:namesPart){
            name=name+  s +" ";
        }
        System.out.println(name);
        return name;
    }

    private  static void saveToXML(){
        Element root = finalDocument.getDocumentElement();
        Element phone = finalDocument.createElement("phone");
        String value;



        for(int i = 0; i < params.size();i++){
            Element element = finalDocument.createElement("attribute");
            value = params.get(i);
            element.appendChild(finalDocument.createTextNode(value));
            phone.appendChild(element);
        }
        root.appendChild(phone);
        params = null;
    }

    private static void removeAll(Node node, short nodeType, String name) {
        if (node.getNodeType() == nodeType && (name == null || node.getNodeName().equals(name))) {
            node.getParentNode().removeChild(node);
        } else {
            NodeList list = node.getChildNodes();
            for (int i = 0; i < list.getLength(); i++) {
                removeAll(list.item(i), nodeType, name);
            }
        }
    }


    public static void parseDocument(Node node) {
        String line = node.getTextContent().replace("\n","");
        if(node.getNodeName().equals("section") && line.replace(" ","").length() != 0) {
            String s[] = line.split("  ");
            for(String s1 : s){
                if(s1.replace(" ","").length() != 0){
                    String finalString =  s1.replace("  ","");
                    StringBuilder sb = new StringBuilder(finalString);
                    while(finalString.charAt(0)==' '){
                        sb.deleteCharAt(0);
                        finalString = sb.toString();
                    }
                    params.add(finalString);
                }
            }
        }

        NodeList nodeList = node.getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node currentNode = nodeList.item(i);
            if (currentNode.getNodeType() == Node.ELEMENT_NODE) {
                parseDocument(currentNode);
            }
        }
    }

    private static Document convertStringToXMLDocument(String xmlString)
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

        factory.setNamespaceAware(true);
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
            try {
                return builder.parse(new ByteArrayInputStream(xmlString.getBytes()));
            } catch (SAXException | IOException e) {
                e.printStackTrace();
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        return null;
    }

    static public void getPage(String urlString, String picUrl) throws Exception {
        System.out.println(urlString);
        URL url;
        InputStream is = null;
        BufferedReader br;
        String line;
        String fullPage = "";
        boolean counter =false;
        int counter2 = 0;
        String price = "";

        try {
            url = new URL(urlString);
            is = url.openStream();  // throws an IOException
            br = new BufferedReader(new InputStreamReader(is));

            while ((line = br.readLine()) != null) {
                counter2--;
                //System.out.println(line);
                if(line.equals("    <span class=\"c-product-top-offer__price u-gamma \">")){
                    counter2 = 3;
                }
                if(counter2 == 1){
                    price = line;
                }
                //line =line.replace(" ","");
                if(line.equals("    <div class=\"o-layout__item c-parameters\">")){
                    counter=true;
                }
                if(line.equals("    <div class=\"o-layout__item\">")){
                    counter = false;


                }
                if(counter && !line.contains("xlink")) {

                    fullPage = fullPage + line + "\n";
                }
            }
        } catch (IOException mue) {
            mue.printStackTrace();
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException ioe) {
                // nothing to see here
            }
        }
        price = price.replace("&nbsp;","");
        price = price.replace("Kč","");
        price = price.replace(" ","");
        if(price.length() == 0){
            System.out.println("cena neznama");
            price="Neuvedena";
        }
        //System.out.println("fulpage:"+fullPage);
        reworkXML(fullPage, urlString,picUrl,price);
    }



    public static String getUrl() throws IOException, SAXException {
        //Get Document Builder
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;
        try {
            builder = factory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

//Build Document
        Document document = builder.parse(new File("C:\\Users\\JanRychlik\\IdeaProjects\\EITMData\\data\\heureka.xml"));

//Normalize the XML Structure; It's just too important !!
        document.getDocumentElement().normalize();

//Here comes the root node
        Element root = document.getDocumentElement();
        System.out.println(root.getNodeName());

        NodeList nList = document.getElementsByTagName("url");
        System.out.println("============================" + nList.getLength());

        //for (int temp = 0; temp < 1; temp++)
        for (int temp = 0; temp < nList.getLength(); temp++)
        {
            Node node = nList.item(temp);
            System.out.println("");    //Just a separator
            if (node.getNodeType() == Node.ELEMENT_NODE)
            {
                //Print each employee's detail
                Element eElement = (Element) node;
                //getName(eElement.getElementsByTagName("loc").item(0).getTextContent());
                System.out.println("Location : "    + eElement.getElementsByTagName("loc").item(0).getTextContent());
                String img ="";
                if(eElement.getElementsByTagName("image:loc").item(0) != null) {
                    img = eElement.getElementsByTagName("image:loc").item(0).getTextContent();
                }
                else{
                    img = "not found";
                }

                try {
                    getPage(eElement.getElementsByTagName("loc").item(0).getTextContent()+"#specifikace", img);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
