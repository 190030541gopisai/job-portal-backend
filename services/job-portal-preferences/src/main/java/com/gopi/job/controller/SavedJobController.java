package com.gopi.job.controller;


import com.gopi.job.dto.ApiResponse;
import com.gopi.job.dto.SavedJobResponse;
import com.gopi.job.payload.SavedJobRequest;
import com.gopi.job.service.SavedJobService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/preferences/saved-jobs")
public class SavedJobController {

    private final SavedJobService savedJobService;

    @PostMapping
    public ResponseEntity<SavedJobResponse> saveJob(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody SavedJobRequest req
    ) throws Exception {
        return ResponseEntity.ok(
                savedJobService.saveJob(candidateId, req)
        );
    }

    @GetMapping
    public ResponseEntity<List<SavedJobResponse>> getMySavedJobs(
            @RequestHeader("X-User-Id") Long candidateId
    ) {
        return ResponseEntity.ok(
                savedJobService.getSavedJob(candidateId)
        );
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> isSaved(
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestParam Long jobId
    ) {
        return ResponseEntity.ok(
                savedJobService.isSaved(candidateId, jobId)
        );
    }

    @DeleteMapping("/{savedJobId}")
    public ResponseEntity<ApiResponse> unsaveJob(
            @PathVariable Long savedJobId,
            @RequestHeader("X-User-Id") Long candidateId
    ) throws Exception {

        savedJobService.unsaveJob(candidateId, savedJobId);

        return ResponseEntity.ok(
                new ApiResponse("Job removed from saved list", true)
        );
    }
}