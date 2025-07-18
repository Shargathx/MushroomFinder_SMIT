package com.mushroomfinder.controller;

import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(
            summary = "Get a single mushroom location by its ID in GeoJSON format",
            description = "Returns the location as a GeoJsonFeature object. Throws 404 if location not found."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location found and returned"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @GetMapping("/locations/{locationId}")
    public ResponseEntity<GeoJsonFeature> getLocationGeoJson(@PathVariable Integer locationId) {
        GeoJsonFeature geoJsonFeature = locationService.findLocationGeoJson(locationId);
        return ResponseEntity.ok(geoJsonFeature);
    }

    @GetMapping("/locations")
    public ResponseEntity<List<GeoJsonFeature>> getAllLocationsGeoJson() {
        List<GeoJsonFeature> allMushroomLocations = locationService.findAllLocationsGeoJson();
        return ResponseEntity.ok(allMushroomLocations);
    }

    @PostMapping("/locations")
    public ResponseEntity<GeoJsonFeature> addLocation(@RequestBody GeoJsonFeature geoJsonFeature) {
        GeoJsonFeature savedGeoJsonFeature = locationService.addLocation(geoJsonFeature);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGeoJsonFeature);
    }

    @PutMapping("/locations/{locationId}")
    public ResponseEntity<Object> updateLocation(@PathVariable Integer locationId, @RequestBody GeoJsonFeature geoJsonFeature) {
        locationService.updateLocation(locationId, geoJsonFeature);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Integer locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}
