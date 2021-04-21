package io.solar.compiler.modules;

import io.solar.utilites.SearchFiles;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    private final List<Module> modules = new ArrayList<>();


    public ModuleLoader(String sourceDir) {
        SearchFiles search = new SearchFiles("", "jar");
        List<String> result = search.flat(new File(sourceDir));
    }
}
