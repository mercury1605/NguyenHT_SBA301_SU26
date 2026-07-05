package com.lab04.orchidmanagement.exceptions;

public class OrchidNotFoundException extends RuntimeException {

    public OrchidNotFoundException(String message) {
        super(message);
    }

    public OrchidNotFoundException(Integer id) {
        super("Orchid not found with id: " + id);
    }
}