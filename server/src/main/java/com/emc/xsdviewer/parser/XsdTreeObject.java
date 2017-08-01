package com.emc.xsdviewer.parser;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class XsdTreeObject {
    private XsdNode rootElement;

    public XsdTreeObject(String fileName) {
        setRootElement(XsdTreeObjectHandler.createXSDNode(fileName));
    }

    public XsdTreeObject(InputStream inputStream) {
        setRootElement(XsdTreeObjectHandler.createXSDNode(inputStream));
    }

    public XsdNode getRootElement() {
        return rootElement;
    }

    public void setRootElement(XsdNode rootElement) {
        this.rootElement = rootElement;
    }

    private void changeTree(XsdNode parent, XsdNode child, Settings settings) {
        List<XsdNode> pointer = child.getNextNodeList();

        if (pointer != null) {
            for (int i = 0; i < pointer.size(); ++i) {
                changeTree(child, pointer.get(i), settings);
            }
        }
        if (settings.getXsdNodesNames().contains(child.getElementName())) {
            List<XsdNode> mem = child.getNextNodeList();

            parent.getNextNodeList().remove(child);
            if (parent.getNextNodeList().isEmpty()) {
                parent.setNextNodeList(mem);
            }
        }
    }

    public XsdTreeObject getTree(Settings settings) {
        XsdTreeObject copyTree = this;
        List<XsdNode> root = new ArrayList<XsdNode>();

        root.add(copyTree.getRootElement());
        XsdNode aboveRoot = new XsdNode();
        aboveRoot.setNextNodeList(root);
        aboveRoot.setElementName("root");
        copyTree.setRootElement(aboveRoot);
        changeTree(copyTree.getRootElement(), copyTree.getRootElement().getNextNodeList().get(0), settings);

        return copyTree;
    }

    public String toString() {
        StringWriter sw = new StringWriter();

        //temporary empty

        return sw.toString();
    }
}
