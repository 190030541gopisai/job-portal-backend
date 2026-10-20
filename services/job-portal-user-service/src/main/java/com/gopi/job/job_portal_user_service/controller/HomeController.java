package com.gopi.job.job_portal_user_service.controller;

import com.gopi.job.domain.UserRole;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping
    public String homeController() {
        return "Job Portal User Service -------- " + UserRole.ROLE_JOB_SEEKER;
    }
}
