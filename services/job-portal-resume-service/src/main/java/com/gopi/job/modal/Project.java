package com.gopi.job.modal;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Resume resume;

    @Column(nullable = false)
    private String title;

    private String description;

    @ElementCollection(fetch = FetchType.EAGER)
    @Builder.Default
    private List<String> technologies = new ArrayList<>();

    private String projectUrl;

    private String sourceCodeUrl;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(nullable = false)
    @Builder.Default
    private Boolean isOngoing = false;

    private Integer displayOrder;
}