package org.reflection.model;

import org.reflection.TypeConverter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class FieldInfo {

    private final String name;
    private final String type;
    private final Visibility vis;
    private final boolean isStatic;

    public FieldInfo(Field field, TypeConverter typeResolver) {
        this.name = field.getName();
        this.type = typeResolver.convertTypeToString(field.getGenericType());
        this.vis = Visibility.convertModifierToVisibility(field.getModifiers());
        this.isStatic = Modifier.isStatic(field.getModifiers());
    }

    public String getName() {
        return this.name;
    }

    public Visibility getVisibility() {
        return this.vis;
    }

    public String getType() {
        return this.type;
    }

    @Override
    public String toString() {
        return "\n\t\tFieldInfo: ["
                + "\n\t\t\tname: "
                + this.name
                + ",\n\t\t\ttype: "
                + this.type
                + ",\n\t\t\tvis: "
                + this.vis
                + "\n\t\t\tisStatic: "
                + this.isStatic
                + "\n\t\t]";
    }
}
