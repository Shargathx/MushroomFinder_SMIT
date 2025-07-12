package com.mushroomfinder.controller;

import com.mushroomfinder.MushroomLocation;
import com.mushroomfinder.persistence.MushroomLocationInfo;
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

    @PostMapping("/location")
    public ResponseEntity<MushroomLocation> addLocation(@RequestBody AddLocationRequest addLocationRequest) {
        MushroomLocation savedMushroomLocation = locationService.addLocation(addLocationRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMushroomLocation);
    }




    // TODO: patch/update location
    // TODO: delete location
}
