package io.solar.compiler;

import io.solar.compiler.exceptions.FileGeneratorException;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

public class ClassLoader {

    /**
     * Загрузка класса из файла
     *
     * @param root           корневой каталог
     * @param classReference ссылка на класс
     * @return - Загруженный класс
     */
    public static Class<?> loadClassFromFile(File root, String classReference) {
        try {
            URL url = root.toURI().toURL();
            URLClassLoader loader = new URLClassLoader(new URL[]{url});
            return Class.forName(
                    classReference.replaceAll("\\.class|\\.java", ""),
                    true, loader);
        } catch (MalformedURLException | ClassNotFoundException e) {
            throw new FileGeneratorException("can't find generated class", e);
        }
    }

    /**
     * Загрузка класса из памяти
     * @return загруженный класс
     */
    public static Class<?> loadClassFromMemory() {
        return null; // TODO: 23.04.2021 сейчас этой логики еще нет
    }
}
