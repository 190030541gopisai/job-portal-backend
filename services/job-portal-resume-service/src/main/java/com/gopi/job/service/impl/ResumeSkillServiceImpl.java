package com.gopi.job.service.impl;

import com.gopi.job.dto.ResumeSkillResponse;
import com.gopi.job.mapper.ResumeMapper;
import com.gopi.job.modal.Resume;
import com.gopi.job.modal.ResumeSkill;
import com.gopi.job.payload.AddResumeSkillRequest;
import com.gopi.job.repository.ResumeSkillRepository;
import com.gopi.job.service.ResumeService;
import com.gopi.job.service.ResumeSkillsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ResumeSkillServiceImpl implements ResumeSkillsService {

    private final ResumeService resumeService;
    private final ResumeSkillRepository resumeSkillRepository;

    @Override
    public ResumeSkillResponse addSkill(Long resumeId, Long candidateId, AddResumeSkillRequest req) throws Exception {
        Resume resume = resumeService.getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        ResumeSkill skill = ResumeSkill.builder()
                .resume(resume)
                .skillName(req.getSkillName())
                .proficiencyLevel(req.getProficiencyLevel())
                .yearsOfExperience(req.getYearsOfExperience())
                .displayOrder(req.getDisplayOrder() != null
                        ? req.getDisplayOrder()
                        : 0)
                .build();

        ResumeSkill savedSkill = resumeSkillRepository.save(skill);

        return ResumeMapper.toSkillResponse(savedSkill);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("resume not found");
        }
    }


    @Override
    public List<ResumeSkillResponse> getSkills(Long resumeId) {
        return resumeSkillRepository.findByResumeIdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ResumeMapper::toSkillResponse)
                .toList();
    }

    @Override
    public ResumeSkillResponse updateSkill(Long skillId, Long resumeId, Long candidateId, AddResumeSkillRequest req) throws Exception {
        ResumeSkill skill = resumeSkillRepository.findByIdAndResumeId(skillId, resumeId)
                .orElseThrow(() -> new Exception("Skill not found"));

        assertOwner(skill.getResume(), candidateId);

        skill.setSkillName(req.getSkillName());
        skill.setProficiencyLevel(req.getProficiencyLevel());
        skill.setYearsOfExperience(req.getYearsOfExperience());

        if (req.getDisplayOrder() != null) {
            skill.setDisplayOrder(req.getDisplayOrder());
        }

        return ResumeMapper.toSkillResponse(
                resumeSkillRepository.save(skill)
        );
    }

    @Override
    public void deleteSkill(Long skillId, Long resumeId, Long candidateId) throws Exception {
        ResumeSkill skill = resumeSkillRepository.findByIdAndResumeId(skillId, resumeId)
                .orElseThrow(() -> new Exception("Skill not found"));

        assertOwner(skill.getResume(), candidateId);

        resumeSkillRepository.delete(skill);
    }
}
