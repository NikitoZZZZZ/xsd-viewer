package com.emc.xsdviewer.parser;

import java.util.List;

import org.w3c.dom.Node;

public class XsdNode {

    private String elementName;
    private String attrName;
    private List<XsdNode> nextNodeList;
    private Node node;
    private boolean visible;

    public XsdNode() {
    }

    public XsdNode(Node node) {
        this.elementName = node.getNodeName();
        this.attrName = XsdNodeHandler.createAttrName(node);
        this.nextNodeList = XsdNodeHandler.createNextNodeList(node);
        this.node = node;
        this.visible = true;
    }

    public String getElementName() {
        return elementName;
    }

    public void setElementName(final String elementName) {
        this.elementName = elementName;
    }

    public String getAttrName() {
        return attrName;
    }

    public void setAttrName(final String attrName) {
        this.attrName = attrName;
    }

    public List<XsdNode> getNextNodeList() {
        return nextNodeList;
    }

    public void setNextNodeList(final List<XsdNode> nextNodeList) {
        this.nextNodeList = nextNodeList;
    }

    protected Node getNode() {
        return node;
    }

    public void setNode(final Node node) {
        this.node = node;
    }

    protected boolean isVisible() {
        return visible;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
}
