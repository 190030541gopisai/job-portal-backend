package com.gopi.job.mapper;

import com.gopi.job.dto.*;
import com.gopi.job.modal.Job;
import com.gopi.job.modal.JobTag;
import com.gopi.job.modal.embeddable.JobLocation;
import com.gopi.job.modal.embeddable.SalaryRange;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class JobMapper {
    public static JobResponse toResponse(Job job, CompanyResponse companyResponse) {
        JobLocation loc=job.getLocation();
        SalaryRange sal=job.getSalaryRange();

        Set<JobSkillResponse> skills = job.getSkills() == null ? Collections.emptySet() :
                job.getSkills().stream().map(JobSkillMapper::toJobSkillResponse)
                        .collect(Collectors.toSet());

        Set<JobTagResponse> tags = job.getTags() == null ? Collections.emptySet():
                job.getTags().stream().map(JobTagMapper::toTagResponse)
                        .collect(Collectors.toSet());


        return JobResponse.builder()
                .id(job.getId())
                .title(job.getTitle())
                .description(job.getDescription())
                .requirements(job.getRequirements())
                .responsibilities(job.getResponsibilities())
                .benefits(job.getBenefits())
                .company(companyResponse)
                .category(JobCategoryMapper.toJobCategoryResponse(job.getCategory(), false))
                .skill(skills)
                .tags(tags)

                // Location
                .address(loc != null ? loc.getAddress() : null)
                .city(loc != null ? loc.getCity() : null)
                .state(loc != null ? loc.getState() : null)
                .country(loc != null ? loc.getCountry() : null)
                .zipCode(loc != null ? loc.getZipCode() : null)
                // Salary
                .minSalary(sal != null ? sal.getMinSalary() : null)
                .maxSalary(sal != null ? sal.getMaxSalary() : null)
                // Classification
                .jobType(job.getJobType())
                .workMode(job.getWorkMode())
                .experienceLevel(job.getExperienceLevel())
                .status(job.getStatus())

                // Posting
                .openings(job.getOpenings())
                .applicationDeadline(job.getApplicationDeadline())
                .expiresAt(job.getExpiresAt())
                .active(job.getActive())

                // Timestamps
                .createdAt(job.getCreatedAt())
                .updatedAt(job.getUpdatedAt())
                .publishedAt(job.getPublishedAt())
                .closedAt(job.getClosedAt())
                .build();
    }
}
