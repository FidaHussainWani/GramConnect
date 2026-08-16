package com.gramconnect.controller;

import com.gramconnect.dto.VillageRequest;
import com.gramconnect.dto.VillageResponse;
import com.gramconnect.service.VillageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/villages")
@RequiredArgsConstructor
public class VillageController {

    private final VillageService villageService;

    @PostMapping
    public ResponseEntity<VillageResponse> createVillage(
            @Valid @RequestBody VillageRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(villageService.createVillage(request));
    }

    @GetMapping
    public ResponseEntity<List<VillageResponse>> getAllVillages() {

        return ResponseEntity.ok(
                villageService.getAllVillages()
        );
    }
}