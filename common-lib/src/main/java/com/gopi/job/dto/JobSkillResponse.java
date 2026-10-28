package com.gopi.job.dto;

import com.gopi.job.domain.SkillCategory;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobSkillResponse {
    private Long id;
    private String name;
    private String slug;
    private SkillCategory category;
    private Boolean active;
}