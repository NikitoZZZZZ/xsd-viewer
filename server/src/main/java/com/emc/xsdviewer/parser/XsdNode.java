package com.emc.xsdviewer.parser;

import java.util.List;

import org.w3c.dom.Node;

public class XsdNode {

    private String elementName;
    private String attrName;
    private List<XsdNode> nextNodeList;
    private Node node;
    private boolean visible;

    XsdNode() {

    }

    XsdNode(Node node) {
        setNode(node);
        setElementName(node.getNodeName());
        setAttrName(XsdNodeHandler.createAttrName(node));
        setNextNodeList(XsdNodeHandler.createNextNodeList(node));
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

    public List<XsdNode> getNextNodeList() {
        return nextNodeList;
    }

    public void setNextNodeList(List<XsdNode> nextNodeList) {
        this.nextNodeList = nextNodeList;
    }

    protected Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }

    protected boolean getVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
