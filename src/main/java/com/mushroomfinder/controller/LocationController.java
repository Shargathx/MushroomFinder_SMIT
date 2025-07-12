package com.mushroomfinder.controller;

import com.mushroomfinder.persistence.MushroomLocationInfo;
import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class LocationController {

    private final LocationService locationService;


    @GetMapping("/{locationId}")
    @Operation(summary = "Leiab kõik asukohad vastavalt location Id-le")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK"),
    })

    public MushroomLocationInfo findLocation(@PathVariable Integer locationId) {
        return locationService.findLocation(locationId);
    }


    @GetMapping("/{id}/geojson")
    public ResponseEntity<GeoJsonFeature> getLocationGeoJson(@PathVariable Integer id) {
        GeoJsonFeature geoJsonFeature = locationService.findLocationGeoJson(id);
        return ResponseEntity.ok(geoJsonFeature);
    }








    // TODO: get single location
    // TODO: get all locations
    // TODO: add location
    // TODO: patch/update location
    // TODO: delete location
}
