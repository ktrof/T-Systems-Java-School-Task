package org.tsystems.javaschool.exception;

public class SBBException extends RuntimeException {

    public SBBException(String message) {
        super(message);
    }

    public SBBException(String message, Throwable cause) {
        super(message, cause);
    }

}
