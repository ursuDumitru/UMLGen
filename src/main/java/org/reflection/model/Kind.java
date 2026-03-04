package org.reflection.model;

public enum Kind {
    CLASS,
    INTERFACE,
    ENUM;

    public static Kind getKindOfClass(Class<?> cls) {
        if (cls.isInterface()) return CLASS;
        else if (cls.isEnum()) return ENUM;
        else return CLASS;
    }
}
