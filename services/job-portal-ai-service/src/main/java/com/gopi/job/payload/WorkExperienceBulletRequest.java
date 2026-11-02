package com.gopi.job.payload;

import lombok.*;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceBulletRequest {

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private String company;

    @NotBlank(message = "Raw description is required")
    private String rawDescription;

    private String achievementsHint;
}