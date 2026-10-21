package com.gopi.job.repository;

import com.gopi.job.domain.CompanyStatus;
import com.gopi.job.domain.CompanyType;
import com.gopi.job.domain.IndustryType;
import com.gopi.job.modal.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    Optional<Company> findByOwnerId(Long ownerId);

    boolean existsByOwnerId(Long ownerId);

    boolean existsByName(String name);

    boolean existsBySlug(String slug);

    boolean existsByRegistrationNumber(String registrationNumber);

    @Query("""
            SELECT c from Company c where 
                        (c.companyType=:companyType or c.companyType IS NULL) AND 
                        (c.industryType=:industryType or c.industryType IS NULL) AND 
                        (c.status=:status or c.status IS NULL)
            """)
    List<Company> findByFilters(
            @Param("companyType") CompanyType companyType,
            @Param("industryType") IndustryType industryType,
            @Param("status") CompanyStatus status
    );
}
