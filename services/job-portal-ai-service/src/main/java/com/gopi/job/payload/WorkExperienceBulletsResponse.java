package com.gopi.job.payload;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WorkExperienceBulletsResponse {
    private List<String> bullets;
}
