package com.gopi.job.controller;

import com.gopi.job.dto.ApiResponse;
import com.gopi.job.dto.JobCategoryResponse;
import com.gopi.job.mapper.JobCategoryMapper;
import com.gopi.job.payload.JobCategoryRequest;
import com.gopi.job.service.JobCategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/job-categories")
@RequiredArgsConstructor
public class JobCategoryController {
    private final JobCategoryService jobCategoryService;

    @PostMapping
    public ResponseEntity<JobCategoryResponse> createCategory(
            @Valid @RequestBody JobCategoryRequest request
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(
                jobCategoryService.createCategory(request)
        );
    }

    @GetMapping
    public ResponseEntity<List<JobCategoryResponse>> getAllCategories() {
        return ResponseEntity.ok(jobCategoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> getCategoryById(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.ok(jobCategoryService.getCategoryById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobCategoryResponse> updateCategory(
            @PathVariable Long id,
            @Valid @RequestBody JobCategoryRequest request
    ) throws Exception {
        return ResponseEntity.ok(jobCategoryService.updateCategory(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(
            @PathVariable Long id
    ) throws Exception {
        jobCategoryService.deleteCategory(id);
        return ResponseEntity.ok(new ApiResponse("Category deleted successfully", true));
    }
}
