package com.venherak.compiler.exceptions;

public class WrongTokenException extends Exception {

    public WrongTokenException() {
        super();
    }

    public WrongTokenException(String message, Throwable cause) {
        super(message, cause);
    }

    public WrongTokenException(String message) {
        super(message);
    }

    public WrongTokenException(Throwable cause) {
        super(cause);
    }
}
