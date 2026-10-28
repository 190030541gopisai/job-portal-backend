package com.gopi.job.controller;

import com.gopi.job.dto.ApiResponse;
import com.gopi.job.dto.JobSkillResponse;
import com.gopi.job.payload.JobSkillRequest;
import com.gopi.job.service.JobSkillService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/job-skills")
public class JobSkillController {
    private final JobSkillService jobSkillService;

    public ResponseEntity<JobSkillResponse> createSkill(
            @Valid @RequestBody JobSkillRequest jobSkillRequest
    ) throws Exception {
        return ResponseEntity.status(
                HttpStatus.CREATED
        ).body(jobSkillService.createSkill(jobSkillRequest));
    }

    @GetMapping
    public ResponseEntity<List<JobSkillResponse>> getAllSkills() {
        return ResponseEntity.ok(jobSkillService.getAllSkills());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobSkillResponse> getSkillById(
            @PathVariable Long id) throws Exception {
        return ResponseEntity.ok(jobSkillService.getSkillById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobSkillResponse> updateSkill(
            @PathVariable Long id,
            @Valid @RequestBody JobSkillRequest request
    ) throws  Exception {
        return ResponseEntity.ok(jobSkillService.updateSkill(
                id, request
        ));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteSkill(
            @PathVariable Long id
    ) throws Exception {
        jobSkillService.deleteSkill(id);
        return ResponseEntity.ok(new ApiResponse(
                "Skill deleted successfully",
                true
        ));
    }
}
