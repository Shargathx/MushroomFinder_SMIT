package com.mushroomfinder.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoJsonGeometry {
    private final String type = "Point";
    private double[] coordinates;

    public GeoJsonGeometry(double x, double y) {
        this.coordinates = new double[] { x, y };
    }
}
