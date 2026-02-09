package org.reflection;

import org.main.Config;
import org.reflection.model.UmlClass;
import org.reflection.model.UmlModel;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ReflectionAnalyzer {

    private final ClassLoaderFactory loaderFactory = new ClassLoaderFactory();
    private final TypeFilter typeFilter = new TypeFilter();

    public ReflectionAnalyzer() {}

    public UmlModel analyze(List<String> classNames, Config config) throws MalformedURLException {
        ClassLoader classLoader = loaderFactory.create(config);

        List<Class<?>> loadedClasses = new ArrayList<>();
        for (String fqcn : classNames) {
            if(config.isFqcnIgnored(fqcn)) {
                continue;
            }
            try {
                Class<?> cls = Class.forName(fqcn, false, classLoader);
                loadedClasses.add(cls);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            } catch (NoClassDefFoundError e) {
                continue;
            }
        }
        System.out.println("[INFO] Loaded classes: \n" + loadedClasses);

        List<Class<?>> includedClasses = new ArrayList<>();
        for(Class<?> cls : loadedClasses) {
            if(typeFilter.includeClass(cls, config)) {
                includedClasses.add(cls);
            }
        }
        System.out.println("[INFO] Included classes: \n" + includedClasses);

        UmlModel umlModel = new UmlModel();
        for(Class<?> cls : includedClasses) {
            UmlClass node = toUmlClassNode(cls, config);
            umlModel.addToClassesByName(node.getFqcn(), node);
        }

        // model.relations = relationExtractor.extract(included, ...)
        return umlModel;
    }

    private UmlClass toUmlClassNode(Class<?> cls, Config config) {
        UmlClass node = new UmlClass();
        node.setFqcn(cls.getName());

        if (cls.isInterface()) node.setKind(UmlClass.Kind.INTERFACE);
        else if (cls.isEnum()) node.setKind(UmlClass.Kind.ENUM);
        else node.setKind(UmlClass.Kind.CLASS);

        return node;
    }
}
