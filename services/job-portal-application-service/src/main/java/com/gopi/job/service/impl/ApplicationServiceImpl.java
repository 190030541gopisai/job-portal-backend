package com.gopi.job.service.impl;

import com.gopi.job.domain.ApplicationStatus;
import com.gopi.job.dto.ApplicationResponse;
import com.gopi.job.dto.CompanyResponse;
import com.gopi.job.dto.JobResponse;
import com.gopi.job.dto.response.UserResponse;
import com.gopi.job.mapper.ApplicationMapper;
import com.gopi.job.modal.Application;
import com.gopi.job.modal.ApplicationNote;
import com.gopi.job.payload.CompanyApplicationFilterRequest;
import com.gopi.job.payload.CreateApplicationRequest;
import com.gopi.job.payload.WithdrawApplicationRequest;
import com.gopi.job.repository.ApplicationNoteRepository;
import com.gopi.job.repository.ApplicationRepository;
import com.gopi.job.repository.ApplicationSpecification;
import com.gopi.job.service.ApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    private final ApplicationRepository applicationRepository;
    private final ApplicationNoteRepository applicationNoteRepository;

    @Override
    public ApplicationResponse createApplication(Long candidateId, CreateApplicationRequest req) throws Exception {
        if (applicationRepository.existsByCandidateIdAndJobId(candidateId, req.getJobId())) {
            throw new Exception("you have already applied");
        }

        Long companyId = 1L;
        Long employeeId = 1L;

        // todo: fetch job
        // todo: fetch resume

        Application application = ApplicationMapper.toEntity(
                req,
                candidateId,
                companyId,
                employeeId
        );

        Application savedApplication = applicationRepository.save(application);

        // todo: AI screening runs in a background thread, no callback needed

        return buildFullResponse(savedApplication);
    }

    public ApplicationResponse buildFullResponse(Application application) {
        // todo: fetch real data from respective microservice
        JobResponse job = JobResponse.builder().id(application.getId()).build();

        CompanyResponse company = CompanyResponse.builder()
                .id(application.getCompanyId())
                .build();

        UserResponse candidate = UserResponse
                .builder()
                .id(application.getCandidateId())
                .build();

        List<ApplicationNote> notes = applicationNoteRepository.findByApplicationId(application.getId());


        return ApplicationMapper.toResponse(
                application,
                notes,
                job,
                company,
                candidate
        );
    }

    @Override
    public ApplicationResponse getApplicationById(Long id) {
        return null;
    }

    @Override
    public List<ApplicationResponse> getMyApplications(Long candidateId) {
        return applicationRepository
                .findByCandidateId(candidateId)
                .stream().map(
                        this::buildFullResponse
                ).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationsForJob(Long jobId) {
        return applicationRepository.findByJobId(jobId)
                .stream()
                .map(
                        this::buildFullResponse
                ).toList();
    }

    @Override
    public List<ApplicationResponse> getApplicationsForCompany(Long userId,
                                                               CompanyApplicationFilterRequest filter) {
        // todo: fetch company by ownerId
        Long companyId = 1L;
        Sort sort = buildSort(filter.getSortBy());
        return applicationRepository.findAll(
                        ApplicationSpecification.forCompanyWithFilters(
                                companyId,
                                filter.getJobId(),
                                filter.getStatus(),
                                filter.getIsStarred(),
                                filter.getAiShortListStatus(),
                                filter.getMinAiScore()
                        )
                )
                .stream()
                .map(this::buildFullResponse)
                .toList();
    }

    private Sort buildSort(String sortBy) {
        if ("AI_SCORE_DESC".equals(sortBy)) {
            return Sort.by(Sort.Order.desc("aiScore")
                    .with(Sort.NullHandling.NULLS_LAST));
        }
        else if("AI_SCORE_ASC".equals(sortBy)) {
            return Sort.by(Sort.Order.asc("aiScore")
                    .with(Sort.NullHandling.NULLS_LAST));
        }

        return Sort.by(Sort.Direction.DESC, "appliedAt");
    }

    @Override
    public ApplicationResponse updateStatus(Long applicationId, Long employerId, ApplicationStatus status) throws Exception {
        Application application = getApplicationEntity(applicationId);

        assertEmployer(application, employerId);

        if(application.getStatus() == ApplicationStatus.WITHDRAWN) {
            throw new Exception("candidate have already withdrawn");
        }

        application.setStatus(status);

        Application savedApplication = applicationRepository.save(application);

        return buildFullResponse(savedApplication);
    }

    private void assertEmployer(Application application, Long employerId) throws Exception {
        if(!application.getEmployerId().equals(employerId)) {
            throw new Exception("you are not the employer for this application");
        }
    }

    @Override
    public ApplicationResponse withdraw(Long applicationId, Long candidateId, WithdrawApplicationRequest req) throws Exception {
        Application application = getApplicationEntity(applicationId);

        assertCandidate(application, candidateId);

        application.setStatus(ApplicationStatus.WITHDRAWN);

        Application savedApplication = applicationRepository.save(application);

        return buildFullResponse(savedApplication);
    }

    private void assertCandidate(Application application, Long candidateId) throws Exception {
        if(!application.getCandidateId().equals(candidateId)) {
            throw new Exception("you are not the owner of this application");
        }
    }

    @Override
    public ApplicationResponse toggleStar(Long applicationId, Long employerId) throws Exception {
        Application application = getApplicationEntity(applicationId);

        assertEmployer(application, employerId);

        application.setIsStarred(!application.getIsStarred());

        Application savedApplication = applicationRepository.save(application);

        return buildFullResponse(savedApplication);
    }

    @Override
    public void deleteApplication(Long applicationId, Long candidateId) throws Exception {
        Application application = getApplicationEntity(applicationId);

        assertCandidate(application, candidateId);

        applicationRepository.delete(application);
    }

    @Override
    public Application getApplicationEntity(Long id) throws Exception {
        return applicationRepository.findById(id)
                .orElseThrow(
                        () -> new Exception("application not found")
                );
    }
}
