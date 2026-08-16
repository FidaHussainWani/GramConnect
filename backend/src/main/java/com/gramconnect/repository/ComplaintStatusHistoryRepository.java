package com.gramconnect.repository;

import com.gramconnect.entity.ComplaintStatusHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintStatusHistoryRepository
        extends JpaRepository<ComplaintStatusHistory, Long> {

    List<ComplaintStatusHistory>
    findByComplaintIdOrderByCreatedAtAsc(Long complaintId);
}