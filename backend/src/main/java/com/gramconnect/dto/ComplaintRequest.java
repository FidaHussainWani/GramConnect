package com.gramconnect.dto;

import com.gramconnect.entity.ComplaintCategory;
import com.gramconnect.entity.ComplaintPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ComplaintRequest {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Category is required")
    private ComplaintCategory category;

    private String subCategory;

    private ComplaintPriority priority;

    private Double latitude;

    private Double longitude;

    private String address;

    @NotNull(message = "Village ID is required")
    private Long villageId;

    private Long departmentId;
}