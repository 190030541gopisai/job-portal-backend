package com.gopi.job.mapper;

import com.gopi.job.dto.CompanyResponse;
import com.gopi.job.dto.SocialLinkResponse;
import com.gopi.job.modal.Company;
import com.gopi.job.modal.SocialLink;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyMapper {
    public static SocialLinkResponse toSocialLinkResponse(SocialLink socialLink) {
        return SocialLinkResponse.builder()
                .platform(socialLink.getPlatform())
                .url(socialLink.getUrl())
                .build();
    }

    public static CompanyResponse toResponse(Company company) {
        List<SocialLinkResponse> socialLinks = company.getSocialLinks() == null ? Collections.emptyList() :
                company.getSocialLinks().stream().map(CompanyMapper::toSocialLinkResponse)
                        .collect(Collectors.toList());

        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .slug(company.getSlug())
                .tagline(company.getTagline())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .coverImageUrl(company.getCoverImageUrl())
                .website(company.getWebsite())
                .email(company.getEmail())
                .phone(company.getPhone())
                .foundedYear(company.getFoundedYear())
                .companySize(company.getCompanySize())
                .companyType(company.getCompanyType())
                .industryType(company.getIndustryType())
                .status(company.getStatus())
                .active(company.getActive())
                .ownerId(company.getOwnerId())
                .socialLinks(socialLinks)
                .createdAt(company.getCreatedAt())
                .updatedAt(company.getUpdatedAt())
                .build();

    }
}
