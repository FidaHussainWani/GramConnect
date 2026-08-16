package com.gramconnect.controller;

import com.gramconnect.dto.ComplaintRequest;
import com.gramconnect.dto.ComplaintResponse;
import com.gramconnect.service.ComplaintService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintController {

    private final ComplaintService complaintService;

    @PostMapping
    public ResponseEntity<ComplaintResponse> createComplaint(
            @RequestParam Long userId,
            @Valid @RequestBody ComplaintRequest request) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        complaintService.createComplaint(
                                userId,
                                request
                        )
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComplaintResponse> getComplaint(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                complaintService.getComplaint(id)
        );
    }

    @GetMapping("/my")
    public ResponseEntity<List<ComplaintResponse>> getMyComplaints(
            @RequestParam Long userId) {

        return ResponseEntity.ok(
                complaintService.getMyComplaints(userId)
        );
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }
}