package com.gopi.job.controller;

import com.gopi.job.dto.ApiResponse;
import com.gopi.job.dto.ResumeSkillResponse;
import com.gopi.job.payload.AddResumeSkillRequest;
import com.gopi.job.service.ResumeSkillsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/resumes/{resumeId}/skills")
public class ResumeSkillController {
    private final ResumeSkillsService resumeSkillsService;

    @PostMapping
    public ResponseEntity<ResumeSkillResponse> addSkill(
            @PathVariable Long resumeId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddResumeSkillRequest req
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(resumeSkillsService.addSkill(resumeId, candidateId, req));
    }

    @GetMapping
    public ResponseEntity<List<ResumeSkillResponse>> getSkills(
            @PathVariable Long resumeId
    ) {
        return ResponseEntity.ok(
                resumeSkillsService.getSkills(resumeId)
        );
    }

    @PutMapping("/{skillId}")
    public ResponseEntity<ResumeSkillResponse> updateSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId,
            @RequestBody @Valid AddResumeSkillRequest req
    ) throws Exception {
        return ResponseEntity.ok(
                resumeSkillsService.updateSkill(
                        skillId, resumeId, candidateId, req
                )
        );
    }

    @DeleteMapping("/{skillId}")
    public ResponseEntity<ApiResponse> deleteSkill(
            @PathVariable Long resumeId,
            @PathVariable Long skillId,
            @RequestHeader("X-User-Id") Long candidateId
    ) throws Exception {
        resumeSkillsService.deleteSkill(skillId, resumeId, candidateId);

        return ResponseEntity.ok(
                new ApiResponse("Skill deleted successfully", true)
        );
    }
}
