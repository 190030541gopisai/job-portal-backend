package com.gopi.job.repository;

import com.gopi.job.modal.ResumeSkill;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ResumeSkillRepository extends JpaRepository<ResumeSkill, Long> {
    List<ResumeSkill> findByResumeIdOrderByDisplayOrderAsc(Long resumeId);

    Optional<ResumeSkill> findByIdAndResumeId(Long skillId, Long resumeId);
}
