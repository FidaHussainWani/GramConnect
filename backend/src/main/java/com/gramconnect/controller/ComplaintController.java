package com.gramconnect.controller;

import org.springframework.security.core.Authentication;
import com.gramconnect.dto.ComplaintRequest;
import com.gramconnect.dto.ComplaintResponse;
import com.gramconnect.dto.ComplaintStatusHistoryResponse;
import com.gramconnect.dto.ComplaintStatusUpdateRequest;
import com.gramconnect.service.ComplaintService;
import com.gramconnect.entity.User;
import com.gramconnect.dto.ComplaintStatusUpdateRequest;
import com.gramconnect.entity.ComplaintStatus;
import com.gramconnect.dto.ComplaintStatusHistoryResponse;
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
        Authentication authentication,
        @Valid @RequestBody ComplaintRequest request) {

    User user = (User) authentication.getPrincipal();

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(
                    complaintService.createComplaint(
                            user.getId(),
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
            Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity.ok(
                complaintService.getMyComplaints(user.getId())
        );
    }

    @GetMapping
    public ResponseEntity<List<ComplaintResponse>> getAllComplaints() {

        return ResponseEntity.ok(
                complaintService.getAllComplaints()
        );
    }

    @PutMapping("/{id}/status")
public ResponseEntity<ComplaintResponse> updateStatus(
        @PathVariable Long id,
        @Valid @RequestBody ComplaintStatusUpdateRequest request,
        Authentication authentication) {

    User user = (User) authentication.getPrincipal();

    return ResponseEntity.ok(
            complaintService.updateStatus(
                    id,
                    request.getStatus(),
                    user,
                    request.getRemarks()
            )
    );
}
@GetMapping("/{id}/history")
public ResponseEntity<List<ComplaintStatusHistoryResponse>> getHistory(
        @PathVariable Long id) {

    return ResponseEntity.ok(
            complaintService.getStatusHistory(id)
    );
}
}