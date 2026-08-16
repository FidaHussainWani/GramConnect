package com.gramconnect.repository;

import com.gramconnect.entity.ComplaintMedia;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComplaintMediaRepository
        extends JpaRepository<ComplaintMedia, Long> {

    List<ComplaintMedia>
    findByComplaintIdOrderByCreatedAtAsc(Long complaintId);
}