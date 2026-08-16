package com.gramconnect.repository;

import com.gramconnect.entity.Complaint;
import com.gramconnect.entity.ComplaintStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ComplaintRepository
        extends JpaRepository<Complaint, Long> {

    Optional<Complaint> findByComplaintNumber(String complaintNumber);

    List<Complaint> findByUserIdOrderByCreatedAtDesc(Long userId);

    List<Complaint> findByVillageIdOrderByCreatedAtDesc(Long villageId);

    List<Complaint> findByStatusOrderByCreatedAtDesc(
            ComplaintStatus status
    );

    List<Complaint> findByDepartmentIdOrderByCreatedAtDesc(
            Long departmentId
    );
}