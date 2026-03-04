package org.main;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * FileScanner Responsibilities:
 * Read a .jar
 * Extract fully qualified class names
 * Return them as a list
 *
 */
public class FileScanner {

    public FileScanner() {}

    private static Set<String> getClassNamesSet(Path artifactPath) {
        Set<String> classNames = new HashSet<>();

        try (JarFile jarFile = new JarFile(artifactPath.toFile())) {
            Enumeration<JarEntry> entries = jarFile.entries();

            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                if (entry.isDirectory())
                    continue;

                if (!entry.getName().endsWith(".class"))
                    continue;

                if (entry.getName().startsWith("META-INF/"))
                    continue;

                if (entry.getName().equals("module-info.class"))
                    continue;

                String className = entry.getName()
                        .replace(".class", "")
                        .replace("/", ".");

                classNames.add(className);
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to read jar file < " + artifactPath + " >", e);
        }

        return classNames;
    }

    private void printClassNames(List<String> classNames) {
        System.out.println("\n[INFO] Classes found in artifact file:\n" + classNames);
    }

    public List<String> scan(Path artifactPath) throws IOException {
        if (!Files.exists(artifactPath)) {
            throw new IllegalArgumentException("[ERROR] Artifact path < " +
                    artifactPath + " >, does not exist.");
        }

        if (!Files.isRegularFile(artifactPath)) {
            throw new IllegalArgumentException("[ERROR] Artifact path < " +
                    artifactPath + " >, is not a file.");
        }

        // TODO do not forget to add other extensions as well
        if (!artifactPath.toString().endsWith(".jar")) {
            throw new IllegalArgumentException("[ERROR] Artifact path < " +
                    artifactPath + " >, does not end in '.jar'.");
        }

        List<String> classNames = getClassNamesSet(artifactPath).stream().sorted().toList();
        printClassNames(classNames);

        return classNames;
    }
}
