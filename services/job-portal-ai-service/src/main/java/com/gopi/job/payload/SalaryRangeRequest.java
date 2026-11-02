package com.gopi.job.payload;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import java.util.List;

@Data
public class SalaryRangeRequest {

    @NotBlank(message = "Job title is required")
    private String title;

    private List<String> skills;

    private String experienceLevel;

    private String jobType;

    private String location;
}