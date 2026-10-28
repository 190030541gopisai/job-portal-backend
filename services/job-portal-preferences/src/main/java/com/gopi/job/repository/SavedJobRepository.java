package com.gopi.job.repository;

import com.gopi.job.modal.SavedJob;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedJobRepository extends JpaRepository<SavedJob, Long> {
    List<SavedJob> findByCandidateId(Long candidateId);
    boolean existsByCandidateIdAndJobId(Long candidateId, Long jobId);
}
