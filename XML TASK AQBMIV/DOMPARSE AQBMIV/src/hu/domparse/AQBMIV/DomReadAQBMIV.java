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

public class DomReadAQBMIV {
	 public static void main(String argv[]) throws SAXException, IOException, ParserConfigurationException {
		File xmlFile = new File("XMLAQBMIV.xml");
		
		//A documnetBuilderFactory-b�l megkapjuk a documentBuildert
		//A documentbuilder tartalmazza az aAPI-t
		//a parse() met�dus elemzi az xml f�jlt a dokument.
		//DOM - fa �pites
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc =dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		//A dokumentum normalizalasa segit a helyes eredmenyek elereseben
		
		
		
		 System.out.println("Root element: "+doc.getDocumentElement().getNodeName());
		String[] tagNames= {"ugyfel" ,"uzlet", "tulajdonos", "fodrasz"};
		for(String tagName :tagNames) {
			NodeList nList = doc.getElementsByTagName(tagName);	
			
		
		for(int i=0; i<	nList.getLength();i++) {
			Node nNode = nList.item(i);
			System.out.println("\nCurrent Element: " + nNode.getNodeName()); //egyedek el�r�se
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element elem =(Element) nNode;
				
				//Azonosit� kiir�sa
				String id = elem.getAttribute("id");
				System.out.println("	ID: " +id);
				//Tulajdons�gok kiir�sa
				
				String nodeContent="";
				NodeList childNodes =elem.getChildNodes();
				
				for(int j =0; j< childNodes.getLength();j++) {
					if(childNodes.item(j).getTextContent().trim() !="") {
						nodeContent =normalizeText(childNodes.item(j).getTextContent().trim());
						System.out.println("	"+childNodes.item(j).getNodeName()+": "+nodeContent);
					}
				}
				
				}
			System.out.println();
			}
				
				
					
				
		}
	}
		private static String normalizeText(String text) {
			text=text.replaceAll("\\n", ", ");
			text=text.replaceAll("\\s+", " ");
			return text;
		}
	}

