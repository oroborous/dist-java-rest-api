package edu.wctc.exception;

/**
 * A runtime (unchecked) exception. This class doesn't do
 * anything special, but it acts like a "marker" so our
 * application can detect when a donut isn't found.
 */
public class DonutNotFoundException extends RuntimeException {
    public DonutNotFoundException(String message) {
        super(message);
    }
}
