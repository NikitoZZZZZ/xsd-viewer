package com.emc.xsdviewer.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class XsdNodeHandler {
	private static Map<String, List<String>> types = new HashMap<>();
	
    static {
    	List<String> ls = new ArrayList<>();
    	ls.add("maxOccurs");
    	ls.add("minOccurs");
    	types.put("xs:sequence", ls);
    	ls = new ArrayList<>();
    	ls.add("type");
    	ls.add("nillable");
    	ls.add("minOccurs");
    	ls.add("maxOccurs");
    	types.put("xs:element", ls);
    	ls = new ArrayList<>();
    	ls.add("base");
    	types.put("xs:restriction", ls);
    	ls = new ArrayList<>();
    	ls.add("value");
    	types.put("xs:minInclusive", ls);
    	ls = new ArrayList<>();
    	ls.add("value");
    	types.put("xs:totalDigits", ls);
    	ls = new ArrayList<>();
    	ls.add("value");
    	types.put("xs:minLength", ls);
    	ls = new ArrayList<>();
    	ls.add("value");
    	types.put("xs:maxLength", ls);
    }

    public static String createName(Node node) {
    	String name = "";
        if (node.getAttributes().getNamedItem("name") != null) {
            name = node.getAttributes().getNamedItem("name").toString().substring(5).replaceAll("[\"]", "");
        }
        else {
        	name = node.getNodeName();
        }
        
        return name;
    }
    
    public static String createDetails(Node node) {
    	String details = "";
    	
        for (Map.Entry<String, List<String>> entry : types.entrySet()) {
        	if(entry.getKey().contains(node.getNodeName())) {
        		for(int i = 0; i < entry.getValue().size(); ++i) {
        			try {
        				details += ", "
        						+ node.getAttributes().getNamedItem((entry.getValue().get(i))).toString();
        			}
        			catch (NullPointerException e) {
        			}
        		}
        	}
        }
    	
    	return details;
    }

    public static List<XsdNode> createNextNodeList(Node node) {
        List<XsdNode> nextNodeList = new ArrayList<>();
        if (node.hasChildNodes()) {
            NodeList nodeList = node.getChildNodes();
            for (int i = 0; i < nodeList.getLength(); ++i) {
                if (nodeList.item(i).getNodeType() != Node.TEXT_NODE) {
                    nextNodeList.add(new XsdNode(nodeList.item(i)));
                }
            }
        }

        return nextNodeList;
    }
}
