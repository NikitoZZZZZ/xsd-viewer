package spbstu.xsdtreeviewer.xsdparser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Application {
	public static void main(String[] args) {
		Settings settings = new Settings();
		XSDTreeObject tree = new XSDTreeObject(args[0]);
		
		settings.addNodeName("xs:simpleType");
		//settings.addNodeName("xs:sequence");
		//XSDTreeObject temp = tree.getTree(settings);
		
/*		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = null;
		try {
			builder = factory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document document = null;
	    try {
	    	document = builder.parse(new InputSource("ex2.xsd"));
		} catch (SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	    System.out.println(tree.getTree(settings));
	}
}