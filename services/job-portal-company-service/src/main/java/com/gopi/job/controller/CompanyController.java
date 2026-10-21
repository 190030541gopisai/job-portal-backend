package com.gopi.job.controller;

import com.gopi.job.domain.CompanyStatus;
import com.gopi.job.domain.CompanyType;
import com.gopi.job.domain.IndustryType;
import com.gopi.job.dto.ApiResponse;
import com.gopi.job.dto.CompanyRequest;
import com.gopi.job.dto.CompanyResponse;
import com.gopi.job.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<CompanyResponse> createCompany(
            @RequestHeader("X-User-Id") Long ownerId,
            @Valid @RequestBody CompanyRequest request) throws Exception {
        return ResponseEntity.status(HttpStatus.CREATED).body(companyService.createCompany(ownerId, request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompanyById(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getCompanyById(id));
    }


    @GetMapping("/my")
    public ResponseEntity<CompanyResponse> getMyCompany(
            @RequestHeader("X-User-Id") Long ownerId
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getMyCompany(ownerId));
    }

    @GetMapping
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
            @RequestParam(required = false) CompanyType companyType,
            @RequestParam(required = false) IndustryType industryType,
            @RequestParam(required = false) CompanyStatus status
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.getAllCompanies(companyType, industryType, status));
    }

    @PutMapping("{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId,
            @RequestBody @Valid CompanyRequest request
    ) throws Exception {
      return ResponseEntity.status(HttpStatus.OK).body(companyService.updateCompany(id, ownerId, request));
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<CompanyResponse> verifyCompany(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.verifyCompany(id));
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<CompanyResponse> deactivateCompany(
            @PathVariable Long id
    ) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(companyService.deactivateCompany(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCompany(
            @PathVariable Long id,
            @RequestHeader("X-User-Id") Long ownerId
    ) throws Exception {
        companyService.deleteCompany(id, ownerId);
        return ResponseEntity.ok(new ApiResponse("Company deleted successfully", true));
    }
}
