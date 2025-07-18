package com.mushroomfinder.infrastructure.exception;

import lombok.Getter;

@Getter
public class InvalidGeoJsonFormatException extends RuntimeException {
    private final String message;
    private final Integer errorCode;

    public InvalidGeoJsonFormatException(String message, Integer errorCode) {
        super(message);
        this.message = message;
        this.errorCode = errorCode;
    }
}