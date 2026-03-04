package org.reflection.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/** Responsibilities: - container for everything extracted */
public class UmlModel {
    // Map = unique Key + Value entries
    private Map<String, UmlClass> classesByName = new HashMap<>();
    // Set = List with unique entries
    private Set<UmlRelation> classesRelations = new HashSet<>();

    public void addToClassesByName(String fqcn, UmlClass node) {
        this.classesByName.put(fqcn, node);
    }

    public Map<String, UmlClass> getClassesByName() {
        return this.classesByName;
    }

    public Set<UmlRelation> getClassesRelations() {
        return this.classesRelations;
    }

    public void setClassesRelations(Set<UmlRelation> classesRelations) {
        this.classesRelations.addAll(classesRelations);
    }

    public void printClassesByName() {
        System.out.println("\n[INFO] Classes by name:");
        for (UmlClass classByName : this.classesByName.values()) {
            System.out.println(classByName);
        }
    }

    public void printClassesRelations() {
        System.out.println("\n[INFO] Classes Relations:");
        for (UmlRelation relation : this.classesRelations) {
            System.out.println(relation);
        }
    }
}
