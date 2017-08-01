package com.emc.xsdviewer.parser;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XsdNodeHandler {
    protected XsdNodeHandler() {
    }

    public static String createAttrName(Node node) {
        return (node.getAttributes().getNamedItem("name") != null)
                ? node.getAttributes().getNamedItem("name").toString().substring(5).replaceAll("[\"]", "")
                : null;
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
