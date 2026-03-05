package org.reflection;

import org.main.Config;
import org.reflection.model.RelationType;
import org.reflection.model.UmlRelation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/** Responsibilities: - extracts relationships between classes/interfaces/enums etc. */
public class RelationExtractor {

    private final Map<String, UmlRelation> relations = new HashMap<>();
    private static final Map<RelationType, Integer> PRIORITY =
            Map.of(
                    RelationType.EXTENDS, 4,
                    RelationType.IMPLEMENTS, 3,
                    RelationType.ASSOCIATION, 2,
                    RelationType.DEPENDENCY, 1);

    public Set<UmlRelation> extractRelations(
            List<Class<?>> includedClasses,
            Config config,
            TypeConverter typeConverter,
            TypeFilter typeFilter) {

        Set<String> includedNames = new HashSet<>();

        for (Class<?> cls : includedClasses) {
            includedNames.add(cls.getName());
        }

        this.relations.clear();
        for (Class<?> cls : includedClasses) {
            String fromClass = cls.getName();

            extractExtend(config, typeConverter, cls, fromClass, includedNames);
            extractImplement(config, typeConverter, cls, fromClass, includedNames);
            extractAssociation(config, typeConverter, typeFilter, cls, fromClass, includedNames);
            extractDependency(config, typeConverter, typeFilter, cls, fromClass, includedNames);
        }

        return new HashSet<>(this.relations.values());
    }

    private void insertRelationByPriority(UmlRelation relation) {
        String key = relation.getFromFqcn() + "|" + relation.getToFqcn();
        UmlRelation existingRelation = relations.get(key);
        if (existingRelation == null
                || PRIORITY.get(relation.getType()) > PRIORITY.get(existingRelation.getType())) {
            relations.put(key, relation);
        }
    }

    private void extractDependency(
            Config config,
            TypeConverter typeConverter,
            TypeFilter typeFilter,
            Class<?> cls,
            String fromClass,
            Set<String> includedNames) {
        for (Method method : cls.getDeclaredMethods()) {
            if (!typeFilter.isMethodOk(method, config)) {
                continue;
            }

            // return type
            Set<String> returnReferencedTypes =
                    typeConverter.getReferencedFqcnsFromType(method.getGenericReturnType());
            for (String toReferencedType : returnReferencedTypes) {
                if (isValidRelation(fromClass, toReferencedType, includedNames, config)) {
                    insertRelationByPriority(
                            new UmlRelation(fromClass, toReferencedType, RelationType.DEPENDENCY));
                }
            }
            // parameter type
            for (Type parameterType : method.getGenericParameterTypes()) {
                String toReferencedType = typeConverter.convertTypeToFqcn(parameterType);
                if (isValidRelation(fromClass, toReferencedType, includedNames, config)) {
                    insertRelationByPriority(
                            new UmlRelation(fromClass, toReferencedType, RelationType.DEPENDENCY));
                }
            }
        }
    }

    private void extractAssociation(
            Config config,
            TypeConverter typeConverter,
            TypeFilter typeFilter,
            Class<?> cls,
            String fromClass,
            Set<String> includedNames) {
        for (Field field : cls.getDeclaredFields()) {
            if (!typeFilter.isFieldOk(field, config)) {
                continue;
            }
            Set<String> referencedTypes =
                    typeConverter.getReferencedFqcnsFromType(field.getGenericType());
            for (String toReferencedType : referencedTypes) {
                if (isValidRelation(fromClass, toReferencedType, includedNames, config)) {
                    insertRelationByPriority(
                            new UmlRelation(fromClass, toReferencedType, RelationType.ASSOCIATION));
                }
            }
        }
    }

    private void extractImplement(
            Config config,
            TypeConverter typeConverter,
            Class<?> cls,
            String fromClass,
            Set<String> includedNames) {
        for (Type interfaceType : cls.getGenericInterfaces()) {
            String toInterface = typeConverter.convertTypeToFqcn(interfaceType);
            if (isValidRelation(fromClass, toInterface, includedNames, config)) {
                insertRelationByPriority(
                        new UmlRelation(toInterface, fromClass, RelationType.IMPLEMENTS));
            }
        }
    }

    private void extractExtend(
            Config config,
            TypeConverter typeConverter,
            Class<?> cls,
            String fromClass,
            Set<String> includedNames) {
        Type superType = cls.getGenericSuperclass();
        String toClass = typeConverter.convertTypeToFqcn(superType);
        if (isValidRelation(fromClass, toClass, includedNames, config)) {
            insertRelationByPriority(new UmlRelation(toClass, fromClass, RelationType.EXTENDS));
        }
    }

    private boolean isValidRelation(
            String fromClass, String toClass, Set<String> includedNames, Config config) {
        if (toClass == null) {
            return false;
        }
        if (fromClass == null) {
            return false;
        }
        //        if (fromClass.equals(toClass)) {
        //            return false;
        //        }
        if (config.isFqcnIgnored(toClass)) {
            return false;
        }
        if (!includedNames.contains(toClass)) {
            return false;
        }

        return true;
    }
}
