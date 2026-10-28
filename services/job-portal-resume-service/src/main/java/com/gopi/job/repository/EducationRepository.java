package com.gopi.job.repository;

import com.gopi.job.modal.Education;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EducationRepository extends JpaRepository<Education, Long> {
    List<Education> findByResume_IdOrderByDisplayOrderAsc(Long resumeId);
}
