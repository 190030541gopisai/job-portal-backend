package com.gopi.job.payload;


import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class CareerFeedbackRequest {

    @NotBlank(message = "Resume content is required")
    private String resumeContent;

    // Optional — if provided, feedback is tailored toward this role
    private String targetJobTitle;
}