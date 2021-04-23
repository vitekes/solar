package io.solar.compiler.exceptions;

public class FileGeneratorException extends RuntimeException {
    public FileGeneratorException(String message, Exception e) {
        super(message, e);
    }
}
