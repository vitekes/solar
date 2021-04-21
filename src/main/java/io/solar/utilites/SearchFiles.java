package io.solar.utilites;

import java.io.File;
import java.util.*;

public class SearchFiles {
    private final List<String> recursiveFiles = new ArrayList<>();
    private final String leadSymbols;
    private final String extension;

    public SearchFiles(String leadSymbols, String extension) {
        this.leadSymbols = leadSymbols;
        this.extension = extension;
    }

    public List<String> flat(File root) {
        if (root == null) return null;

        String[] files = root.list((dir, name) -> {
            if (!leadSymbols.isEmpty() && !extension.isEmpty())
                return name.startsWith(leadSymbols) && name.endsWith(".".concat(extension));
            if (!leadSymbols.isEmpty())
                return name.startsWith(leadSymbols);
            if (!extension.isEmpty())
                return name.endsWith(".".concat(extension));
            return true;
        });

        if (files != null)
            return Arrays.asList(files);
        return Collections.emptyList();
    }

    public void recursive(File root) {
        for (File file : Objects.requireNonNull(root.listFiles())) {
            if (file.isDirectory()) {
                recursive(file);
            }
        }
        if (root.isDirectory()) {
            recursiveFiles.addAll(flat(root));
        }
    }

    public List<String> getRecursiveFiles() {
        return recursiveFiles;
    }
}
