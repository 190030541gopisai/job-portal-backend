package com.gopi.job.payload;

import com.gopi.job.domain.AiShortListStatus;
import com.gopi.job.domain.ApplicationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyApplicationFilterRequest {

    private Long jobId;

    private ApplicationStatus status;

    private Boolean isStarred;

    private AiShortListStatus aiShortListStatus;

    private Integer minAiScore;

    private String sortBy;
}