package com.gopi.job.service.impl;

import com.gopi.job.dto.*;
import com.gopi.job.mapper.ResumeMapper;
import com.gopi.job.mapper.WorkExperienceMapper;
import com.gopi.job.modal.PersonalInfo;
import com.gopi.job.modal.Resume;
import com.gopi.job.payload.CreateResumeRequest;
import com.gopi.job.repository.*;
import com.gopi.job.service.ResumeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {
    private final ResumeRepository resumeRepository;
    private final WorkExperienceRepository workExperienceRepository;
    private final EducationRepository educationRepository;
    private final ResumeSkillRepository resumeSkillRepository;
    private final ProjectRepository projectRepository;
    private final LanguageRepository languageRepository;

    @Override
    public ResumeResponse createResume(Long candidateId, CreateResumeRequest req) {
        if(Boolean.TRUE.equals(req.getIsDefault())){
            resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                    .ifPresent(existing -> {
                        existing.setIsDefault(false);
                        resumeRepository.save(existing);
                    });
        }

        Resume resume = Resume.builder()
                .candidateId(candidateId)
                .title(req.getTitle())
                .template(req.getTemplate())
                .visibility(req.getVisibility())
                .isDefault(Boolean.TRUE.equals(req.getIsDefault()))
                .isActive(true)
                .build();
        Resume saved = resumeRepository.save(resume);

        return buildFullResponse(saved);
    }

    @Override
    public ResumeResponse getResumeById(Long resumeId, Long candidateId) throws Exception{
        Resume resume = getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        return buildFullResponse(resume);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if(!resume.getCandidateId().equals(candidateId)){
            throw new Exception("resume not found with id");
        }
    }

    @Override
    public List<ResumeResponse> getMyResumes(Long candidateId) {
        return resumeRepository.findByCandidateIdAndIsActiveTrue(candidateId)
                .stream()
                .map(this::buildFullResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ResumeResponse updatePersonalInfo(Long resumeId,
                                             Long candidateId,
                                             PersonalInfoResponse req) throws Exception {
        Resume resume = getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        PersonalInfo info = resume.getPersonalInfo();

        if(info == null) {
            info = new PersonalInfo();
        }

        if (req.getFirstName() != null)
            info.setFirstName(req.getFirstName());

        if (req.getLastName() != null)
            info.setLastName(req.getLastName());

        if (req.getHeadline() != null)
            info.setHeadline(req.getHeadline());

        if (req.getEmail() != null)
            info.setEmail(req.getEmail());

        if (req.getPhone() != null)
            info.setPhone(req.getPhone());

        if (req.getCity() != null)
            info.setCity(req.getCity());

        if (req.getCountry() != null)
            info.setCountry(req.getCountry());

        if (req.getLinkedinUrl() != null)
            info.setLinkedinUrl(req.getLinkedinUrl());

        if (req.getGithubUrl() != null)
            info.setGithubUrl(req.getGithubUrl());

        if (req.getPortfolioUrl() != null)
            info.setPortfolioUrl(req.getPortfolioUrl());

        if (req.getWebsiteUrl() != null)
            info.setWebsiteUrl(req.getWebsiteUrl());

        resume.setPersonalInfo(info);

        return buildFullResponse(resumeRepository.save(resume));
    }

    @Override
    public ResumeResponse updateSummary(Long resumeId, Long candidateId, String summary) throws Exception {
        Resume resume = getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        resume.setSummary(summary);

        return buildFullResponse(resumeRepository.save(resume));
    }

    @Override
    public ResumeResponse setDefaultResume(Long resumeId, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        resumeRepository.findByCandidateIdAndIsDefaultTrue(candidateId)
                .ifPresent(existing -> {
                    existing.setIsDefault(false);
                    resumeRepository.save(existing);
                });

        resume.setIsDefault(true);

        return buildFullResponse(resumeRepository.save(resume));
    }

    @Override
    public void deleteResume(Long resumeId, Long candidateId) throws Exception {
        Resume resume = getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        resume.setIsActive(false);
        resume.setIsDefault(false);

        resumeRepository.save(resume);
    }

    @Override
    public Resume getResumeEntity(Long resumeId) throws Exception {
       return resumeRepository.findById(resumeId)
                .orElseThrow(
                        () -> new Exception("resume not found with id " + resumeId)
                );
    }

    private ResumeResponse buildFullResponse(Resume resume) {
        Long resumeId = resume.getId();

        List<WorkExperienceResponse> workExperienceResponses =
                workExperienceRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId)
                        .stream()
                        .map(WorkExperienceMapper::toWorkExperienceResponse)
                        .toList();

        List<EducationResponse> educationResponses =
                educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                        .stream()
                        .map(ResumeMapper::toEducationResponse)
                        .toList();


        List<ResumeSkillResponse> skills =
                resumeSkillRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId)
                        .stream()
                        .map(ResumeMapper::toSkillResponse)
                        .toList();

        List<ProjectResponse> projects =
                projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                        .stream()
                        .map(ResumeMapper::toProjectResponse)
                        .toList();

        List<LanguageResponse> languageResponses =
                languageRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                        .stream()
                        .map(ResumeMapper::toLanguageResponse)
                        .toList();

        return ResumeMapper.toResponse(
                resume,
                workExperienceResponses,
                educationResponses,
                skills,
                projects,
                languageResponses
        );

    }
}
