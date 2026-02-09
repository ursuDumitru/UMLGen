package org.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class GenericTypeResolver {

    private String toDisplay(Type type) {
        if (type == null) throw new NullPointerException("[ERROR] Type cannot be null");

        if (type instanceof Class<?> c) {
            if (c.isArray()) {
                return toDisplay(c.getComponentType()) + "[]";
            }
            return c.getTypeName();
        }

        if (type instanceof ParameterizedType) return ((ParameterizedType) type).getRawType().toString();
    }
}
