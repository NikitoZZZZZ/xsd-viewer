package com.emc.xsdviewer.server.XSDParser;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XSDTreeObject {
	private XSDNode rootElement;

	public XSDTreeObject(String fileName) {
		setRootElement(XSDTreeObjectHandler.createXSDNode(fileName));
	}
	
	public XSDTreeObject(InputStream inputStream) {
		setRootElement(XSDTreeObjectHandler.createXSDNode(inputStream));
	}
	
	public XSDNode getRootElement() {
		return rootElement;
	}
	public void setRootElement(XSDNode rootElement) {
		this.rootElement = rootElement;
	}
	
	private void changeTree(XSDNode parent, XSDNode child, Settings settings) {
		List<XSDNode> pointer = child.getNextNodeList();
		
		if (pointer != null) {
			for (int i = 0; i < pointer.size(); ++i) {
				changeTree(child, pointer.get(i), settings);
			}
		}
		if (settings.getXSDNodesNames().contains(child.getElementName())) {
			List<XSDNode> mem = child.getNextNodeList();
				
			parent.getNextNodeList().remove(child);
			if (parent.getNextNodeList().isEmpty()) {
				parent.setNextNodeList(mem);
			}
		}
	}
	
	public XSDTreeObject getTree(Settings settings) {
		XSDTreeObject copyTree = this;
		List<XSDNode> root = new ArrayList<XSDNode>();
		
		root.add(copyTree.getRootElement());
		XSDNode aboveRoot = new XSDNode();
		aboveRoot.setNextNodeList(root);
		changeTree(copyTree.getRootElement(), copyTree.getRootElement().getNextNodeList().get(0), settings);

		return copyTree;
	}
	
	public String toString() {
		StringWriter sw = new StringWriter();
		
		//temporary empty
		
		return sw.toString();
	}
}
