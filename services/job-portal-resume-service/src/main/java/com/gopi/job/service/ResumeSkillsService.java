package com.gopi.job.service;

import com.gopi.job.dto.ResumeSkillResponse;
import com.gopi.job.payload.AddResumeSkillRequest;

import java.util.List;

public interface ResumeSkillsService {

    ResumeSkillResponse addSkill(
            Long resumeId,
            Long candidateId,
            AddResumeSkillRequest req
    ) throws Exception;

    List<ResumeSkillResponse> getSkills(Long resumeId);

    ResumeSkillResponse updateSkill(
            Long skillId,
            Long resumeId,
            Long candidateId,
            AddResumeSkillRequest req
    ) throws Exception;

    void deleteSkill(
            Long skillId,
            Long resumeId,
            Long candidateId
    ) throws Exception;
}