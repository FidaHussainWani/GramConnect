package com.gramconnect.service;

import com.gramconnect.dto.VillageRequest;
import com.gramconnect.dto.VillageResponse;
import com.gramconnect.entity.Village;
import com.gramconnect.repository.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VillageService {

    private final VillageRepository villageRepository;

    public VillageResponse createVillage(VillageRequest request) {

        Village village = Village.builder()
                .name(request.getName())
                .panchayatName(request.getPanchayatName())
                .district(request.getDistrict())
                .state(request.getState())
                .pincode(request.getPincode())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .build();

        Village savedVillage = villageRepository.save(village);

        return mapToResponse(savedVillage);
    }

    public List<VillageResponse> getAllVillages() {

        return villageRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private VillageResponse mapToResponse(Village village) {

        return VillageResponse.builder()
                .id(village.getId())
                .name(village.getName())
                .panchayatName(village.getPanchayatName())
                .district(village.getDistrict())
                .state(village.getState())
                .pincode(village.getPincode())
                .latitude(village.getLatitude())
                .longitude(village.getLongitude())
                .build();
    }
}