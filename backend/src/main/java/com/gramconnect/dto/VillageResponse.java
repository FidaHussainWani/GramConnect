package com.gramconnect.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VillageResponse {

    private Long id;
    private String name;
    private String panchayatName;
    private String district;
    private String state;
    private String pincode;
    private Double latitude;
    private Double longitude;
}