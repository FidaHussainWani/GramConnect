package com.gramconnect.dto;

import com.gramconnect.entity.ComplaintStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ComplaintStatusHistoryResponse {

    private Long id;

    private ComplaintStatus oldStatus;

    private ComplaintStatus newStatus;

    private Long changedBy;

    private String changedByName;

    private String remarks;

    private LocalDateTime createdAt;
}