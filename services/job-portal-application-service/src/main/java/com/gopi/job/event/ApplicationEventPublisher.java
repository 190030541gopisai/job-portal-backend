package com.gopi.job.event;


import com.gopi.job.client.CompanyClient;
import com.gopi.job.client.JobClient;
import com.gopi.job.client.UserClient;
import com.gopi.job.domain.ApplicationStatus;
import com.gopi.job.dto.CompanyResponse;
import com.gopi.job.dto.JobResponse;
import com.gopi.job.dto.response.UserResponse;
import com.gopi.job.modal.Application;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class ApplicationEventPublisher {

    public static final String TOPIC = "application.status.changed";

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final UserClient userClient;
    private final JobClient jobClient;
    private final CompanyClient companyClient;

    public void publishStatusChange(
            Application app,
            ApplicationStatus oldStatus,
            ApplicationStatus newStatus,
            String note
    ) {

       try{
           UserResponse candidate = userClient.getUserById(app.getCandidateId());

           JobResponse job = jobClient.getJobById(app.getJobId());

           CompanyResponse company = companyClient.getCompanyById(app.getCompanyId());

           ApplicationStatusChangedEvent event = ApplicationStatusChangedEvent.builder()
                   .applicationId(app.getId())
                   .candidateId(app.getCandidateId())
                   .candidateEmail(candidate.getEmail())
                   .candidateName(candidate.getFullName())
                   .oldStatus(oldStatus)
                   .newStatus(app.getStatus())
                   .note(note)
                   .jobTitle(job.getTitle())
                   .companyName(company.getName())
                   .changedAt(LocalDateTime.now())
                   .build();

           kafkaTemplate.send(TOPIC, String.valueOf(app.getId()), event);

       } catch (Exception e){
           System.out.println("Error in publishStatusChange ===================== " + e.getMessage());
       }
    }

}