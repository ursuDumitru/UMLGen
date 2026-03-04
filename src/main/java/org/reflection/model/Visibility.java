package org.reflection.model;

import java.lang.reflect.Modifier;

public enum Visibility {
    PUBLIC,
    PROTECTED,
    PRIVATE,
    PACKAGE;

    public static Visibility convertModifierToVisibility(int modifiers) {
        if (Modifier.isPublic(modifiers)) {
            return Visibility.PUBLIC;
        }
        if (Modifier.isProtected(modifiers)) {
            return Visibility.PROTECTED;
        }
        if (Modifier.isPrivate(modifiers)) {
            return Visibility.PRIVATE;
        }
        return Visibility.PACKAGE;
    }
}
