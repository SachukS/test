package com.sachuk.TestTask.exception;

public class ResourceAlreadyExistException extends RuntimeException {
    public ResourceAlreadyExistException() {
        super();
    }

    public ResourceAlreadyExistException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ResourceAlreadyExistException(final String message) {
        super(message);
    }

    public ResourceAlreadyExistException(final Throwable cause) {
        super(cause);
    }
}
