package com.gramconnect.service;

import com.gramconnect.dto.DepartmentRequest;
import com.gramconnect.dto.DepartmentResponse;
import com.gramconnect.entity.Department;
import com.gramconnect.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentResponse createDepartment(
            DepartmentRequest request) {

        if (departmentRepository.existsByNameIgnoreCase(request.getName())) {
            throw new RuntimeException("Department already exists");
        }

        Department department = Department.builder()
                .name(request.getName())
                .description(request.getDescription())
                .contactEmail(request.getContactEmail())
                .contactPhone(request.getContactPhone())
                .build();

        return mapToResponse(
                departmentRepository.save(department)
        );
    }

    public List<DepartmentResponse> getAllDepartments() {

        return departmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private DepartmentResponse mapToResponse(
            Department department) {

        return DepartmentResponse.builder()
                .id(department.getId())
                .name(department.getName())
                .description(department.getDescription())
                .contactEmail(department.getContactEmail())
                .contactPhone(department.getContactPhone())
                .build();
    }
}