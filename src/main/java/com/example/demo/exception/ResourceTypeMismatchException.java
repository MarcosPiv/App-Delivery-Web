package com.example.demo.exception;

public class ResourceTypeMismatchException extends RuntimeException {
    public ResourceTypeMismatchException(String message) {
        super(message);
    }
}
