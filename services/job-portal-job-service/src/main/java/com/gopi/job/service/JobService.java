package com.gopi.job.service;

import com.gopi.job.dto.JobRequest;
import com.gopi.job.dto.JobResponse;
import com.gopi.job.payload.JobSearchRequest;

import java.util.List;

public interface JobService {
    JobResponse createJob(Long employerId, JobRequest request) throws Exception;

    JobResponse getJobById(Long id) throws Exception;

    List<JobResponse> getJobs(JobSearchRequest request);

    List<JobResponse> getJobsByCompany(Long companyId);

    JobResponse updateJob(Long jobId, Long employerId, JobRequest request) throws Exception;

    JobResponse publishJob(Long jobId, Long employerId) throws Exception;

    JobResponse closeJob(Long jobId, Long employerId) throws Exception;

    void deleteJob(Long jobId, Long employerId) throws Exception;

    List<JobResponse> getAllJobsAdmin();
}
