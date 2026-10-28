package com.gopi.job.payload;

import com.gopi.job.domain.JobType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddWorkExperience {

    @NotBlank(message = "company name is required")
    private String companyName;

    private String companyLogoUrl;

    @NotBlank(message = "Job title is required")
    private String jobTitle;

    private JobType employmentType;

    private String location;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    @Builder.Default
    private Boolean isCurrentJob = false;

    private String description;

    @Builder.Default
    private List<String> technologies = new ArrayList<>();

    private Integer displayOrder;
}