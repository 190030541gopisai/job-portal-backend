package com.gopi.job.payload;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class ResumeImprovementRequest {

    @NotBlank(message = "Resume content is required")
    private String resumeContent;

    private String targetJobTitle;
}
