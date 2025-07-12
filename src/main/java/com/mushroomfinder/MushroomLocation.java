package com.mushroomfinder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.locationtech.jts.geom.Point;

@Getter
@Setter
@Entity
@Table(name = "mushroom_locations", schema = "mushroom_finder")
public class MushroomLocation {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(columnDefinition = "geometry(Point,4326)")
    private Point location;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;
}