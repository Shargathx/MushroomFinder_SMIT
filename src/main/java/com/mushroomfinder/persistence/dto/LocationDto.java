package com.mushroomfinder.persistence.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationDto {
    @Schema(example = "25.123456", description = "Longitude (X coordinate)")
    private double x;

    @Schema(example = "58.123456", description = "Latitude (Y coordinate)")
    private double y;
}
