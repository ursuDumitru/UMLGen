package org.main;

import java.nio.file.Path;
import java.util.List;

/** Config Responsibilities: - Store arguments/options parsed from CLI */
public class Config {

    private final Path artifactPath;
    private final List<String> ignorePatterns;
    private final boolean showPacket;
    private final boolean showMethods;
    private final boolean showAttributes;

    Config(
            Path artifactPath,
            List<String> ignorePatterns,
            boolean showPacket,
            boolean showMethods,
            boolean showAttributes) {
        this.artifactPath = artifactPath;
        this.ignorePatterns = ignorePatterns;
        this.showPacket = showPacket;
        this.showMethods = showMethods;
        this.showAttributes = showAttributes;

        printConfig();
    }

    public void printConfig() {
        System.out.println(
                "\n[INFO] Config Object:"
                        + "\n\tartifactPath = "
                        + artifactPath
                        + ",\n\tignorePatterns = "
                        + ignorePatterns
                        + ",\n\tshowPacket = "
                        + showPacket
                        + ",\n\tshowMethods = "
                        + showMethods
                        + ",\n\tshowAttributes = "
                        + showAttributes);
    }

    public boolean isFqcnIgnored(String fqcn) {
        if (ignorePatterns == null || ignorePatterns.isEmpty()) {
            return false;
        }

        for (String pattern : ignorePatterns) {
            if (pattern.endsWith(".*")) {
                String prefix = pattern.substring(0, pattern.length() - 1);
                if (fqcn.startsWith(prefix)) {
                    return true;
                }
            } else if (fqcn.equals(pattern)) {
                return true;
            }
        }
        return false;
    }

    public Path getArtifactPath() {
        return artifactPath;
    }

    public boolean isShowPacket() {
        return showPacket;
    }

    public boolean isShowMethods() {
        return showMethods;
    }

    public boolean isShowAttributes() {
        return showAttributes;
    }
}
