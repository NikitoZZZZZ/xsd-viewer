package com.emc.xsdviewer.parser;

import java.util.HashSet;
import java.util.Set;

public class Settings {
    private Set<String> xsdNodesNames;

    public Settings() {
        setXsdNodesNames(new HashSet<>());
    }

    public Set<String> getXsdNodesNames() {
        return xsdNodesNames;
    }

    public void setXsdNodesNames(final Set<String> xSDNodesNames) {
        xsdNodesNames = xSDNodesNames;
    }

    public void addNodeName(final String nodeName) {
        xsdNodesNames.add(nodeName);
    }
}
