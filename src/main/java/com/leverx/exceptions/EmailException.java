package com.leverx.exceptions;

public class EmailException extends Exception {
    public EmailException(String errorMessage) {
        super(errorMessage);
    }
}