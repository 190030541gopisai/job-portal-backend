package com.gopi.job.dto;

import com.gopi.job.domain.ApplicationStatus;
import com.gopi.job.dto.response.UserResponse;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {

    private Long id;
    private UserResponse candidate;
    private Long employerId;

    private JobResponse job;
    private CompanyResponse company;

    private ApplicationStatus status;
    private Boolean isStarred;
    private String withdrawReason;

    // Submission content
    private Long resumeId;
    private String coverLetter;

    // Candidate preferences
    private BigDecimal expectedSalary;
    private LocalDate availableFrom;

    // TODO
    private List<ApplicationNoteResponse> notes;

    // Withdrawal
    private LocalDateTime withdrawnAt;
    private String withdrawnReason;

    private LocalDateTime appliedAt;
    private LocalDateTime updatedAt;

    // AI screening result — null until background scoring completes
    // private ApplicationScreeningResponse screening;
}
