package com.gramconnect.service;

import com.gramconnect.dto.ComplaintRequest;
import com.gramconnect.dto.ComplaintResponse;
import com.gramconnect.entity.*;
import com.gramconnect.repository.ComplaintRepository;
import com.gramconnect.repository.DepartmentRepository;
import com.gramconnect.repository.UserRepository;
import com.gramconnect.repository.VillageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Year;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ComplaintService {

    private final ComplaintRepository complaintRepository;
    private final UserRepository userRepository;
    private final VillageRepository villageRepository;
    private final DepartmentRepository departmentRepository;

    public ComplaintResponse createComplaint(
            Long userId,
            ComplaintRequest request) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        Village village = villageRepository.findById(
                        request.getVillageId())
                .orElseThrow(() ->
                        new RuntimeException("Village not found"));

        Department department = null;

        if (request.getDepartmentId() != null) {
            department = departmentRepository.findById(
                            request.getDepartmentId())
                    .orElseThrow(() ->
                            new RuntimeException("Department not found"));
        }

        Complaint complaint = Complaint.builder()
                .title(request.getTitle())
                .description(request.getDescription())
                .category(request.getCategory())
                .subCategory(request.getSubCategory())
                .priority(request.getPriority())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .address(request.getAddress())
                .user(user)
                .village(village)
                .department(department)
                .status(ComplaintStatus.SUBMITTED)
                .build();

        complaint.setComplaintNumber(
                generateComplaintNumber()
        );

        Complaint savedComplaint =
                complaintRepository.save(complaint);

        return mapToResponse(savedComplaint);
    }

    public ComplaintResponse getComplaint(Long id) {

        Complaint complaint = complaintRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Complaint not found"));

        return mapToResponse(complaint);
    }

    public List<ComplaintResponse> getMyComplaints(Long userId) {

        return complaintRepository
                .findByUserIdOrderByCreatedAtDesc(userId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public List<ComplaintResponse> getAllComplaints() {

        return complaintRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private String generateComplaintNumber() {

        long count = complaintRepository.count() + 1;

        return String.format(
                "GC-%d-%06d",
                Year.now().getValue(),
                count
        );
    }

    private ComplaintResponse mapToResponse(
            Complaint complaint) {

        return ComplaintResponse.builder()
                .id(complaint.getId())
                .complaintNumber(complaint.getComplaintNumber())
                .title(complaint.getTitle())
                .description(complaint.getDescription())
                .category(complaint.getCategory())
                .subCategory(complaint.getSubCategory())
                .priority(complaint.getPriority())
                .status(complaint.getStatus())
                .latitude(complaint.getLatitude())
                .longitude(complaint.getLongitude())
                .address(complaint.getAddress())
                .userId(complaint.getUser().getId())
                .villageId(complaint.getVillage().getId())
                .villageName(complaint.getVillage().getName())
                .departmentId(
                        complaint.getDepartment() != null
                                ? complaint.getDepartment().getId()
                                : null
                )
                .departmentName(
                        complaint.getDepartment() != null
                                ? complaint.getDepartment().getName()
                                : null
                )
                .createdAt(complaint.getCreatedAt())
                .updatedAt(complaint.getUpdatedAt())
                .resolvedAt(complaint.getResolvedAt())
                .build();
    }
}