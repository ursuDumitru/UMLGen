package org.main;

import org.reflection.ReflectionAnalyzer;
import org.reflection.model.UmlModel;
import org.uml.YumlRenderer;
import org.uml.YumlWriter;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws IOException {

        // 1. Parse CLI arguments
        ArgParser parser = new ArgParser();
        Config config = parser.parse(args);

        // 2. Scan artifact for class names
        FileScanner scanner = new FileScanner();
        List<String> classNames = scanner.scan(config.getArtifactPath());

        // 3. Analyze classes using Reflection API
        ReflectionAnalyzer analyzer = new ReflectionAnalyzer();
        UmlModel umlModel = analyzer.analyze(classNames, config);

        umlModel.printClassesByName();
//
//        // 4. Convert extracted data to yUML
//        YumlRenderer renderer = new YumlRenderer();
//        List<String> yumlLines = renderer.render(umlModel, config);
//
//        // 5. Output result
//        YumlWriter writer = new YumlWriter();
//        writer.write(yumlLines);
    }
}