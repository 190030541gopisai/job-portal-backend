package com.gopi.job.service.impl;

import com.gopi.job.dto.EducationResponse;
import com.gopi.job.mapper.ResumeMapper;
import com.gopi.job.modal.Education;
import com.gopi.job.modal.Resume;
import com.gopi.job.payload.AddEducationRequest;
import com.gopi.job.repository.EducationRepository;
import com.gopi.job.service.EducationService;
import com.gopi.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {
    private final ResumeService resumeService;
    private final EducationRepository educationRepository;

    @Override
    public EducationResponse addEducation(Long resumeId, Long candidateId, AddEducationRequest req) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        Education education = Education.builder()
                .resume(resume)
                .institutionName(req.getInstitutionName())
                .degree(req.getDegree())
                .fieldOfStudy(req.getFieldOfStudy())
                .grade(req.getGrade())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isCurrentlyStudying(Boolean.TRUE.equals(req.getIsCurrentlyStudying()))
                .description(req.getDescription())
                .displayOrder(req.getDisplayOrder() != null
                        ? req.getDisplayOrder()
                        : 0)
                .build();

        Education savedEducation = educationRepository.save(education);

        return ResumeMapper.toEducationResponse(savedEducation);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("resume not found");
        }
    }

    @Override
    public List<EducationResponse> getEducations(Long resumeId) {
        return educationRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ResumeMapper::toEducationResponse)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(
            Long educationId,
            Long resumeId,
            Long candidateId,
            AddEducationRequest req
    ) throws Exception {

        Education edu = educationRepository.findById(educationId)
                .orElseThrow(
                        () -> new Exception("education does not exist")
                );

        if (!edu.getResume().getId().equals(resumeId)) {
            throw new Exception("Education does not belong to this resume");
        }

        assertOwner(edu.getResume(), candidateId);

        edu.setInstitutionName(req.getInstitutionName());
        edu.setDegree(req.getDegree());
        edu.setFieldOfStudy(req.getFieldOfStudy());
        edu.setGrade(req.getGrade());
        edu.setStartDate(req.getStartDate());
        edu.setEndDate(req.getEndDate());
        edu.setIsCurrentlyStudying(
                Boolean.TRUE.equals(req.getIsCurrentlyStudying())
        );
        edu.setDescription(req.getDescription());

        if (req.getDisplayOrder() != null) {
            edu.setDisplayOrder(req.getDisplayOrder());
        }

        Education savedEducation = educationRepository.save(edu);

        return ResumeMapper.toEducationResponse(savedEducation);
    }

    @Override
    public void deleteEducation(Long educationId, Long resumeId, Long candidateId) throws Exception {
        Education edu = educationRepository.findById(educationId)
                .orElseThrow(
                        () -> new Exception("education does not exist")
                );

        if (!edu.getResume().getId().equals(resumeId)) {
            throw new Exception("Education does not belong to this resume");
        }

        assertOwner(edu.getResume(), candidateId);

        educationRepository.delete(edu);
    }
}
