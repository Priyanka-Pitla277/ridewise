package com.airtribe.ridewise.exception;

import java.io.Serial;

/**
 * @author Priyanka Pitla
 */
public class NoDriverAvailableException extends RuntimeException {

    // 1. Unique serial version ID for serialization safety
    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

    // 2. Default constructor
    public NoDriverAvailableException() {
        super();
    }

    // 3. Constructor that accepts a custom error message (Most Common)
    public NoDriverAvailableException(String message) {
        super(message);
    }

    // 4. Constructor that accepts a custom message AND another root cause exception
    public NoDriverAvailableException(String message, Throwable cause) {
        super(message, cause);
    }
}