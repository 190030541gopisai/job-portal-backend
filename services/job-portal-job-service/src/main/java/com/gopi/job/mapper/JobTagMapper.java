package com.gopi.job.mapper;

import com.gopi.job.dto.JobTagResponse;
import com.gopi.job.modal.JobTag;

public class JobTagMapper {

    public static JobTagResponse toTagResponse(JobTag jobTag) {
        return JobTagResponse.builder()
                .id(jobTag.getId())
                .name(jobTag.getName())
                .slug(jobTag.getSlug())
                .build();
    }
}