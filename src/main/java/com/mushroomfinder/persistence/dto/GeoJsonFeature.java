package com.mushroomfinder.persistence.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GeoJsonFeature {
    private final String type = "Feature";
    private GeoJsonGeometry geometry;
    private Object properties;
}
