package io.solar.compiler;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Compiler {
    private File classes;
    private File sources;

    public Compiler setClassesLocation(File classesLocation) {
        this.classes = classesLocation;
        return this;
    }

    public Compiler sourceLocation(File sourceLocation) {
        this.sources = sourceLocation;
        return this;
    }

    public Compiler compile() throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        StandardJavaFileManager fileManager = compiler.getStandardFileManager(diagnostics, Locale.getDefault(), null);
        List<JavaFileObject> javaObjects = scanRecursivelyForJavaObjects(sources, fileManager);

        if (javaObjects.size() == 0) {
            throw new IOException("There are no source files to compile in " + sources.getAbsolutePath());
        }
        String[] compileOptions = new String[]{"-d", classes.getAbsolutePath(), "-encoding", "UTF-8"};
        Iterable<String> compilationOptions = Arrays.asList(compileOptions);

        JavaCompiler.CompilationTask compilerTask = compiler.getTask(null, fileManager, diagnostics, compilationOptions, null, javaObjects);

        if (!compilerTask.call()) {
            for (Diagnostic<?> diagnostic : diagnostics.getDiagnostics()) {
                System.err.format("Error on line %d in %s", diagnostic.getLineNumber(), diagnostic);
            }
            throw new IOException("Could not compile project");
        }
        return this;
    }

    private List<JavaFileObject> scanRecursivelyForJavaObjects(File dir, StandardJavaFileManager fileManager) {
        List<JavaFileObject> javaObjects = new LinkedList<>();
        File[] files = dir.listFiles();
        for (File file : Objects.requireNonNull(files)) {
            if (file.isDirectory()) {
                javaObjects.addAll(scanRecursivelyForJavaObjects(file, fileManager));
            } else if (file.isFile() && file.getName().toLowerCase().endsWith(".java")) {
                javaObjects.add(readJavaObject(file, fileManager));
            }
        }
        return javaObjects;
    }


    private JavaFileObject readJavaObject(File file, StandardJavaFileManager fileManager) {
        Iterable<? extends JavaFileObject> javaFileObjects = fileManager.getJavaFileObjects(file);
        Iterator<? extends JavaFileObject> it = javaFileObjects.iterator();
        if (it.hasNext()) {
            return it.next();
        }
        throw new RuntimeException("Could not load " + file.getAbsolutePath() + " java file object");
    }
}
