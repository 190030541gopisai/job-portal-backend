package com.gopi.job.dto;

import com.gopi.job.domain.LanguageProficiency;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LanguageResponse {
    private Long id;
    private String languageName;
    private LanguageProficiency proficiency;
    private Integer displayOrder;
}
