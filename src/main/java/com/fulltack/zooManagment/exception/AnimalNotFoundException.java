package com.fulltack.zooManagment.exception;

public class AnimalNotFoundException extends RuntimeException {

    public AnimalNotFoundException(String message) {
        super(message);
    }

    public AnimalNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}