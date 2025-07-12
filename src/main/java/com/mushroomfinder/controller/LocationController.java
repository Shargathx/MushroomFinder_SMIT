package com.mushroomfinder.controller;

import com.mushroomfinder.MushroomLocation;
import com.mushroomfinder.persistence.LocationMapper;
import com.mushroomfinder.persistence.LocationRepository;
import com.mushroomfinder.persistence.dto.AddLocationRequest;
import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor

public class LocationController {

    private final LocationService locationService;
    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

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
    public ResponseEntity<Object> updateLocation(@PathVariable Integer locationId, @RequestBody AddLocationRequest addLocationRequest) {
        locationService.updateLocation(locationId, addLocationRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/location/{locationId}")
    public void deleteLocation(@PathVariable Integer locationId) {
        locationService.deleteLocation(locationId);
    }
}

// TODO: test with postman
