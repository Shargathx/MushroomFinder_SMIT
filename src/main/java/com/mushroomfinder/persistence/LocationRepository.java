package com.mushroomfinder.persistence;

import com.mushroomfinder.MushroomLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.*;
import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<MushroomLocation, Integer> {
    Optional<MushroomLocation> findById(Integer id);
    List<MushroomLocation> findAllById(Integer id);
}