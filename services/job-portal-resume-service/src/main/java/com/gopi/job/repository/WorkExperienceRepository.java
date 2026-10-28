package com.gopi.job.repository;

import com.gopi.job.modal.WorkExperience;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WorkExperienceRepository extends JpaRepository<WorkExperience, Long> {
    List<WorkExperience> findByResumeIdOrderByDisplayOrderAsc(Long resumeId);
}
