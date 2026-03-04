package org.reflection;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashSet;
import java.util.Set;
import java.util.StringJoiner;

/**
 * Converts java.lang.reflect.Type into a readable string. examples: - String.class ->
 * "java.lang.String" - String[] -> "java.lang.String[]"
 */
public class TypeConverter {

    public String convertTypeToString(Type type) {
        if (type == null) throw new NullPointerException("[ERROR] Type cannot be null");

        // plain classes, including arrays
        if (type instanceof Class<?> c) {
            if (c.isArray()) {
                return convertTypeToString(c.getComponentType()) + "[]";
            }
            return c.getTypeName();
        }

        // parameterized types
        if (type instanceof ParameterizedType p) {
            String raw = convertTypeToString(p.getRawType());
            Type[] args = p.getActualTypeArguments();

            StringJoiner sj = new StringJoiner(", ");
            for (Type t : args) {
                sj.add(convertTypeToString(t));
            }

            return raw + "<" + sj + ">";
        }

        // TODO: see if wildcards, TypeVariables and generic array types are needed

        // default
        return type.getTypeName();
    }

    public String convertTypeToFqcn(Type type) {
        if (type == null) {
            System.out.println("[ERROR] Type cannot be null");
            return null;
        }

        // plain class
        if (type instanceof Class<?> c) {
            if (c.isArray()) {
                return convertTypeToFqcn(c.getComponentType());
            }
            return c.getName();
        }

        // parameterized type
        if (type instanceof ParameterizedType p) {
            Type rawType = p.getRawType();
            return convertTypeToFqcn(rawType);
        }

        return null;
    }

    public Set<String> getReferencedFqcnsFromType(Type type) {
        Set<String> result = new HashSet<>();
        addAllReferences(type, result);
        return result;
    }

    void addAllReferences(Type type, Set<String> result) {
        if (type == null) throw new NullPointerException("[ERROR] Type cannot be null");

        // plain class
        if (type instanceof Class<?> c) {
            if (c.isArray()) {
                addAllReferences(c.getComponentType(), result);
            } else {
                result.add(c.getTypeName());
            }
            return;
        }

        // parameterized types
        if (type instanceof ParameterizedType p) {
            addAllReferences(p.getRawType(), result);

            for (Type t : p.getActualTypeArguments()) {
                addAllReferences(t, result);
            }
        }
    }
}
