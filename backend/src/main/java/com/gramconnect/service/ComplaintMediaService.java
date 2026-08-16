package com.gramconnect.service;

import com.gramconnect.dto.ComplaintMediaResponse;
import com.gramconnect.entity.*;
import com.gramconnect.repository.ComplaintMediaRepository;
import com.gramconnect.repository.ComplaintRepository;
import com.gramconnect.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplaintMediaService {

    private final ComplaintMediaRepository mediaRepository;
    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;

    @Value("${file.upload-dir}")
    private String uploadDir;

    public ComplaintMediaResponse uploadMedia(
            Long complaintId,
            Long userId,
            MultipartFile file
    ) throws IOException {

        if (file.isEmpty()) {
            throw new RuntimeException("File cannot be empty");
        }

        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        if (!complaint.getUser().getId().equals(userId)) {
            throw new RuntimeException(
                    "You can only upload media to your own complaint"
            );
        }

        MediaType mediaType = determineMediaType(
                file.getContentType()
        );

        Path uploadPath = Paths.get(uploadDir)
                .toAbsolutePath()
                .normalize();

        Files.createDirectories(uploadPath);

        String originalName = file.getOriginalFilename();

        String extension = "";

        if (originalName != null && originalName.contains(".")) {
            extension = originalName.substring(
                    originalName.lastIndexOf(".")
            );
        }

        String storedFileName =
                UUID.randomUUID() + extension;

        Path targetPath =
                uploadPath.resolve(storedFileName);

        Files.copy(
                file.getInputStream(),
                targetPath,
                StandardCopyOption.REPLACE_EXISTING
        );

        ComplaintMedia media = ComplaintMedia.builder()
                .complaint(complaint)
                .mediaType(mediaType)
                .fileUrl("/uploads/" + storedFileName)
                .fileName(originalName != null
                        ? originalName
                        : storedFileName)
                .contentType(file.getContentType())
                .fileSize(file.getSize())
                .uploadedBy(user)
                .build();

        ComplaintMedia savedMedia =
                mediaRepository.save(media);

        return mapToResponse(savedMedia);
    }

    public List<ComplaintMediaResponse> getComplaintMedia(
            Long complaintId) {

        if (!complaintRepository.existsById(complaintId)) {
            throw new RuntimeException("Complaint not found");
        }

        return mediaRepository
                .findByComplaintIdOrderByCreatedAtAsc(complaintId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private MediaType determineMediaType(String contentType) {

        if (contentType == null) {
            throw new RuntimeException("Unknown file type");
        }

        if (contentType.startsWith("image/")) {
            return MediaType.IMAGE;
        }

        if (contentType.startsWith("video/")) {
            return MediaType.VIDEO;
        }

        if (contentType.startsWith("audio/")) {
            return MediaType.AUDIO;
        }

        throw new RuntimeException(
                "Only image, video and audio files are supported"
        );
    }

    private ComplaintMediaResponse mapToResponse(
            ComplaintMedia media) {

        return ComplaintMediaResponse.builder()
                .id(media.getId())
                .complaintId(
                        media.getComplaint().getId()
                )
                .mediaType(media.getMediaType())
                .fileUrl(media.getFileUrl())
                .fileName(media.getFileName())
                .contentType(media.getContentType())
                .fileSize(media.getFileSize())
                .uploadedBy(
                        media.getUploadedBy().getId()
                )
                .uploadedByName(
                        media.getUploadedBy().getName()
                )
                .createdAt(media.getCreatedAt())
                .build();
    }
}