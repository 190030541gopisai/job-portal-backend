package com.gopi.job.controller;

import com.gopi.job.payload.JobAlertSuggestRequest;
import com.gopi.job.payload.JobAlertSuggestResponse;
import com.gopi.job.payload.SearchEnhanceRequest;
import com.gopi.job.payload.SearchEnhanceResponse;
import com.gopi.job.service.SearchAiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/ai/search")
public class AiSearchController {

    private final SearchAiService searchAiService;

    @PostMapping("/enhance")
    public ResponseEntity<SearchEnhanceResponse> enhanceSearch(
            @Valid @RequestBody SearchEnhanceRequest request) throws Exception {

        return ResponseEntity.ok(
                searchAiService.enhanceSearch(request)
        );
    }

    @PostMapping("/alert-suggestion")
    public ResponseEntity<JobAlertSuggestResponse> suggestAlertCriteria(
            @Valid @RequestBody JobAlertSuggestRequest request) throws Exception {

        return ResponseEntity.ok(
                searchAiService.suggestJobAlertCriteria(request)
        );
    }
}