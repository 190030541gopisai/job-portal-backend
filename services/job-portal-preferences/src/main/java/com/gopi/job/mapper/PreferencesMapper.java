package com.gopi.job.mapper;

import com.gopi.job.dto.SavedJobResponse;
import com.gopi.job.modal.SavedJob;

public class PreferencesMapper {
    public static SavedJobResponse toSavedJobResponse(SavedJob savedJob) {
        return SavedJobResponse.builder()
                .id(savedJob.getId())
                .candidateId(savedJob.getCandidateId())
                .jobId(savedJob.getJobId())
                .savedAt(savedJob.getSavedAt())
                .build();
    }
}
