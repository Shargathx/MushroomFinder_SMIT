package com.mushroomfinder.service;

import com.mushroomfinder.MushroomLocation;
import com.mushroomfinder.infrastructure.error.Error;
import com.mushroomfinder.infrastructure.exception.DataNotFoundException;
import com.mushroomfinder.infrastructure.exception.InvalidGeoJsonFormatException;
import com.mushroomfinder.persistence.LocationMapper;
import com.mushroomfinder.persistence.LocationRepository;
import com.mushroomfinder.persistence.MushroomLocationInfo;
import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.persistence.dto.GeoJsonGeometry;
import com.mushroomfinder.persistence.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class LocationService {

    private final LocationMapper locationMapper;
    private final LocationRepository locationRepository;

    public GeoJsonFeature findLocationGeoJson(Integer locationId) {
        return getLocationAsGeoJson(locationId);
    }

    public List<GeoJsonFeature> findAllLocationsGeoJson() {
        return fetchAllGeoJsonLocations();
    }

    public GeoJsonFeature addLocation(GeoJsonFeature geoJsonFeature) {
        return convertAndSaveGeoJsonFeature(geoJsonFeature);
    }

    public GeoJsonFeature updateLocation(Integer locationId, GeoJsonFeature geoJsonFeature) {
        updateLocationFromGeoJsonData(locationId, geoJsonFeature);
        return geoJsonFeature;
    }

    public void deleteLocation(Integer locationId) {
        verifyLocationExists(locationId);
        locationRepository.deleteById(locationId);
    }

    public GeoJsonFeature convertToGeoJsonFeature(MushroomLocationInfo locationInfo) {
        return mapLocationInfoToGeoJsonFeature(locationInfo);
    }

    private GeoJsonFeature getLocationAsGeoJson(Integer locationId) {
        MushroomLocation mushroomLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new DataNotFoundException(Error.MUSHROOM_LOCATION_NOT_FOUND.getMessage(), Error.MUSHROOM_LOCATION_NOT_FOUND.getErrorCode()));
        MushroomLocationInfo locationInfo = locationMapper.toMushroomLocationDto(mushroomLocation);
        return convertToGeoJsonFeature(locationInfo);
    }

    private static void validateLocationListNotEmpty(List<MushroomLocation> allMushroomLocations) {
        if (allMushroomLocations.isEmpty()) {
            throw new DataNotFoundException(Error.NO_MUSHROOM_LOCATIONS_IN_DATABASE.getMessage(), Error.NO_MUSHROOM_LOCATIONS_IN_DATABASE.getErrorCode());
        }
    }

    private List<GeoJsonFeature> mapLocationsToGeoJsonFeatures(List<MushroomLocation> allMushroomLocations) {
        return allMushroomLocations.stream()
                .map(locationMapper::toMushroomLocationDto)
                .map(this::convertToGeoJsonFeature)
                .collect(Collectors.toList());
    }

    private List<GeoJsonFeature> fetchAllGeoJsonLocations() {
        List<MushroomLocation> allMushroomLocations = locationRepository.findAll();
        validateLocationListNotEmpty(allMushroomLocations);
        return mapLocationsToGeoJsonFeatures(allMushroomLocations);
    }

    private GeoJsonFeature convertAndSaveGeoJsonFeature(GeoJsonFeature geoJsonFeature) {
        MushroomLocation mushroomLocation = locationMapper.fromGeoJsonFeature(geoJsonFeature);
        MushroomLocation savedMushroomLocation = locationRepository.save(mushroomLocation);
        return locationMapper.toGeoJsonFeature(savedMushroomLocation);
    }

    private void updateDescriptionFromGeoJson(GeoJsonFeature geoJsonFeature, MushroomLocation mushroomLocation) {
        Object description = geoJsonFeature.getProperties().get("description");
        if (description != null) {
            mushroomLocation.setDescription(description.toString());
        }
    }

    private MushroomLocation updateLocationFromGeoJsonData(Integer locationId, GeoJsonFeature geoJsonFeature) {
        MushroomLocation mushroomLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new DataNotFoundException(Error.MUSHROOM_LOCATION_NOT_FOUND.getMessage(), Error.MUSHROOM_LOCATION_NOT_FOUND.getErrorCode()));
        double[] coords = geoJsonFeature.getGeometry().getCoordinates();
        if (coords == null || coords.length != 2) {
            throw new InvalidGeoJsonFormatException(Error.INVALID_GEOJSON_FORMAT.getMessage(), Error.INVALID_GEOJSON_FORMAT.getErrorCode());
        }
        LocationDto locationDto = new LocationDto(coords[0], coords[1]);
        mushroomLocation.setLocationDto(locationDto);
        updateDescriptionFromGeoJson(geoJsonFeature, mushroomLocation);
        locationRepository.save(mushroomLocation);
        return mushroomLocation;
    }

    private void verifyLocationExists(Integer locationId) {
        if (!locationRepository.existsById(locationId)) {
            throw new DataNotFoundException(Error.MUSHROOM_LOCATION_NOT_FOUND.getMessage(), Error.MUSHROOM_LOCATION_NOT_FOUND.getErrorCode());
        }
    }

    private static GeoJsonFeature mapLocationInfoToGeoJsonFeature(MushroomLocationInfo locationInfo) {
        LocationDto location = locationInfo.getLocation();
        GeoJsonGeometry geometry = new GeoJsonGeometry(location.getX(), location.getY());

        Map<String, Object> properties = new HashMap<>();
//        properties.put("id", locationInfo.getId()); // <- comment this in, if Id is needed in return
        properties.put("description", locationInfo.getDescription());
        return new GeoJsonFeature(geometry, properties);
    }
}