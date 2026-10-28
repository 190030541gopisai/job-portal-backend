package com.gopi.job.modal;


import com.gopi.job.domain.AiShortListStatus;
import com.gopi.job.domain.ApplicationStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private Long candidateId;

    @Column(nullable = false)
    private Long jobId;

    @Column(nullable = false)
    private Long companyId;

    @Column(nullable = false)
    private Long employerId;

    @Column(nullable = false)
    private Long resumeId;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ApplicationStatus status = ApplicationStatus.PENDING;

    private String coverLetter;

    private BigDecimal expectedSalary;

    private LocalDate availableFrom;

    private Boolean isStarred = false;

    @Column
    private Integer aiScore;

    private AiShortListStatus aiShortListStatus;

    @Column
    private LocalDateTime withdrawnAt;

    private String withdrawReason;

    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime appliedAt;

    @Column(nullable = false)
    @UpdateTimestamp
    private LocalDateTime updatedAt;
}