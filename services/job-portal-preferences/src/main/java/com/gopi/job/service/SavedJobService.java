package com.gopi.job.service;

import com.gopi.job.dto.SavedJobResponse;
import com.gopi.job.payload.SavedJobRequest;

import java.util.List;

public interface SavedJobService {

    SavedJobResponse saveJob(Long candidateId, SavedJobRequest req) throws Exception;

    void unsaveJob(Long candidateId, Long savedJobId) throws Exception;

    List<SavedJobResponse> getSavedJob(Long candidateId);

    boolean isSaved(Long candidateId, Long jobId);
}
