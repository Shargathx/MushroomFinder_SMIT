package com.mushroomfinder.service;

import com.mushroomfinder.MushroomLocation;
import com.mushroomfinder.persistence.LocationMapper;
import com.mushroomfinder.persistence.LocationRepository;
import com.mushroomfinder.persistence.MushroomLocationInfo;
import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.persistence.dto.GeoJsonGeometry;
import com.mushroomfinder.persistence.dto.LocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class LocationService {

    private final LocationRepository mushroomLocationRepository;
    private final LocationMapper locationMapper;

    public MushroomLocationInfo findLocation(Integer locationId) {
        MushroomLocation mushroomLocation = mushroomLocationRepository.findById(locationId).orElseThrow(() -> new RuntimeException("No such location exists"));
        MushroomLocationInfo mushroomLocationInfo = locationMapper.toMushroomLocationDto(mushroomLocation);
        return mushroomLocationInfo;
    }

    public GeoJsonFeature findLocationGeoJson(Integer locationId) {
        MushroomLocation mushroomLocation = mushroomLocationRepository.findById(locationId)
                .orElseThrow(() -> new RuntimeException("No such location exists"));
        MushroomLocationInfo info = locationMapper.toMushroomLocationDto(mushroomLocation);

        return convertToGeoJsonFeature(info);
    }

    public GeoJsonFeature convertToGeoJsonFeature(MushroomLocationInfo locationInfo) {
        LocationDto location = locationInfo.getLocation();
        GeoJsonGeometry geometry = new GeoJsonGeometry(location.getX(), location.getY());

        Map<String, Object> properties = new HashMap<>();
        properties.put("id", locationInfo.getId());
        properties.put("description", locationInfo.getDescription());

        return new GeoJsonFeature(geometry, properties);
    }
}
