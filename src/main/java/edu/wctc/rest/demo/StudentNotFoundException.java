package edu.wctc.rest.demo;

/**
 * A runtime (unchecked) exception. This class doesn't do
 * anything special, but it acts like a "marker" so our
 * application can detect when a student isn't found.
 */
public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String message) {
        super(message);
    }
}
