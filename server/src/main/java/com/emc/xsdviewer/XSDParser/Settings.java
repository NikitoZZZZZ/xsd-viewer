package com.emc.xsdviewer.XSDParser;

import java.util.HashSet;
import java.util.Set;

public class Settings {
	private Set<String> XSDNodesNames;

	Settings() {
		setXSDNodesNames(new HashSet<>());
	}
	
	public Set<String> getXSDNodesNames() {
		return XSDNodesNames;
	}
	public void setXSDNodesNames(Set<String> xSDNodesNames) {
		XSDNodesNames = xSDNodesNames;
	}
	
	public void addNodeName(String nodeName) {
		XSDNodesNames.add(nodeName);
	}
}
