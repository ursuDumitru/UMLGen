package org.uml;

import java.util.List;

public class YumlWriter {
    public void outputInConsole(List<String> lines) {
        System.out.println("\n\n[INFO] Final Result:");
        for (String line : lines) {
            System.out.println(line);
        }
    }
}
