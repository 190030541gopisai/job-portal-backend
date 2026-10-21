package com.gopi.job.dto;

import com.gopi.job.domain.CompanySize;
import com.gopi.job.domain.CompanyStatus;
import com.gopi.job.domain.CompanyType;
import com.gopi.job.domain.IndustryType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CompanyResponse {
    private Long id;
    private String name;
    private String slug;
    private String tagline;
    private String description;
    private String logoUrl;
    private String coverImageUrl;
    private String website;
    private String email;
    private String phone;
    private Integer foundedYear;
    private CompanySize companySize;
    private CompanyType companyType;
    private IndustryType industryType;
    private CompanyStatus status;
    private String registrationNumber;
    private Long ownerId;
    private List<SocialLinkResponse> socialLinks;
    private Boolean active;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
