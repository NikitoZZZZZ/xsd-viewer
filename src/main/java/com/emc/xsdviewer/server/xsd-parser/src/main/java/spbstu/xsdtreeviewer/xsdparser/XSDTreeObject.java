package spbstu.xsdtreeviewer.xsdparser;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XSDTreeObject {
	private XSDNode rootElement;

	XSDTreeObject(String fileName) {
		setRootElement(XSDTreeObjectHandler.createXSDNode(fileName));
	}
	
	public XSDNode getRootElement() {
		return rootElement;
	}
	public void setRootElement(XSDNode rootElement) {
		this.rootElement = rootElement;
	}
	
	public XSDTreeObject getTree(Settings settings) {
		XSDTreeObject copyTree = this;
		List<XSDNode> pointer = copyTree.getRootElement().getNextNodeList();
		List<XSDNode> parentNode = new ArrayList<>();
		
		parentNode.add(rootElement);
		while (!pointer.isEmpty()) {
			List<XSDNode> childNode = pointer.get(0).getNextNodeList();
			
			for (int i = 0; i < pointer.size(); ++i) {
				if (settings.getXSDNodesNames().contains(pointer.get(i).getElementName())) {
					pointer.remove(i);
				}
			}
			if (pointer.size() != 0) {
				parentNode = pointer;
			}
			else {
				pointer = parentNode;
				for (int j = 0; j < pointer.size(); ++j) {
					pointer.get(j).setNextNodeList(childNode);
				}
			}
			pointer = pointer.get(0).getNextNodeList();
		}
		
		return copyTree;
	}
	
	public String toString() {
		StringWriter sw = new StringWriter();
		
		//temporary empty
/*		sw.append(this.rootElement.getNextNodeList().get(0)
				.getNextNodeList().get(0)
				.getNextNodeList().get(0)
				.getNextNodeList().get(0)
				.getNextNodeList().get(0)
				.getNextNodeList().get(0)
				.getNextNodeList().get(3)
				.getNextNodeList().get(0).getElementName());*/
		
		sw.append(this.rootElement.getNextNodeList().get(0).getNextNodeList().get(0).getElementName());
		
		return sw.toString();
	}
}
