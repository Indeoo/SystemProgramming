package com.venherak.lab3.Exceptions;

public class SyntaxException extends Exception {

    public SyntaxException() {
        super();
    }

    public SyntaxException(String message, Throwable cause) {
        super(message, cause);
    }

    public SyntaxException(String message) {
        super(message);
    }

    public SyntaxException(Throwable cause) {
        super(cause);
    }
}
