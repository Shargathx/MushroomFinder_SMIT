package com.mushroomfinder.persistence.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Map;

@Data
@AllArgsConstructor
public class GeoJsonFeature {
    private final String type = "Feature";
    private GeoJsonGeometry geometry;
    private Map<String, Object> properties;
}
