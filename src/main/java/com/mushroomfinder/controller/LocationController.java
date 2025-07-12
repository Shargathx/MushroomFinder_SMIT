package com.mushroomfinder.controller;

import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class LocationController {

    private final LocationService locationService;

    @GetMapping("/{id}/geojson")
    public ResponseEntity<GeoJsonFeature> getLocationGeoJson(@PathVariable Integer id) {
        GeoJsonFeature geoJsonFeature = locationService.findLocationGeoJson(id);
        return ResponseEntity.ok(geoJsonFeature);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<GeoJsonFeature>> getAllLocationsGeoJson() {
        List<GeoJsonFeature> allMushroomLocations = locationService.findAllLocationsGeoJson();
        return ResponseEntity.ok(allMushroomLocations);
    }


    // TODO: add location
    // TODO: patch/update location
    // TODO: delete location
}
