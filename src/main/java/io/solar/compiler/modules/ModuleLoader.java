package io.solar.compiler.modules;

import io.solar.compiler.Compiler;
import io.solar.utilites.SearchFiles;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModuleLoader {
    private final List<Module> modules = new ArrayList<>();


    public ModuleLoader(String sourceDir) throws IOException {
        SearchFiles search = new SearchFiles("", "jar");
        List<String> result = search.flat(new File(sourceDir));
        new Compiler()
                .setClassesLocation(new File(""))
                .sourceLocation(new File(""))
                .compile();

    }
}
