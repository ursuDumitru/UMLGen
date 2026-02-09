package org.reflection.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsibilities:
 * - one UML node (class/interface/enum).
 * */
public class UmlClass {

    public enum Kind { CLASS, INTERFACE, ENUM }
    public enum Visibility { PUBLIC, PROTECTED, PRIVATE, PACKAGE }

    public class FieldInfo {
        String name;
        String type;
        Visibility vis;
        boolean isStatic;
    }

    public class MethodInfo {
        class ParamInfo { String name; String type; }

        String name;
        String returnType;
        List<ParamInfo> params = new ArrayList<>();
        Visibility vis;
        boolean isStatic;
    }

    public String getFqcn() {
        return fqcn;
    }

    public void setFqcn(String fqcn) {
        this.fqcn = fqcn;
    }

    public Kind getKind() {
        return kind;
    }

    public void setKind(Kind kind) {
        this.kind = kind;
    }

    public List<FieldInfo> getFields() {
        return fields;
    }

    public void setFields(List<FieldInfo> fields) {
        this.fields = fields;
    }

    public List<MethodInfo> getMethods() {
        return methods;
    }

    public void setMethods(List<MethodInfo> methods) {
        this.methods = methods;
    }

    private String fqcn; // Fully Qualified Class Name
    private Kind kind;
    private List<FieldInfo> fields = new ArrayList<>();
    private List<MethodInfo> methods = new ArrayList<>();


}