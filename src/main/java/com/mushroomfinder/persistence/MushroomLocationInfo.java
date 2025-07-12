package com.mushroomfinder.persistence;

import com.mushroomfinder.persistence.dto.LocationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MushroomLocationInfo {
    Integer id;
    private LocationDto location;
    String description;
}