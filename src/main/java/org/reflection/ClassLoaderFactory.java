package org.reflection;

import org.main.Config;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Path;

/**
 * ClassLoaderFactory Responsibilities: - Convert jar Path to URL - Build a URLClassLoader pointing
 * to that jar - Choose a parent loader
 */
public class ClassLoaderFactory {
    public ClassLoader create(Config config) throws MalformedURLException {
        Path artifactPath = config.getArtifactPath();
        URL artifactUrl = artifactPath.toUri().toURL();

        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null) {
            parent = ClassLoaderFactory.class.getClassLoader();
        }

        return new URLClassLoader(new URL[] {artifactUrl}, parent);
    }
}
