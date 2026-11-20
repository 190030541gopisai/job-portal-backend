package com.gopi.job.client;

import com.gopi.job.dto.CompanyResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "job-portal-company-service")
public interface CompanyClient {

    @GetMapping("/api/companies/{id}")
    CompanyResponse getCompanyById(@PathVariable("id") Long id);
}
