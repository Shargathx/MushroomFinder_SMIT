package com.mushroomfinder;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mushroomfinder.persistence.dto.LocationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;


@Getter
@Setter
@Entity
@Table(name = "mushroom_locations", schema = "mushroom_finder")
public class MushroomLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @JsonIgnore
    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    public LocationDto getLocationDto() {
        return new LocationDto(location.getX(), location.getY());
    }

    @JsonProperty("location")
    public void setLocationDto(LocationDto dto) {
        this.location = geometryFactory.createPoint(new Coordinate(dto.getX(), dto.getY()));
    }

    @Transient
    @JsonIgnore
    private GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;
}