package com.gramconnect.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VillageRequest {

    @NotBlank(message = "Village name is required")
    private String name;

    @NotBlank(message = "Panchayat name is required")
    private String panchayatName;

    @NotBlank(message = "District is required")
    private String district;

    @NotBlank(message = "State is required")
    private String state;

    private String pincode;

    private Double latitude;

    private Double longitude;
}