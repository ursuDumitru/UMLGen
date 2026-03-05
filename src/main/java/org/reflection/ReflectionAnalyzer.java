package org.reflection;

import org.main.Config;
import org.reflection.model.*;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ReflectionAnalyzer {

    private final ClassLoaderFactory loaderFactory = new ClassLoaderFactory();
    private final TypeFilter typeFilter = new TypeFilter();
    private final TypeConverter typeConverter = new TypeConverter();
    private final RelationExtractor relationExtractor = new RelationExtractor();

    public ReflectionAnalyzer() {}

    public UmlModel analyze(List<String> classNames, Config config) throws MalformedURLException {
        ClassLoader classLoader = loaderFactory.create(config);
        List<Class<?>> loadedClasses = getLoadedClasses(classNames, classLoader);
        List<Class<?>> includedClasses = getIncludedClasses(config, loadedClasses);
        UmlModel umlModel = getUmlModel(config, includedClasses);

        return umlModel;
    }

    private UmlModel getUmlModel(Config config, List<Class<?>> includedClasses) {
        UmlModel umlModel = new UmlModel();

        for (Class<?> cls : includedClasses) {
            UmlClass node = toUmlClassNode(cls, config);
            umlModel.addToClassesByName(node.getFqcn(), node);
        }

        umlModel.setClassesRelations(
                this.relationExtractor.extractRelations(
                        includedClasses, config, this.typeConverter, this.typeFilter));

//        umlModel.printClassesByName();
//        umlModel.printClassesRelations();

        return umlModel;
    }

    private List<Class<?>> getIncludedClasses(Config config, List<Class<?>> loadedClasses) {
        List<Class<?>> includedClasses = new  ArrayList<>();
        for (Class<?> cls : loadedClasses) {
            if (typeFilter.isClassOk(cls, config)) {
                includedClasses.add(cls);
            }
        }
        //        System.out.println("\n[INFO] Included classes: \n" + includedClasses);
        return includedClasses;
    }

    private static List<Class<?>> getLoadedClasses(
            List<String> classNames, ClassLoader classLoader) {
        List<Class<?>> loadedClasses = new ArrayList<>();
        for (String fqcn : classNames) {
            try {
                Class<?> cls = Class.forName(fqcn, false, classLoader);
                loadedClasses.add(cls);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoClassDefFoundError e) {
                System.out.println("[WARNING] Class {" + fqcn + "} not found.");
            }
        }
        return loadedClasses;
    }

    private UmlClass toUmlClassNode(Class<?> cls, Config config) {
        UmlClass node = new UmlClass();

        node.setFqcn(cls.getName());
        node.setKind(Kind.getKindOfClass(cls));

        // attributes
        if (config.isShowAttributes()) {
            for (Field f : cls.getDeclaredFields()) {
                if (this.typeFilter.isFieldOk(f, config)) {
                    node.addToFields(new FieldInfo(f, this.typeConverter));
                }
            }
        }

        // methods
        if (config.isShowMethods()) {
            for (Method m : cls.getDeclaredMethods()) {
                if (this.typeFilter.isMethodOk(m, config)) {
                    node.addToMethods(new MethodInfo(m, this.typeConverter));
                }
            }
        }

        return node;
    }
}
