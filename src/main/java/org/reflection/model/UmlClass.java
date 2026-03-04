package org.reflection.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** Responsibilities: - one UML node (class/interface/enum). */
public class UmlClass {

    private String fqcn; // Fully Qualified Class Name
    private Kind kind;
    private List<FieldInfo> fields = new ArrayList<>();
    private List<MethodInfo> methods = new ArrayList<>();

    private String extendsFqcn; // superclass
    private Set<String> implementsFqcns = new HashSet<>();
    private Set<String> associationTargets = new HashSet<>(); // from fields
    private Set<String> dependencyTargets = new HashSet<>(); // from methods

    public List<FieldInfo> getFields() {
        return this.fields;
    }

    public List<MethodInfo> getMethods() {
        return this.methods;
    }

    public String getFqcn() {
        return this.fqcn;
    }

    public void setFqcn(String fqcn) {
        this.fqcn = fqcn;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public void addToFields(FieldInfo field) {
        this.fields.add(field);
    }

    public void addToMethods(MethodInfo methods) {
        this.methods.add(methods);
    }

    public String getExtendsFqcn() {
        return extendsFqcn;
    }

    public void setExtendsFqcn(String extendsFqcn) {
        this.extendsFqcn = extendsFqcn;
    }

    public void addToImplementsFqcn(String implementsFqcn) {
        this.implementsFqcns.add(implementsFqcn);
    }

    public void addToAssociationTargets(String targetFqcn) {
        this.associationTargets.add(targetFqcn);
    }

    public void addToDependencyTargets(String targetFqcn) {
        this.dependencyTargets.add(targetFqcn);
    }

    @Override
    public String toString() {
        StringBuilder fieldsString = new StringBuilder();
        for (FieldInfo field : fields) {
            fieldsString.append(field.toString()).append(", ");
        }
        if (!fieldsString.isEmpty()) {
            fieldsString.append("\n\t");
        }
        StringBuilder methodsString = new StringBuilder();
        for (MethodInfo method : methods) {
            methodsString.append(method.toString()).append(", ");
        }
        if (!methodsString.isEmpty()) {
            methodsString.append("\n\t");
        }

        return "UmlClass: [\n\tfqcn: "
                + this.fqcn
                + ",\n\tkind: "
                + this.kind
                + ",\n\tfields: ["
                + fieldsString
                + "],\n\tmethods: ["
                + methodsString
                + "]\n]";
    }
}
