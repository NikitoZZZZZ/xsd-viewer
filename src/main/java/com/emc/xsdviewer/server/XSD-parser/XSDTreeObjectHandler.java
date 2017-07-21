package com.emc.xsdviewer.xsdparser;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XSDTreeObjectHandler {
	public static XSDNode createXSDNode(String fileName) {
		XSDNode element = null;
		try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(new InputSource(fileName));
		element = new XSDNode(document.getDocumentElement());
		} catch (IOException | SAXException | ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		return element;
	}
}
