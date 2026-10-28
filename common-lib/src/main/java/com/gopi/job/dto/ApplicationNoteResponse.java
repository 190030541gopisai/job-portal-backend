package com.gopi.job.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationNoteResponse {
    private Long id;
    private Long addedByUserId;
    private String content;
    private LocalDateTime createdAt;
}
