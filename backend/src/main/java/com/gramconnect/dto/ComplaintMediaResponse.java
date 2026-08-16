package com.gramconnect.dto;

import com.gramconnect.entity.MediaType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ComplaintMediaResponse {

    private Long id;

    private Long complaintId;

    private MediaType mediaType;

    private String fileUrl;

    private String fileName;

    private String contentType;

    private Long fileSize;

    private Long uploadedBy;

    private String uploadedByName;

    private LocalDateTime createdAt;
}