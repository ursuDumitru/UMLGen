package org.reflection.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Responsibilities:
 * - container for everything extracted
 * */
public class UmlModel {
    private Map<String, UmlClass> classesByName = new HashMap<>();
    private Set<Relation> relations = new HashSet<>();

    public void addToClassesByName(String fqcn, UmlClass node) {
        classesByName.put(fqcn, node);
    }

    public void printClassesByName() {
        System.out.println("[INFO] Classes by name:");
        for (Map.Entry<String, UmlClass> entry : classesByName.entrySet()) {
            System.out.println("[ " + entry.getKey() + ": " + entry.getValue().getFqcn() + " ]");
        }
    }
}