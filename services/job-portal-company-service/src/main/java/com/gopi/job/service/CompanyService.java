package com.gopi.job.service;

import com.gopi.job.domain.CompanyStatus;
import com.gopi.job.domain.CompanyType;
import com.gopi.job.domain.IndustryType;
import com.gopi.job.dto.CompanyRequest;
import com.gopi.job.dto.CompanyResponse;
import com.gopi.job.modal.Company;

import java.util.List;

public interface CompanyService {
    CompanyResponse createCompany(Long ownerId, CompanyRequest request) throws Exception;

    CompanyResponse getCompanyById(Long id) throws Exception;

    CompanyResponse getMyCompany(Long ownerId) throws Exception;

    List<CompanyResponse> getAllCompanies(
            CompanyType companyType,
            IndustryType industryType,
            CompanyStatus companyStatus
    );

    CompanyResponse updateCompany(Long companyId, Long ownerId, CompanyRequest request) throws Exception;
    CompanyResponse verifyCompany(Long companyId) throws Exception;
    void deleteCompany(Long companyId, Long ownerId) throws Exception;
    CompanyResponse deactivateCompany(Long companyId) throws Exception;

    // intersive call only
    Company getCompanyEntityById(Long id) throws Exception;
}
