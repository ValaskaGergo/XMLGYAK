package hu.domparse.AQBMIV;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomQueryAQBMIV {
	public static void main(String[] args) {
		try {
		File xmlFile = new File("XMLAQBMIV.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc =dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		
		String feladat="Jól keresõ fodrászok (205000 ft felett):";
		System.out.println(feladat +"\n\n");
		Lekerdezes1(doc);
		
		
		}
		catch(ParserConfigurationException | IOException | SAXException ex){
			System.out.println("Some error happened:\n"+ex.getMessage());
			ex.printStackTrace();
		}
	}
	
	private static String normalizeText(String text) {
		text=text.replaceAll("\\n", ", ");
		text=text.replaceAll("\\s+", " ");
		return text;
	}
	
	private static void Lekerdezes1(Document doc) {
		NodeList fodraszok = doc.getElementsByTagName("fodrasz");
		for(int i = 0; i < fodraszok.getLength(); i++) {
			Element fodrasz =(Element)fodraszok.item(i);
			NodeList childNodes = fodrasz.getChildNodes();
			for(int j =0;j<childNodes.getLength();j++) {
				Node childNode = childNodes.item(j);
				if(childNode.getNodeName().equals("kereset")) {
					if(Integer.parseInt(childNode.getTextContent())>=205000) {
						printElement(fodrasz);
					}
				}
			}
		}
		}
	
	private static void printElement(Element elem) {
		String id = elem.getAttribute("id");
		System.out.println("	ID: "+ id);
		
		String nodeContent="";
		NodeList childNodes = elem.getChildNodes();
		for(int j =0;j<childNodes.getLength() ; j++) {
			if(childNodes.item(j).getTextContent().trim()!="") {
				nodeContent = normalizeText(childNodes.item(j).getTextContent().trim());
				System.out.println(childNodes.item(j).getNodeName()+": "+nodeContent);
			}
		}
		
		
	}
}
