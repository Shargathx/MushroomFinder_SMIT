package com.mushroomfinder.persistence;

import com.mushroomfinder.MushroomLocation;
import com.mushroomfinder.persistence.dto.AddLocationRequest;
import com.mushroomfinder.persistence.dto.GeoJsonFeature;
import com.mushroomfinder.persistence.dto.LocationDto;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.mapstruct.*;

import java.util.Map;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LocationMapper {

    @Mapping(source = "id", target = "id")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    MushroomLocationInfo toMushroomLocationDto(MushroomLocation mushroomLocation);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    MushroomLocation toMushroomLocation(MushroomLocationInfo mushroomLocationInfo);


    @Mapping(source = "location", target = "location")
    @Mapping(source = "description", target = "description")
    MushroomLocation partialUpdate(@MappingTarget MushroomLocation mushroomLocation, AddLocationRequest addLocationRequest);

    @Mapping(target = "geometry", expression = "java(new GeoJsonGeometry(mushroomLocation.getLocation().getX(), mushroomLocation.getLocation().getY()))")
    @Mapping(target = "properties", expression = "java(java.util.Map.of(\"description\", mushroomLocation.getDescription()))")
    GeoJsonFeature toGeoJsonFeature(MushroomLocation mushroomLocation);

    default MushroomLocation fromGeoJsonFeature(GeoJsonFeature geoJsonFeature) {
        MushroomLocation mushroomLocation = new MushroomLocation();
        double[] coords = geoJsonFeature.getGeometry().getCoordinates();
        Coordinate coordinate = new Coordinate(coords[0], coords[1]);
        Point point = new GeometryFactory(new PrecisionModel(), 4326).createPoint(coordinate);
        mushroomLocation.setLocation(point);

        Object props = geoJsonFeature.getProperties();
        if (props instanceof Map<?, ?> propsMap) {
            Object desc = propsMap.get("description");
            if (desc != null) {
                mushroomLocation.setDescription(desc.toString());
            }
        }
        return mushroomLocation;
    }


    GeometryFactory toGeoJsonFeature(GeoJsonFeature geoJsonFeature);

    default LocationDto map(Point point) {
        if (point == null) {
            return null;
        }
        LocationDto dto = new LocationDto();
        dto.setX(point.getX());
        dto.setY(point.getY());
        return dto;
    }

    default Point map(LocationDto dto) {
        if (dto == null) {
            return null;
        }
        GeometryFactory factory = new GeometryFactory(new PrecisionModel(), 4326); // WGS84 SRID
        return factory.createPoint(new Coordinate(dto.getX(), dto.getY()));
    }
}