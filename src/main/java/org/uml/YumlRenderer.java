package org.uml;

import org.main.Config;
import org.reflection.model.*;

import java.util.*;

/** Responsibility: convert UmlModel into yUML */
public class YumlRenderer {

    private final NameFormatter nameFormatter = new NameFormatter();

    public List<String> render(UmlModel model, Config config) {
        Set<String> lines = new LinkedHashSet<>();

        // classes
        for (UmlClass cls : model.getClassesByName().values()) {
            lines.add(renderClass(cls, config));
        }

        // relations
        for (UmlRelation relation : model.getClassesRelations()) {
            lines.add(renderRelation(relation, config));
        }

        return new ArrayList<>(lines);
    }

    private String renderRelation(UmlRelation relation, Config config) {
        String fromClass =
                "[" + this.nameFormatter.formatClassName(relation.getFromFqcn(), config) + "]";
        String toClass =
                "[" + this.nameFormatter.formatClassName(relation.getToFqcn(), config) + "]";

        return switch (relation.getType()) {
            case EXTENDS -> fromClass + "^- " + toClass;
            case IMPLEMENTS -> fromClass + "^-.-" + toClass;
            case ASSOCIATION -> fromClass + "->" + toClass;
//            case DEPENDENCY -> fromClass + "-.->" + toClass;
            default -> "";
        };
    }

    private String renderClass(UmlClass cls, Config config) {
        String className = this.nameFormatter.formatClassName(cls.getFqcn(), config);

        String fieldPart = "";
        if (config.isShowAttributes()) {
            StringJoiner joiner = new StringJoiner(";");
            for (FieldInfo field : cls.getFields()) {
                joiner.add(renderField(field, config));
            }
            fieldPart = joiner.toString();
        }

        String methodPart = "";
        if (config.isShowMethods()) {
            StringJoiner joiner = new StringJoiner(";");
            for (MethodInfo method : cls.getMethods()) {
                joiner.add(renderMethod(method, config));
            }
            methodPart = joiner.toString();
        }

        boolean showFields = config.isShowAttributes() && !fieldPart.isEmpty();
        boolean showMethods = config.isShowMethods() && !methodPart.isEmpty();

        if (!showFields && !showMethods) {
            return "[" + className + "]";
        } else if (showFields && !showMethods) {
            return "[" + className + "|" + fieldPart + "|]";
        } else if (!showFields) {
            return "[" + className + "||" + methodPart + "]";
        }

        return "[" + className + "|" + fieldPart + "|" + methodPart + "]";
    }

    private String renderMethod(MethodInfo method, Config config) {
        return renderVisibility(method.getVisibility()) + " " + method.getName() + "()";
    }

    private String renderField(FieldInfo field, Config config) {
        return renderVisibility(field.getVisibility())
                + " "
                + field.getName()
                + ": "
                + nameFormatter.formatType(field.getType(), config);
    }

    private String renderVisibility(Visibility visibility) {
        return switch (visibility) {
            case PUBLIC -> "+";
            case PRIVATE -> "-";
            case PROTECTED -> "#";
            case PACKAGE -> "~";
            default -> "";
        };
    }
}
