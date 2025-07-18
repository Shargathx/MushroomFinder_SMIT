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

    @Operation(
            summary = "Get all mushroom locations in GeoJSON format",
            description = "Returns a list of all locations as GeoJsonFeature objects. Throws 404 if no locations exist."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of all locations returned"),
            @ApiResponse(responseCode = "404", description = "No locations found")
    })
    @GetMapping("/locations")
    public ResponseEntity<List<GeoJsonFeature>> getAllLocationsGeoJson() {
        List<GeoJsonFeature> allMushroomLocations = locationService.findAllLocationsGeoJson();
        return ResponseEntity.ok(allMushroomLocations);
    }


    @Operation(
            summary = "Add a new mushroom location",
            description = "Saves the provided GeoJsonFeature as a new location and returns the saved entity."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Location created successfully")
    })
    @PostMapping("/locations")
    public ResponseEntity<GeoJsonFeature> addLocation(@RequestBody GeoJsonFeature geoJsonFeature) {
        GeoJsonFeature savedGeoJsonFeature = locationService.addLocation(geoJsonFeature);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedGeoJsonFeature);
    }

    @Operation(
            summary = "Update an existing mushroom location",
            description = "Updates the location with the given ID using the provided GeoJsonFeature. Returns the updated location."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Location updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid GeoJSON data"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @PutMapping("/locations/{locationId}")
    public ResponseEntity<Object> updateLocation(@PathVariable Integer locationId, @RequestBody GeoJsonFeature geoJsonFeature) {
        GeoJsonFeature updatedLocation = locationService.updateLocation(locationId, geoJsonFeature);
        return ResponseEntity.ok(updatedLocation);
    }

    @Operation(
            summary = "Delete a mushroom location by ID",
            description = "Deletes the location with the specified ID. Returns no content on success."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Location deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Location not found")
    })
    @DeleteMapping("/locations/{locationId}")
    public ResponseEntity<Object> deleteLocation(@PathVariable Integer locationId) {
        locationService.deleteLocation(locationId);
        return ResponseEntity.noContent().build();
    }
}