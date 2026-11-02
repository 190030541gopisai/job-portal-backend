package com.gopi.job.payload;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AiTextResponse {

    private String content;

    @Builder.Default
    private LocalDateTime generatedAt = LocalDateTime.now();
}
