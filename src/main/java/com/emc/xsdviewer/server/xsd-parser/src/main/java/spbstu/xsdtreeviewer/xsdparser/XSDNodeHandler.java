package spbstu.xsdtreeviewer.xsdparser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XSDNodeHandler {
	XSDNodeHandler() {
	}
	
	public static String createAttrName(Node node) {
		return (node.getAttributes().getNamedItem("name") != null)
				? node.getAttributes().getNamedItem("name").toString()
				: null;
	}
	
	public static List<XSDNode> createNextNodeList(Node node) {
		List<XSDNode> nextNodeList = new ArrayList<>();
		if (node.hasChildNodes()) {
			NodeList nodeList = node.getChildNodes();
			for (int i = 0; i < nodeList.getLength(); ++i) {
				if (nodeList.item(i).getNodeType() != Node.TEXT_NODE) {
					nextNodeList.add(new XSDNode(nodeList.item(i)));
				}
			}
		}
		
		return nextNodeList;
	}
}
