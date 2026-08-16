package com.gramconnect.dto;

import com.gramconnect.entity.ComplaintCategory;
import com.gramconnect.entity.ComplaintPriority;
import com.gramconnect.entity.ComplaintStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ComplaintResponse {

    private Long id;

    private String complaintNumber;

    private String title;

    private String description;

    private ComplaintCategory category;

    private String subCategory;

    private ComplaintPriority priority;

    private ComplaintStatus status;

    private Double latitude;

    private Double longitude;

    private String address;

    private Long userId;

    private Long villageId;

    private String villageName;

    private Long departmentId;

    private String departmentName;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private LocalDateTime resolvedAt;
}