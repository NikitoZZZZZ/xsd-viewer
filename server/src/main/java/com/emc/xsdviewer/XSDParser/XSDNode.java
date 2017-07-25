package com.emc.xsdviewer.XSDParser;

import java.util.List;

import org.w3c.dom.Node;

public class XSDNode {
	private String elementName;
	private String attrName;
	private List<XSDNode> nextNodeList;
	private Node node;
	private boolean visible;

	XSDNode() {
		
	}
	
	XSDNode(Node node) {
		setNode(node);
		setElementName(node.getNodeName());
		setAttrName(XSDNodeHandler.createAttrName(node));
		setNextNodeList(XSDNodeHandler.createNextNodeList(node));
		setVisible(true);
	}
	
	public String getElementName() {
		return elementName;
	}
	public void setElementName(String elementName) {
		this.elementName = elementName;
	}
	public String getAttrName() {
		return attrName;
	}
	public void setAttrName(String attrName) {
		this.attrName = attrName;
	}
	public List<XSDNode> getNextNodeList() {
		return nextNodeList;
	}
	public void setNextNodeList(List<XSDNode> nextNodeList) {
		this.nextNodeList = nextNodeList;
	}
	public Node getNode() {
		return node;
	}
	public void setNode(Node node) {
		this.node = node;
	}
	public boolean getVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
}
