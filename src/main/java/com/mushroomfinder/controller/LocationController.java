package com.mushroomfinder.controller;

import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200")

public class LocationController {

    private final LocationService locationService;

    @GetMapping("/location/{locationId}")
    public ResponseEntity<GeoJsonFeature> getLocationGeoJson(@PathVariable Integer locationId) {
        GeoJsonFeature geoJsonFeature = locationService.findLocationGeoJson(locationId);
        return ResponseEntity.ok(geoJsonFeature);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<GeoJsonFeature>> getAllLocationsGeoJson() {
        List<GeoJsonFeature> allMushroomLocations = locationService.findAllLocationsGeoJson();
        return ResponseEntity.ok(allMushroomLocations);
    }

    @PostMapping("/location")
    public ResponseEntity<GeoJsonFeature> addLocation(@RequestBody GeoJsonFeature geoJsonFeature) {
        GeoJsonFeature savedGeoJsonFeature = locationService.addLocation(geoJsonFeature);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGeoJsonFeature);
    }

    @PutMapping("/location/{locationId}")
    public ResponseEntity<Object> updateLocation(@PathVariable Integer locationId, @RequestBody GeoJsonFeature geoJsonFeature) {
        locationService.updateLocation(locationId, geoJsonFeature);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/location/{locationId}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Integer locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}
