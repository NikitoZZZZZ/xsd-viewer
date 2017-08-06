package com.emc.xsdviewer.parser;

import java.util.List;

import org.w3c.dom.Node;

public class XsdNode {

    private String name;
    private List<XsdNode> children;
    private Node node;
    private boolean visible;

    public XsdNode() {
    }

    public XsdNode(Node node) {
        this.name = XsdNodeHandler.createName(node);
        this.children = XsdNodeHandler.createNextNodeList(node);
        this.node = node;
        this.visible = true;
    }

    public String getName() {
        return name;
    }

    public void setElementName(final String elementName) {
        this.name = elementName;
    }

    public List<XsdNode> getChildren() {
        return children;
    }

    public void setNextNodeList(final List<XsdNode> nextNodeList) {
        this.children = nextNodeList;
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
