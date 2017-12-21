package com.venherak.compiler.exceptions;

public class SemanticException extends Exception {

    public SemanticException() {
        super();
    }

    public SemanticException(String message, Throwable cause) {
        super(message, cause);
    }

    public SemanticException(String message) {
        super(message);
    }

    public SemanticException(Throwable cause) {
        super(cause);
    }
}
