package com.gopi.job.service.impl;

import com.gopi.job.domain.CompanyStatus;
import com.gopi.job.domain.CompanyType;
import com.gopi.job.domain.IndustryType;
import com.gopi.job.dto.CompanyRequest;
import com.gopi.job.dto.CompanyResponse;
import com.gopi.job.dto.SocialLinkResponse;
import com.gopi.job.mapper.CompanyMapper;
import com.gopi.job.modal.Company;
import com.gopi.job.modal.SocialLink;
import com.gopi.job.repository.CompanyRepository;
import com.gopi.job.service.CompanyService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    @Override
    public CompanyResponse createCompany(Long ownerId, CompanyRequest request) throws Exception {
        if (companyRepository.existsByOwnerId(ownerId)) {
            throw new Exception("You already have a company registered. Only one company per account is allowed");
        }

        if (companyRepository.existsByName(request.getName())) {
            throw new Exception("Company already exists. Please choose a different name");
        }

        if (request.getRegistrationNumber() != null &&
                companyRepository.existsByRegistrationNumber(request.getRegistrationNumber())) {
            throw new Exception("Company already exists. Please choose a different registration number");
        }

        String slug = generateUniqueSlug(request.getName());

        Company company = Company.builder()
                .name(request.getName())
                .slug(slug)
                .tagline(request.getTagline())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .coverImageUrl(request.getCoverImageUrl())
                .website(request.getWebsite())
                .email(request.getEmail())
                .phone(request.getPhone())
                .foundedYear(request.getFoundedYear())
                .companySize(request.getCompanySize())
                .companyType(request.getCompanyType())
                .industryType(request.getIndustryType())
                .registrationNumber(request.getRegistrationNumber())
                .ownerId(ownerId)
                .socialLinks(mapSocialLinks(request.getSocialLinks()))
                .build();

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    private List<SocialLink> mapSocialLinks(List<SocialLinkResponse> socialLinks) {
        if (socialLinks == null || socialLinks.isEmpty()) {
            return Collections.emptyList();
        }

        return socialLinks
                .stream()
                .map(e ->
                        SocialLink
                                .builder()
                                .platform(e.getPlatform())
                                .url(e.getUrl())
                                .build()
                ).collect(Collectors.toList());
    }

    private String generateUniqueSlug(String name) {
        String base = name.toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .trim().replaceAll("[\\s-]", "-");

        if (!companyRepository.existsBySlug(base)) {
            return base;
        }

        int counter = 1;

        while (companyRepository.existsBySlug(base + "-" + counter)) {
            counter++;
        }

        return base + "-" + counter;
    }

    @Override
    public CompanyResponse getCompanyById(Long id) throws Exception {
        Company company = companyRepository.findById(id).orElseThrow(
                () -> new Exception("company not found with id")
        );

        return CompanyMapper.toResponse(company);
    }

    @Override
    public CompanyResponse getMyCompany(Long ownerId) throws Exception {
        Company company = companyRepository.findByOwnerId(ownerId).orElseThrow(
                () -> new Exception("company not found for owner " + ownerId)
        );

        return CompanyMapper.toResponse(company);
    }

    @Override
    public List<CompanyResponse> getAllCompanies(CompanyType companyType, IndustryType industryType, CompanyStatus companyStatus) {
        return companyRepository.findByFilters(
                        companyType,
                        industryType,
                        companyStatus)
                .stream()
                .map(CompanyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request) throws Exception {
        Company company = getCompanyEntityById(companyId);

        if (!company.getName().equals(request.getName())
                && companyRepository.existsByName(request.getName())
        ) {
            throw new Exception("Company already exists. Please choose a different name.");
        }

        if (request.getRegistrationNumber() != null
                && !request.getRegistrationNumber().equals(company.getRegistrationNumber())
                && companyRepository.existsByRegistrationNumber(request.getRegistrationNumber())
        ) {
            throw new Exception("Company already exists. Please choose a different registration number.");
        }

        company.setName(request.getName());
        company.setTagline(request.getTagline());
        company.setDescription(request.getDescription());
        company.setLogoUrl(request.getLogoUrl());
        company.setCoverImageUrl(request.getCoverImageUrl());
        company.setWebsite(request.getWebsite());
        company.setEmail(request.getEmail());
        company.setPhone(request.getPhone());
        company.setFoundedYear(request.getFoundedYear());
        company.setCompanySize(request.getCompanySize());
        company.setCompanyType(request.getCompanyType());
        company.setIndustryType(request.getIndustryType());
        company.setRegistrationNumber(request.getRegistrationNumber());
        company.setSocialLinks(mapSocialLinks(request.getSocialLinks()));

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public CompanyResponse verifyCompany(Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);

        company.setStatus(CompanyStatus.ACTIVE);
        company.setVerified(true);

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long companyId, Long ownerId) throws Exception {
        Company company = getCompanyEntityById(companyId);
        assertOwner(company, ownerId);
        companyRepository.deleteById(companyId);
    }

    private void assertOwner(Company company, Long ownerId) throws Exception {
        if (!company.getOwnerId().equals(ownerId)) {
            throw new Exception("you are not the owner of this company");
        }
    }

    @Override
    public CompanyResponse deactivateCompany(Long companyId) throws Exception {
        Company company = getCompanyEntityById(companyId);

        company.setStatus(CompanyStatus.SUSPENDED);

        return CompanyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public Company getCompanyEntityById(Long id) throws Exception {
        return companyRepository.findById(id).orElseThrow(
                () -> new Exception("Company not found")
        );
    }
}
