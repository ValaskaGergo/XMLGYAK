package hu.domparse.AQBMIV;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class DomModifyAQBMIV {
	public static void main(String[] args) {
		try {
		File xmlFile = new File("XMLAQBMIV.xml");
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = factory.newDocumentBuilder();
		Document doc =dBuilder.parse(xmlFile);
		doc.getDocumentElement().normalize();
		//DOM fává alakítom
		
		
		//A tulajdonos profit módositása
		NodeList nodes =doc.getElementsByTagName("tulajdonos");
		for(int i =0;i<nodes.getLength();i++) {
			Node node = nodes.item(i);
			if(node.getNodeType()== Node.ELEMENT_NODE) {
				if(node.getAttributes().getNamedItem("id").getTextContent().equals("T0")) {
					NodeList childNodes = node.getChildNodes();
					for(int j=0 ; j < childNodes.getLength();j++) {
						Node childNode = childNodes.item(j);
						if(childNode.getNodeName().equals("profit")) {
							childNode.setTextContent("0");
						}
					}
				}
			}
		}
		//ügyfél név módosítás
		nodes =doc.getElementsByTagName("ugyfel");
		for(int i =0;i<nodes.getLength();i++) {
			Node node = nodes.item(i);
			if(node.getNodeType()== Node.ELEMENT_NODE) {
				if(node.getAttributes().getNamedItem("id").getTextContent().equals("Ugy1")) {
					NodeList childNodes = node.getChildNodes();
					for(int j=0 ; j < childNodes.getLength();j++) {
						Node childNode = childNodes.item(j);
						if(childNode.getNodeName().equals("nev")) {
							childNode.setTextContent("Kiss Gertrud");
						}
					}
				}
			}
		}
		
		
		File file =new File("XMLAQBMIV.xml");
		writeXml(doc,file);
		
		}
		catch(ParserConfigurationException | IOException | SAXException | TransformerException ex){
			System.out.println("Some error happened:\n"+ex.getMessage());
			ex.printStackTrace();
		}
	}
	//fájl felülirasa
	private static void writeXml(Document doc, File output) throws TransformerException,UnsupportedEncodingException{
		TransformerFactory transformerFactory= TransformerFactory.newInstance();
		Transformer transf =transformerFactory.newTransformer();
		transf.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
		transf.setOutputProperty(OutputKeys.INDENT, "yes");
		transf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		DOMSource source = new DOMSource(doc);
		
		StreamResult console = new StreamResult(System.out);
		StreamResult file = new StreamResult(output);
		
		transf.transform(source, console);
		transf.transform(source, file);
	}

}
