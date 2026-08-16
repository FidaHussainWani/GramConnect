package com.gramconnect.controller;

import com.gramconnect.dto.ComplaintMediaResponse;
import com.gramconnect.entity.User;
import com.gramconnect.service.ComplaintMediaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/complaints")
@RequiredArgsConstructor
public class ComplaintMediaController {

    private final ComplaintMediaService mediaService;

    @PostMapping("/{complaintId}/media")
    public ResponseEntity<ComplaintMediaResponse> uploadMedia(
            @PathVariable Long complaintId,
            @RequestParam("file") MultipartFile file,
            Authentication authentication
    ) throws IOException {

        User user = (User) authentication.getPrincipal();

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(
                        mediaService.uploadMedia(
                                complaintId,
                                user.getId(),
                                file
                        )
                );
    }

    @GetMapping("/{complaintId}/media")
    public ResponseEntity<List<ComplaintMediaResponse>> getMedia(
            @PathVariable Long complaintId
    ) {

        return ResponseEntity.ok(
                mediaService.getComplaintMedia(complaintId)
        );
    }
}