package com.gopi.job.service.impl;

import com.gopi.job.dto.ProjectResponse;
import com.gopi.job.mapper.ResumeMapper;
import com.gopi.job.modal.Project;
import com.gopi.job.modal.Resume;
import com.gopi.job.payload.AddProjectRequest;
import com.gopi.job.repository.ProjectRepository;
import com.gopi.job.service.ProjectService;
import com.gopi.job.service.ResumeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private final ResumeService resumeService;
    private final ProjectRepository projectRepository;

    @Override
    public ProjectResponse addProject(
            Long resumeId,
            Long candidateId,
            AddProjectRequest req
    ) throws Exception {

        Resume resume = resumeService.getResumeEntity(resumeId);

        assertOwner(resume, candidateId);

        Project project = Project.builder()
                .resume(resume)
                .title(req.getTitle())
                .description(req.getDescription())
                .technologies(req.getTechnologies() != null
                        ? req.getTechnologies()
                        : List.of())
                .projectUrl(req.getProjectUrl())
                .sourceCodeUrl(req.getSourceCodeUrl())
                .startDate(req.getStartDate())
                .endDate(req.getEndDate())
                .isOngoing(Boolean.TRUE.equals(req.getIsOngoing()))
                .displayOrder(req.getDisplayOrder() != null
                        ? req.getDisplayOrder()
                        : 0)
                .build();

        projectRepository.save(project);

        return ResumeMapper.toProjectResponse(project);
    }

    private void assertOwner(Resume resume, Long candidateId) throws Exception {
        if (!resume.getCandidateId().equals(candidateId)) {
            throw new Exception("resume not found");
        }
    }


    @Override
    public List<ProjectResponse> getAllProjects(Long resumeId) {
        return projectRepository.findByResume_IdOrderByDisplayOrderAsc(resumeId)
                .stream()
                .map(ResumeMapper::toProjectResponse)
                .toList();
    }

    @Override
    public ProjectResponse updateProject(
            Long projectId,
            Long resumeId,
            Long candidateId,
            AddProjectRequest req
    ) throws Exception {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("project not found"));

        assertOwner(project.getResume(), candidateId);

        project.setTitle(req.getTitle());
        project.setDescription(req.getDescription());

        if (req.getTechnologies() != null) {
            project.setTechnologies(req.getTechnologies());
        }

        project.setProjectUrl(req.getProjectUrl());
        project.setSourceCodeUrl(req.getSourceCodeUrl());
        project.setStartDate(req.getStartDate());
        project.setEndDate(req.getEndDate());
        project.setIsOngoing(Boolean.TRUE.equals(req.getIsOngoing()));

        if (req.getDisplayOrder() != null) {
            project.setDisplayOrder(req.getDisplayOrder());
        }

        Project savedProject = projectRepository.save(project);

        return ResumeMapper.toProjectResponse(savedProject);
    }

    @Override
    public void deleteProject(
            Long projectId,
            Long resumeId,
            Long candidateId
    ) throws Exception {

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new Exception("project not found"));

        assertOwner(project.getResume(), candidateId);

        if (!project.getResume().getId().equals(resumeId)) {
            throw new Exception("Project does not belong to this resume");
        }

        projectRepository.delete(project);
    }
}
