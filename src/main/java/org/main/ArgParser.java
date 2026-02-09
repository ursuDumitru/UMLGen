package org.main;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * ArgParser Responsibilities:
 * - Take arguments/options from CLI
 * - Create a Config object with parsed info
 *
 */
public class ArgParser {

    private String[] args;
    private Set<String> seenOptions = new HashSet<>();

    public Config parse(String[] args) {
        this.args = args;
        checkForHelp();

        Path artifactPath = getArtifactPath();
        List<String> ignoredPatterns = new ArrayList<>();
        boolean showPacket = false;
        boolean showMethods = false;
        boolean showAttributes = false;

        // TODO also check for duped options
        int optionPosition = 1;
        while (optionPosition < args.length) {

            String token = args[optionPosition];
            checkTokenStartsWithDDash(token);

            switch (token) {
                case "--ignore" -> ignoredPatterns = getIgnoreOption(optionPosition, token);
                case "--show-package" -> showPacket = getBooleanOption(optionPosition, token);
                case "--show-methods" -> showMethods = getBooleanOption(optionPosition, token);
                case "--show-attributes" -> showAttributes = getBooleanOption(optionPosition, token);
                default -> throw new IllegalArgumentException("[ERROR] Unexpected option: " + token);
            }

            optionPosition += 2;
        }

        return new Config(artifactPath, ignoredPatterns, showPacket, showMethods, showAttributes);
    }

    private void checkForHelp() {
        String argsToStringArray = Arrays.toString(args);
        if (args.length != 0 && (argsToStringArray.contains("--help") || argsToStringArray.contains("-h"))) {
            System.out.println(
                    """
                            ************************************************
                            [INFO] Help option detected, ignore normal flow.
                            
                            Usage: java -jar ArgParser.jar <artifact path> [options]
                            
                            \t<artifact path>\t\t\t\tPath to the .jar file.
                            Options:
                            \t--ignore <pattern1,pattern2,...>\tSet of classes to be ignored (ex. java.lang.*), \
                            separated by comma, no spaces between them
                            \t--show-package <yes|no>\t\t\tInclude package path for classes
                            \t--show-methods <yes|no>\t\t\tInclude method names for classes
                            \t--show-attributes <yes|no>\t\tInclude attribute names for classes
                            \t--help|-h\t\t\t\tDisplay help information
                            ************************************************"""
            );
            System.exit(0);
        }
    }

    private void printRawArgs() {
        int j = 0;
        for (String arg : args) {
            System.out.println("[INFO] args[" + j + "]: " + arg);
            j += 1;
        }
    }

    private Path getArtifactPath() {
        if (args.length == 0) {
            throw new IllegalArgumentException("[ERROR] No arguments provided.");
        }

        return Paths.get(args[0]);
    }

    private List<String> getIgnoreOption(int optionPosition, String token) {
        checkSeenOption(token);
        checkTokenHasValues(optionPosition + 1, token);

        String value = args[optionPosition + 1];
        checkValueDoesNotStartWithDash(value, token);

        String[] values = value.split(",");
        List<String> ignoredPatterns = new ArrayList<>();
        for (String v : values) {
            String trimmedV = v.trim();
            if (!trimmedV.isEmpty()) {
                ignoredPatterns.add(trimmedV);
            }
        }

        seenOptions.add(token);
        return ignoredPatterns;
    }

    private boolean getBooleanOption(int optionPosition, String token) {
        checkSeenOption(token);
        checkTokenHasValues(optionPosition + 1, token);

        String value = args[optionPosition + 1];
        checkValueDoesNotStartWithDash(value, token);

        String[] values = value.split(",");
        checkBoolOptionHasOnlyOneValue(values, token);

        seenOptions.add(token);
        if (values[0].equals("yes")) {
            return true;
        } else if (values[0].equals("no")) {
            return false;
        } else {
            throw new IllegalArgumentException("[ERROR] Unknown value < " +
                    values[0] + " > for option < " + token + " > (yes|no only)!");
        }
    }

    private void checkSeenOption(String token) {
        if (seenOptions.contains(token)) {
            throw new IllegalArgumentException("[ERROR] Seen more than one option < " +
                    token + " >");
        }
    }

    private void checkTokenStartsWithDDash(String token) {
        if (!token.startsWith("--")) {
            throw new IllegalArgumentException("[ERROR] Unexpected argument < " +
                    token + " >");
        }
    }

    private void checkTokenHasValues(int optionPosition, String token) {
        if (optionPosition >= args.length) {
            throw new IllegalArgumentException("[ERROR] < " + token + " > option expects a value!");
        }
    }

    private void checkValueDoesNotStartWithDash(String value, String token) {
        if (value.startsWith("-")) {
            throw new IllegalArgumentException("[ERROR] invalid value < " + value +
                    " > for option < " + token + " >!");
        }
    }

    private void checkBoolOptionHasOnlyOneValue(String[] values, String token) {
        if (values.length != 1) {
            throw new IllegalArgumentException("[ERROR] the option < " + token +
                    " > expects only one value, < yes|no >" + " instead of < " +
                    Arrays.toString(values) + " >!");
        }
    }
}