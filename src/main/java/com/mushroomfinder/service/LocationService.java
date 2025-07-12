package com.mushroomfinder.service;

import com.mushroomfinder.MushroomLocation;
import com.mushroomfinder.persistence.LocationMapper;
import com.mushroomfinder.persistence.LocationRepository;
import com.mushroomfinder.persistence.MushroomLocationInfo;
import com.mushroomfinder.persistence.dto.AddLocationRequest;
import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.persistence.dto.GeoJsonGeometry;
import com.mushroomfinder.persistence.dto.LocationDto;
import jakarta.persistence.EntityNotFoundException;
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
        MushroomLocation mushroomLocation = locationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("No such location exists"));
        MushroomLocationInfo locationInfo = locationMapper.toMushroomLocationDto(mushroomLocation);

        return convertToGeoJsonFeature(locationInfo);
    }

    public List<GeoJsonFeature> findAllLocationsGeoJson() {
        List<MushroomLocation> allMushroomLocations = locationRepository.findAll();
        if (allMushroomLocations.isEmpty()) {
            throw new RuntimeException("No locations exist yet");
        }
        return allMushroomLocations.stream()
                .map(locationMapper::toMushroomLocationDto)
                .map(this::convertToGeoJsonFeature)
                .collect(Collectors.toList());
    }

    public MushroomLocation addLocation(AddLocationRequest addLocationRequest) {
        MushroomLocation mushroomLocation = locationMapper.fromCreateLocationRequest(addLocationRequest);
        MushroomLocation savedMushroomLocation = locationRepository.save(mushroomLocation);
        return savedMushroomLocation;
    }

    public void updateLocation(Integer locationId, AddLocationRequest addLocationRequest) {
        MushroomLocation mushroomLocation = locationRepository.findById(locationId).orElseThrow(() -> new EntityNotFoundException("No such location exists"));
        locationMapper.partialUpdate(mushroomLocation, addLocationRequest);
        locationRepository.save(mushroomLocation);
    }

    public void deleteLocation(Integer locationId) {
        if(!locationRepository.existsById(locationId)) {
            throw new EntityNotFoundException("Location with id " + locationId + " does not exist");
        }
        locationRepository.deleteById(locationId);
    }

    public GeoJsonFeature convertToGeoJsonFeature(MushroomLocationInfo locationInfo) {
        LocationDto location = locationInfo.getLocation();
        GeoJsonGeometry geometry = new GeoJsonGeometry(location.getX(), location.getY());

        Map<String, Object> properties = new HashMap<>();
//        properties.put("id", locationInfo.getId()); // comment this in, if Id is needed in return
        properties.put("description", locationInfo.getDescription());

        return new GeoJsonFeature(geometry, properties);
    }
}
