package com.mushroomfinder.infrastructure.error;

import lombok.Getter;

@Getter
public enum Error {
    MUSHROOM_LOCATION_NOT_FOUND("No such location in the database", 404),
    NO_MUSHROOM_LOCATIONS_IN_DATABASE("There are currently no mushroom locations in the database", 404),
    INVALID_GEOJSON_FORMAT("Current GeoJson input does not match correct GeoJson format", 400);

    private final String message;
    private final int errorCode;

    Error(String message, int errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}