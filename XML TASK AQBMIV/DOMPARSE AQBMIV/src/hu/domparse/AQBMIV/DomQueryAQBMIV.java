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
		
		String feladat1="20 év feletti fodraszok:";
		System.out.println(feladat1 +"\n\n");
		Lekerdezes2(doc);
		
		String feladat2="25 év alatti ügyfelek:";
		System.out.println(feladat2 +"\n\n");
		Lekerdezes3(doc);
		
		
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
	
 //fodrasz kereset mely tobb mint 205000 ft
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
	//20 ev feletti fodraszok
	private static void Lekerdezes2(Document doc) {
		NodeList fodraszok = doc.getElementsByTagName("fodrasz");
		for(int i = 0; i < fodraszok.getLength(); i++) {
			Element fodrasz =(Element)fodraszok.item(i);
			NodeList childNodes = fodrasz.getChildNodes();
			for(int j =0;j<childNodes.getLength();j++) {
				Node childNode = childNodes.item(j);
				if(childNode.getNodeName().equals("kor")) {
					if(Integer.parseInt(childNode.getTextContent())>=20) {
						printElement(fodrasz);
					}
				}
			}
		}
		}
	//25 ev alatti ugyfel
	private static void Lekerdezes3(Document doc) {
		NodeList ugyfelek = doc.getElementsByTagName("ugyfel");
		for(int i = 0; i < ugyfelek.getLength(); i++) {
			Element fodrasz =(Element)ugyfelek.item(i);
			NodeList childNodes = fodrasz.getChildNodes();
			for(int j =0;j<childNodes.getLength();j++) {
				Node childNode = childNodes.item(j);
				if(childNode.getNodeName().equals("kor")) {
					if(Integer.parseInt(childNode.getTextContent())<=25) {
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
