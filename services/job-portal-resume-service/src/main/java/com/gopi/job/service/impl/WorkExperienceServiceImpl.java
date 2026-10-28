package com.gopi.job.service.impl;

import com.gopi.job.dto.WorkExperienceResponse;
import com.gopi.job.mapper.WorkExperienceMapper;
import com.gopi.job.modal.Resume;
import com.gopi.job.modal.WorkExperience;
import com.gopi.job.payload.AddWorkExperience;
import com.gopi.job.repository.WorkExperienceRepository;
import com.gopi.job.service.ResumeService;
import com.gopi.job.service.WorkExperienceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkExperienceServiceImpl implements WorkExperienceService {
    private final ResumeService resumeService;
    private final WorkExperienceRepository workExperienceRepository;

    @Override
    public WorkExperienceResponse addWorkExperience(
            Long resumeId,
            Long candidateId,
            AddWorkExperience req
    ) throws Exception {

        Resume resume = resumeService.getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        WorkExperience workExperience = WorkExperience.builder()
                .resume(resume)
                .companyName(req.getCompanyName())
                .companyLogoUrl(req.getCompanyLogoUrl())
                .jobTitle(req.getJobTitle())
                .employmentType(req.getEmploymentType())
                .location(req.getLocation())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()))
                .description(req.getDescription())
                .technologies(req.getTechnologies() != null
                        ? req.getTechnologies()
                        : List.of())
                .displayOrder(req.getDisplayOrder() != null
                        ? req.getDisplayOrder()
                        : 0)
                .build();

        return WorkExperienceMapper.toWorkExperienceResponse(workExperienceRepository.save(workExperience));
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("resume not found");
        }
    }

    @Override
    public List<WorkExperienceResponse> getWorkExperiences(Long resumeId) {
        return workExperienceRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId)
                .stream().map(WorkExperienceMapper::toWorkExperienceResponse)
                .collect(Collectors.toList());
    }

    @Override
    public WorkExperienceResponse updateWorkExperience(Long resumeId, Long candidateId, Long workExperienceId, AddWorkExperience req) throws Exception {
        WorkExperience exp = getWorkExperienceEntity(workExperienceId);

        assertOwner(exp.getResume(), candidateId);

        exp.setCompanyName(req.getCompanyName());
        exp.setCompanyLogoUrl(req.getCompanyLogoUrl());
        exp.setJobTitle(req.getJobTitle());
        exp.setEmploymentType(req.getEmploymentType());
        exp.setLocation(req.getLocation());
        exp.setStartDate(req.getStartDate());
        exp.setEndDate(req.getEndDate());
        exp.setIsCurrentJob(Boolean.TRUE.equals(req.getIsCurrentJob()));
        exp.setDescription(req.getDescription());

        if (req.getTechnologies() != null) {
            exp.setTechnologies(req.getTechnologies());
        }

        if (req.getDisplayOrder() != null) {
            exp.setDisplayOrder(req.getDisplayOrder());
        }

        return WorkExperienceMapper.toWorkExperienceResponse(workExperienceRepository.save(exp));
    }

    @Override
    public void deleteWorkExperience(Long resumeId, Long workExperienceId, Long candidateId) throws Exception {
        WorkExperience exp = getWorkExperienceEntity(workExperienceId);

        assertOwner(exp.getResume(), candidateId);

        workExperienceRepository.delete(exp);
    }

    @Override
    public WorkExperience getWorkExperienceEntity(Long workExperienceId) throws Exception {
        return workExperienceRepository.findById(workExperienceId)
                .orElseThrow(
                        () -> new Exception("work experience not found")
                );
    }
}
