package com.mushroomfinder.persistence.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class AddLocationRequest {
    private LocationDto location;
    private String description;
}
