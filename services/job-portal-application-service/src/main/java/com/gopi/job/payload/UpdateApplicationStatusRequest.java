package com.gopi.job.payload;

import com.gopi.job.domain.ApplicationStatus;
import lombok.Data;

@Data
public class UpdateApplicationStatusRequest {
    private ApplicationStatus status;
}
